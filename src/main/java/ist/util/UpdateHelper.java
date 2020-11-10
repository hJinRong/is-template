package ist.util;

import com.wps.api.tree.kso.MsoTriState;
import com.wps.api.tree.wps.*;
import com4j.Variant;

import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ist.util.WpsApplication.currentActiveDoc;

public class UpdateHelper {

    public static void newLine() {
        int count = currentActiveDoc().get_Paragraphs().get_Count();
        currentActiveDoc().get_Paragraphs().Item(count).get_Range().InsertParagraphAfter();
    }

    public static Range supplement(String content, Style style) {
        int count = currentActiveDoc().get_Paragraphs().get_Count();
        Range currParag = currentActiveDoc().get_Paragraphs().Item(count).get_Range();
        currParag.InsertAfter(content);
        currParag.put_Style(style);
        return currParag;
    }

    public static void newParagraph(String content, Style paragStyle) throws NoSuchFieldException, NoSuchFileException {
        String[] split = content.split("\r\n");
        for (String s : split) {
            if (s.startsWith("![") && s.endsWith(")")) {
                String style, href;
                Matcher matcher1 = Pattern.compile("(?<=!\\[)(\\W|\\w)*(?=])").matcher(s);
                if (matcher1.find()) {
                    style = matcher1.group();
                } else {
                    throw new NoSuchFieldException("Invalid style at " + s);
                }
                Matcher matcher2 = Pattern.compile("(?<=\\()(\\W|\\w)*(?=\\))").matcher(s);
                if (matcher2.find()) {
                    href = matcher2.group();
                } else {
                    throw new NoSuchFileException("Invalid href at " + s);
                }
                insertImage(href, style);
                continue;
            }
            supplement(s, paragStyle);
            newLine();
        }
    }


    public static InlineShape insertImage(String img, String style) {
        Range lastRange = currentActiveDoc().Range(lastParagraph().get_Range().get_End(),
                lastParagraph().get_Range().get_End());
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

    public static void addDecoration(int start, int end, Style decoration) {
        currentActiveDoc().Range(start, end).put_Style(decoration);
    }

    public static void decorateWholeRange(Range range, Style decoration) {
        UpdateHelper.addDecoration(range.get_Start(), range.get_End(), decoration);
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
