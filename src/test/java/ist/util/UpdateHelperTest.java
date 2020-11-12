package ist.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class UpdateHelperTest {

    @Test
    public void regexTest() {
        String s = "![50.0, 50.0, true, center, true](C:\\Users\\rong\\Desktop\\adir\\img\\b.jpg)";
        Matcher matcher1 = Pattern.compile("(?<=!\\[)(\\W|\\w)*(?=])").matcher(s);
        Matcher matcher2 = Pattern.compile("(?<=\\()(\\W|\\w)*(?=\\))").matcher(s);
        assertTrue(matcher1.find());
        assertTrue(matcher2.find());
        assertEquals(matcher1.group(), "50.0, 50.0, true, center, true");
        assertEquals(matcher2.group(), "C:\\Users\\rong\\Desktop\\adir\\img\\b.jpg");
    }

    @Test
    public void stringTrimTest() {
        String s = "50.0, 50.0, true, center, true";
        String[] sarr = s.split(",");
        assertArrayEquals(Arrays.stream(sarr).map(String::trim).toArray(String[]::new),
                new String[]{"50.0", "50.0", "true", "center", "true"});
    }
}
