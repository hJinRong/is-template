package ist;

import com.wps.api.tree.wps.*;
import com4j.Variant;
import ist.construct.AssemblyLine;

import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.nio.file.Path;

import static ist.util.UpdateHelper.lastParagraph;
import static ist.util.WpsApplication.*;

public class Main {

    public static void main(String[] args) {
//        worker.setProjRoot("src\\main\\resources\\fakeproj");
        final String projRoot = String.valueOf(Path.of(args[0]).toAbsolutePath());
        if(!new File(projRoot).exists()) {
            System.out.println("PATH " + projRoot + " NOT EXISTS");
            System.exit(-1);
        }
        show();
        newDoc();
        AssemblyLine worker = new AssemblyLine();
        System.out.println("当前执行目录为：" + projRoot);
        worker.setProjRoot(args[0]);
        try {
            worker.start();
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        System.out.println("Construct the document successfully");

//        Style listStyle = currentActiveDoc().get_Styles().Add("List style", WdStyleType.wdStyleTypeList);
//        lastParagraph().get_Range().InsertAfter("hello world");

//        ListTemplate template = getApp().get_ListGalleries(WdListGalleryType.wdNumberGallery).get_ListTemplates().Item(3);
//        currentActiveDoc().get_Paragraphs().Item(last).get_Range().get_ListFormat().ApplyListTemplate(template,
//                        Variant.getMissing(),
//                        Variant.getMissing(),
//                        Variant.getMissing());



        int number = currentActiveDoc().get_Paragraphs().get_Count();
        System.out.println("段落数：" + number);
        int number1 = currentActiveDoc().get_InlineShapes().get_Count();
        System.out.println("内联数：" + number1);
        int number2 = currentActiveDoc().get_Lists().get_Count();
        System.out.println("列表数：" + number2);
    }
}
