package com.server.test.testPost.service;

import com.google.gson.Gson;
import com.server.test.testPost.config.ConfigSSH;
import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class SessionSSH {
    private final ConfigSSH configSSH;
    @Autowired
    public SessionSSH(ConfigSSH configSSH) {
        this.configSSH = configSSH;
    }

    public ResponseDto getResponse(String command) throws IOException {
            String outputResponse = executeCommand(connectionToSSH(), command);
            ResultDTO outputResurces = new ResultDTO();
            outputResurces.setReturnText(outputResponse);
            return new ResponseDto(true, outputResurces);
    }

    public SSHClient connectionToSSH() throws IOException {
            SSHClient client = new SSHClient();
            File privateKeyFile = new File(configSSH.getEnvPrivateKey());
            client.addHostKeyVerifier(new PromiscuousVerifier());
            KeyProvider privateKey = client.loadKeys(privateKeyFile.getPath());
            client.connect(configSSH.getEnvHost(), configSSH.getEnvPort());
            client.authPublickey(configSSH.getEnvUsername(), privateKey);
            return client;

    }

    public void disconnectFromSSH(SSHClient client) throws IOException {
        client.disconnect();
    }

    public String executeCommand(SSHClient client, String command) throws IOException {
        StringBuilder outputResponse = new StringBuilder();
        try {
            Session.Command cmd = client.startSession().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                outputResponse.append(line);
            }
        }finally {
            disconnectFromSSH(client);
        }

        return outputResponse.toString();
    }

    public String formatString(String data){
        if(data.contains("There are no existing clients!")){
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setError("No clients");
            ResponseDto responseDto = new ResponseDto(false, resultDTO);

            return new Gson().toJson(responseDto);

        }else {
            StringBuilder dataSource = new StringBuilder(data.replace("  ", ""));
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

            return new Gson().toJson(responseDto);
        }

    }


}




