package co.archifact.core.controller;

import co.archifact.core.InitializeWorkspaceRequest;
import co.archifact.core.InitializeWorkspaceResponse;
import co.archifact.core.WorkspaceServiceGrpc;
import co.archifact.core.service.workspace.CreateWorkspaceRequest;
import co.archifact.core.service.workspace.ProjectProgrammingLanguage;
import co.archifact.core.service.workspace.WorkspaceService;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkspaceManagementController extends WorkspaceServiceGrpc.WorkspaceServiceImplBase {

    private static final Logger logger = LogManager.getLogger(WorkspaceManagementController.class);

    private final WorkspaceService workspaceService;

    public WorkspaceManagementController(final WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Override
    public void initializeWorkspace(
            final InitializeWorkspaceRequest request,
            final StreamObserver<InitializeWorkspaceResponse> responseObserver
    ) {
        logger.info("Initializing workspace request={}", request);

        workspaceService.create(new CreateWorkspaceRequest(
                request.getWorkspaceName(),
                request.getCloudProvider(),
                request.getWorkspacePath(),
                new ProjectProgrammingLanguage(request.getProgrammingLanguage())
        ));

        final InitializeWorkspaceResponse response = InitializeWorkspaceResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
