package version.base;

import com.google.gson.JsonDeserializer;


public interface IVersionManifest
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

}
