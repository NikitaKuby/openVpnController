package com.server.test.testPost.controller;

import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import com.server.test.testPost.service.impl.ConnectionToOpenVpnFromSSH;
import com.server.test.testPost.service.TransformationToJson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/openvpn/health")
public class HealthController {

    ConnectionToOpenVpnFromSSH sessionSSH;

    @CrossOrigin
    @GetMapping(value = "/availability",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAvailability(){
            return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(true)), HttpStatus.OK);
        }

    @CrossOrigin
    @GetMapping(value = "/check",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCheck() throws IOException {
        String checkScript = "test -f 'openvpn.sh' && echo yes";

        ResponseDto responseSSH = sessionSSH.getResponse(checkScript);
            if (Objects.equals(responseSSH.getResult().getReturnText(), "yes")) {
                return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(true)), HttpStatus.OK);
            }else{
                ResultDTO resultDTO = new ResultDTO();
                resultDTO.setError("Script openvpn not found");
                return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(false,resultDTO)), HttpStatus.OK);
            }
    }


}
