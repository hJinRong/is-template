package ist.node;

import com.wps.api.tree.wps.WdParagraphAlignment;

public final class Paragraph {
    private final String id;
    private WdParagraphAlignment alignment = WdParagraphAlignment.wdAlignParagraphJustify;
    private float firstlineIntent = 21f;
    private float lineSpacing = 1f;
    private float lineUnitBefore = 0f;
    private float lineUnitAfter = 0f;


    public Paragraph(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public WdParagraphAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(WdParagraphAlignment alignment) {
        this.alignment = alignment;
    }

    public float getFirstlineIntent() {
        return firstlineIntent;
    }

    public void setFirstlineIntent(float firstlineIntent) {
        this.firstlineIntent = firstlineIntent;
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
