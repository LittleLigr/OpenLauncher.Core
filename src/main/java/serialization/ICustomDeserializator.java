package serialization;

import com.google.gson.JsonDeserializer;


public interface ICustomDeserializator {
    JsonDeserializer getBuilder();

}
