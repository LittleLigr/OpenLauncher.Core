package version.standart;

import version.VersionType;
import version.config.IVersionConfig;
import version.config.nodes.VersionArguments;
import version.config.nodes.VersionArtifact;
import version.config.nodes.VersionLibrary;

public class VersionConfig implements IVersionConfig {

    public VersionArgumentsContainer arguments;
    public VersionArtifact assetIndex;
    public String assets,id,mainClass,minimumlauncherVersion,releaseTime,time;
    public VersionType type;
    public VersionLibrary[] libraries;

    @Override
    public VersionLibrary[] getLibraries() {
        return libraries;
    }

    @Override
    public String getProperty(String id) throws Exception {
        switch (id)
        {
            case "assets": return assets;
            case "minimumLauncherVersion": return minimumlauncherVersion;
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
    public VersionArtifact getAssetsIndex() {
        return assetIndex;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public VersionArguments getArguments(String id) throws Exception {
        switch (id)
        {
            case "game":return arguments.game;
            case "jvm": return arguments.jvm;
        }
        throw new Exception("Argument "+id+" not detected");
    }

    public class VersionArgumentsContainer
    {
        public VersionArguments game, jvm;
    }
}
