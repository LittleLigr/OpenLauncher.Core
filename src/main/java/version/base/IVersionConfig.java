package version.base;

import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionArtifact;
import version.base.nodes.IVersionLibrary;
import version.vanilla.standartconfig.VersionArguments;
import version.vanilla.standartconfig.VersionArtifact;
import version.vanilla.standartconfig.VersionLibrary;


public interface IVersionConfig {
    IVersionLibrary[] getLibraries();
    String getProperty(String id) throws Exception;
    String getMainClass();
    VersionType getType();
    IVersionArtifact getAssetsIndex() throws Exception;
    String getID();
    IVersionArguments getArguments(String id) throws Exception;
    IVersionArtifact getClient() throws Exception;

}
