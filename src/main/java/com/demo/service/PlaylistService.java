package com.demo.service;

import com.demo.entity.*;
import com.demo.mapper.CustomMapper;
import com.demo.model.MockedDataDTO;
import com.demo.model.PlaylistDTO;
import com.demo.repository.ContentRepository;
import com.demo.repository.CountryRepository;
import com.demo.repository.PrerollRepository;
import com.demo.repository.VideoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@Transactional
public class PlaylistService {

    private final ObjectMapper objectMapper;
    private final CustomMapper customMapper;
    private final CountryRepository countryRepository;
    private final VideoRepository videoRepository;
    private final ContentRepository contentRepository;
    private final PrerollRepository prerollRepository;

    @Autowired
    public PlaylistService(final ObjectMapper objectMapper,
                           final CustomMapper customMapper,
                           final CountryRepository countryRepository,
                           final VideoRepository videoRepository,
                           final ContentRepository contentRepository,
                           final PrerollRepository prerollRepository) {
        this.objectMapper = objectMapper;
        this.customMapper = customMapper;
        this.countryRepository = countryRepository;
        this.videoRepository = videoRepository;
        this.contentRepository = contentRepository;
        this.prerollRepository = prerollRepository;
    }

    @PostConstruct
    public void init() {
        populateMockData();
    }

    public List<PlaylistDTO> findByContentIdAndCountry(final String contentId, final String country) {
        final Optional<ContentEntity> contentEntityOptional = contentRepository.findByName(contentId);
        if (contentEntityOptional.isEmpty()) {
            return Collections.emptyList();
        }

        final ContentEntity contentEntity = contentEntityOptional.get();
        final List<PrerollEntity> associatedPrerolls = contentEntity.getPrerolls();
        final List<String> prerollVideos = associatedPrerolls.stream()
                .map(PrerollEntity::getVideoEntities)
                .flatMap(Collection::stream)
                .filter(video -> {
                    final List<String> countries = video.getCountries().stream()
                            .map(CountryEntity::getCountryName)
                            .collect(Collectors.toList());
                    return countries.contains(country);
                })
                .map(VideoEntity::getVideoId)
                .collect(Collectors.toList());


        final List<VideoProjection> videoEntities = contentRepository.findVideosByCountryAndContentId(contentId, country);
        return IntStream
                .range(0, videoEntities.size())
                .mapToObj(index -> {
                    final VideoProjection videoProjection = videoEntities.get(index);
                    final String videoId = videoProjection.getVideoName();
                    final List<String> videoIds = new ArrayList<>();

                    if (index < prerollVideos.size()) {
                        videoIds.add(prerollVideos.get(index));
                    }
                    videoIds.add(videoId);

                    return PlaylistDTO.builder()
                            .id(index + 1)
                            .videoIds(videoIds)
                            .build();
                })
                .collect(Collectors.toList());
    }


    private void populateMockData() {
        final TypeReference<MockedDataDTO> typeReference = new TypeReference<>() {
        };
        try (InputStream inputStream = PlaylistService.class.getClassLoader().getResourceAsStream("fixture.json")) {
            final MockedDataDTO mockedData = objectMapper.readValue(inputStream, typeReference);

            final List<PrerollEntity> savedPrerolls = mockedData.getPreroll().stream().map(preroll -> {
                final PrerollEntity prerollEntity = customMapper.toEntity(preroll);

                final List<VideoEntity> savedVideos = preroll.getVideos().stream().map(video -> {
                    final List<CountryEntity> countryEntities = customMapper.buildCountryEntity(video.getAttributes().getCountries());
                    final List<CountryEntity> savedCountries = countryRepository.saveAll(countryEntities);

                    final VideoEntity videoEntity = customMapper.toEntity(video);
                    videoEntity.addCountries(savedCountries);
                    return videoRepository.save(videoEntity);
                }).collect(Collectors.toList());

                prerollEntity.addVideos(savedVideos);

                return prerollRepository.save(prerollEntity);
            }).collect(Collectors.toList());

            mockedData.getContent().forEach(content -> {
                final List<MockedDataDTO.Video> videos = content.getVideos();
                final List<VideoEntity> savedEntities = videos.stream().map(video -> {
                    final List<CountryEntity> countryEntities = customMapper.buildCountryEntity(video.getAttributes().getCountries());
                    final List<CountryEntity> savedCountries = countryRepository.saveAll(countryEntities);

                    final VideoEntity videoEntity = customMapper.toEntity(video);

                    videoEntity.addCountries(savedCountries);
                    return videoRepository.save(videoEntity);
                }).collect(Collectors.toList());

                final ContentEntity contentEntity = customMapper.toEntity(content);
                contentEntity.addVideos(savedEntities);
                final ContentEntity savedContent = contentRepository.save(contentEntity);

                final List<String> prerollNames = content.getPreroll()
                        .stream()
                        .map(MockedDataDTO.PrerollName::getName)
                        .collect(Collectors.toList());

                final List<PrerollEntity> toAssociated = new ArrayList<>();
                prerollNames.forEach(name -> {
                    toAssociated.addAll(savedPrerolls.stream()
                            .filter(preroll -> preroll.getPrerollName().equals(name))
                            .collect(Collectors.toList()));
                });

                savedContent.addPrerolls(toAssociated);
                contentRepository.save(savedContent);
            });

        } catch (IOException ex) {
            log.error("Failed to deserialize mocked data!", ex);
        }
    }

}
