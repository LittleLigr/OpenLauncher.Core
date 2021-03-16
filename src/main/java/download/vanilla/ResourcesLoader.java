package download.vanilla;

import config.Config;
import config.base.GameModeConfig;
import download.base.IResourceLoadKeeper;
import download.base.IResourcesLoader;
import util.filemanager.R;
import version.base.IAssetConfig;
import version.base.IVersion;
import version.base.IVersionConfig;
import version.base.IVersionManifest;
import version.base.nodes.IVersionArtifact;
import version.base.nodes.IVersionLibrary;
import version.base.serialization.Json;
import version.vanilla.AssetConfig;
import version.vanilla.VersionConfig;
import version.vanilla.VersionManifest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResourcesLoader implements IResourcesLoader {

    private final GameModeConfig config;

    private ArrayList<IResourceLoadKeeper> keepers = new ArrayList<>();

    public ResourcesLoader(GameModeConfig config)
    {
        this.config = config;
    }

    @Override
    public void downloadResources(IVersionConfig versionConfig, IAssetConfig assetConfig) throws Exception {

        System.out.println("Download game assets and libraries");
        start();
        tick(0);
        downloadAssets(assetConfig.getArtifacts());
        tick(0.33f);
        downloadLibraries(versionConfig.getLibraries());
        downloadClient(versionConfig);
        tick(1);
        end();
        System.out.println("Download game data successfully\n");
    }

    private void downloadAssets(IVersionArtifact[] artifacts) throws Exception {
        String path = config.buildAssetsDir()+"/objects";
        for(IVersionArtifact item : artifacts)
        {
            String hash = item.getProperty("hash");
            R.downloadFromUrl(config.assetsUrl+"/"+hash.charAt(0)+hash.charAt(1)+"/"+hash,
                    path+"/"+hash.charAt(0)+hash.charAt(1)+"/"+hash);
        }
    }
    @Override
    public void downloadLibraries(IVersionLibrary[] libraries) throws Exception {
        float step = 1f/ libraries.length;
        float curStep = 0;
        for(IVersionLibrary item : libraries)
        {
            R.downloadFromUrl(item.getDownloadDescription().getArtifact().getUrl(), config.buildLibraryPath(item.getDownloadDescription().getArtifact().getPath()));

            IVersionArtifact nativeLib = item.getDownloadDescription().getClassifier(config.config.systemConfig.name);
            if(nativeLib!=null)
                R.downloadFromUrl(nativeLib.getUrl(), config.buildLibraryPath(nativeLib.getPath()));
            tick(0.33f+0.33f*curStep);
            curStep+=step;
        }
    }
    @Override
    public void downloadClient(IVersionConfig versionConfig) throws Exception {
        File nativeFolder = new File(config.buildNativesPath(versionConfig.getID()));
        nativeFolder.mkdirs();
        R.downloadFromUrl(versionConfig.getClient().getUrl(), config.buildJarPath(versionConfig.getID()));
    }

    @Override
    public void downloadVersionManifest() throws IOException {
        System.out.println("Download version manifest from "+config.manifestUrl+" to "+config.manifestFileName);
        R.downloadFromUrl(config.getManifestUrl(), config.buildManifestPath());
        System.out.println("Download version manifest successfully\n");
    }

    @Override
    public void downloadVersionConfig(IVersion manifest) throws Exception {
        String path = config.buildVersionConfigPath(manifest.getVersionID());
        System.out.println("Download version config file from "+manifest.getVersionUrl()+" to "+path);
        R.downloadFromUrl(manifest.getVersionUrl(), path);
        System.out.println("Download version config successfully\n");
    }

    @Override
    public void downloadAssetManifest(IVersionConfig versionConfig) throws Exception {
        System.out.println("Download asset config from "+versionConfig.getAssetsIndex().getUrl() +" to "+config.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json");
        R.downloadFromUrl(versionConfig.getAssetsIndex().getUrl(), config.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json");
        System.out.println("Download asset config successfully\n");
    }

    @Override
    public IAssetConfig readAssetConfig(IVersionConfig versionConfig) throws Exception {
        IAssetConfig assetConfig = Json.parse(R.readFile(config.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json"), AssetConfig.class);
        System.out.println("Asset config read successfully.");
        return assetConfig;
    }

    @Override
    public IVersionManifest readVersionManifest() throws IOException {
        IVersionManifest manifest = Json.parse(R.readFile(config.buildManifestPath()), VersionManifest.class);
        System.out.println("Version manifest read successfully.");
        return manifest;
    }

    @Override
    public IVersionConfig readVersionConfig(IVersion manifest) throws IOException {
        IVersionConfig versionConfig = Json.parse(R.readFile(config.buildVersionConfigPath(manifest.getVersionID())), VersionConfig.class);
        System.out.println("Version config "+versionConfig.getID()+" read successfully.");
        return versionConfig;
    }

    @Override
    public boolean assetConfigExist(IVersionConfig versionConfig) {
        try
        {
            IAssetConfig assetConfig = readAssetConfig(versionConfig);
            String path = config.buildAssetsDir()+"/objects";
            for(IVersionArtifact item : assetConfig.getArtifacts())
            {
                String hash = item.getProperty("hash");
                boolean value = new File(config.assetsUrl+"/"+hash.charAt(0)+hash.charAt(1)+"/"+hash,
                        path+"/"+hash.charAt(0)+hash.charAt(1)+"/"+hash).exists();
                if(!value)
                    return false;
            }
            System.out.println("Asset config and objects for version "+versionConfig.getID()+" pass checks succesfully");
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Asset config and objects for version "+versionConfig.getID()+" not pass checks");
            return false;
        }
    }

    @Override
    public boolean versionManifestExist() {
        try
        {
            readVersionManifest();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean versionConfigExist(IVersion manifest) {
        try
        {
            readVersionConfig(manifest);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean allFilesExist(IVersion manifest) {
        try {
            boolean result = true;

            IVersionConfig versionConfig = readVersionConfig(manifest);
            if(assetConfigExist(versionConfig)==false)
                result = false;

            if(clientExist(versionConfig)==false)
                result = false;

            if(librariesExist(versionConfig)==false)
                result = false;

            if(result)
                System.out.println("All components of version "+manifest.getVersionID()+" pass checks successfully.");
            else
                System.out.println("Components of version "+manifest.getVersionID()+" not pass checks.");
            return result;
        }
        catch (Exception e)
        {
            System.out.println("Components of version "+manifest.getVersionID()+" not pass checks.");
            return false;
        }
    }

    @Override
    public boolean clientExist(IVersionConfig versionConfig) {
        try
        {
            boolean result =new File(config.buildJarPath(versionConfig.getID())).exists();
            if(result)
                System.out.println("Version "+versionConfig.getID()+" client pass checks successfully.");
            else System.out.println("Version "+versionConfig.getID()+" client not pass checks.");
            return result;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean librariesExist(IVersionConfig versionConfig) {
        try {
            boolean buffer;
            for(IVersionLibrary item : versionConfig.getLibraries())
            {
                buffer = new File(config.buildLibraryPath(item.getDownloadDescription().getArtifact().getPath())).exists();
                if(buffer==false)
                    return false;

                IVersionArtifact nativeLib = item.getDownloadDescription().getClassifier(config.config.systemConfig.name);
                if(nativeLib!=null)
                {
                    buffer = new File(config.buildLibraryPath(nativeLib.getPath())).exists();
                    if(buffer==false)
                        return false;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Libraries "+versionConfig.getID()+" client pass checks successfully.");
            return false;
        }

        return true;
    }

    @Override
    public void tick(float perc) {
        for(IResourceLoadKeeper keeper : keepers)
            keeper.tick(perc);
    }

    @Override
    public void start() {
        for(IResourceLoadKeeper keeper : keepers)
            keeper.start();
    }

    @Override
    public void end() {
        for(IResourceLoadKeeper keeper : keepers)
            keeper.end();
    }

    @Override
    public void addDownloadKeeper(IResourceLoadKeeper keeper) {
        keepers.add(keeper);
    }


    @Override
    public boolean versionReady(IVersion manifest) {
        try {
            IVersionConfig versionConfig = readVersionConfig(manifest);
            IAssetConfig assetConfig = readAssetConfig(versionConfig);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean minecraftMetaReady() {
        try {
            File root = new File(config.config.root);
            if(root.exists() == false)
                return false;
            IVersionManifest manifest = readVersionManifest();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
