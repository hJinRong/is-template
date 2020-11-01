package ist.construct;

import com.wps.api.tree.wps.WdParagraphAlignment;
import ist.node.conf.ContentConf;
import ist.node.conf.ModuleConf;
import ist.node.conf.OrderConf;
import ist.node.conf.StyleConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AssemblyLineTest {

    private AssemblyLine assemblyLine;

    @BeforeEach
    public void init() {
        assemblyLine = new AssemblyLine();
        assemblyLine.setProjRoot("C:\\Users\\rong\\Desktop\\adir");
    }


    @Test
    public void setProjRootTest() {
        assertEquals(assemblyLine.setProjRoot("C:\\Users\\rong\\Desktop\\adir").toString()
                , "C:\\Users\\rong\\Desktop\\adir");
        assertEquals(assemblyLine.setProjRoot("C:\\Users\\rong\\Desktop\\adir\\").toString()
                , "C:\\Users\\rong\\Desktop\\adir");
    }

    @Test
    public void constructModuleConfTest() {
        ModuleConf moduleConf = assemblyLine.constructTConf("\\bdir\\module.yaml", ModuleConf.class);
        assertEquals(moduleConf.id, "module");
        assertEquals(moduleConf.getStyles().size(), 2);
    }

    @Test
    public void constructOrderConfTest() {
        OrderConf orderConf = assemblyLine.constructTConf("\\order.yaml", OrderConf.class);
        assertEquals(orderConf.id, "123456");
        assertEquals(orderConf.getContents().size(), 3);
    }

    @Test
    public void constructContentConfTest() {
        ContentConf contentConf = assemblyLine.constructTConf("\\content1.yaml", ContentConf.class);
        assertEquals(contentConf.id, "content1");
        assertEquals(contentConf.shortContent, "foo, bar, baz");
        assertEquals(contentConf.getStyles().size(), 2);
        assertEquals(contentConf.getFiles().size(), 3);
    }

    @Test
    public void constructStyleConfTest() {
        StyleConf styleConf = assemblyLine.constructTConf("\\bdir\\style1.yaml", StyleConf.class);
        assertEquals(styleConf.getStyles().get(0).id, "style1-1");
        assertFalse(styleConf.getStyles().get(0).getFontStyles().isItalic());
        assertEquals(styleConf.getStyles().get(0).getParagraphStyles().getInnerAlignment()
                , WdParagraphAlignment.wdAlignParagraphCenter);
        assertTrue(styleConf.getStyles().get(1).getFontStyles().isItalic());
    }
}