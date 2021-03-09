package version.base.nodes;

public class OsDescription {
    public String name,version,arch;

    @Override
    public String toString() {
        return "OsDescription{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", arch='" + arch + '\'' +
                '}';
    }
}
