package ist.node.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wps.api.tree.wps.WdCellVerticalAlignment;
import com.wps.api.tree.wps.WdParagraphAlignment;

import java.nio.file.Path;

import static ist.util.ConstructTConf.constructTConf;

public class Cell {
    private final int row;
    private final int column;
    private final String content;
    private final String alignment;
    private final String verticalAlignment;
    private final String cellVerticalAlignment;
    private final String font;
    @JsonIgnore
    private WdParagraphAlignment innerAlignment;
    @JsonIgnore
    private WdCellVerticalAlignment innerCellVerticalAlignment;
    @JsonIgnore
    private Font innerFont;

    @JsonCreator
    private Cell(@JsonProperty("row") int row,
                 @JsonProperty("column") int column,
                 @JsonProperty("content") String content,
                 @JsonProperty("alignment") String alignment,
                 @JsonProperty("verticalAlignment") String verticalAlignment,
                 @JsonProperty("cellVerticalAlignment") String cellVerticalAlignment,
                 @JsonProperty("font") String font) {
        this.row = row;
        this.column = column;
        this.content = content;
        this.alignment = alignment;
        this.verticalAlignment = verticalAlignment;
        this.cellVerticalAlignment = cellVerticalAlignment;
        this.font = font;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getContent() {
        return content;
    }

    public String getAlignment() {
        return alignment;
    }

    public WdParagraphAlignment getInnerAlignment() {
        switch (alignment) {
            case "justify":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphJustify;
                break;
            case "thai":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphThaiJustify;
                break;
            case "hi":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphJustifyHi;
                break;
            case "low":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphJustifyLow;
                break;
            case "med":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphJustifyMed;
                break;
            case "center":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphCenter;
                break;
            case "left":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphLeft;
                break;
            case "right":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphRight;
                break;
            case "distribute":
                innerAlignment = WdParagraphAlignment.wdAlignParagraphDistribute;
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

    public String getVerticalAlignment() {
        return verticalAlignment;
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

    public String getFont() {
        return font;
    }

    public Font getInnerFont(String at) {
        innerFont = constructTConf(Path.of(at).resolve(font).toString(), Font.class);
        return innerFont;
    }
}
