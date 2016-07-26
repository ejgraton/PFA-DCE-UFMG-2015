package br.com.conexaozero.prafrentequeseanda_dce2105ufmg;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener, View.OnDragListener {
    //private float moveStartX, moveStartY; //Inicio do movimento
    private double moveStartAngle; //inicio do movimento em radianos
    private PFALayout layoutPFA;
    private CircleAttr circPrinc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutPFA = (PFALayout) findViewById(R.id.layoutPrincipal);
        circPrinc = layoutPFA.getCircleAttr();
        setChildsOnTouch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //Atribui OnTouch de todos as view tipo RADIUS
    private void setChildsOnTouch()
    {
        int count = layoutPFA.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = layoutPFA.getChildAt(i);
            if (child.getClass() != LinhaEntreCirculos.class) {
                final PFALayout.LayoutParams lp = (PFALayout.LayoutParams) child.getLayoutParams();
                final double angleRad = lp.angleRad;

                if (lp.position == PFALayout.LayoutParams.POSITION_OVER_RADIUS) {
                    child.setOnTouchListener(this); //Gerencimento de movimento ser� feito nesta classe
                }
            }
        }
    }

    final static double minAngleDrop = Math.toRadians(255);
    final static double maxAngleDrop = Math.toRadians(285);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean handled = false;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                circPrinc = layoutPFA.getCircleAttr();
                moveStartAngle = angleRadCalc(circPrinc.x, circPrinc.y, event.getRawX(), event.getRawY());
                findViewById(R.id.imDropArea).getAnimation().start();

// Drag and Drop - Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                // Create a new ClipData.Item from the ImageView object's tag
                ClipData.Item item = new ClipData.Item(v.toString());

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"
//                ClipData dragData = new ClipData(v);

//                v.startDrag(,,,);

                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:
                float x1 = event.getRawX();
                float y1 = event.getRawY();
                final double moveNewAngle = angleRadCalc(circPrinc.x, circPrinc.y, x1, y1);
                final double diffAngle = moveNewAngle - moveStartAngle;
                //redesenha todos os childs
                rotateChilds(diffAngle);
                moveStartAngle = moveNewAngle; //salva o novo angulo como referencia para o proximo movimento

                handled = true;
                break;

            case MotionEvent.ACTION_UP:
                findViewById(R.id.imDropArea).getAnimation().cancel();

                //identifica qual view parou na area dropable e chama a view de propostas
                int count = layoutPFA.getChildCount();
                Intent intent = null;
                int i = 0;
                do {
                    final View child = layoutPFA.getChildAt(i);
                    final PFALayout.LayoutParams lp = (PFALayout.LayoutParams) child.getLayoutParams();
                    if(child.getClass() != LinhaEntreCirculos.class &&
                       lp.position == PFALayout.LayoutParams.POSITION_OVER_RADIUS){
                        if(lp.angleRad > minAngleDrop && lp.angleRad < maxAngleDrop){
                            //chama a Activity para propostas
                            intent = new Intent(this, ItemListActivity.class);
                            startActivity(intent);
                        }
                    }
                } while (++i < count && intent == null);

                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:
                handled = true;
                break;

            default:
                // do nothing
                break;
        }

        return handled;
    }

    //calcula angulo do ponto em rela��o ao centro
    public double angleRadCalc(float x0, float y0, float x1, float y1) {
        //raio = Sqr(base^2 + altura^2)
        final float base = x1 - x0;
        final float altura = y1 - y0;
        final double raio = Math.sqrt(Math.pow(base,2) + Math.pow(altura,2));
        //identificar quadante pelos sinais de base e altura
        return quadranteDeltaCos(altura, Math.acos(base / raio)); //ajuste de quadrante !!
    }

    //dominio função Cosseno : 0 - 180
    public static double quadranteDeltaCos(float altura, double arccos) {
        return  altura > 0.0 ?
                (double) arccos :   //offSet 1o e 2o quadrantes -> y positivo
                2*Math.PI - arccos; //offSet 2o e 4o quadrantes -> y negativo
    }

/* nunca testada - é preciso validar lógica da base < 0
    //dominio função Seno : 90 - 270 ?
    public static double quadranteDeltaSen(float base, asen) {
        return  base > 0.0 ?
                arcsen :            //offSet 2o e 3o quadrantes -> x positivo
                2*Math.PI - arcsen; /offSet 1o e 4o quadrantes -> x negativo
    }
*/

    private void rotateChilds(double diffAngle) {
        int count = layoutPFA.getChildCount();
        CircleAttr circleA, circleB; //Pontos A e B
        final Point ptoA = new Point();

        for (int i = 0; i < count; i++) {
            View child = layoutPFA.getChildAt(i);
            //if (false)
            if (child.getVisibility() != View.GONE && (child.getClass() != LinhaEntreCirculos.class)) {
                final PFALayout.LayoutParams lp = (PFALayout.LayoutParams) child.getLayoutParams();
                if (lp.position == PFALayout.LayoutParams.POSITION_OVER_RADIUS) {
                    final LinhaEntreCirculos linhaConexao = layoutPFA.getLinhasConexao().get(child);
                    lp.angleRad = lp.angleRad + diffAngle;

                    //posiciona a View conforme o Grau selecionado
                    PFALayout.getPointAtRadius(circPrinc.x, circPrinc.y, circPrinc.radius,
                            lp.angleRad, ptoA);

                    circleA = linhaConexao.getCirculo(0);
                    circleB = linhaConexao.getCirculo(1);

                    child.setX(ptoA.x - circleB.radius); //considerando botoes quadrados
                    child.setY(ptoA.y - circleB.radius);

                    //ponto na borda do circulo, conforme angulo
                    PFALayout.getPointAtRadius(circPrinc.x, circPrinc.y, lp.angleRad, circleA);
                    //(180 + angle - para angulo suplementar
                    PFALayout.getPointAtRadius(ptoA.x, ptoA.y, Math.PI + lp.angleRad, circleB);

                    linhaConexao.invalidate();
                    child.invalidate();
                }
            }
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }
}
