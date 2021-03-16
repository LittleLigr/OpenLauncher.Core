package version.fabric.nodes;

import com.google.gson.*;
import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionRule;
import version.base.serialization.Json;
import version.vanilla.standartconfig.VersionArguments;
import version.vanilla.standartconfig.VersionRule;
import version.vanilla.standartconfig.VersionRuleValue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class FabricArguments implements JsonDeserializer<FabricArguments>, IVersionArguments {

    public VersionRuleValue[] rules;

    @Override
    public String[] getStringArguments() {
        return new String[0];
    }

    @Override
    public IVersionRule[] getRuleArguments() {
        return rules;
    }

    @Override
    public FabricArguments deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        FabricArguments arguments = new FabricArguments();

        JsonArray root = jsonElement.getAsJsonArray();

        ArrayList<String> argumentsList = new ArrayList<>();
        ArrayList<VersionRuleValue> rulesList = new ArrayList<>();

        for (Iterator<JsonElement> i = root.iterator(); i.hasNext();)
        {
            JsonObject element = i.next().getAsJsonObject();

            VersionRuleValue value = new VersionRuleValue();
            if(element.has("rules"))
                if(element.getAsJsonArray("rules").iterator().hasNext())
                    value.rules = Json.parse(element.getAsJsonObject().getAsJsonArray("rules").get(0), VersionRule.class);
            value.value = Json.parse(element.getAsJsonArray("values"), String[].class);

            rulesList.add(value);
        }

        arguments.rules = rulesList.toArray(new VersionRuleValue[0]);

        return arguments;
    }
}
