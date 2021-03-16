package version.vanilla.standartconfig;

import com.google.gson.*;
import version.base.nodes.IVersionArtifact;
import version.base.nodes.IVersionDownload;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VersionDownloadDescription implements JsonDeserializer<VersionDownloadDescription>, IVersionDownload {
    public VersionArtifact artifact;
    public HashMap<String, VersionArtifact> classifiers;


    @Override
    public VersionDownloadDescription deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        VersionDownloadDescription downloadDescription = new VersionDownloadDescription();
        JsonObject root = jsonElement.getAsJsonObject();
        if (root.has("artifact"))
            downloadDescription.artifact = new Gson().fromJson(root.get("artifact"), VersionArtifact.class);
        if (root.has("classifiers")) {
            downloadDescription.classifiers = new HashMap<>();
            Set<Map.Entry<String, JsonElement>> map = root.getAsJsonObject("classifiers").entrySet();

            for (Map.Entry<String, JsonElement> item : map)
                downloadDescription.classifiers.put(item.getKey(), jsonDeserializationContext.deserialize(item.getValue(), VersionArtifact.class));
        }
        return downloadDescription;
    }

    @Override
    public String toString() {
        return "VersionDownloadDescription{" +
                "artifact=" + artifact +
                ", classifiers=" + classifiers +
                '}';
    }

    @Override
    public IVersionArtifact getArtifact() {
        return artifact;
    }

    @Override
    public IVersionArtifact getClassifier(String id) throws Exception {
        if(classifiers == null)
            return null;
        return classifiers.get("natives-"+id);
    }
}
