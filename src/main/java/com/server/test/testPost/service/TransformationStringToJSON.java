package com.server.test.testPost.service;

import com.google.gson.Gson;
import com.server.test.testPost.dto.ResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TransformationStringToJSON {

    private String responseJson;

    public TransformationStringToJSON(String data) {
        if(data.contains("There are no existing clients!")){
            responseJson="{No clients}";

        }else {
            validate(new StringBuilder(data.replace("  ", "")));
        }
    }



    private void validate(StringBuilder dataSource){
        dataSource.delete(0, dataSource.indexOf("1)"))
                .delete(dataSource.indexOf("Total"),dataSource.length());

        List<String> clients = new ArrayList<>();

        String[] words = dataSource.toString().split(" ");

        for (int i = 0; i < words.length; i++) {
            if (!words[i].contains(")")) {
                if (!words[i].contains(" ")) {
                    clients.add(words[i]);
                }
            }
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setClients(clients);

        responseJson = new Gson().toJson(responseDto);
    }
}
