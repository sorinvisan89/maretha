package com.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "VIDEOS")
public class VideoEntity {

    @Id
    @Column(name = "VIDEO_NAME")
    private String videoId;

    @Column(name = "ASPECT_RATIO")
    private String aspectRatio;

    @Column(name = "LANGUAGE")
    private String language;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "VIDEOS_COUNTRIES",
            joinColumns = @JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_NAME"),
            inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_NAME")
    )
    private List<CountryEntity> countries = new ArrayList<>();

    public void addCountries(final List<CountryEntity> countries){
        this.countries.addAll(countries);
    }
}
