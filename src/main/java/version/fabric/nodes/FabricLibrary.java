package version.fabric.nodes;

import com.google.gson.*;
import version.base.nodes.IVersionDownload;
import version.base.nodes.IVersionLibrary;
import version.base.nodes.IVersionRule;
import version.base.nodes.VersionNativesDescription;
import version.base.serialization.Json;
import version.vanilla.standartconfig.VersionArtifact;
import version.vanilla.standartconfig.VersionDownloadDescription;
import version.vanilla.standartconfig.VersionRule;

import java.lang.reflect.Type;
import java.util.Arrays;

public class FabricLibrary implements IVersionLibrary, JsonDeserializer<FabricLibrary> {
    public VersionDownloadDescription downloads;
    public String name, url;
    public VersionNativesDescription natives;
    public VersionRule[] rules;

    @Override
    public String toString() {
        return "VersionLibrary{" +
                "downloads=" + downloads +
                ", name='" + name + '\'' +
                ", natives=" + natives +
                ", rules=" + Arrays.toString(rules) +
                '}';
    }

    @Override
    public IVersionDownload getDownloadDescription() {
        return downloads;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public VersionNativesDescription getNativeDescription() {
        return natives;
    }

    @Override
    public IVersionRule[] getRules() {
        return rules;
    }

    @Override
    public FabricLibrary deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        FabricLibrary library = new FabricLibrary();
        library.downloads = new VersionDownloadDescription();

        JsonObject root = jsonElement.getAsJsonObject();

        if(root.has("artifact"))
            library.downloads = Json.parse(root, VersionDownloadDescription.class);

        if(root.has("natives"))
            library.natives = Json.parse(root.get("natives"), VersionNativesDescription.class);

        if(root.has("rules"))
            library.rules = Json.parse(root.getAsJsonArray("rules"), VersionRule[].class);

        library.name = root.getAsJsonPrimitive("name").getAsString();
        if(root.has("url"))
            library.url = root.getAsJsonPrimitive("url").getAsString();

        return library;
    }
}
