FROM openjdk:24-slim-bullseye
WORKDIR /root
RUN apt update && apt upgrade -y
RUN apt install openssh-server -y
RUN  apt install vim -y

ENV VPN_SSH_SERVER_IP="172.17.0.3"
ENV VPN_SSH_SERVER_PORT="22"
ENV VPN_SSH_USERNAME="root"
ENV VPN_SSH_PATH_PRIVATE_KEY="/root/.ssh/id_rsa"
COPY id_rsa /root/.ssh/id_rsa
RUN chmod 400 ~/.ssh/id_rsa
COPY /target/testPost-0.0.2.jar /root/testPost.jar
ENTRYPOINT ["java","-jar","testPost.jar"]
EXPOSE 8081