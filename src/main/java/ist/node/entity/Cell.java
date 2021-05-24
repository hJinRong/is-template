package ist.node.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wps.api.tree.wps.WdParagraphAlignment;

import java.nio.file.Path;
import java.util.Arrays;

import static ist.util.ConstructTConf.constructTConf;

public class Cell {
    private final int row;
    private final int column;
    private final String content;
    private final String alignment;
    private final float[] cellPadding;
    private final String font;
    @JsonIgnore
    private WdParagraphAlignment innerAlignment;
    @JsonIgnore
    private Font innerFont;

    @JsonCreator
    private Cell(@JsonProperty("row") int row,
                 @JsonProperty("column") int column,
                 @JsonProperty("content") String content,
                 @JsonProperty("alignment") String alignment,
                 @JsonProperty("cellPadding") float[] cellPadding,
                 @JsonProperty("font") String font) {
        this.row = row;
        this.column = column;
        this.content = content;
        this.alignment = alignment;
        this.cellPadding = cellPadding;
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

    public WdParagraphAlignment getInnerAlignment() {
        if (alignment == null) {
            innerAlignment = WdParagraphAlignment.wdAlignParagraphCenter;
        } else {
            switch (alignment) {
                case "justify":
                    innerAlignment = WdParagraphAlignment.wdAlignParagraphJustify;
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
                        String tip = "This type of cell alignment <" + alignment + "> does not exist.";
                        throw new NoSuchFieldException(tip);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
            }
        }
        return innerAlignment;
    }

    public Font getInnerFont(String at) {
        innerFont = constructTConf(Path.of(at).resolve(font).toString(), Font.class);
        return innerFont;
    }
}
