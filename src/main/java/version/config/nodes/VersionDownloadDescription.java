package version.config.nodes;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class VersionDownloadDescription implements JsonDeserializer<VersionDownloadDescription> {
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
                downloadDescription.classifiers.put(item.getKey(), new Gson().fromJson(item.getValue(), VersionArtifact.class));
        }
        return downloadDescription;
    }
}
