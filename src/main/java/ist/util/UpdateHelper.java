package ist.util;

import com.wps.api.tree.kso.MsoTriState;
import com.wps.api.tree.wps.InlineShape;
import com.wps.api.tree.wps.Range;
import com.wps.api.tree.wps.Style;
import com.wps.api.tree.wps.WdParagraphAlignment;
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

    public static void supplement(String content) {
        int count = currentActiveDoc().get_Paragraphs().get_Count();
        currentActiveDoc().get_Paragraphs().Item(count).get_Range().InsertAfter(content);
    }

    public static int newParagraph(Range range, String content) throws NoSuchFieldException, NoSuchFileException {
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
            supplement(s);
            newLine();
        }
        return split.length;
    }


    public static InlineShape insertImage(String img, String style) {
        int lastParagraph = currentActiveDoc().get_Paragraphs().get_Count();
        Range lastRange = currentActiveDoc().Range(currentActiveDoc().get_Paragraphs().Item(lastParagraph).get_Range().get_End(),
                currentActiveDoc().get_Paragraphs().Item(lastParagraph).get_Range().get_End());
        InlineShape shape = lastRange.get_InlineShapes().AddPicture(img,
                Variant.getMissing(), Variant.getMissing(), Variant.getMissing());
        int lastInlineShape = currentActiveDoc().get_InlineShapes().get_Count();
        Range shapeRange = currentActiveDoc().get_InlineShapes().Item(lastInlineShape).get_Range();
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
}
