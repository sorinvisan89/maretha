package com.demo.controller;

import com.demo.model.PlaylistDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/playlists")
@Tag(name = "Playlists", description = "The playlists")
public interface PlaylistApi {

    @GetMapping
    @Operation(description = "Search playlists by contentId and country", summary = "Search playlists",
            responses = {
                    @ApiResponse(
                            description = "Search completed successfully!",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaylistDTO.class))
                    ),
                    @ApiResponse(description = "Invalid parameters provided!", responseCode = "400"),
                    @ApiResponse(description = "Internal error", responseCode = "500")
            })

    ResponseEntity<List<PlaylistDTO>> getPlaylistByCountryAndContentId(
            @Parameter(description = "The content id to search by", required = true)
            @RequestParam(value = "contentId") final String contentId,
            @Parameter(description = "The country to search by", required = true)
            @RequestParam(value = "country") final String country);
}
