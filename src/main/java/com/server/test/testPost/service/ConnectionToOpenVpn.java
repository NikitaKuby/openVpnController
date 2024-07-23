package com.server.test.testPost.service;

import com.server.test.testPost.dto.ResponseDto;
import net.schmizz.sshj.SSHClient;

import java.io.IOException;

public interface ConnectionToOpenVpn {
    ResponseDto getResponse(String command) throws IOException;
    String formatString(String data);
    SSHClient connectionToSSH() throws IOException;
    void disconnectFromSSH(SSHClient client) throws IOException;
    String executeCommand(SSHClient client, String command) throws IOException;

}
