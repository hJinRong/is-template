package ist.node.conf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ContentConf {
    public final String id;
    private final List<String> styles;
    private final List<String> files;

    @JsonCreator
    public ContentConf(@JsonProperty("id") String id,
                       @JsonProperty("styles") List<String> styles,
                       @JsonProperty("files") List<String> files) {
        this.id = id;
        this.styles = styles;
        this.files = files;
    }

    public List<String> getStyles() {
        return new ArrayList<>(styles);
    }

    public List<String> getFiles() {
        return new ArrayList<>(files);
    }
}
