package com.webfluxstudy.application.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class JavaIoClient {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        try(Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 8080));

            OutputStream outputStream = socket.getOutputStream();
            String requestBody = "This is Client";
            outputStream.write(requestBody.getBytes());
            outputStream.flush();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);

            log.info("result: {}", new String(bytes).trim());
        }

        log.info("end main");
    }
}
