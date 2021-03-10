package version.base;

import com.google.gson.JsonDeserializer;

public interface IVersion {
    /***
     * Return type of version (release or snapshot)
     * @return VersionType.release / VersionType.shapshot
     */
    VersionType getVersionType();

    /***
     * Return version name (1.16.5 / 21w08b)
     * @return
     */
    String getVersionID();

    /***
     * Return download link to version config file
     * @return
     */
    String getVersionUrl() throws Exception;

    /***
     * return custom property (time, releaseTime)
     * @param propetyID Propety name (time, Compliancelevel)
     * @return
     */
    String getProperty(String propetyID) throws Exception;

}
