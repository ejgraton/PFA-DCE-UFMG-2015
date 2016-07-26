package br.com.conexaozero.prafrentequeseanda_dce2105ufmg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class LinhaEntreCirculos extends View {
    private Path path;
    private Paint paint;
    private CircleAttr[] mCircleAttrs = new CircleAttr[2]; //Circulos 1 e 2
    private double angle; //Angulo de saida do circulo 1

    public LinhaEntreCirculos(Context context) {
        this(context, new CircleAttr(), new CircleAttr());
    }

    //Recebe xy1 e xy2 em centros
    public LinhaEntreCirculos(Context context, CircleAttr circ1, CircleAttr circ2) {
        super(context);
        init(circ1, circ2);
    }

    public LinhaEntreCirculos(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinhaEntreCirculos(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LinhaEntreCirculos, defStyle, 0);

        CircleAttr circ1 = new CircleAttr(
                a.getInt(R.styleable.LinhaEntreCirculos_raio1, 0),
                a.getInt(R.styleable.LinhaEntreCirculos_xCenter1, 0),
                a.getInt(R.styleable.LinhaEntreCirculos_yCenter1, 0));

        CircleAttr circ2 = new CircleAttr(
                a.getInt(R.styleable.LinhaEntreCirculos_raio2, 0),
                a.getInt(R.styleable.LinhaEntreCirculos_xCenter2, 0),
                a.getInt(R.styleable.LinhaEntreCirculos_yCenter2, 0));

        init(circ1, circ2);

        //        if (a.hasValue(R.styleable.LinhaEntreCirculos_exampleDrawable)) {
        //            mExampleDrawable = a.getDrawable(
        //                    R.styleable.LinhaEntreCirculos_exampleDrawable);
        //            mExampleDrawable.setCallback(this);
        //        }

        a.recycle();
    }

    public static int colorPFAVioletaEscuro = 0xFFA902D4;
    private void init(CircleAttr circ1, CircleAttr circ2) {
        path = new Path();
        paint = new Paint();
        paint.setStrokeWidth(12);
        paint.setColor(Color.MAGENTA);
        paint.setColor(colorPFAVioletaEscuro);
        //paint.setPathEffect(new DashPathEffect(new float[] {10, 8}, 0));

        setCirculos(circ1, circ2);
/*
        //calcula inicio e final da linha, calculando a reducao devido ao raio
        double rad = Math.toRadians(90);
        int x1 = mCentros.left + (int) (mRaio1 * Math.cos(rad));
        int y1 = mCentros.top + (int) (mRaio2 * Math.sin(rad));
*/
    }

    public void setCirculos(CircleAttr circ1, CircleAttr circ2){
        mCircleAttrs[0] = circ1;
        mCircleAttrs[1] = circ2;
    }

    public CircleAttr getCirculo(int index){
        return mCircleAttrs[index];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int contentWidth = getWidth();
        int contentHeight = getHeight();

        //paint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
        canvas.drawLine(mCircleAttrs[0].x, mCircleAttrs[0].y, mCircleAttrs[1].x, mCircleAttrs[1].y, paint);
/*
        path.moveTo(mCircleAttrs[0].x, mCircleAttrs[0].y);
        path.lineTo(mCircleAttrs[1].x, mCircleAttrs[1].y);
        canvas.drawPath(path, paint);
*/

        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);

//            mExampleDrawable.draw(canvas);
    }

}
