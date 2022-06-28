package com.demo.controller;


import com.demo.model.PlaylistDTO;
import com.demo.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class PlaylistController implements PlaylistApi {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(final PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public ResponseEntity<List<PlaylistDTO>> getPlaylistByCountryAndContentId(final String contentId, final String country) {
        final List<PlaylistDTO> result = playlistService.findByContentIdAndCountry(contentId, country);
        return ResponseEntity.ok(result);
    }

}
