package co.archifact.core.service;

import co.archifact.core.DeployResourcesRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceManagementService {

    private static Logger logger = LogManager.getLogger(ResourceManagementService.class);

    public void prepareDeployment(DeployResourcesRequest request) {
        logger.info("Preparing deployment = {}" + request);
    }
}
