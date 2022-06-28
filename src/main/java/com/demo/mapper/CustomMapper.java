package com.demo.mapper;

import com.demo.entity.ContentEntity;
import com.demo.entity.CountryEntity;
import com.demo.entity.PrerollEntity;
import com.demo.entity.VideoEntity;
import com.demo.model.MockedDataDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomMapper {

    public PrerollEntity toEntity(final MockedDataDTO.Preroll preroll) {
        final PrerollEntity prerollEntity = new PrerollEntity();
        prerollEntity.setPrerollName(preroll.getName());
        return prerollEntity;
    }

    public ContentEntity toEntity(final MockedDataDTO.Content content) {
        final ContentEntity contentEntity = new ContentEntity();
        contentEntity.setName(content.getName());
        return contentEntity;
    }

    public VideoEntity toEntity(final MockedDataDTO.Video video) {
        final VideoEntity videoEntity = new VideoEntity();
        videoEntity.setVideoId(video.getName());
        videoEntity.setAspectRatio(video.getAttributes().getAspect());
        videoEntity.setLanguage(video.getAttributes().getLanguage());
        return videoEntity;
    }

    public List<CountryEntity> buildCountryEntity(final List<String> countryIds) {
        return countryIds.stream().map(id -> {
            final CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCountryName(id);
            return countryEntity;
        }).collect(Collectors.toList());


    }
}
