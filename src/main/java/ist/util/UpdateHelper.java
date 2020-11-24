package ist.util;

import com.wps.api.tree.kso.MsoTriState;
import com.wps.api.tree.wps.*;
import com4j.Variant;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ist.util.ConstructTConf.constructTConf;
import static ist.util.WpsApplication.currentActiveDoc;

public class UpdateHelper {

    public static void newLine() {
        lastParagraph().get_Range().InsertParagraphAfter();
        lastParagraph().get_Format().Reset();
    }

    public static Range supplement(String content, Style style) {
        Range current = lastParagraph().get_Range();
        current.InsertAfter(content);
        current.put_Style(style);
        return current;
    }

    public static void newParagraph(String content, Style style, Path at) throws NoSuchFieldException, NoSuchFileException {
        String[] split = content.split("\r\n");
        for (String s : split) {
            if (s.startsWith("![table]")) {
                Matcher matcher = Pattern.compile("(?<=!\\[table]\\()(\\W|\\w)*(?=\\))").matcher(s);
                if (matcher.find()) {
                    insertTable(at, at.resolve(matcher.group()));
                    continue;
                } else {
                    throw new NoSuchFieldException("Invalid table at " + s);
                }
            }

            if (s.startsWith("![") && s.endsWith(")")) {
                String imageStyle, href;
                Matcher matcher1 = Pattern.compile("(?<=!\\[)(\\W|\\w)*(?=])").matcher(s);
                if (matcher1.find()) {
                    imageStyle = matcher1.group();
                } else {
                    throw new NoSuchFieldException("Invalid style at " + s);
                }
                Matcher matcher2 = Pattern.compile("(?<=\\()(\\W|\\w)*(?=\\))").matcher(s);
                if (matcher2.find()) {
                    href = matcher2.group();
                } else {
                    throw new NoSuchFileException("Invalid href at " + s);
                }
                insertImage(String.valueOf(at.resolve(href).toAbsolutePath()), imageStyle);
                continue;
            }

            supplement(s, style);
            newLine();
        }
    }


    public static InlineShape insertImage(String img, String style) {
        Range lastRange = currentActiveDoc().Range(lastParagraph().get_Range().get_End(),
                lastParagraph().get_Range().get_End());
        boolean imgExisted = new File(img).exists();
        if (!imgExisted)
            try {
                throw new NoSuchFileException("Image <" + img + "> does not exist.");
            } catch (NoSuchFileException e) {
                e.printStackTrace();
            }
        InlineShape shape = lastRange.get_InlineShapes().AddPicture(img,
                Variant.getMissing(), Variant.getMissing(), Variant.getMissing());
        Range shapeRange = lastInlineShape().get_Range();
        shapeRange.get_ParagraphFormat().put_Alignment(WdParagraphAlignment.wdAlignParagraphCenter);

        String[] styarr = Arrays.stream(style.split(",")).map(String::trim).toArray(String[]::new);
        float width = Float.parseFloat(styarr[0]),
                height = Float.parseFloat(styarr[1]);
        boolean lockAspectRatio = Boolean.parseBoolean(styarr[2]);
        String alignment = styarr[3];
        boolean wrap = Boolean.parseBoolean(styarr[4]);
        shape.put_Width(width);
        shape.put_Height(height);
        shape.put_LockAspectRatio(lockAspectRatio ? MsoTriState.msoTrue : MsoTriState.msoFalse);

        WdParagraphAlignment innerAlignment = null;
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
                    String tip = "This type of picture alignment <" + alignment + "> does not exist.";
                    throw new NoSuchFieldException(tip);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
        }
        shapeRange.get_ParagraphFormat().put_Alignment(innerAlignment);
        if (wrap) newLine();
        return shape;
    }

    public static void insertTable(Path at, Path tablePath) {
        ist.node.entity.Table table = constructTConf(tablePath.toString(), ist.node.entity.Table.class);
        if (table.getLineUnitBefore() >= 1) {
            lastParagraph().get_Range().get_ParagraphFormat().put_LineUnitAfter(table.getLineUnitBefore() - 1);
            newLine();
        }
        Table ui = currentActiveDoc().get_Tables().Add(lastParagraph().get_Range(), table.getRows(), table.getColumns(),
                Variant.getMissing(), Variant.getMissing());
        ui.get_Rows().put_Alignment(table.getInnerAlignment());
        ui.get_Range().get_ParagraphFormat().put_Alignment(table.getInnerCellAlignment());
        ui.put_TopPadding(table.getCellPadding()[0]);
        ui.put_RightPadding(table.getCellPadding()[1]);
        ui.put_BottomPadding(table.getCellPadding()[2]);
        ui.put_BottomPadding(table.getCellPadding()[3]);
        ui.put_Spacing(table.getCellSpacing());
        ui.get_Range().get_Cells().put_VerticalAlignment(table.getInnerCellVerticalAlignment());
        for (ist.node.entity.Cell cell :
                table.getCells()) {
            Cell cur = ui.Cell(cell.getRow(), cell.getColumn());
            Range cellRange = cur.get_Range();
            cellRange.InsertAfter(cell.getContent());
            cellRange.get_ParagraphFormat().put_Alignment(cell.getInnerAlignment());
            ist.node.entity.Font font = cell.getInnerFont(at.toString());
            cellRange.get_Font().put_Name(font.getFontFamily());
            cellRange.get_Font().put_Italic(font.isItalic() ? 1 : 0);
            cellRange.get_Font().put_Bold(font.isBold() ? 1 : 0);
            cellRange.get_Font().put_Size(font.getSize());
            cellRange.get_Font().put_Spacing(font.getSpacing());
            cellRange.get_Font().put_ColorIndex(font.getTextColor());
            cur.put_TopPadding(cell.getCellPadding()[0]);
            cur.put_RightPadding(cell.getCellPadding()[1]);
            cur.put_BottomPadding(cell.getCellPadding()[2]);
            cur.put_BottomPadding(cell.getCellPadding()[3]);
        }
        if (table.getLineUnitAfter() >= 1) {
            lastParagraph().get_Range().get_ParagraphFormat().put_LineUnitAfter(table.getLineUnitAfter() - 1);
            newLine();
        }
    }

    public static void addDecoration(int start, int end, Style decoration) {
        currentActiveDoc().Range(start, end).put_Style(decoration);
    }

    public static void decorateWholeRange(Range range, Style decoration) {
        addDecoration(range.get_Start(), range.get_End(), decoration);
    }

    public static Paragraph lastParagraph() {
        int last = currentActiveDoc().get_Paragraphs().get_Count();
        return currentActiveDoc().get_Paragraphs().Item(last);
    }

    public static InlineShape lastInlineShape() {
        int last = currentActiveDoc().get_InlineShapes().get_Count();
        return currentActiveDoc().get_InlineShapes().Item(last);
    }
}
