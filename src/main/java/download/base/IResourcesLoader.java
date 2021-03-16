package download.base;

import config.Config;
import version.base.IAssetConfig;
import version.base.IVersion;
import version.base.IVersionConfig;
import version.base.IVersionManifest;
import version.base.nodes.IVersionLibrary;

import java.awt.event.ActionListener;
import java.io.IOException;

public interface IResourcesLoader {

    void downloadResources(IVersionConfig config, IAssetConfig assetConfig) throws Exception;

    void downloadVersionManifest() throws IOException;

    void downloadVersionConfig(IVersion manifest) throws Exception;

    void downloadAssetManifest(IVersionConfig versionConfig) throws Exception;

    void downloadLibraries(IVersionLibrary[] libraries) throws Exception;

    void downloadClient(IVersionConfig versionConfig) throws Exception;

    IAssetConfig readAssetConfig(IVersionConfig versionConfig) throws Exception;

    IVersionManifest readVersionManifest() throws Exception;

    IVersionConfig readVersionConfig(IVersion manifest) throws IOException;

    boolean assetConfigExist(IVersionConfig versionConfig);
    boolean versionManifestExist();
    boolean versionConfigExist(IVersion manifest);
    boolean allFilesExist(IVersion manifest);
    boolean clientExist(IVersionConfig versionConfig);
    boolean librariesExist(IVersionConfig versionConfig);

    void tick(float perc);
    void start();
    void end();

    void addDownloadKeeper(IResourceLoadKeeper keeper);

    boolean versionReady(IVersion manifest);

    boolean minecraftMetaReady();
}
