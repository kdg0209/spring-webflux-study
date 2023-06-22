package com.webfluxstudy.application.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIoServer {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start server main");
        try(ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            while (true) {
                Socket clientSocket = serverSocket.accept();

                byte[] requestBytes = new byte[1024];
                InputStream inputStream = clientSocket.getInputStream();
                inputStream.read(requestBytes);
                log.info("request: {}", new String(requestBytes).trim());

                OutputStream outputStream = clientSocket.getOutputStream();
                String response = "This is Server";
                outputStream.write(response.getBytes());
                outputStream.flush();
            }
        }
    }
}
