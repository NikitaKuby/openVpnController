package com.server.test.testPost.service;

import com.google.gson.Gson;
import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationClients {

    private String responseJson;

    public ValidationClients(String data) {
        if(data.contains("There are no existing clients!")){
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setError("No clients");
            ResponseDto responseDto = new ResponseDto(false, resultDTO);

            responseJson = new Gson().toJson(responseDto);

        }else {
            validate(new StringBuilder(data.replace("  ", "")));
        }
    }



    private void validate(StringBuilder dataSource){
        dataSource.delete(0, dataSource.indexOf("1)"))
                .delete(dataSource.indexOf("Total"),dataSource.length());

        List<String> clients = new ArrayList<>();

        String[] words = dataSource.toString().split(" ");

        for (String word : words) {
            if (!word.contains(")")) {
                if (!word.contains(" ")) {
                    clients.add(word);
                }
            }
        }
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setClients(clients);
        ResponseDto responseDto = new ResponseDto(true, resultDTO);

        responseJson = new Gson().toJson(responseDto);
    }
}
