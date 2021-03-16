package version.vanilla;

import version.base.IVersion;
import version.base.IVersionManifest;
import version.base.VersionType;

import java.util.ArrayList;
import java.util.Optional;

public class VersionManifest implements IVersionManifest {

    private VersionManifestLatest latest;
    private ArrayList<VersionM2> versions = new ArrayList<>();

    @Override
    public IVersion getLatest(VersionType versionType) throws Exception {
        String versionTypeID;

        if(versionType == VersionType.release)
            versionTypeID = latest.release;
        else versionTypeID = latest.snapshot;

        Optional<VersionM2> searchResult = versions.stream().filter(x->x.getVersionID().equals(versionTypeID) && x.getVersionType()==versionType).findFirst();
        if(searchResult.isPresent())
            return searchResult.get();

        throw new Exception("Version with id "+ versionTypeID + " and type "+versionType + " not detected.");
    }

    @Override
    public IVersion getByID(String id) throws Exception {
        Optional<VersionM2> searchResult = versions.stream().filter(x->x.getVersionID().equals(id)).findFirst();
        if(searchResult.isPresent())
            return searchResult.get();

        throw new Exception("Version with id "+ id + " not detected.");
    }

    @Override
    public IVersion[] getAllVersions() {
        return (IVersion[]) versions.toArray();
    }


    @Override
    public int generation() {
        return 2;
    }

    @Override
    public String toString() {
        return "VersionManifest{" +
                "latestReleaseID='" + latest.release + '\'' +
                ", latestSnapshotID='" + latest.snapshot + '\'' +
                ", versions=" + versions +
                '}';
    }

    public class VersionManifestLatest
    {
        private String release, snapshot;
    }

}
