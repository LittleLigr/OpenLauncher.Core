package version.fabric;

import version.base.IVersionConfig;
import version.base.VersionType;
import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionArtifact;
import version.base.nodes.IVersionLibrary;
import version.vanilla.VersionConfig;
import version.vanilla.standartconfig.VersionLibrary;

import java.util.ArrayList;

public class FabricVersionConfig implements IVersionConfig {

    public String id, inheritsFrom, releaseTime, time, mainClass;
    public VersionType type;

    public VersionConfig.VersionArgumentsContainer arguments;
    public VersionLibrary[] libraries;

    @Override
    public IVersionLibrary[] getLibraries() {
        return libraries;
    }

    @Override
    public String getProperty(String id) throws Exception {
        switch (id)
        {
            case "inheritsFrom": return inheritsFrom;
            case "releaseTime": return releaseTime;
            case "time": return time;
        }
        throw new Exception("Property "+id+" not detected");
    }

    @Override
    public String getMainClass() {
        return mainClass;
    }

    @Override
    public VersionType getType() {
        return type;
    }

    @Override
    public IVersionArtifact getAssetsIndex() throws Exception {
        throw new Exception("This field not available");
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public IVersionArguments getArguments(String id) throws Exception {
        switch (id)
        {
            case "game":return arguments.game;
            case "jvm": return arguments.jvm;
        }
        throw new Exception("Argument "+id+" not detected");
    }

    @Override
    public IVersionArtifact getClient() throws Exception {
        throw new Exception("This field not available");
    }
}
