package com.demo.repository;

import com.demo.entity.ContentEntity;
import com.demo.entity.VideoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentEntity, String> {

    @Query("SELECT NEW com.demo.entity.VideoProjection(ve.videoId) FROM ContentEntity ce " +
            "INNER JOIN ce.videos ve " +
            "INNER JOIN ve.countries c " +
            "WHERE ce.name = :name " +
            "AND c.countryName = :countryName")
    List<VideoProjection> findVideosByCountryAndContentId(
            @Param("name") final String contentId,
            @Param("countryName") final String countryName);

    Optional<ContentEntity> findByName(@Param("contentId") final String contentId);
}
