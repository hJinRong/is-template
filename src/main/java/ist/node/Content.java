package ist.node;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Content {
    private final String id;
    private String shortContent;
    private List<Style> styles = new LinkedList<>();
    private List<Path> files = new LinkedList<>();

    public Content(String id) {
        this.id = id;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    public List<Path> getFile() {
        return files;
    }

    public void setFile(List<Path> file) {
        this.files = file;
    }

    public String getId() {
        return id;
    }

    public void appendStyle(Style style) {
        this.styles.add(style);
    }

    public void appendFile(Path file) {
        this.files.add(file);
    }
}
