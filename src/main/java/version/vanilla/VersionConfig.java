package version.vanilla;

import version.base.VersionType;
import version.base.IVersionConfig;
import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionArtifact;
import version.base.nodes.IVersionLibrary;
import version.vanilla.standartconfig.VersionArguments;
import version.vanilla.standartconfig.VersionArtifact;
import version.vanilla.standartconfig.VersionLibrary;

import java.util.Arrays;

public class VersionConfig implements IVersionConfig {

    public VersionArgumentsContainer arguments;
    public VersionArtifact assetIndex;
    public String assets,id,mainClass,minimumlauncherVersion,releaseTime,time;
    public VersionType type;
    public VersionLibrary[] libraries;
    public VersionDownloadsContainer downloads;

    @Override
    public IVersionLibrary[] getLibraries() {
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
    public IVersionArtifact getAssetsIndex() {
        return assetIndex;
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
    public IVersionArtifact getClient() {
        return downloads.client;
    }

    public class VersionArgumentsContainer
    {
        public VersionArguments game, jvm;

        @Override
        public String toString() {
            return "VersionArgumentsContainer{" +
                    "game=" + game +
                    ", jvm=" + jvm +
                    '}';
        }
    }

    public class VersionDownloadsContainer
    {
        public VersionArtifact client, server;
    }

    @Override
    public String toString() {
        return "VersionConfig{" +
                "arguments=" + arguments +
                ", assetIndex=" + assetIndex +
                ", assets='" + assets + '\'' +
                ", id='" + id + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", minimumlauncherVersion='" + minimumlauncherVersion + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", time='" + time + '\'' +
                ", type=" + type +
                ", libraries=" + Arrays.toString(libraries) +
                '}';
    }
}
