package version.fabric;

import com.google.gson.annotations.SerializedName;
import version.base.IVersion;
import version.base.VersionType;

public class FabricVersion implements IVersion {

    private String id, url;

    @Override
    public VersionType getVersionType() {
        return VersionType.release;
    }

    @Override
    public String getVersionID() {
        return id;
    }

    @Override
    public String getVersionUrl() {
        return url;
    }

    @Override
    public String getProperty(String propetyID) throws Exception {
        throw new Exception("FabricVersion don't have addtional properties");
    }
}
