package ist.node.conf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import ist.node.entity.StyleItem;

public class StyleConf {
    private final List<StyleItem> styles;

    @JsonCreator
    public StyleConf(@JsonProperty("styles") List<StyleItem> styles) {
        this.styles = styles;
    }

    public List<StyleItem> getStyles() {
        return new ArrayList<>(styles);
    }
}
