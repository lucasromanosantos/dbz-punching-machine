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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by lucas on 11/5/16.
 */

public class GraphView extends View {
    Context graphContext;

    Paint paintAxis, paintValues;
    float[] ptsY;

    public GraphView(Context context) {
        super(context);
        initGraphView(context);
    }
    public GraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initGraphView(context);
    }

    public void initGraphView(Context context) {
        Resources r = this.getResources();

        paintAxis = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintAxis.setColor(Color.BLACK);
        paintAxis.setStrokeWidth(4);
        paintAxis.setStyle(Paint.Style.FILL_AND_STROKE);

        paintValues = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintValues.setColor(Color.BLUE);
        paintValues.setStrokeWidth(4);
        paintValues.setStyle(Paint.Style.FILL_AND_STROKE);

        this.invalidate();
    }

    float getNextHundred(float n) {
        // Get next full hundred bigger than n (if n = 35, returns 100; if n = 1399, returns 1400).
        float i=0;
        while(i <= n)
            i += 100;
        return i;
    }

    float scale(float value, float max, int yAxisSize, int measuredHeight, int marginBottom) {
        // This function should be called for every y value in the graph value's array.
        // Max should be the return from function 'getNextHundred'.
        // Funcionamento: É basicamente uma regra de 3.
        // Valor do Array - f
        // Max - yAxisSize
        // --- f = (ValorDoArray * yAxisSize) / Max
        float newValue = (value * yAxisSize) / max;
        return newValue;
    }

    void scaleVector(float[] array, float max, int yAxisSize, int measuredHeight, int marginBottom) {
        for(int i=0; i<array.length; i++) {
            array[i] = scale(array[i], max, yAxisSize, measuredHeight, marginBottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // centro e raio
        int i, j;
        int x, y;
        float max = 0;
        int marginTop = 70;
        int marginLeft = 90;
        int marginRight = 70;
        int marginBottom = 70;
        int scaleLineSize = 15;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int xAxisSize = measuredWidth - marginLeft - marginRight;
        int yAxisSize = measuredHeight - marginTop - marginBottom;

        // Draw x axis
        canvas.drawLine(marginLeft, measuredHeight-marginBottom, measuredWidth-marginRight, measuredHeight-marginBottom, paintAxis);
        // Draw y axis
        canvas.drawLine(marginLeft, marginTop, marginLeft, measuredHeight-marginBottom, paintAxis);

        // Draw scale on x axis
        for(i=1; i<4; i++) {
            x = marginLeft + (i * xAxisSize / 3);
            canvas.drawLine(x, measuredHeight - marginBottom, x, measuredHeight - marginBottom + scaleLineSize, paintAxis);
        }
        // Draw scale on y axis
        for(i=0; i<4; i++) {
            y = marginTop + (i * yAxisSize / 4);
            canvas.drawLine(marginLeft - scaleLineSize, y, marginLeft, y, paintAxis);
        }

        for(i=0; i<ptsY.length; i++) {
            if(ptsY[i] > max) {
                max = ptsY[i];
            }
        }

        float scaleMax = getNextHundred(max);
        float[] scaledPtsY = new float[ptsY.length];

        // Copy ptsY to scaledPtsY
        for(i=0; i< ptsY.length; i++) {
            scaledPtsY[i] = ptsY[i];
        }

        scaleVector(scaledPtsY, scaleMax, yAxisSize, measuredHeight, marginBottom);

        float freq = (measuredWidth - marginLeft - marginRight) / ptsY.length;
        float[] pts;
        pts = new float[2 * scaledPtsY.length + 1];

        for(i=0, j=0; i < scaledPtsY.length; i++, j+=2) {
            pts[j] = i * freq + marginLeft;
            pts[j+1] = scaledPtsY[i];
        }

        for(i=0; i+3<pts.length; i += 2) {
            canvas.drawLine(pts[i+0], measuredHeight - pts[i+1] - marginBottom,
                            pts[i+2], measuredHeight - pts[i+3] - marginBottom, paintValues);
        }
    }

    public float[] getPtsY() {
        return ptsY;
    }

    public void setPtsY(float[] ptsY) {
        this.ptsY = ptsY;
    }
}
