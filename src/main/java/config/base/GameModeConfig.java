package config.base;

import config.Config;

public class GameModeConfig {

    public String manifestUrl, manifestFileName;
    public String assetsUrl, assetsPath, assetFileName;
    public String resourcesUrl;

    public transient Config config;

    public String getManifestUrl() {
        return manifestUrl;
    }


    public String getResourcesUrl() {
        return resourcesUrl;
    }

    public String getManifestFileName() {
        return manifestFileName;
    }

    public GameMode getMode() {
        return GameMode.vanilla;
    }

    public String buildAssetsDir() { return config.root+"/assets"; }

    public String buildLibraryPath(String librarySubPath){return config.root+"/libraries/"+librarySubPath;}

    public String buildJarPath(String id){return config.root+"/versions/"+id+"/"+id+".jar";}

    public String buildNativesPath(String id){return config.root+"/versions/"+id+"/natives";}

    public String buildVersionConfigPath(String id){return config.root+"/versions/"+id+"/"+id+".json";}

    public String buildManifestPath(){ return config.root+"/versions/"+manifestFileName;}

    public String buildVersionsPath(){return config.root+"/versions";}

}
