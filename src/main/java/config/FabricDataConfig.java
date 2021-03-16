package config;

import config.base.GameMode;
import config.base.GameModeConfig;

public class FabricDataConfig extends GameModeConfig {

    @Override
    public GameMode getMode() {
        return GameMode.fabric;
    }
}
