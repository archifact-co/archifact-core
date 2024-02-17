package co.archifact.core;

import co.archifact.core.controller.ResourceManagementServiceController;
import co.archifact.core.service.ResourceManagementService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting Archifact server at port 8080");
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new ResourceManagementServiceController(
                                new ResourceManagementService()
                        )
                ).build();

        server.start();
        server.awaitTermination();
    }
}