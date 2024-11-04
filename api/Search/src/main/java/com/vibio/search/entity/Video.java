/*
 *  Video
 *  @author: Minhhieuano
 *  @created 11/1/2024 10:13 PM
 * */


package com.vibio.search.entity;

import com.vibio.search.commom.enums.Visibility;
import com.vibio.search.dto.common.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "videos")
public class Video {
    private String id;
    private String title;
    private String keyword;
    private Set<String> tags;
    private String thumbnail;
    private Visibility visibility;
    private Channel channel;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

}
