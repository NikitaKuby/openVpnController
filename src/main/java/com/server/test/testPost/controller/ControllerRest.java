package com.server.test.testPost.controller;

import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import com.server.test.testPost.enums.Comd;
import com.server.test.testPost.service.SessionSSH;
import com.server.test.testPost.service.TransformationToJson;
import com.server.test.testPost.service.ValidationClients;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/openvpn")
public class ControllerRest {
    SessionSSH sessionSSH;

    @CrossOrigin
    @GetMapping(value = "/getClients",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClients() throws IOException {
        ResponseDto responseSSH = sessionSSH.SetCommandGetResponse("bash openvpn.sh " + Comd.LISTCLIENTS.getTitle());
        if (responseSSH.isOk()) {
            String json = new ValidationClients(responseSSH.getResult().getReturnText()).getResponseJson();
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            String response = new TransformationToJson().transform(responseSSH);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/addClient/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addClients(@PathVariable String name) throws IOException {
        ResponseDto responseSSH = sessionSSH.SetCommandGetResponse("bash openvpn.sh"+" "+Comd.ADDCLIENTS.getTitle()+" "+name);

        if (responseSSH.getResult().getReturnText().isEmpty()) {
            ResultDTO error = new ResultDTO();
            error.setError("This client already exists");
            return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(false,error)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(true)), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/revokeClient/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> revokeClients(@PathVariable String name) throws IOException {
        ResponseDto responseSSH = sessionSSH.SetCommandGetResponse("bash openvpn.sh "+Comd.REVOKECLIENT.getTitle()
                                                                    +" "+name+" "+Comd.YES.getTitle());
        if (responseSSH.getResult().getReturnText().isEmpty()) {
            ResultDTO error = new ResultDTO();
            error.setError("No such client found");
            return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(false,error)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(true)), HttpStatus.OK);
        } }

    @CrossOrigin
    @GetMapping(value = "/watchClient/{name}")
    public ResponseEntity<?> watchClients(@PathVariable String name) throws IOException {
        ResponseDto responseSSH = sessionSSH.SetCommandGetResponse("cat "+ name+".ovpn");
        if (responseSSH.getResult().getReturnText().isEmpty()) {
            ResultDTO error = new ResultDTO();
            error.setError("No such client found");
            return new ResponseEntity<>(new TransformationToJson().transform(new ResponseDto(false,error)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseSSH.getResult().getReturnText(), HttpStatus.OK);
        } }


}
