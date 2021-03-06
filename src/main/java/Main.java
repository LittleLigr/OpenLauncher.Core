import config.Config;
import serialization.Json;
import version.IVersionManifest;
import version.config.IVersionConfig;
import version.standart.VersionConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Openlauncher.Core project");
        System.out.println("Launch directory "+System.getProperty("user.dir"));
        String configJson = Json.parse(Config.defaultConfigFile, Config.class);
        try {
            new File("src/main/resources/config/config.json").createNewFile();
            Files.write(Paths.get("src/main/resources/config/config.json"), configJson.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "/home/igor/.minecraft/versions/1.16.5/1.16.5.json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)));
            IVersionConfig manifest = Json.parse(json, VersionConfig.class);
            System.out.println(manifest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
