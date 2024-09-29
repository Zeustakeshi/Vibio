/*
 *  VideoRepository
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:20 PM
 * */

package com.vibio.video.repository;

import com.vibio.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {}
