package version.standart;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import version.IVersion;
import version.IVersionManifest;
import version.VersionType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

public class VersionManifest implements IVersionManifest {

    private VersionManifestLatest latest;
    private ArrayList<IVersion> versions = new ArrayList<>();

    @Override
    public IVersion getLatest(VersionType versionType) throws Exception {

        IVersion version;
        String versionTypeID;

        if(versionType == VersionType.release)
            versionTypeID = latest.release;
        else versionTypeID = latest.snapshot;

        Optional<IVersion> searchResult = versions.stream().filter(x->x.getVersionID().equals(versionTypeID) && x.getVersionType() == versionType).findFirst();
        if(searchResult.isPresent())
            return searchResult.get();

        throw new Exception("Version with id "+ versionTypeID + " and type "+versionType + " not detected.");
    }

    @Override
    public IVersion getByID(String id) throws Exception {
        Optional<IVersion> searchResult = versions.stream().filter(x->x.getVersionID().equals(id)).findFirst();
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
