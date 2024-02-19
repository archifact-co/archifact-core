package co.archifact.core.service.workspace;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorkspaceService {

    private static final String FAILED_TO_CREATE_WORKSPACE_DIRECTORY = "Failed to create workspace directory";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String ARCHIFACT_CONFIG_FILE = "archifact.json";

    public void create(final CreateWorkspaceRequest request) {
        try {
            final Path workspacePath = request.getWorkspacePath();

            Files.createDirectory(workspacePath);

            final ArchifactConfig config = new ArchifactConfig(
                    request.cloudProvider().name(),
                    request.programmingLanguage()
            );

            writeConfigToFile(workspacePath, ARCHIFACT_CONFIG_FILE, config);
        } catch (IOException e) {
            throw new IllegalStateException(FAILED_TO_CREATE_WORKSPACE_DIRECTORY, e);
        }
    }

    public static void writeConfigToFile(Path directoryPath, String fileName, ArchifactConfig config) throws IOException {
        Path filePath = directoryPath.resolve(fileName);

        String jsonString = OBJECT_MAPPER.writeValueAsString(config);

        Files.writeString(filePath, jsonString);
    }

}
