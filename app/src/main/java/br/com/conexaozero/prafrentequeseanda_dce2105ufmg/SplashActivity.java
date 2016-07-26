package br.com.conexaozero.prafrentequeseanda_dce2105ufmg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;


public class SplashActivity extends Activity implements View.OnClickListener {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        long mMediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        long offSet = 0;

        offSet = criaCrossfade(findViewById(R.id.imVermelho), offSet, mMediumAnimationDuration, true, false);
        offSet = criaCrossfade(findViewById(R.id.imLaranja), offSet+mMediumAnimationDuration, mMediumAnimationDuration);
        offSet = criaCrossfade(findViewById(R.id.imAmarelo), offSet+mMediumAnimationDuration, mMediumAnimationDuration);
        offSet = criaCrossfade(findViewById(R.id.imVerde), offSet+mMediumAnimationDuration, mMediumAnimationDuration);
        offSet = criaCrossfade(findViewById(R.id.imAzul), offSet+mMediumAnimationDuration, mMediumAnimationDuration);
        criaCrossfade(findViewById(R.id.imVioleta), offSet + mMediumAnimationDuration, mMediumAnimationDuration, false, true); //ultima View

        /*
        View v = findViewById(R.id.imPFA);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                view.startAnimation(anim);
                v.animate().start();
                findViewById(R.id.imPFA).setVisibility(View.INVISIBLE);
            }
        });
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);

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

    //Retorna offSet com o tempo do meio da animacao
    private long criaCrossfade(final View v, long offSet, long animationTime) {
        return criaCrossfade(v, offSet, animationTime, false, false);
    }

    private long criaCrossfade(final View v, long offSet, final long animationTime, boolean firstView, boolean lastView) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setStartOffset(offSet);
        fadeIn.setDuration(animationTime);

        offSet += animationTime;
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(offSet);
        fadeOut.setDuration(animationTime);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);

        v.setAnimation(animation);

/* Mantido em comentario para o caso de Debug
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                view.startAnimation(anim);
                v.animate().start();
                findViewById(R.id.imPFA).setVisibility(View.INVISIBLE);
            }
        });
*/
        if(firstView) animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation a) {
                Uri batimentos = Uri.parse("android.resource://br.com.conexaozero.prafrentequeseanda_dce2105ufmg/" + R.raw.hearbeat_2_mike_koenig_143666461);
                mediaPlayer = MediaPlayer.create(v.getContext(), batimentos);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
            }

            public void onAnimationRepeat(Animation a) {}

            public void onAnimationEnd(Animation a) {}
        });

        //Programa exibicao de todos os Views
        // lastView.animate().setListener(
        if(lastView) animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation a) {}

            public void onAnimationRepeat(Animation a) {}

            public void onAnimationEnd(Animation a) {
                findViewById(R.id.imVermelho).setVisibility(View.VISIBLE);
                findViewById(R.id.imLaranja).setVisibility(View.VISIBLE);
                findViewById(R.id.imAmarelo).setVisibility(View.VISIBLE);
                findViewById(R.id.imVerde).setVisibility(View.VISIBLE);
                findViewById(R.id.imAzul).setVisibility(View.VISIBLE);
                v.setVisibility(View.VISIBLE);
                aumentarView(findViewById(R.id.imPFA), animationTime);
            }
        });

        return offSet;
    }

    private void aumentarView(final View v, long animationTime) {
        //final Animation anim =  AnimationUtils.loadAnimation(this, R.anim.scale);
        ScaleAnimation scaleAnim1 = new ScaleAnimation(
                (float) 0.3, (float) 1.1,
                (float) 0.3, (float) 1.1,
                Animation.RELATIVE_TO_SELF, (float) 0.5,  //ponto fixo no centro
                Animation.RELATIVE_TO_SELF, (float) 0.5); //ponto fixo no centro
        scaleAnim1.setDuration(animationTime);

        final ScaleAnimation scaleAnim2 = new ScaleAnimation(
                (float) 0.9, (float) 1.0,
                (float) 0.9, (float) 1.0,
                Animation.RELATIVE_TO_SELF, (float) 0.5,  //ponto fixo no centro
                Animation.RELATIVE_TO_SELF, (float) 0.5); //ponto fixo no centro
        scaleAnim2.setRepeatMode(Animation.REVERSE);
        scaleAnim2.setRepeatCount(2);
        scaleAnim2.setDuration(animationTime);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(scaleAnim1);
        animation.addAnimation(scaleAnim2);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation a) {
            }

            public void onAnimationRepeat(Animation a) {
            }

            public void onAnimationEnd(Animation a) {
                v.setVisibility(View.VISIBLE);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                findViewById(R.id.imPFA).callOnClick();
            }
        });

        v.setAnimation(animation);
    }

    //Inicia Activity principal
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
