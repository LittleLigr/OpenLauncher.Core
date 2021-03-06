package version;

import com.google.gson.JsonDeserializer;
import serialization.ICustomDeserializator;

public interface IVersionManifest extends ICustomDeserializator
{
    /***
     * Return latest version of release or shapshot
     * @param versionType release/shapshot
     * @return
     */
    IVersion getLatest(VersionType versionType) throws Exception;

    /***
     * Return version config by name (1.16.5 / 21w08b)
     * @param id name
     * @return
     */
    IVersion getByID(String id) throws Exception;

    /***
     * Return list of versions in manifest file
     * @return
     */
    IVersion[] getAllVersions();

    int generation();

    @Override
    default JsonDeserializer getBuilder() {
        return null;
    }
}
