package version.fabric;

import version.base.IVersion;
import version.base.IVersionManifest;
import version.base.VersionType;

import java.util.ArrayList;
import java.util.Optional;

public class FabricManifest implements IVersionManifest {

    public FabricVersion latest;
    public ArrayList<FabricVersion> versions;

    @Override
    public IVersion getLatest(VersionType versionType) throws Exception {
        return latest;
        //throw new Exception("Version with id "+ versionTypeID + " and type "+versionType + " not detected.");
    }

    @Override
    public IVersion getByID(String id) throws Exception {
        Optional<FabricVersion> searchResult = versions.stream().filter(x->x.getVersionID().equals(id)).findFirst();
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
        return 1;
    }
}
