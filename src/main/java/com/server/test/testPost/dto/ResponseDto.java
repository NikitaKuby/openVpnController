package com.server.test.testPost.dto;

import lombok.Data;


@Data
public class ResponseDto {
    private boolean ok;
    private ResultDTO result;

    public ResponseDto(boolean ok, ResultDTO result) {
        this.ok = ok;
        this.result = result;
    }
    public ResponseDto(boolean ok) {
        this.ok = ok;
    }
}
