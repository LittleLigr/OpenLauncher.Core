package version.vanilla;

import com.google.gson.annotations.SerializedName;
import version.base.IVersion;
import version.base.VersionType;

public class VersionM2 implements IVersion {

    private String id, url, time, releaseTime, sha1, complianceLevel;
    @SerializedName("type")
    private VersionType versionType;

    @Override
    public VersionType getVersionType() {
        return versionType;
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
        switch (propetyID)
        {
            case "time": return time;
            case "releaseTime": return releaseTime;
            case "sha1": return sha1;
            case "complianceLevel": return complianceLevel;
        }

        throw new Exception("Field "+propetyID+" not detected in manifest realisation VersionM2.");
    }

}
