package ist.node.entity;

import com.wps.api.tree.wps.WdParagraphAlignment;

public final class Paragraph {
    private WdParagraphAlignment innerAlignment = WdParagraphAlignment.wdAlignParagraphJustify;
    private String alignment = "justify";
    private float textIntent = 21f;
    private float lineSpacing = 1f;
    private float lineUnitBefore = 0f;
    private float lineUnitAfter = 0f;

    public WdParagraphAlignment getInnerAlignment() {
        setInnerAlignment(alignment);
        return innerAlignment;
    }

    public void setInnerAlignment(String alignment) {
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
                    String tip = "This type of paragraph alignment <" + alignment + "> does not exist.";
                    throw new NoSuchFieldException(tip);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
        }
    }

    public void setInnerAlignment(WdParagraphAlignment innerAlignment) {
        this.innerAlignment = innerAlignment;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public float getTextIntent() {
        return textIntent;
    }

    public void setTextIntent(float textIntent) {
        this.textIntent = textIntent;
    }

    public float getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public float getLineUnitBefore() {
        return lineUnitBefore;
    }

    public void setLineUnitBefore(float lineUnitBefore) {
        this.lineUnitBefore = lineUnitBefore;
    }

    public float getLineUnitAfter() {
        return lineUnitAfter;
    }

    public void setLineUnitAfter(float lineUnitAfter) {
        this.lineUnitAfter = lineUnitAfter;
    }
}
