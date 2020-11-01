package ist.construct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ist.node.conf.OrderConf;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class AssemblyLine {

    private Path projRoot;

    public Path setProjRoot(String root) {
        projRoot = Path.of(root.endsWith("\\") ? root.substring(0, root.length() - 1) : root);
        return projRoot;
    }

    public Path getProjRoot() {
        return projRoot;
    }

    /**
     *
     * @param path 相对于根目录的路径，不必反斜杠“\”开头
     * @param valueType conf的类
     * @param <T> 将要构造的conf的类型
     * @return 构造的conf
     */
    public <T> T constructTConf(String path, Class<T> valueType) {
        T tConf = null;
        File confFile = new File(projRoot + path);
        try {
            if (!confFile.exists()) {
                String tip = "The file<" + confFile.getName() + "> does not exist.";
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
