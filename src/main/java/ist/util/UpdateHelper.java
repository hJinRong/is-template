package ist.util;

import com.wps.api.tree.wps.Range;
import com.wps.api.tree.wps.Style;

import static ist.util.WpsApplication.currentActiveDoc;

public class UpdateHelper {

    public static void newLine(Range range) {
        range.InsertParagraphAfter();
    }

    public static void supplement(Range range, String content) {
        range.InsertAfter(content);
    }

    public static int newParagraph(Range range, String content) {
        String[] split = content.split("\r\n");
        for (String s : split) {
            supplement(range, s);
            newLine(range);
        }
        return split.length;
    }

    public static void addDecoration(int start, int end, Style decoration) {
        currentActiveDoc().Range(start, end).put_Style(decoration);
    }
}
