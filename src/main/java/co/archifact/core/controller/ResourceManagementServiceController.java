package co.archifact.core.controller;

import co.archifact.core.DeployResourcesRequest;
import co.archifact.core.DeployResourcesResponse;
import co.archifact.core.DeploymentStatus;
import co.archifact.core.ResourceManagementServiceGrpc;
import co.archifact.core.service.ResourceManagementService;
import io.grpc.stub.StreamObserver;

public class ResourceManagementServiceController
        extends ResourceManagementServiceGrpc.ResourceManagementServiceImplBase {

    private final ResourceManagementService resourceManagementService;

    public ResourceManagementServiceController(
            final ResourceManagementService resourceManagementService
    ) {
        this.resourceManagementService = resourceManagementService;
    }

    @Override
    public void deployResources(
            final DeployResourcesRequest request,
            final StreamObserver<DeployResourcesResponse> responseObserver
    ) {
        System.out.println("Preparing resource deployment resources");

        resourceManagementService.prepareDeployment(request);

        DeployResourcesResponse response = DeployResourcesResponse.newBuilder()
                .setStatus(DeploymentStatus.PENDING)
                .setDeploymentId(1)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
