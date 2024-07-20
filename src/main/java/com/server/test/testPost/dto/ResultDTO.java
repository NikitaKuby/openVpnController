package com.server.test.testPost.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    List<String> clients;
    String returnText;
    String error;

}
