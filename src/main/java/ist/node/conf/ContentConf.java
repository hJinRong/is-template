package ist.node.conf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ContentConf {
    public final String id;
    public final String shortContent;
    public final String listTitle;
    private final List<String> styles;
    private final List<String> files;

    @JsonCreator
    public ContentConf(@JsonProperty("id") String id,
                       @JsonProperty("shortContent") String shortContent,
                       @JsonProperty("styles") List<String> styles,
                       @JsonProperty("files") List<String> files,
                       @JsonProperty("listTitle") String listTitle) {
        this.id = id;
        this.shortContent = shortContent;
        this.styles = styles;
        this.files = files;
        this.listTitle = listTitle;
    }

    public List<String> getStyles() {
        return new ArrayList<>(styles);
    }

    public List<String> getFiles() {
        return new ArrayList<>(files);
    }
}
