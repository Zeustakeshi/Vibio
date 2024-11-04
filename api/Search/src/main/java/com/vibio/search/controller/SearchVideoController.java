/*
 *  SearchController
 *  @author: Minhhieuano
 *  @created 11/1/2024 12:08 AM
 * */


package com.vibio.search.controller;

import com.vibio.search.dto.response.ApiResponse;
import com.vibio.search.service.SearchVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class SearchVideoController {

    private final SearchVideoService searchVideoService;


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> searchVideo(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        return ApiResponse.success(searchVideoService.searchVideo(query, page, limit));
    }

    @GetMapping("/autocomplete")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> videoAutocomplete(@RequestParam("query") String query) {
        return ApiResponse.success(searchVideoService.searchVideoAutoComplete(query));
    }
}

