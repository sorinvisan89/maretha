package com.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CONTENTS")
public class ContentEntity {

    @Id
    @Column(name = "CONTENT_NAME")
    private String name;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "CONTENT_PREROLLS",
            uniqueConstraints = @UniqueConstraint(columnNames={"CONTENT_ID", "PREROLL_ID"}),
            joinColumns = @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_NAME"),
            inverseJoinColumns = @JoinColumn(name = "PREROLL_ID", referencedColumnName = "PREROLL_NAME")
    )
    private List<PrerollEntity> prerolls = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "CONTENT_VIDEOS",
            uniqueConstraints = @UniqueConstraint(columnNames={"CONTENT_ID", "VIDEO_ID"}),
            joinColumns = @JoinColumn(name = "CONTENT_ID", referencedColumnName = "CONTENT_NAME"),
            inverseJoinColumns = @JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_NAME")
    )
    private List<VideoEntity> videos = new ArrayList<>();


    public void addVideos(final List<VideoEntity> videoEntities){
        this.videos.addAll(videoEntities);
    }

    public void addPrerolls(final List<PrerollEntity> prerolls){
        this.prerolls.addAll(prerolls);
    }
}
