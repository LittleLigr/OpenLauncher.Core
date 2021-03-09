package download.base;

import config.Config;
import version.base.IAssetConfig;
import version.base.IVersion;
import version.base.IVersionConfig;
import version.base.IVersionManifest;

import java.io.IOException;

public interface IResourcesLoader {

    void downloadResources(IVersionConfig config, IAssetConfig assetConfig) throws Exception;

    void downloadVersionManifest() throws IOException;

    void downloadVersionConfig(IVersion manifest) throws Exception;

    void downloadAssetManifest(IVersionConfig versionConfig) throws Exception;

    IAssetConfig readAssetConfig(IVersionConfig versionConfig) throws Exception;

    IVersionManifest readVersionManifest() throws Exception;

    IVersionConfig readVersionConfig(IVersion manifest) throws IOException;
}
