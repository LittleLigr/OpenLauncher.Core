package config;

import config.base.GameModeConfig;
import util.filemanager.R;
import version.base.serialization.Json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;

public class Config {

    public static transient final String configPath = "src/main/resources/config";
    public static transient final String configFileName = "config.json";

    public transient static OSconfig linux = new OSconfig(System.getProperty("user.home") + "/.minecraft", "linux", "", "");
    public transient static OSconfig windows = new OSconfig(System.getProperty("user.home") + "/.minecraft", "windows", "", "");
    public transient static OSconfig osx = new OSconfig(System.getProperty("user.home") + "/.minecraft", "osx", "", "");

    public transient String launcherName = "OpenLauncher.Core";
    public transient String launcherVersion = "1.0";

    public static Config createDefaultConfig()
    {
        String configJson = Json.parse(Config.defaultConfigFile, Config.class);
        try { ;
            File configFolder = new File(configPath);
            configFolder.mkdirs();
            File config = new File(configPath+"/"+configFileName);
            config.createNewFile();
            Files.write(Paths.get(config.getPath()), configJson.getBytes());
            System.out.println("Create default config successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Config.defaultConfigFile;
    }

    public static Config loadConfig() throws IOException {
        return Json.parse(R.readFile(Config.configPath+"/"+configFileName), Config.class);
    }

    private static transient final Config defaultConfigFile = new Config();
    static {
        defaultConfigFile.root = System.getProperty("user.home")+"/.minecraft";
        defaultConfigFile.username = "test";

        defaultConfigFile.vanilla = new VanillaConfig();

        defaultConfigFile.vanilla.manifestUrl = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";
        defaultConfigFile.vanilla.resourcesUrl = "http://resources.download.minecraft.net";
        defaultConfigFile.vanilla.manifestFileName = "version_manifest_v2.json";
        defaultConfigFile.vanilla.config = defaultConfigFile;

        defaultConfigFile.fabric = new FabricConfig();
        defaultConfigFile.fabric.manifestUrl = "https://maven.fabricmc.net/net/fabricmc/fabric-installer/maven-metadata.xml";
        defaultConfigFile.fabric.resourcesUrl = "https://maven.fabricmc.net/net/fabricmc/fabric-installer";
        defaultConfigFile.fabric.manifestFileName = "metaFabric.xml";
        defaultConfigFile.fabric.manifestPath = "src/main/resources/config/";
        defaultConfigFile.fabric.config = defaultConfigFile;
    }

    public VanillaConfig vanilla;
    public FabricConfig fabric;

    public transient GameModeConfig modeConfig;

    public transient OSconfig systemConfig;

    public String root;
    public String username;

    public Config()
    {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win"))
            systemConfig = Config.windows;
        else if(os.contains("lin"))
            systemConfig = Config.linux;
        else systemConfig = Config.osx;

        systemConfig.arch = System.getProperty("os.arch");
        systemConfig.version = System.getProperty("os.version");

        modeConfig = vanilla;
    }

    public void chooseGameMode(GameMode mode)
    {
        switch (mode)
        {
            case fabric: modeConfig = fabric; break;
            case vanilla: modeConfig = vanilla; break;
        }

        modeConfig.config = this;
    }
}
