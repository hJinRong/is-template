package ist.util;

import com.wps.api.tree.wps.Application;
import com.wps.api.tree.wps.ClassFactory;
import com.wps.api.tree.wps.Document;
import com.wps.runtime.utils.WpsArgs;
import com4j.Variant;

import java.awt.*;
import java.lang.management.ManagementFactory;

public final class WpsApplication {

    private static Application app;

    private WpsApplication() {}

    private static void init() {
        Rectangle windowBounds = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
        long winId = ManagementFactory.getRuntimeMXBean().getPid();
        WpsArgs wpsArgs = WpsArgs.ARGS_MAP.get(WpsArgs.App.WPS);
        wpsArgs.setWinid(winId);
        wpsArgs.setWidth(windowBounds.width);
        wpsArgs.setHeight(windowBounds.height);
        WpsApplication.app = ClassFactory.createApplication();
    }

    public static Application getApp() {
        if (WpsApplication.app == null) {
            init();
        }
        return app;
    }

    public static void show() {
        getApp().put_Visible(true);
    }

    public static void hide() {
        getApp().put_Visible(false);
    }

    public static Document newDoc() {
        return getApp().get_Documents()
                .Add(Variant.getMissing(), Variant.getMissing(), Variant.getMissing(), Variant.getMissing());
    }

    public static Document currentActiveDoc() {
        return getApp().get_ActiveDocument();
    }

}
