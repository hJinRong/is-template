package ist.node.conf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ModuleConf {
    public final String id;
    private final List<String> styles;
    private final List<String> contents;

    @JsonCreator
    public ModuleConf(@JsonProperty("id") String id,
                      @JsonProperty("styles") List<String> styles,
                      @JsonProperty("contents") List<String> contents) {
        this.id = id;
        this.styles = styles;
        this.contents = contents;
    }

    public List<String> getStyles() {
        return new ArrayList<>(styles);
    }

    public List<String> getContents() {
        return new ArrayList<>(contents);
    }
}
