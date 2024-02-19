package co.archifact.core;

import co.archifact.core.api.ResourceManagementServiceApi;
import co.archifact.core.api.WorkspaceManagementApi;
import co.archifact.core.service.ResourceManagementService;
import co.archifact.core.service.workspace.WorkspaceService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Started Archifact server successfully");

        Server server = ServerBuilder
                .forPort(8080)
                .addService(new ResourceManagementServiceApi(
                                new ResourceManagementService()
                        )
                )
                .addService(new WorkspaceManagementApi(
                                new WorkspaceService()
                        )
                )
                .build();

        server.start();
        server.awaitTermination();
    }
}