package version;

import com.google.gson.annotations.SerializedName;

public enum VersionType {
    @SerializedName("release")
    release,
    @SerializedName("shapshot")
    snapshot
}
