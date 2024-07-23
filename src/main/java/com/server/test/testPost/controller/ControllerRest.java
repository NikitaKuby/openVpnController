package com.server.test.testPost.controller;

import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import com.server.test.testPost.enums.Comd;
import com.server.test.testPost.service.ConnectionToOpenVpn;
import com.server.test.testPost.service.TransformationToJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/openvpn")
public class ControllerRest{

    public final ConnectionToOpenVpn connectionToOpenVpn;
    public final TransformationToJson transformationToJson;


    @CrossOrigin
    @GetMapping(value = "/getClients",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClients() throws IOException {
        ResponseDto responseSSH = connectionToOpenVpn.getResponse("bash openvpn.sh " + Comd.LISTCLIENTS.getTitle());
        String listClients = connectionToOpenVpn.formatString(responseSSH.getResult().getReturnText());
        return new ResponseEntity<>(listClients, HttpStatus.OK);
        }

    @CrossOrigin
    @GetMapping(value = "/addClient/{name}",produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> addClients(@PathVariable String name) throws IOException {
        ResponseDto responseSSH = connectionToOpenVpn.getResponse("bash openvpn.sh"+" "+Comd.ADDCLIENTS.getTitle()+" "+name);

        if (responseSSH.getResult().getReturnText().isEmpty()) {
            ResultDTO error = new ResultDTO();
            error.setError("This client already exists");
            return new ResponseEntity<>(transformationToJson.transform(new ResponseDto(false,error)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(transformationToJson.transform(new ResponseDto(true)), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/revokeClient/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> revokeClients(@PathVariable String name) throws IOException {
        ResponseDto responseSSH = connectionToOpenVpn.getResponse("bash openvpn.sh "+Comd.REVOKECLIENT.getTitle()
                                                                    +" "+name+" "+Comd.YES.getTitle());
        if (responseSSH.getResult().getReturnText().isEmpty()) {
            ResultDTO error = new ResultDTO();
            error.setError("No such client found");
            return new ResponseEntity<>(transformationToJson.transform(new ResponseDto(false,error)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(transformationToJson.transform(new ResponseDto(true)), HttpStatus.OK);
        } }

    @CrossOrigin
    @GetMapping(value = "/watchClient/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> watchClients(@PathVariable String name) throws IOException {
        ResponseDto responseSSH = connectionToOpenVpn.getResponse("cat "+ name+".ovpn");
        if (responseSSH.getResult().getReturnText().isEmpty()) {
            ResultDTO error = new ResultDTO();
            error.setError("No such client found");
            return new ResponseEntity<>(transformationToJson.transform(new ResponseDto(false,error)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(transformationToJson.transform(responseSSH), HttpStatus.OK);
        }
    }

}
