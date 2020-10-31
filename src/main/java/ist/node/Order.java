package ist.node;

import java.util.List;

public class Order {
    private final String id;
    private List<Content> contents;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void appendContent(Content content) {
        this.contents.add(content);
    }
}
