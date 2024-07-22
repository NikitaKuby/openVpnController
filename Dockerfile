FROM openjdk:24-slim-bullseye
WORKDIR /root

#установка
RUN apt update && apt upgrade -y
RUN apt install openssh-server -y

#Конфигурация ENV
ENV VPN_SSH_SERVER_IP="172.17.0.3"
ENV VPN_SSH_SERVER_PORT="22"
ENV VPN_SSH_USERNAME="root"
ENV VPN_SSH_PATH_PRIVATE_KEY="/root/.ssh/id_rsa"

COPY /target/testPost-0.0.3.jar /root/controllerOvpn.jar

#запускаем приложение
ENTRYPOINT ["java","-jar","controllerOvpn.jar"]
EXPOSE 8081