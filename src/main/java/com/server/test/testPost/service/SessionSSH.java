package com.server.test.testPost.service;

import com.server.test.testPost.config.ConfigSSH;
import com.server.test.testPost.dto.ResponseDto;
import com.server.test.testPost.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


@Slf4j
@Service
public class SessionSSH {
    public ResponseDto SetCommandGetResponse(String command) throws IOException {

        StringBuilder outputResponse = new StringBuilder();
        SSHClient client = new SSHClient();

        try {
            ConfigSSH configSSH=new ConfigSSH();
            File privateKeyFile = new File(configSSH.getEnvPrivateKey());

            client.addHostKeyVerifier(new PromiscuousVerifier());
            KeyProvider privateKey = client.loadKeys(privateKeyFile.getPath());
            client.connect(configSSH.getEnvHost(), configSSH.getEnvPort());
            client.authPublickey(configSSH.getEnvUsername(), privateKey);

        }catch (IOException e){
            ResultDTO error = new ResultDTO();
            error.setError("Client ssh no connection");
            return new ResponseDto(false, error);
        }

        try {
            Session session = client.startSession();
            try {
                Session.Command cmd = session.exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    outputResponse.append(line);
                }
            }finally {
                session.close();
            }
        } finally {
            client.disconnect();
        }

        ResultDTO outputResurces = new ResultDTO();
        outputResurces.setReturnText(outputResponse.toString());
        return new ResponseDto(true, outputResurces);
    }




}
