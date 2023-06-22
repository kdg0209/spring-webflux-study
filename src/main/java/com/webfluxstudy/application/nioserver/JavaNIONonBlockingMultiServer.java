package com.webfluxstudy.application.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JavaNIONonBlockingMultiServer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start server main");
        try(ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);

            while (true) {
                SocketChannel clientSocket = serverSocket.accept();

                if (clientSocket == null) {
                    Thread.sleep(100);
                    log.info("wait accept");
                    continue;
                }

                CompletableFuture.runAsync(() -> {
                    handleRequest(clientSocket);
                });
            }
        }
    }

    @SneakyThrows
    private static void handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        while (clientSocket.read(requestByteBuffer) == 0) {
            log.info("Reading...");
        }

        requestByteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
        log.info("request: {}", requestBody);

        Thread.sleep(10);

        ByteBuffer responseBody = ByteBuffer.wrap("This is server".getBytes());
        clientSocket.write(responseBody);
        clientSocket.close();
    }
}
