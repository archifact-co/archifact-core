package co.archifact.core.service.workspace;

import co.archifact.core.CloudProvider;

import java.nio.file.Path;

public record CreateWorkspaceRequest(
        String workspaceName,
        CloudProvider cloudProvider,
        String workspacePath,
        ProjectProgrammingLanguage programmingLanguage
) {

    public Path getWorkspacePath() {
        return Path.of(String.format("%s/%s", workspacePath, workspaceName));
    }
}
