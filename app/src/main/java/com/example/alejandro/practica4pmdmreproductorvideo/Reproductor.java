package com.example.alejandro.practica4pmdmreproductorvideo;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.MotionEventCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;


public class Reproductor extends Activity {
    private String ruta;
    private VideoView vv;
    private boolean visible = true;
    private LinearLayout botones;
    private ImageButton play, pausa;
    private TextView tvTiempoTotal, tvTiempoActual;
    private SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);


        vv = (VideoView) findViewById(R.id.videoView);
        sb = (SeekBar) findViewById(R.id.seekBar);
        botones = (LinearLayout) findViewById(R.id.botones);

        play = (ImageButton) findViewById(R.id.ibPlay);
        pausa = (ImageButton) findViewById(R.id.ibPause);

        tvTiempoTotal = (TextView) findViewById(R.id.tvTiempoTotal);
        tvTiempoActual = (TextView) findViewById(R.id.tvTiempoActual);

        Bundle b = getIntent().getExtras();
        ruta = b.getString("ruta");

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(ruta);
        String orientation = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        /*
        Log.v("orientacion", orientation + "");
        if(orientation.compareTo("90")==0){
            Log.v("rotacion","mal");
                //vv.setRotation(90);
        }*/

        Uri miUri = Uri.parse(new File(ruta).toString());
        vv.setVideoURI(miUri);
        vv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        if (visible == true) {
                            botones.setVisibility(View.INVISIBLE);
                            visible = false;
                        } else if (visible == false) {
                            botones.setVisibility(View.VISIBLE);
                            visible = true;
                        }
                        Log.v("probando", "a");
                        return true;
                    default:
                        return true;
                }
            }
        });


        vv.start();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                sb.setMax(vv.getDuration());
                sb.postDelayed(onEverySecond, 1000);
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // this is when actually seekbar has been seeked to a new position
                    vv.seekTo(progress);
                }
            }
        });
    }

    private Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {
            if (sb != null) {
                sb.setProgress(vv.getCurrentPosition());
            }
            if (vv.isPlaying()) {
                sb.postDelayed(onEverySecond, 500);
            }if(vv.isPlaying()!=true){
                sb.postDelayed(onEverySecond, 0);
            }

            tvTiempoTotal.setText(darTiempo(vv.getDuration()));
            tvTiempoActual.setText(darTiempo(vv.getCurrentPosition()));
        }
    };


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

    public void play(View v) {

        play.setVisibility(View.INVISIBLE);
        pausa.setVisibility(View.VISIBLE);
        vv.start();

    }

    public void pause(View v) {
        pausa.setVisibility(View.INVISIBLE);
        play.setVisibility(View.VISIBLE);
        vv.pause();
    }

    public void anterior(View v) {
        vv.seekTo(vv.getCurrentPosition()-5000);
    }

    public void siguiente(View v) {
        vv.seekTo(vv.getCurrentPosition()+5000);
    }

    public String darTiempo(int milisegundos){
        int segundos = (int) (milisegundos / 1000) % 60 ;
        int minutos = (int) ((milisegundos / (1000*60)) % 60);
        int hora   = (int) ((milisegundos / (1000*60*60)) % 24);
        if(minutos<10 && segundos<10){
            String tiempo = hora+":0"+minutos+":0"+segundos;
            return tiempo;
        }else if(minutos<10){
            String tiempo = hora+":0"+minutos+":"+segundos;
            return tiempo;
        }else if(segundos<10){
            String tiempo = hora+":"+minutos+":0"+segundos;
            return tiempo;
        }
        String tiempo = hora+":"+minutos+":"+segundos;
        return tiempo;
    }

}
