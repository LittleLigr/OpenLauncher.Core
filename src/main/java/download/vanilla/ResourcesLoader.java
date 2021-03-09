package download.vanilla;

import config.Config;
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

public class ResourcesLoader implements IResourcesLoader {

    private final Config config;

    public ResourcesLoader(Config config)
    {
        this.config = config;
    }

    @Override
    public void downloadResources(IVersionConfig versionConfig, IAssetConfig assetConfig) throws Exception {
        System.out.println("Download game assets and libraries");
        downloadAssets(assetConfig.getArtifacts());
        downloadLibraries(versionConfig.getLibraries());
        downloadClient(versionConfig);
        System.out.println("Download game data successfully\n");
    }

    private void downloadAssets(IVersionArtifact[] artifacts) throws Exception {
        String path = config.vanilla.buildAssetsDir()+"/objects";
        for(IVersionArtifact item : artifacts)
        {
            String hash = item.getProperty("hash");
            R.downloadFromUrl(config.modeConfig.assetsUrl+"/"+hash.charAt(0)+hash.charAt(1)+"/"+hash,
                    path+"/"+hash.charAt(0)+hash.charAt(1)+"/"+hash);
        }
    }

    private void downloadLibraries(IVersionLibrary[] libraries) throws Exception {
        for(IVersionLibrary item : libraries)
        {
            R.downloadFromUrl(item.getDownloadDescription().getArtifact().getUrl(), item.getDownloadDescription().getArtifact().getPath());

            IVersionArtifact nativeLib = item.getDownloadDescription().getClassifier(config.systemConfig.name);
            if(nativeLib!=null)
                R.downloadFromUrl(nativeLib.getUrl(), nativeLib.getPath());
        }
    }

    private void downloadClient(IVersionConfig versionConfig) throws IOException {
        File nativeFolder = new File(config.root+"/versions/"+versionConfig.getID()+"/natives");
        nativeFolder.mkdirs();
        R.downloadFromUrl(versionConfig.getClient().getUrl(), config.vanilla.buildJarPath(versionConfig.getID()));
    }

    @Override
    public void downloadVersionManifest() throws IOException {
        System.out.println("Download version manifest from "+config.vanilla.manifestUrl+" to "+config.vanilla.manifestPath);
        R.downloadFromUrl(config.vanilla.getManifestUrl(), config.vanilla.buildManifestPath());
        System.out.println("Download version manifest successfully\n");
    }

    @Override
    public void downloadVersionConfig(IVersion manifest) throws IOException {
        String path = config.vanilla.buildVersionConfigPath(manifest.getVersionID());
        System.out.println("Download version config file from "+manifest.getVersionUrl()+" to "+path);
        R.downloadFromUrl(manifest.getVersionUrl(), path);
        System.out.println("Download version config successfully\n");
    }

    @Override
    public void downloadAssetManifest(IVersionConfig versionConfig) throws Exception {
        System.out.println("Download asset config from "+versionConfig.getAssetsIndex().getUrl() +" to "+config.vanilla.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json");
        R.downloadFromUrl(versionConfig.getAssetsIndex().getUrl(), config.vanilla.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json");
        System.out.println("Download asset config successfully\n");
    }

    @Override
    public IAssetConfig readAssetConfig(IVersionConfig versionConfig) throws Exception {
        return Json.parse(R.readFile(config.vanilla.buildAssetsDir()+"/indexes/"+versionConfig.getProperty("assets")+".json"), AssetConfig.class);
    }

    @Override
    public IVersionManifest readVersionManifest() throws IOException {
        return Json.parse(R.readFile(config.vanilla.buildManifestPath()), VersionManifest.class);
    }

    @Override
    public IVersionConfig readVersionConfig(IVersion manifest) throws IOException {
        return Json.parse(R.readFile(config.vanilla.buildVersionConfigPath(manifest.getVersionID())), VersionConfig.class);
    }


}
