package com.cw14.dbz_punching_machine;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    Paint paint;

    public void initGraphView() {
        Resources r = this.getResources();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        // centro e raio
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int px = measuredWidth/2;
        int py = measuredHeight/2;

        canvas.drawPaint(paint);
        Path path = new Path();
        for(int i = 0; i < 50; ++i) {
            path.moveTo(4, i);
            path.lineTo(4, i);
        }

        path.close();

        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(path, paint);

        canvas.save();
    }


}
