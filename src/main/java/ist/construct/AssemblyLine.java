package ist.construct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.nio.file.Path;

public class AssemblyLine {

    private Path projRoot;

    public void setProjRoot(String root) {
        projRoot = Path.of(root);
    }

    public void constructOrderConf() {
        ObjectMapper mapper = new ObjectMapper();
        YAMLFactory factory = new YAMLFactory();
    }

}
