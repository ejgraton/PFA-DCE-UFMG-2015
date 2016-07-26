package br.com.conexaozero.prafrentequeseanda_dce2105ufmg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RemoteViews;

import java.util.HashMap;

/**
 * Created by User on 19/04/15.
 */
@RemoteViews.RemoteView
public class PFALayout extends ViewGroup {
    private int raio = 0, xc = 0, yc = 0; //raio, x centro e y centro

    private HashMap<View, LinhaEntreCirculos> mLinhasConexao;

/*
    //Posicoes preferenciais em graus (convertidos em radianos) -
    private static final double posRad[] = new double[] {
            Math.toRadians(-1),   //Reestruturacao DCE fica na pos central
            Math.toRadians(30),   //Instituicoes Autonomas
            Math.toRadians(90),   //Esporte Universitario
            Math.toRadians(150),  //Qualidade Ensino Pesquisa Extensao
            Math.toRadians(210),  //Seguranca
            Math.toRadians(270),  //PFA que recebera DragAndDrop
            Math.toRadians(330)}; //Assistencia Estudantil
*/

    public PFALayout(Context context) { super(context);  }

    public PFALayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PFALayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.PFALayout);
        CharSequence o = arr.getString(R.styleable.PFALayout_layout_position);

        // do something here with your custom property

        arr.recycle();
    }

//    public Destructor;

    // Any layout manager that doesn't scroll will want this.
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public void addView( View child, int index, ViewGroup.LayoutParams params ) {
        super.addView(child, index, params);
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();

        if (lp.position == LayoutParams.POSITION_DROPABLE) {
            ScaleAnimation animation = new ScaleAnimation(
                    (float) 0.97, (float) 1.0,
                    (float) 0.97, (float) 1.0,
                    Animation.RELATIVE_TO_SELF, (float) 0.5,  //ponto fixo no centro
                    Animation.RELATIVE_TO_SELF, (float) 0.5); //ponto fixo no centro
            long mMeduimAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);
            animation.setDuration(mMeduimAnimationDuration);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(Animation.INFINITE);
            child.setAnimation(animation);
        }
        else if (lp.position != LayoutParams.POSITION_CENTER) {
            if (mLinhasConexao == null)
                mLinhasConexao = new HashMap<View, LinhaEntreCirculos>();

            //Para cada novo item adiciona uma linha
            LinhaEntreCirculos linhaNova = new LinhaEntreCirculos(child.getContext());
            mLinhasConexao.put(child, linhaNova);
            super.addView(linhaNova, 0, params); //index 0 para fixar abaixo dos demais views
        }
    }

    @Override
    public void removeView( View child) {
        if(mLinhasConexao != null)
            mLinhasConexao.remove(child);
        super.removeView(child);
    }

    public HashMap<View, LinhaEntreCirculos> getLinhasConexao() {
        return mLinhasConexao;
    }
        /**
         * Ask all children to measure themselves and compute the measurement of this
         * layout based on the children.
         */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();

        // Measure the child.
//                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
/*
        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
*/
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    // Position all children within this layout.
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        final Point childCenter = new Point();
        CircleAttr circleA, circleB; //Pontos A e B

        final int widthUtil = r - l - getPaddingLeft() - getPaddingRight(); //getMeasuredWidth()
        final int heightUtil = b - t - getPaddingTop() - getPaddingBottom(); //getMeasuredHeight()

        //calcular widht e height dos Viewchilds = 3/14 da area disponivel
        // o central sera maior que o dos demais eixos = 4/14 da area disponivel
        // o que recebe o DragAndDrop sera maior que o central = ?
        final int menorLado = Math.min(widthUtil, heightUtil);
        final double fracaoProporcao = menorLado / 28; //28 partes para calculo dos proporcionais
        final int childSqrHalfSize = (int) (3 * fracaoProporcao); //Peso child = 6
        final int childCenterSqrHalfSize = (int) (3.5 * fracaoProporcao); // Peso childCenter = 7
        final int childDropableSqrHalfSize = (int) (4 * fracaoProporcao); // Peso childDropable = 8

        //xy centro
        xc = (int) widthUtil / 2;
        yc = (int) heightUtil / 2;

        //raio corresponde a metade da menor distancia útil da View
        raio = (int) ((menorLado / 2) - (6 * fracaoProporcao)); // raio deixa 2 partes da fracao livre

        int halfSize;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE && (child.getClass() != LinhaEntreCirculos.class)) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                final double angleRad = lp.angleRad;

                if (lp.position == LayoutParams.POSITION_CENTER) {
                    //posiciona a View no Centro - Reestruturacao DCE
                    childCenter.x = xc;
                    childCenter.y = yc;
                    halfSize = childCenterSqrHalfSize;
                }
                else {
                    //posiciona a View conforme o Grau selecionado
                    getPointAtRadius(angleRad, childCenter);
                    if(lp.position == LayoutParams.POSITION_DROPABLE)
                        //Dropable tem mesmo tamannho da imagem do centro
                        halfSize = (int) childDropableSqrHalfSize;
                    else
                    {
                        halfSize = childSqrHalfSize;
                        circleA = new CircleAttr(childCenterSqrHalfSize);
                        circleB = new CircleAttr(childSqrHalfSize);

                        //ponto na borda do circulo, conforme angulo
                        getPointAtRadius(xc, yc, angleRad, circleA);
                        //180 + angle - para angulo complementar
                        getPointAtRadius(childCenter.x, childCenter.y, Math.PI + angleRad, circleB);

                        final LinhaEntreCirculos linhaConexao = mLinhasConexao.get(child);
                        linhaConexao.setCirculos(circleA, circleB);
                        linhaConexao.layout(l, t, r, b); //(0, 0, getMeasuredWidth(), getMeasuredHeight());
                    }
                }
                child.layout(
                        childCenter.x - halfSize, childCenter.y - halfSize,
                        childCenter.x + halfSize, childCenter.y + halfSize);
            }
        }
    }

    public CircleAttr getCircleAttr(){ return new CircleAttr(xc, yc, raio);}

    public void getPointAtRadius(double angleRad, Point childCenter) {
        getPointAtRadius(this.xc, this.yc, this.raio, angleRad, childCenter);
    }

    public static void getPointAtRadius(int x, int y, double angleRad, CircleAttr childCenter) {
        getPointAtRadius(x, y, childCenter.radius, angleRad, (Point) childCenter);
    }

    public static void getPointAtRadius(int x, int y, int raio, double angleRad, Point childCenter) {
        childCenter.x = x + (int) (raio * Math.cos(angleRad));
        childCenter.y = y + (int) (raio * Math.sin(angleRad));
    }

//LayoutParameters Methods

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new PFALayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.POSITION_CENTER, LayoutParams.DEGREE_ZERO);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {
        public static int POSITION_CENTER = 0;
        public static int POSITION_OVER_RADIUS = 1;
        public static int POSITION_DROPABLE = 2;
        public static int POSITION_LINE = 3;

        public static float DEGREE_ZERO = (float) 0.0;

        public int position = POSITION_CENTER;
        public double angleRad = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            // Pull the layout param values from the layout XML during
            // inflation.  This is not needed if you don't care about
            // changing the layout behavior in XML.
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PFALayout);
            position = a.getInt(R.styleable.PFALayout_layout_position, position);
            angleRad = Math.toRadians(a.getFloat(R.styleable.PFALayout_angle, (float) Math.toDegrees(angleRad)));
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int position, float angleRad) {
            super(width, height);
            this.position = position;
            this.angleRad = angleRad;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
