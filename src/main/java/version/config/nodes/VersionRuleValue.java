package version.config.nodes;

import com.google.gson.*;

import java.lang.reflect.Type;

public class VersionRuleValue implements JsonDeserializer<VersionRuleValue> {
    public VersionRule[] rules;
    public String[] value;

    @Override
    public VersionRuleValue deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        System.out.println("ok");
        VersionRuleValue ruleValue = new VersionRuleValue();
        JsonObject root = jsonElement.getAsJsonObject();

        if(root.has("rules"))
        {
            JsonElement rulesRoot = root.get("rules");
            if(rulesRoot.isJsonArray())
                ruleValue.rules = new Gson().fromJson(rulesRoot, VersionRule[].class);
            else if(rulesRoot.isJsonObject())
                ruleValue.rules = new VersionRule[]{new Gson().fromJson(rulesRoot, VersionRule.class)};
        }

        if(root.has("value"))
        {

            JsonElement valueRoot = root.get("value");
            if(valueRoot.isJsonArray())
                ruleValue.value = new Gson().fromJson(valueRoot, String[].class);
            else if(valueRoot.isJsonPrimitive())
                ruleValue.value = new String[]{valueRoot.getAsString()};
        }

        return ruleValue;
    }
}
