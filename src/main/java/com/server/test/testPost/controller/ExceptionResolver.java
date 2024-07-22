package com.server.test.testPost.controller;

import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import com.server.test.testPost.service.TransformationToJson;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<?> handleNoHandlerFound() {
        ResultDTO error = new ResultDTO();
        error.setError("API not found");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(false, error)),headers, HttpStatus.OK);

    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> ioexception() {
        ResultDTO error2 = new ResultDTO();
        error2.setError("Connection bad");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(false, error2)),headers, HttpStatus.OK);

    }
}
