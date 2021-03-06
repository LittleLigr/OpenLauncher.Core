package serialization;

import com.google.gson.*;
import org.reflections.Reflections;
import version.IVersion;
import version.IVersionManifest;
import version.standart.VersionM2;
import version.standart.VersionManifest;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

public abstract class Json {

    private static GsonBuilder builder = makeBuilder();

    private static GsonBuilder makeBuilder()
    {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(IVersion.class, (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, VersionM2.class));
        builder.registerTypeAdapter(IVersionManifest.class, (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, VersionManifest.class));

        Reflections reflections = new Reflections();
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

        return builder;
    }

    public static void defineBuilder(Class interfaceClass, Class implementClass) throws Exception {
        if(interfaceClass.isInterface())
            builder.registerTypeAdapter(interfaceClass.getClass(), (JsonDeserializer) (json, typeOfT, context) -> context.deserialize(json, implementClass.getClass()));
        else throw new Exception("First parameter should be interface");
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
