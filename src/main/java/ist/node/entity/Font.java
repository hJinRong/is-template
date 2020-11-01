package ist.node.entity;

import com.wps.api.tree.wps.WdColorIndex;

public final class Font {
    private String fontFamily = "Arial";
    private boolean bold = false;
    private boolean italic = false;
    private float size = 10.5f;
    private float spacing = 0f;
    //FIXME 使用字符串设置文字前景色
    private WdColorIndex textColor = WdColorIndex.wdAuto;

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    public WdColorIndex getTextColor() {
        return textColor;
    }

    public void setTextColor(WdColorIndex textColor) {
        this.textColor = textColor;
    }
}
