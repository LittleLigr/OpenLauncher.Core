package version.config;

import com.google.gson.JsonDeserializer;
import serialization.ICustomDeserializator;
import version.VersionType;
import version.config.nodes.VersionArguments;
import version.config.nodes.VersionArtifact;
import version.config.nodes.VersionLibrary;

public interface IVersionConfig extends ICustomDeserializator {
    VersionLibrary[] getLibraries();
    String getProperty(String id) throws Exception;
    String getMainClass();
    VersionType getType();
    VersionArtifact getAssetsIndex();
    String getID();
    VersionArguments getArguments(String id) throws Exception;

    @Override
    default JsonDeserializer getBuilder(){
        return null;
    }
}
