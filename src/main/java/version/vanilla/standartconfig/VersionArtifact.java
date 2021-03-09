package version.vanilla.standartconfig;

import version.base.nodes.IVersionArtifact;

public class VersionArtifact implements IVersionArtifact {
    public String path,sha1,url,size,id,totalSize;

    @Override
    public String toString() {
        return "VersionArtifact{" +
                "path='" + path + '\'' +
                ", sha1='" + sha1 + '\'' +
                ", url='" + url + '\'' +
                ", size='" + size + '\'' +
                ", id='" + id + '\'' +
                ", totalSize='" + totalSize + '\'' +
                '}';
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getProperty(String id) throws Exception {
        switch (id)
        {
            case "sha1": return sha1;
            case "size": return size;
            case "totalSize": return totalSize;
        }

        throw new Exception("Property with id "+id+" not detected");
    }
}
