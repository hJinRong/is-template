package ist.construct;

import com.wps.api.tree.wps.Style;
import com.wps.api.tree.wps.WdStyleType;
import ist.node.conf.ContentConf;
import ist.node.conf.ModuleConf;
import ist.node.conf.OrderConf;
import ist.node.conf.StyleConf;
import ist.node.entity.Font;
import ist.node.entity.Paragraph;
import ist.node.entity.StyleItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ist.util.ConstructTConf.constructTConf;
import static ist.util.UpdateHelper.newParagraph;
import static ist.util.WpsApplication.currentActiveDoc;

public class AssemblyLine {

    private Path projRoot;

    public Path setProjRoot(String root) {
        projRoot = Path.of(root);
        return projRoot;
    }

    public Path getProjRoot() {
        return projRoot;
    }

    public void start() throws IOException, NoSuchFieldException {
        //read order
        OrderConf orderConf = constructTConf(projRoot.resolve("order.yaml").toString(), OrderConf.class);
        List<String> imports = orderConf.getImports();
        for (String i : imports) {
            ModuleConf module = constructTConf(projRoot.resolve(i).resolve("module.yaml").toString(), ModuleConf.class);
            //collect styles
            ArrayList<String> styles = (ArrayList<String>) module.getStyles();
            HashMap<String, StyleItem> moduleScopeStyles = new HashMap<>();
            for (String s : styles) {
                StyleConf styleConf = constructTConf(projRoot.resolve(i).resolve(s).toString(), StyleConf.class);
                ArrayList<StyleItem> items = (ArrayList<StyleItem>) styleConf.getStyles();
                for (StyleItem item : items) {
                    moduleScopeStyles.put(item.id, item);
                }
            }
            //collect supplementary content
            ArrayList<String> contents = (ArrayList<String>) module.getContents();
            for (String c : contents) {
                ContentConf contentConf = constructTConf(projRoot.resolve(i).resolve(c).toString(), ContentConf.class);
                ArrayList<String> files = (ArrayList<String>) contentConf.getFiles();

                ArrayList<String> contentStyles = (ArrayList<String>) contentConf.getStyles();
                StyleItem mergedStyle = new StyleItem("merged", new Font(), new Paragraph());
                for (String co : contentStyles) {
                    mergedStyle.mergeStyles(moduleScopeStyles.get(co));
                }

                //use merged style on tmp style
                Style tmp = currentActiveDoc().get_Styles().Add("tmp", WdStyleType.wdStyleTypeParagraph);
                tmp.get_Font().put_Name(mergedStyle.getFontStyles().getFontFamily());
                tmp.get_Font().put_Bold(mergedStyle.getFontStyles().isBold() ? 1 : 0);
                tmp.get_Font().put_Italic(mergedStyle.getFontStyles().isItalic() ? 1 : 0);
                tmp.get_Font().put_Size(mergedStyle.getFontStyles().getSize());
                tmp.get_Font().put_Spacing(mergedStyle.getFontStyles().getSpacing());
                tmp.get_ParagraphFormat().put_Alignment(mergedStyle.getParagraphStyles().getInnerAlignment());
                tmp.get_ParagraphFormat().put_FirstLineIndent(mergedStyle.getParagraphStyles().getTextIntent());
                tmp.get_ParagraphFormat().put_LineSpacing(mergedStyle.getParagraphStyles().getLineSpacing());
                tmp.get_ParagraphFormat().put_LineUnitBefore(mergedStyle.getParagraphStyles().getLineUnitBefore());
                tmp.get_ParagraphFormat().put_LineUnitAfter(mergedStyle.getParagraphStyles().getLineUnitAfter());

                //update document and add style
                for (String f : files) {
                    String fc = Files.readString(projRoot.resolve(i).resolve(f));
                    newParagraph(fc, tmp, projRoot.resolve(i));
                }
            }
        }

    }
}
