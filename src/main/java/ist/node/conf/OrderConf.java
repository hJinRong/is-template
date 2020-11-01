package ist.node.conf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OrderConf {
    public final String id;
    private final List<String> contents;

    @JsonCreator
    private OrderConf(@JsonProperty("id") String id,
                      @JsonProperty("contents") List<String> contents) {
        this.id = id;
        this.contents = contents;
    }

    public List<String> getContents() {
        return new ArrayList<>(contents);
    }
}
