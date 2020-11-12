package ist.util;

import com.wps.api.tree.wps.WdParagraphAlignment;
import ist.construct.AssemblyLine;
import ist.node.conf.ContentConf;
import ist.node.conf.ModuleConf;
import ist.node.conf.OrderConf;
import ist.node.conf.StyleConf;
import ist.node.entity.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ist.util.ConstructTConf.constructTConf;
import static org.junit.Assert.*;

public class ConstructTConfTest {

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
        ModuleConf moduleConf = constructTConf(assemblyLine.getProjRoot().resolve("adir\\module.yaml").toString(), ModuleConf.class);
        assertEquals(moduleConf.id, "module-a");
        assertEquals(moduleConf.getStyles().size(), 1);
    }

    @Test
    public void constructOrderConfTest() {
        OrderConf orderConf = constructTConf(assemblyLine.getProjRoot().resolve("order.yaml").toString(), OrderConf.class);
        assertEquals(orderConf.getImports().size(), 1);
    }

    @Test
    public void constructContentConfTest() {
        ContentConf contentConf = constructTConf(assemblyLine.getProjRoot().resolve("adir\\content.yaml").toString(), ContentConf.class);
        assertEquals(contentConf.id, "content-a");
        assertEquals(contentConf.shortContent, "foo, bar, baz");
        assertEquals(contentConf.getStyles().size(), 2);
        assertEquals(contentConf.getFiles().size(), 1);
    }

    @Test
    public void constructStyleConfTest() {
        StyleConf styleConf = constructTConf(assemblyLine.getProjRoot().resolve("adir\\style1.yaml").toString(), StyleConf.class);
        assertEquals(styleConf.getStyles().get(0).id, "style1-1");
        assertFalse(styleConf.getStyles().get(0).getFontStyles().isItalic());
        assertEquals(styleConf.getStyles().get(0).getParagraphStyles().getInnerAlignment()
                , WdParagraphAlignment.wdAlignParagraphJustify);
        assertTrue(styleConf.getStyles().get(1).getFontStyles().isItalic());
        assertNull(styleConf.getStyles().get(1).getParagraphStyles());
    }

    @Test
    public void constructTableEntityConfTest() {
        Table table = constructTConf(assemblyLine.getProjRoot().resolve("adir\\table1.yaml").toString(), Table.class);
        assertEquals(table.getAlignment(), "center");
    }
}
