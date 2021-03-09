package version.base.nodes;

public interface IVersionDownload {
    IVersionArtifact getArtifact();
    IVersionArtifact getClassifier(String id) throws Exception;
}
