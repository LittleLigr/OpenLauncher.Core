package version.base.nodes;

public interface IVersionLibrary {

    IVersionDownload getDownloadDescription();
    String getName();
    VersionNativesDescription getNativeDescription();
    IVersionRule[] getRules();

}
