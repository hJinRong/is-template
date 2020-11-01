package ist.util;

import com.wps.api.tree.wps.Range;

public class UpdateHelper {

    public static void insertLineBreaks(Range range) {
        range.InsertParagraphAfter();
    }

    public static void newParagraph(Range range, String content) {
        range.InsertAfter(content);
    }
}
