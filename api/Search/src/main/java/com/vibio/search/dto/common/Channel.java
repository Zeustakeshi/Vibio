/*
 *  Channel
 *  @author: Minhhieuano
 *  @created 11/1/2024 10:08 PM
 * */


package com.vibio.search.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    private String id;
    private String name;
    private String thumbnail;
}
