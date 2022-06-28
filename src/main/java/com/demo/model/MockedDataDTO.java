package com.demo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MockedDataDTO {

    List<Content> content = new ArrayList<>();

    List<Preroll> preroll = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Content {

        String name;

        List<PrerollName> preroll = new ArrayList<>();

        List<Video> videos = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Preroll {

        String name;

        @Builder.Default
        List<Video> videos = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PrerollName {

        String name;
    }

    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Video {

        String name;

        VideoAttributes attributes;

        @Data
        @NoArgsConstructor
        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class VideoAttributes {

            String language;

            String aspect;

            List<String> countries = new ArrayList<>();
        }
    }
}
