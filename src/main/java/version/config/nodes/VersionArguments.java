package version.config.nodes;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VersionArguments implements JsonDeserializer<VersionArguments> {

    public String[] arguments;
    public VersionRuleValue[] rules;


    @Override
    public VersionArguments deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        VersionArguments arguments = new VersionArguments();

        JsonArray root = jsonElement.getAsJsonArray();

        ArrayList<String> argumentsList = new ArrayList<>();
        ArrayList<VersionRuleValue> rulesList = new ArrayList<>();

        Gson parse = new Gson();

        for (Iterator<JsonElement> i = root.iterator(); i.hasNext();)
        {
            JsonElement element = i.next();

            if (element.isJsonPrimitive())
                argumentsList.add(element.getAsString());
            else rulesList.add(parse.fromJson(element, VersionRuleValue.class));
        }

        arguments.arguments = (String[]) argumentsList.toArray();
        arguments.rules = (VersionRuleValue[]) rulesList.toArray();

        return arguments;
    }
}
