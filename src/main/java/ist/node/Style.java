package ist.node;

import java.util.List;

public class Style {
    public Style(String id) {
        this.id = id;
    }

    private final String id;
    List<Font> fontStyles;
    List<Paragraph> paragraphStyles;

    public String getId() {
        return id;
    }

    public List<Font> getFontStyles() {
        return fontStyles;
    }

    public List<Paragraph> getParagraphStyles() {
        return paragraphStyles;
    }

    public void appendFontStyle(Font fontStyle) {
        this.fontStyles.add(fontStyle);
    }

    public void appendParagraphStyle(Paragraph paragraphStyle) {
        this.paragraphStyles.add(paragraphStyle);
    }
}
