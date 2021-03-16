package version.vanilla;

import version.base.IAssetConfig;
import version.base.nodes.IVersionArtifact;

public class AssetConfig implements IAssetConfig {

    public IVersionArtifact[] artifacts;

    @Override
    public IVersionArtifact[] getArtifacts() {
        return new IVersionArtifact[0];
    }
}
