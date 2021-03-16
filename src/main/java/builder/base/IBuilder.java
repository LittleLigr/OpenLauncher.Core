package builder.base;

import config.Config;
import version.base.IVersionConfig;

public interface IBuilder {
    String buildRun() throws Exception;
    String setProperties(String command, Config config, IVersionConfig versionConfig) throws Exception;
}
