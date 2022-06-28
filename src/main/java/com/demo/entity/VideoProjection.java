package com.demo.entity;

import lombok.Data;

@Data
public class VideoProjection {

    private final String videoName;

    public VideoProjection(final String videoName){
        this.videoName = videoName;
    }
}
