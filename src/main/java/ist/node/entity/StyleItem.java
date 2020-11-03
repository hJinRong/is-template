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

    public void mergeStyles(StyleItem merged) {
        if (merged.getFontStyles() != null) {
            this.fontStyles.setFontFamily(merged.fontStyles.getFontFamily());
            this.fontStyles.setBold(merged.fontStyles.isBold());
            this.fontStyles.setItalic(merged.fontStyles.isItalic());
            this.fontStyles.setSize(merged.fontStyles.getSize());
            this.fontStyles.setSpacing(merged.fontStyles.getSpacing());
            this.fontStyles.setTextColor(merged.fontStyles.getTextColor());
        }

        if (merged.getParagraphStyles() != null) {
            this.paragraphStyles.setAlignment(merged.paragraphStyles.getAlignment());
            this.paragraphStyles.setTextIntent(merged.paragraphStyles.getTextIntent());
            this.paragraphStyles.setLineSpacing(merged.paragraphStyles.getLineSpacing());
            this.paragraphStyles.setLineUnitBefore(merged.paragraphStyles.getLineUnitBefore());
            this.paragraphStyles.setLineUnitAfter(merged.paragraphStyles.getLineUnitAfter());
        }
    }
}
