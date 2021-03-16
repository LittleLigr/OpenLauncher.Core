package version.vanilla.standartconfig;

import version.base.nodes.IVersionRule;
import version.base.nodes.OsDescription;

public class VersionRule implements IVersionRule {
    public String action;
    public OsDescription os;

    @Override
    public String toString() {
        return "VersionRule{" +
                "action='" + action + '\'' +
                ", os=" + os +
                '}';
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public OsDescription getOsDescription() {
        return os;
    }

    @Override
    public String[] getValues() {
        return null;
    }
}
