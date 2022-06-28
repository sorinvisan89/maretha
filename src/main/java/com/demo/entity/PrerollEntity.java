package com.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PREROLLS")
public class PrerollEntity {

    @Id
    @Column(name = "PREROLL_NAME")
    private String prerollName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PREROLL_VIDEOS",
            joinColumns = @JoinColumn(name = "CONTENT_ID", referencedColumnName = "PREROLL_NAME"),
            inverseJoinColumns = @JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_NAME")
    )
    private List<VideoEntity> videoEntities = new ArrayList<>();


    public void addVideos(final List<VideoEntity> videoEntities){
        this.videoEntities.addAll(videoEntities);
    }
}
