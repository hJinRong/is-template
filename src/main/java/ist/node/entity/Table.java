package ist.node.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wps.api.tree.wps.WdCellVerticalAlignment;
import com.wps.api.tree.wps.WdRowAlignment;

import java.nio.file.Path;
import java.util.List;

import static ist.util.ConstructTConf.constructTConf;

public class Table {
    private final int rows;
    private final List<Cell> cells;
    private final int columns;
    private final String alignment;
    private final String cellVerticalAlignment;
    private final String cellPadding;
    private final String cellSpacing;
    private final String font;
    @JsonIgnore
    private WdRowAlignment innerAlignment;
    @JsonIgnore
    private WdCellVerticalAlignment innerCellVerticalAlignment;
    @JsonIgnore
    private Font innerFont;

    @JsonCreator
    private Table(@JsonProperty("rows") int rows,
                  @JsonProperty("columns") int columns,
                  @JsonProperty("alignment") String alignment,
                  @JsonProperty("cellPadding") String cellPadding,
                  @JsonProperty("cellSpacing") String cellSpacing,
                  @JsonProperty("cellVerticalAlignment") String cellVerticalAlignment,
                  @JsonProperty("font") String font,
                  @JsonProperty("cells") List<Cell> cells) {
        this.rows = rows;
        this.columns = columns;
        this.alignment = alignment;
        this.cellPadding = cellPadding;
        this.cellSpacing = cellSpacing;
        this.cellVerticalAlignment = cellVerticalAlignment;
        this.cells = cells;
        this.font = font;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public WdRowAlignment getInnerAlignment() {
        switch (alignment) {
            case "top":
                innerAlignment = WdRowAlignment.wdAlignRowLeft;
                break;
            case "center":
                innerAlignment = WdRowAlignment.wdAlignRowCenter;
                break;
            case "left":
                innerAlignment = WdRowAlignment.wdAlignRowRight;
                break;

            default:
                try {
                    String tip = "This type of table alignment <" + alignment + "> does not exist.";
                    throw new NoSuchFieldException(tip);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
        }
        return innerAlignment;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getCellPadding() {
        return cellPadding;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public WdCellVerticalAlignment getInnerCellVerticalAlignment() {
        switch (cellVerticalAlignment) {
            case "Top":
                innerCellVerticalAlignment = WdCellVerticalAlignment.wdCellAlignVerticalTop;
                break;
            case "center":
                innerCellVerticalAlignment = WdCellVerticalAlignment.wdCellAlignVerticalCenter;
                break;
            case "bottom":
                innerCellVerticalAlignment = WdCellVerticalAlignment.wdCellAlignVerticalBottom;
                break;
            default:
                try {
                    throw new NoSuchFieldException("This type of cell vertical alignment <" + cellVerticalAlignment + "> does not exists");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
        }
        return innerCellVerticalAlignment;
    }


    public String getCellVerticalAlignment() {
        return cellVerticalAlignment;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public String getFont() {
        return font;
    }

    public Font getInnerFont(String at) {
        innerFont = constructTConf(Path.of(at).resolve(font).toString(), Font.class);
        return innerFont;
    }
}
