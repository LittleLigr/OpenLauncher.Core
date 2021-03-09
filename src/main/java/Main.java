import builder.base.IBuilder;
import builder.vanilla.Builder;
import com.google.gson.Gson;
import config.Config;
import config.GameMode;
import download.vanilla.ResourcesLoader;
import util.filemanager.R;
import version.base.*;
import version.base.serialization.Json;
import version.vanilla.VersionConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.println("Openlauncher.Core project");
        System.out.println("Launch directory "+System.getProperty("user.dir"));

        System.out.println("Loading config");
        Config config = null;
        try {
            config = Config.loadConfig();
            System.out.println("Load config successfully");
        }
        catch (Exception e)
        {
            System.out.println("Error, config not detected, create default config?\n[y/n]");
            String response = input.next();

            if(response.equals("y"))
                config = Config.createDefaultConfig();
            else System.exit(0);
        }

        config.chooseGameMode(GameMode.vanilla);

        ResourcesLoader loader = new ResourcesLoader(config);
        IVersionManifest manifest = loader.readVersionManifest();
        //IVersionConfig versionConfig = loader.readVersionConfig(manifest.getLatest(VersionType.release));

        IVersion minecraftVersion = manifest.getByID("1.15.2");
        loader.downloadVersionConfig(minecraftVersion);
        IVersionConfig versionConfig = loader.readVersionConfig(minecraftVersion);
        loader.downloadAssetManifest(versionConfig);
        IAssetConfig assetConfig = loader.readAssetConfig(versionConfig);

        loader.downloadResources(versionConfig, assetConfig);

        IBuilder builder = new Builder(config, versionConfig);
        Runtime.getRuntime().exec(builder.buildRun());

        //System.out.println("Type command. Type \"help\" to get more information.");
        //menu(input);


        /*
        ResourcesLoader loader = new ResourcesLoader(Config.defaultConfigFile);
        try {
            Config config = Json.parse(R.readFile(Config.configPath), Config.class);

            loader.loadVersionManifest();

            IVersionManifest versionManifest = loader.readVersionManifest();
            System.out.println(versionManifest);

            IVersionConfig versionConfig = loader.readVersionConfig(versionManifest.getLatest(VersionType.release));
            System.out.println(versionConfig);

            IBuilder builder = new Builder(config, versionConfig);

            System.out.println(builder.buildRun());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
         */


    }

    static void menu(Scanner r)
    {
        String command = r.next();

        String print = "";

        if(actionMap.containsKey(command))
            actionMap.get(command).action();

        menu(r);
    }

    static Map<String, Action> actionMap = new HashMap<>();
    static {
        actionMap.put("help", new Action() {
            @Override
            public void action() {
                for (Map.Entry<String, Action>item : actionMap.entrySet())
                    System.out.println(item.getKey()+" - "+item.getValue().description());
            }

            @Override
            public String description() {
                return "Show information about all available commands";
            }
        });
    }

    interface Action
    {
        void action();
        String description();
    }
}
