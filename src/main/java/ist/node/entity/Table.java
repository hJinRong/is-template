package ist.node.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wps.api.tree.wps.WdCellVerticalAlignment;
import com.wps.api.tree.wps.WdParagraphAlignment;
import com.wps.api.tree.wps.WdRowAlignment;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static ist.util.ConstructTConf.constructTConf;

public class Table {
    private final int rows;
    private final List<Cell> cells;
    private final int columns;
    private final float lineUnitBefore;
    private final float lineUnitAfter;
    private final String alignment;
    private final String cellAlignment;
    private final String cellVerticalAlignment;
    private final float[] cellPadding;
    private final float cellSpacing;
    private final String font;
    @JsonIgnore
    private WdRowAlignment innerAlignment;
    @JsonIgnore
    private WdParagraphAlignment innerCellAlignment;
    @JsonIgnore
    private WdCellVerticalAlignment innerCellVerticalAlignment;
    @JsonIgnore
    private Font innerFont;

    @JsonCreator
    private Table(@JsonProperty("rows") int rows,
                  @JsonProperty("columns") int columns,
                  @JsonProperty("lineUnitBefore") float lineUnitBefore,
                  @JsonProperty("lineUnitAfter") float lineUnitAfter,
                  @JsonProperty("alignment") String alignment,
                  @JsonProperty("cellPadding") float[] cellPadding,
                  @JsonProperty("cellSpacing") float cellSpacing,
                  @JsonProperty("cellAlignment") String cellAlignment,
                  @JsonProperty("cellVerticalAlignment") String cellVerticalAlignment,
                  @JsonProperty("font") String font,
                  @JsonProperty("cells") List<Cell> cells) {
        this.rows = rows;
        this.columns = columns;
        this.lineUnitBefore = lineUnitBefore;
        this.lineUnitAfter = lineUnitAfter;
        this.alignment = alignment;
        this.cellPadding = cellPadding;
        this.cellSpacing = cellSpacing;
        this.cellAlignment = cellAlignment;
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

    public float getLineUnitBefore() {
        return lineUnitBefore;
    }

    public float getLineUnitAfter() {
        return lineUnitAfter;
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

    public WdParagraphAlignment getInnerCellAlignment() {
        if (cellAlignment == null) {
            innerCellAlignment = WdParagraphAlignment.wdAlignParagraphCenter;
        } else {
            switch (cellAlignment) {
                case "justify":
                    innerCellAlignment = WdParagraphAlignment.wdAlignParagraphJustify;
                    break;
                case "center":
                    innerCellAlignment = WdParagraphAlignment.wdAlignParagraphCenter;
                    break;
                case "left":
                    innerCellAlignment = WdParagraphAlignment.wdAlignParagraphLeft;
                    break;
                case "right":
                    innerCellAlignment = WdParagraphAlignment.wdAlignParagraphRight;
                    break;
                case "distribute":
                    innerCellAlignment = WdParagraphAlignment.wdAlignParagraphDistribute;
                    break;
                default:
                    try {
                        String tip = "This type of cell alignment <" + alignment + "> does not exist.";
                        throw new NoSuchFieldException(tip);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
            }
        }
        return innerCellAlignment;
    }

    public float[] getCellPadding() {
        if (cellPadding == null) {
            return new float[]{0, 0, 0, 0};
        } else {
            float[] rt = new float[4];
            switch (cellPadding.length) {
                case 1:
                    Arrays.fill(rt, cellPadding[0]);
                    break;
                case 2:
                    rt[0] = cellPadding[0];
                    rt[1] = cellPadding[1];
                    rt[2] = rt[0];
                    rt[3] = rt[1];
                    break;
                case 3:
                    rt[0] = cellPadding[0];
                    rt[1] = cellPadding[1];
                    rt[2] = cellPadding[2];
                    rt[3] = rt[1];
                    break;
                case 4:
                    rt[0] = cellPadding[0];
                    rt[1] = cellPadding[1];
                    rt[2] = cellPadding[2];
                    rt[3] = cellPadding[3];
                    break;
                default:
                    try {
                        throw new NoSuchFieldException("The number of parameter is too much.");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
            }
            return rt;
        }
    }

    public float getCellSpacing() {
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
