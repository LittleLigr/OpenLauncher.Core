package version.base.serialization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

public abstract class Xml {

    public static <T> T parse(String path, Class<T>type) throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(type);
        return (T) context.createUnmarshaller().unmarshal(new File(path));
    }
}
