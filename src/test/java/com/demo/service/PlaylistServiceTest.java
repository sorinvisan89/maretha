package com.demo.service;

import com.demo.mapper.CustomMapper;
import com.demo.model.PlaylistDTO;
import com.demo.repository.ContentRepository;
import com.demo.repository.CountryRepository;
import com.demo.repository.PrerollRepository;
import com.demo.repository.VideoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class PlaylistServiceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private PrerollRepository prerollRepository;


    private PlaylistService playlistService;


    @BeforeEach
    public void init() {
        playlistService = new PlaylistService(objectMapper, customMapper, countryRepository, videoRepository, contentRepository, prerollRepository);
    }

    @Test
    public void givenContentIdAndMatchingCountryId_WhenTwoPlayListMatch_shouldReturnExpected() {
        final List<PlaylistDTO> result = playlistService.findByContentIdAndCountry("MI3", "UK");
        MatcherAssert.assertThat(result, hasSize(2));

        final PlaylistDTO first = result.get(0);
        MatcherAssert.assertThat(first.getId(), equalTo(1));
        MatcherAssert.assertThat(first.getVideoIds(), equalTo(Arrays.asList("V6", "V2")));

        final PlaylistDTO second = result.get(1);
        MatcherAssert.assertThat(second.getId(), equalTo(2));
        MatcherAssert.assertThat(second.getVideoIds(), equalTo(Arrays.asList("V7", "V3")));
    }

    @Test
    public void givenContentIdButNoMatchingCountryId_whenNoPlaylistMatches_shouldReturnExpected() {
        final List<PlaylistDTO> result = playlistService.findByContentIdAndCountry("MI3", "RO");
        MatcherAssert.assertThat(result, hasSize(0));
    }

    @Test
    public void givenNoMatchingContentId_whenNoPlaylistMatches_shouldReturnExpected() {
        final List<PlaylistDTO> result = playlistService.findByContentIdAndCountry("Not-a-match", "UK");
        MatcherAssert.assertThat(result, hasSize(0));
    }

    @Test
    public void givenContentIdAndMatchingCountryId_whenOnlyOnePlaylistMatches_shouldReturnExpected() {
        final List<PlaylistDTO> result = playlistService.findByContentIdAndCountry("MI3", "CA");
        MatcherAssert.assertThat(result, hasSize(1));
        final PlaylistDTO actualPlaylist = result.get(0);
        MatcherAssert.assertThat(actualPlaylist.getId(), equalTo(1));
        MatcherAssert.assertThat(actualPlaylist.getVideoIds(), equalTo(Arrays.asList("V5", "V1")));
    }
}
