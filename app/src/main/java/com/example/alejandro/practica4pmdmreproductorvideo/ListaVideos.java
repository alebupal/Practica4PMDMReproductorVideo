package com.example.alejandro.practica4pmdmreproductorvideo;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class ListaVideos extends ActionBarActivity {
    Cursor cursor;
    private AdaptadorArrayList ad2;
    private AdaptadorCursor ad;
    private int REPRODUCIR = 1;
    private ArrayList<Video> datos = new ArrayList<Video>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);

        Cursor cur = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null, null,	null, null);
        final ListView ls = (ListView) findViewById(R.id.listView);






        /*Con array*/
        cur.moveToFirst();
        //for(int i=0;i<cur.getCount();i++){
        for(int i=0;i<15;i++){
            Video v = new Video();
            v.setNombre(cur.getString(cur.getColumnIndex(MediaStore.Video.Media.TITLE)));
            v.setPeso(cur.getString(cur.getColumnIndex(MediaStore.Video.Media.SIZE)));
            v.setRuta(cur.getString(cur.getColumnIndex(MediaStore.Video.Media.DATA)));
            datos.add(v);
            cur.moveToNext();
            //Log.v("video","nombre: "+v.getNombre()+" peso: "+ miniatura: "+v.getMiniatura());
        }
        cur.close();
        ad2 = new AdaptadorArrayList(this, R.layout.lista_detalle, datos);
        ls.setAdapter(ad2);
        registerForContextMenu(ls);









        /* Con cursor*/
        /*ad = new AdaptadorCursor(this, cur);

        ls.setAdapter(ad);
        registerForContextMenu(ls);*/

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), Reproductor.class);
                Bundle b = new Bundle();
                /*Cursor c=(Cursor)ls.getItemAtPosition(i);
                b.putString("ruta", c.getString(c.getColumnIndex(MediaStore.Video.Media.DATA)));*/
                b.putString("ruta", datos.get(i).getRuta().toString());
                it.putExtras(b);
                startActivityForResult(it, REPRODUCIR);
            }
        });

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
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }



}

