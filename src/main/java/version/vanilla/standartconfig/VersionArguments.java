package version.vanilla.standartconfig;

import com.google.gson.*;
import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionRule;

import java.lang.reflect.Type;
import java.util.*;

public class VersionArguments implements JsonDeserializer<VersionArguments>, IVersionArguments {

    public String[] arguments;
    public VersionRuleValue[] rules;

    @Override
    public VersionArguments deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        VersionArguments arguments = new VersionArguments();

        JsonArray root = jsonElement.getAsJsonArray();

        ArrayList<String> argumentsList = new ArrayList<>();
        ArrayList<VersionRuleValue> rulesList = new ArrayList<>();

        for (Iterator<JsonElement> i = root.iterator(); i.hasNext();)
        {
            JsonElement element = i.next();

            if (element.isJsonPrimitive())
                argumentsList.add(element.getAsString());
            else if(element.isJsonArray())
                rulesList.add(jsonDeserializationContext.deserialize(element, VersionRuleValue.class));
        }

        arguments.arguments = argumentsList.toArray(new String[0]);
        arguments.rules = rulesList.toArray(new VersionRuleValue[0]);

        return arguments;
    }

    @Override
    public String toString() {
        return "VersionArguments{" +
                "arguments=" + Arrays.toString(arguments) +
                ", rules=" + Arrays.toString(rules) +
                '}';
    }

    @Override
    public String[] getStringArguments() {
        return arguments;
    }

    @Override
    public IVersionRule[] getRuleArguments() {
        return rules;
    }
}
