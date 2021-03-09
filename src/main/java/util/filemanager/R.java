package util.filemanager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class R {

    public static Path downloadFromUrl(String url, String path) throws IOException {
        URL webUrl = new URL(url);
        Path targetPath = new File(path).toPath();
        targetPath.toFile().mkdirs();
        targetPath.toFile().createNewFile();
        Files.copy(webUrl.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return targetPath;
    }

    public static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


}
