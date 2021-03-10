package version.base.serialization;

import com.google.gson.*;
import version.base.IVersion;
import version.base.IVersionConfig;
import version.base.IVersionManifest;
import version.base.nodes.IVersionArguments;
import version.fabric.nodes.FabricArguments;
import version.fabric.nodes.FabricLibrary;
import version.vanilla.VersionConfig;
import version.vanilla.VersionM2;
import version.vanilla.VersionManifest;
import version.vanilla.standartconfig.VersionArguments;
import version.vanilla.standartconfig.VersionDownloadDescription;
import version.vanilla.standartconfig.VersionRuleValue;

import java.util.Set;

public abstract class Json {

    private static GsonBuilder builder = makeBuilder();

    private static GsonBuilder makeBuilder()
    {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(IVersion.class, (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, VersionM2.class));
        builder.registerTypeAdapter(IVersionManifest.class, (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, VersionManifest.class));
        builder.registerTypeAdapter(IVersionConfig.class, (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, VersionConfig.class));
        builder.registerTypeAdapter(IVersionArguments.class, (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, VersionArguments.class));


        /*
        Set<Class<? extends ICustomDeserializator>> classes = reflections.getSubTypesOf(ICustomDeserializator.class);
        for (Class<? extends ICustomDeserializator> aClass : classes) {
            if(aClass.isInterface() == false)
            try {
                ICustomDeserializator instance = aClass.newInstance();
                if(instance.getBuilder() != null)
                   builder.registerTypeAdapter(instance.getClass(), instance.getBuilder());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        */

        builder.registerTypeAdapter(VersionRuleValue.class, new VersionRuleValue());
        builder.registerTypeAdapter(VersionArguments.class, new VersionArguments());
        builder.registerTypeAdapter(VersionDownloadDescription.class, new VersionDownloadDescription());
        builder.registerTypeAdapter(FabricLibrary.class, new FabricLibrary());
        builder.registerTypeAdapter(FabricArguments.class, new FabricArguments());

        /*
        Set<Class<? extends JsonDeserializer>> jsonDeserializatoImpl = reflections.getSubTypesOf(JsonDeserializer.class);
        for (Class<? extends JsonDeserializer> aClass : jsonDeserializatoImpl) {
            if(aClass.isInterface() == false)
                try {
                    JsonDeserializer instance = aClass.newInstance();
                    builder.registerTypeAdapter(instance.getClass(), instance);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
         */

        return builder;
    }

    public static <T> T parse(JsonArray json, Class<T> className)
    {
        return builder.create().fromJson(json, className);
    }

    public static <T> T parse(JsonObject json, Class<T> className)
    {
        return builder.create().fromJson(json, className);
    }

    public static <T> T parse(JsonElement json, Class<T> className)
    {
        return builder.create().fromJson(json, className);
    }

    public static <T> T parse(String json, Class<T> className)
    {
        return builder.create().fromJson(json, className);
    }

    public static <T> String parse(T obj, Class<T> className)
    {
        return builder.create().toJson(obj, className);
    }

}
