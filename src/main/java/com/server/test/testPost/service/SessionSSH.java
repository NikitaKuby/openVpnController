package com.server.test.testPost.service;

import com.server.test.testPost.config.ConfigSSH;
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
    public String SetCommandGetResponse(String command) throws IOException {

        StringBuilder outputResponse = new StringBuilder();

        ConfigSSH configSSH=new ConfigSSH();
        File privateKeyFile = new File(configSSH.getEnvPrivateKey());

        final SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        KeyProvider privateKey = client.loadKeys(privateKeyFile.getPath());
        client.connect(configSSH.getEnvHost(), configSSH.getEnvPort());
        client.authPublickey(configSSH.getEnvUsername(), privateKey);

        try {
            final Session session = client.startSession();
            try {
                Session.Command cmd = session.exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    outputResponse.append(line);
                }
            } finally {
                session.close();
            }
        } finally {
            client.disconnect();
        }
        return outputResponse.toString();
    }



}
