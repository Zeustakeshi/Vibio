/*
 *  ApiResponse
 *  @author: Minhhieuano
 *  @created 9/19/2024 2:32 PM
 * */

package com.vibio.search.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean isSuccess;
    private T data;
    private Object errors;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .isSuccess(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(Object errors) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
