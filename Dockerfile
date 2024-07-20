FROM openjdk:24-slim-bullseye
WORKDIR /root

#установка
RUN apt update && apt upgrade -y
RUN apt install openssh-server -y
RUN  apt install vim -y


#Конфигурация ENV
ENV VPN_SSH_SERVER_IP="172.17.0.3"
ENV VPN_SSH_SERVER_PORT="22"
ENV VPN_SSH_USERNAME="root"
ENV VPN_SSH_PATH_PRIVATE_KEY="/root/.ssh/id_rsa"

#Копируем jar и private_key в наш контейнер
COPY id_rsa /root/.ssh/id_rsa
COPY /target/testPost-0.0.2.jar /root/controllerOvpn.jar

#кидаем доверенность на ключ, без него ругается (ненадежный ключ и т.д)
RUN chmod 400 ~/.ssh/id_rsa

#запускаем приложение
ENTRYPOINT ["java","-jar","controllerOvpn.jar"]
EXPOSE 8081