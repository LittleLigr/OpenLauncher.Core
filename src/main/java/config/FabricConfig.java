package config;

import config.base.GameModeConfig;

public class FabricConfig extends GameModeConfig {

    @Override
    public GameMode getMode() {
        return GameMode.fabric;
    }
}
