package co.archifact.core.service.workspace;

public record ArchifactConfig(
        String cloudProvider,
        ProjectProgrammingLanguage programmingLanguage
) {
}
