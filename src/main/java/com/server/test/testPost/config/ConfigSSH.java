package com.server.test.testPost.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ConfigSSH {

    final private String envHost = System.getenv("VPN_SSH_SERVER_IP");
    final private String envPort = System.getenv("VPN_SSH_SERVER_PORT");
    final private String envUsername = System.getenv("VPN_SSH_USERNAME");
    final private String envPrivateKey = System.getenv("VPN_SSH_PATH_PRIVATE_KEY");

    public String getEnvPrivateKey() {
        if (envPrivateKey == null) {
            return "C:/Users/nikit/.ssh/id_rsa";
        }
        return envPrivateKey;
    }


    public String getEnvHost() {
        if (envHost == null) {
            return "localhost";
        }
        return envHost;
    }

    public int getEnvPort() {
        if (envPort == null) {
            return 22;
        }
        return Integer.parseInt(envPort);
    }

    public String getEnvUsername() {
        if (envUsername == null) {
            return "root";
        }
        return envUsername;
    }
}