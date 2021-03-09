package version.base.nodes;

public interface IVersionRule {
    String getAction();
    OsDescription getOsDescription();
    String[] getValues();
}
