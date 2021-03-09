package version.base.nodes;

public class VersionNativesDescription {
    public String osx,linux,windows;

    public String getByName(String name) throws Exception {
        if(name.equals("osx"))
            return osx;
        else if(name.equals("windows"))
            return windows;
        else if(name.equals("linux"))
            return linux;
        else throw new Exception("OS with name "+name+" not detected");
    }


    @Override
    public String toString() {
        return "VersionNativesDescription{" +
                "osx='" + osx + '\'' +
                ", linux='" + linux + '\'' +
                ", windows='" + windows + '\'' +
                '}';
    }
}
