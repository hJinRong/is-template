package ist.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class ConstructTConf {
    public static <T> T constructTConf(String path, Class<T> valueType) {
        T tConf = null;
        File confFile = new File(path);
        try {
            if (!confFile.exists()) {
                String tip = "The file <" + confFile.getAbsolutePath() + "> does not exist.";
                throw new NoSuchFileException(tip);
            }
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            tConf = mapper.readValue(confFile, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tConf;
    }
}
