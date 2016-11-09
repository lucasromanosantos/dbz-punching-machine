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
        // Tendo o valor que quero desenhar, tenho que ajustar no grafico.
        // O valor vai ser:
        // measuredHeight - marginBottom - newValue
        float newValue = (value * yAxisSize) / max;
        return measuredHeight - marginBottom - newValue;
    }

    float[] scaleVector(float[] array, int max, int yAxisSize, int measuredHeight, int marginBottom) {
        // Nota para o i += 2: soh quero fazer para valores de x.
        for(int i=0; i<array.length; i += 2) {
            if(i > array.length)
                return array;
            array[i] = scale(array[i], max, yAxisSize, measuredHeight, marginBottom);
        }
        return array;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // centro e raio
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int marginBottom = 70;
        int marginLeft = 70;
        int marginRight = 70;
        int marginTop = 70;

        int i, j;
        int x, y;
        int xAxisSize = measuredWidth - marginLeft - marginRight;
        int yAxisSize = measuredHeight - marginTop - marginBottom;
        int scaleLineSize = 15;

        // Draw x axis
        canvas.drawLine(marginLeft, measuredHeight-marginBottom, measuredWidth-marginRight, measuredHeight-marginBottom, paintAxis);
        // Draw y axis
        canvas.drawLine(marginLeft, marginTop, marginLeft, measuredHeight-marginBottom, paintAxis);

        // THIS 2 FORS ARE NOT TESTED YET!!
        // Draw scale on x axis
        for(i=1; i<5; i++) {
            x = marginLeft + (i * xAxisSize / 4);
            canvas.drawLine(x, measuredHeight - marginBottom, x, measuredHeight - marginBottom + scaleLineSize, paintAxis);
            // We still need to draw the number (exactly i, position (x,y) should be something like: x-20, measuredHeight-marginBottom)
        }
        // Draw scale on y axis
        for(i=0; i<4; i++) {
            y = marginTop + (i * yAxisSize / 4);
            canvas.drawLine(marginLeft - scaleLineSize, y, marginLeft, y, paintAxis);
            // We still need to draw the number (what is the number?)
        }

        float max = 0;
        for(i=0; i<ptsY.length; i++) {
            if(ptsY[i] > max) {
                max = ptsY[i];
            }
        }

        float scaleMax = getNextHundred(max);

        float freq = (measuredWidth - marginLeft - marginRight) / ptsY.length;
        float[] pts = new float[2* ptsY.length + 1];

        for(i=0, j=0; i < ptsY.length; i++, j+=2) {
            pts[j] = i * freq;
            pts[j + 1] = ptsY[i];
        }

        pts = scaleVector(pts, max, yAxisSize, measuredHeight, marginBottom);

        canvas.drawLines(pts, paintAxis);
        // Provavelmente tem que chamar mais isso aqui.
        //canvas.drawLines(pts, 2, pts.length-2, paintAxis);
    }

    public float[] getPtsY() {
        return ptsY;
    }

    public void setPtsY(float[] ptsY) {
        this.ptsY = ptsY;
    }
}
