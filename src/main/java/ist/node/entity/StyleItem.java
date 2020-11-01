package ist.node.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StyleItem {
    public final String id;
    private final Font fontStyles;
    private final Paragraph paragraphStyles;

    @JsonCreator
    public StyleItem(@JsonProperty("id") String id,
                     @JsonProperty("fontStyles") Font fontStyles,
                     @JsonProperty("paragraphStyles") Paragraph paragraphStyles) {
        this.id = id;
        this.fontStyles = fontStyles;
        this.paragraphStyles = paragraphStyles;
    }

    public Font getFontStyles() {
        return fontStyles;
    }

    public Paragraph getParagraphStyles() {
        return paragraphStyles;
    }
}
