package com.webfluxstudy.application.nioserver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JavaIoMultiClient {

    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        long start = System.currentTimeMillis();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress("localhost", 8080));

                    OutputStream outputStream = socket.getOutputStream();
                    String requestBody = "This is Client";
                    outputStream.write(requestBody.getBytes());
                    outputStream.flush();

                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    inputStream.read(bytes);

                    log.info("result: {}", new String(bytes).trim());
                } catch (Exception e) {}
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        log.info("duration: {}", (end - start) / 1000.0);
        log.info("end main");
    }
}
