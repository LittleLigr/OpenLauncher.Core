package config;

public class OSconfig {

    public String defaultRoot;
    public String name,version,arch;

    public OSconfig() {
    }

    public OSconfig(String defaultRoot, String name, String version, String arch) {
        this.defaultRoot = defaultRoot;
        this.name = name;
        this.version = version;
        this.arch = arch;
    }
}
