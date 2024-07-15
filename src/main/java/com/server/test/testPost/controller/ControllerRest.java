package com.server.test.testPost.controller;

import com.server.test.testPost.enums.Comd;
import com.server.test.testPost.service.SessionSSH;
import com.server.test.testPost.service.TransformationStringToJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/openvpn")
public class ControllerRest {
    SessionSSH sessionSSH;

    @Autowired
    public ControllerRest(SessionSSH sessionSSH) {
        this.sessionSSH = sessionSSH;

    }

    @CrossOrigin
    @GetMapping(value = "/getClients")
    public ResponseEntity<?> getClients() throws IOException {
        String responseSSH = sessionSSH.SetCommandGetResponse("bash openvpn.sh "+ Comd.LISTCLIENTS.getTitle());
        String json = new TransformationStringToJSON(responseSSH).getResponseJson();
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/addClient/{name}")
    public ResponseEntity<?> addClients(@PathVariable String name) throws IOException {
        String responseSSH = sessionSSH.SetCommandGetResponse("bash openvpn.sh"+" "+Comd.ADDCLIENTS.getTitle()+" "+name);
        if (responseSSH.isEmpty()) {
            return new ResponseEntity<>("Пользователя с таким именем уже существует", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Пользователь "+name+" успешно добавлен", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/revokeClient/{name}")
    public ResponseEntity<?> revokeClients(@PathVariable String name) throws IOException {
        String responseSSH = sessionSSH.SetCommandGetResponse("bash openvpn.sh "+Comd.REVOKECLIENT.getTitle()
                                                                    +" "+name+" "+Comd.YES.getTitle());
        if (responseSSH.isEmpty()) {
            return new ResponseEntity<>("Пользователя с таким именем не существует", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Пользователь "+name+" успешно отозван", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/watchClient/{name}")
    public ResponseEntity<?> watchClients(@PathVariable String name) throws IOException {
        String responseSSH = sessionSSH.SetCommandGetResponse("cat "+ name+".ovpn");
        if (responseSSH.isEmpty()) {
            return new ResponseEntity<>("Пользователя с таким именем не существует", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseSSH, HttpStatus.OK);
    }


}
