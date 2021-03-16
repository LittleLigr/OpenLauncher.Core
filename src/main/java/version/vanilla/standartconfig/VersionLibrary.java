package version.vanilla.standartconfig;

import version.base.nodes.IVersionDownload;
import version.base.nodes.IVersionLibrary;
import version.base.nodes.IVersionRule;
import version.base.nodes.VersionNativesDescription;

import java.util.Arrays;

public class VersionLibrary implements IVersionLibrary {
    public VersionDownloadDescription downloads;
    public String name;
    public VersionNativesDescription natives;
    public VersionRule[] rules;

    @Override
    public String toString() {
        return "VersionLibrary{" +
                "downloads=" + downloads +
                ", name='" + name + '\'' +
                ", natives=" + natives +
                ", rules=" + Arrays.toString(rules) +
                '}';
    }

    @Override
    public IVersionDownload getDownloadDescription() {
        return downloads;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public VersionNativesDescription getNativeDescription() {
        return natives;
    }

    @Override
    public IVersionRule[] getRules() {
        return rules;
    }
}
