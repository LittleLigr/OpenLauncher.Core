package download.fabric;

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
import version.base.serialization.Xml;
import version.fabric.FabricManifest;
import version.fabric.FabricVersion;
import version.fabric.FabricVersionConfig;
import version.vanilla.AssetConfig;
import version.vanilla.VersionConfig;
import version.vanilla.VersionManifest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FabricLoader implements IResourcesLoader {

    public GameModeConfig config;

    private ArrayList<IResourceLoadKeeper> keepers = new ArrayList<>();

    public FabricLoader(GameModeConfig config)
    {
        this.config = config;
    }

    @Override
    public void downloadResources(IVersionConfig versionConfig, IAssetConfig assetConfig) throws Exception {
        System.out.println("Download game assets and libraries");
        start();
        downloadAssets(assetConfig.getArtifacts());
        downloadLibraries(versionConfig.getLibraries());
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
        for(IVersionLibrary item : libraries)
        {
            R.downloadFromUrl(item.getDownloadDescription().getArtifact().getUrl(), item.getDownloadDescription().getArtifact().getPath());

            IVersionArtifact nativeLib = item.getDownloadDescription().getClassifier(config.config.systemConfig.name);
            if(nativeLib!=null)
                R.downloadFromUrl(nativeLib.getUrl(), nativeLib.getPath());
        }
    }

    @Override
    public void downloadClient(IVersionConfig versionConfig) throws Exception {
        throw new Exception("This not supported");
    }

    @Override
    public void downloadVersionManifest() throws IOException {
        System.out.println("Download version manifest from "+config.manifestUrl+" to "+config.manifestFileName);
        R.downloadFromUrl(config.getManifestUrl(), config.buildManifestPath());
        System.out.println("Download version manifest successfully\n");
    }

    @Override
    public void downloadVersionConfig(IVersion manifest) throws Exception {
        throw new Exception("Fabric config downloading not supported now.");
    }

    @Override
    public void downloadAssetManifest(IVersionConfig versionConfig) throws Exception {
        System.out.println("Download asset config from "+versionConfig.getAssetsIndex().getUrl() +" to "+config.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json");
        R.downloadFromUrl(versionConfig.getAssetsIndex().getUrl(), config.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json");
        System.out.println("Download asset config successfully\n");
    }

    @Override
    public IAssetConfig readAssetConfig(IVersionConfig versionConfig) throws Exception {
        return Json.parse(R.readFile(config.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json"), AssetConfig.class);
    }

    @Override
    public IVersionManifest readVersionManifest() throws Exception {
        File[] directories = new File(config.buildVersionsPath()).listFiles(File::isDirectory);
        FabricManifest manifest = new FabricManifest();
        manifest.versions = new ArrayList<>();
        for (File file : directories)
            if(file.getName().contains("fabric"))
                manifest.versions.add(new FabricVersion(file.getName()));
        if(manifest.versions.size()>0)
            manifest.latest = manifest.versions.get(0);
        return manifest;
        //return Json.parse(R.readFile(config.buildManifestPath()), VersionManifest.class);
        //throw new Exception("This not available now");
    }

    @Override
    public IVersionConfig readVersionConfig(IVersion manifest) throws IOException {
        return Json.parse(R.readFile(config.buildVersionConfigPath(manifest.getVersionID())), FabricVersionConfig.class);
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

            return true;
        }
        catch (Exception e)
        {
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
            result |= assetConfigExist(versionConfig);
            result |= clientExist(versionConfig);
            result |= librariesExist(versionConfig);

            return result;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean clientExist(IVersionConfig versionConfig) {
        try
        {
            return new File(config.buildJarPath(versionConfig.getID())).exists();
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
