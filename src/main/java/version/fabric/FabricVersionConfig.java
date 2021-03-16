package version.fabric;

import version.base.IVersionConfig;
import version.base.VersionType;
import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionArtifact;
import version.base.nodes.IVersionLibrary;
import version.fabric.nodes.FabricArguments;
import version.fabric.nodes.FabricLibrary;
import version.vanilla.VersionConfig;
import version.vanilla.standartconfig.VersionArguments;
import version.vanilla.standartconfig.VersionLibrary;

import java.util.ArrayList;

public class FabricVersionConfig implements IVersionConfig {

    public String id, inheritsFrom, releaseTime, time, mainClass, assets;
    public VersionType type;

    public FabricArgumentsContainer arguments;
    public FabricLibrary[] libraries;

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
            case "assets": return assets;
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

    public class FabricArgumentsContainer
    {
        public FabricArguments game, jvm;

        @Override
        public String toString() {
            return "VersionArgumentsContainer{" +
                    "game=" + game +
                    ", jvm=" + jvm +
                    '}';
        }
    }
}
