package ist.node.conf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OrderConf {
    private final List<String> imports;

    @JsonCreator
    private OrderConf(@JsonProperty("imports") List<String> imports) {
        this.imports = imports;
    }

    public List<String> getImports() {
        return new ArrayList<>(imports);
    }
}
