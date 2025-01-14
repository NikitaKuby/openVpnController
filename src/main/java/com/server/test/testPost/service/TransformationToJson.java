package com.server.test.testPost.service;

import com.google.gson.Gson;
import com.server.test.testPost.dto.ResponseDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransformationToJson {

    public String transform(ResponseDto responseDto) {
        return new Gson().toJson(responseDto);
    }

}