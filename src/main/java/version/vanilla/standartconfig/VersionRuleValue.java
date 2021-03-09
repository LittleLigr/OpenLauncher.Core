package version.vanilla.standartconfig;

import com.google.gson.*;
import version.base.nodes.IVersionRule;
import version.base.nodes.OsDescription;

import java.lang.reflect.Type;
import java.util.Arrays;

public class VersionRuleValue implements JsonDeserializer<VersionRuleValue>, IVersionRule {
    public VersionRule rules;
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
                ruleValue.rules = new Gson().fromJson(rulesRoot.getAsJsonArray().get(0), VersionRule.class);
            else if(rulesRoot.isJsonObject())
                ruleValue.rules = jsonDeserializationContext.deserialize(rulesRoot, VersionRule.class);
        }

        if(root.has("value"))
        {
            JsonElement valueRoot = root.get("value");
            if(valueRoot.isJsonArray())
                ruleValue.value = jsonDeserializationContext.deserialize(valueRoot, String[].class);
            else if(valueRoot.isJsonPrimitive())
                ruleValue.value = new String[]{valueRoot.getAsString()};
        }

        return ruleValue;
    }

    @Override
    public String toString() {
        return "VersionRuleValue{" +
                "rules=" + rules +
                ", value=" + Arrays.toString(value) +
                '}';
    }

    @Override
    public String getAction() {
        return rules.getAction();
    }

    @Override
    public OsDescription getOsDescription() {
        return rules.getOsDescription();
    }

    @Override
    public String[] getValues() {
        return value;
    }
}
