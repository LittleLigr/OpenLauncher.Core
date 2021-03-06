package config;

public class Config {

    public static transient final Config defaultConfigFile = new Config();
    static {
        defaultConfigFile.versionMetaUrl = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";
        defaultConfigFile.assetsResourceUrl = "http://resources.download.minecraft.net";
        defaultConfigFile.versionManifestFileName = "version_manifest_v2.json";
        defaultConfigFile.versionManifestPath = "/versions";
        defaultConfigFile.root = System.getProperty("user.home")+"/.minecraft";
        defaultConfigFile.linux = new OSconfig(System.getProperty("user.home") + "/.minecraft");
        defaultConfigFile.windows = new OSconfig(System.getProperty("user.home") + "/.minecraft");
        defaultConfigFile.osx = new OSconfig(System.getProperty("user.home") + "/.minecraft");
        defaultConfigFile.arch = new OSconfig(System.getProperty("user.home") + "/.minecraft");
        defaultConfigFile.fabricMetaUrl = "https://maven.fabricmc.net/net/fabricmc/fabric-installer/maven-metadata.xml";
    }

    public String versionMetaUrl, versionManifestFileName, versionManifestPath;
    public String assetsResourceUrl;
    public String root;
    public OSconfig linux, windows, osx, arch;

    public String fabricMetaUrl;

}
