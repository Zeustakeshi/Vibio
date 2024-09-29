/*
 *  TagRespository
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:21 PM
 * */

package com.vibio.video.repository;

import com.vibio.video.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {}
