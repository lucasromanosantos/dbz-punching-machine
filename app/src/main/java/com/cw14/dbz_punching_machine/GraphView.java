package com.cw14.dbz_punching_machine;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lucas on 11/5/16.
 */

public class GraphView extends View {

    public GraphView(Context context) {
        super(context);
        initGraphView();
    }
    public GraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initGraphView();
    }

    Paint paintAxis;
    float[] ptsY;

    public void initGraphView() {
        Resources r = this.getResources();

        paintAxis = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintAxis.setColor(Color.BLACK);
        paintAxis.setStrokeWidth(4);
        paintAxis.setStyle(Paint.Style.FILL_AND_STROKE);

        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // centro e raio
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int marginBottom = 50;
        int marginLeft = 50;
        int marginRight = 50;
        int marginTop = 50;

        // x
        canvas.drawLine(marginLeft, measuredHeight-marginBottom, measuredWidth-marginRight, measuredHeight-marginBottom, paintAxis);
        // y
        canvas.drawLine(marginLeft, marginTop, marginLeft, measuredHeight-marginBottom, paintAxis);

        float freq = (measuredWidth - marginLeft - marginRight) / ptsY.length;
        float[] pts = new float[2* ptsY.length + 1];
        int i, j;

        for(i=0, j=0; i < ptsY.length; i++, j+=2) {
            pts[j] = i * freq;
            pts[j + 1] = ptsY[i];
        }
        canvas.drawLines(pts, paintAxis);

    }

    public float[] getPtsY() {
        return ptsY;
    }

    public void setPtsY(float[] ptsY) {
        this.ptsY = ptsY;
    }

}
