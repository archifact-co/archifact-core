package co.archifact.core.api;

import co.archifact.core.CloudProvider;
import co.archifact.core.InitializeWorkspaceRequest;
import co.archifact.core.InitializeWorkspaceResponse;
import co.archifact.core.WorkspaceServiceGrpc;
import co.archifact.core.service.workspace.WorkspaceService;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkspaceManagementApiTest {

    public static final String WORKSPACE_NAME = "workspaceName";

    private static final String CONFIG_FILE_NAME = "archifact.json";

    private static final String WORKSPACE_TEST = "workspaceTest";

    private final Server server;

    private final ManagedChannel channel;

    private Path tempDirectory;


    public WorkspaceManagementApiTest() {
        // Create an in-process server with a unique name
        String serverName = InProcessServerBuilder.generateName();
        server = InProcessServerBuilder.forName(serverName)
                .directExecutor() // directExecutor is fine for unit tests
                .addService(new WorkspaceManagementApi(
                        new WorkspaceService()
                )) // Your service implementation
                .build();

        // Create a channel for a client to connect to the server
        channel = InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build();
    }

    @BeforeEach
    public void startServer() throws IOException {
        tempDirectory = Files.createTempDirectory(WORKSPACE_TEST);
        server.start();
    }

    @AfterEach
    public void stopServer() {
        server.shutdownNow();
        channel.shutdownNow();
    }

    @Test
    public void shouldInitializeWorkspace() throws Exception {
        // Given
        WorkspaceServiceGrpc.WorkspaceServiceBlockingStub stub = WorkspaceServiceGrpc.newBlockingStub(channel);

        InitializeWorkspaceRequest request = InitializeWorkspaceRequest.newBuilder()
                .setWorkspaceName(WORKSPACE_NAME)
                .setCloudProvider(CloudProvider.AWS)
                .setWorkspacePath(tempDirectory.toString())
                .build();

        // When
        InitializeWorkspaceResponse response = stub.initializeWorkspace(request);

        // Then
        Path workspacePath = tempDirectory.resolve(String.format("%s/%s", tempDirectory, WORKSPACE_NAME));
        assertTrue(Files.exists(workspacePath), "Workspace directory should exist");

        Path configFilePath = workspacePath.resolve(CONFIG_FILE_NAME);
        assertTrue(Files.exists(configFilePath), "Config file should exist inside workspace directory");
    }
}