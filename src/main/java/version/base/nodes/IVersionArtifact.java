package version.base.nodes;

import download.base.IResource;

public interface IVersionArtifact extends IResource {
    String getID();
    String getProperty(String id) throws Exception;
}
