package com.example.alejandro.practica4pmdpreproductorvideo;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListaVideos extends ActionBarActivity {
    Cursor cursor;
    private AdaptadorCursor ad2;
    private int REPRODUCIR = 1;

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
        ad2 = new AdaptadorCursor(this, cur);
        final ListView ls = (ListView) findViewById(R.id.listView);
        ls.setAdapter(ad2);
        registerForContextMenu(ls);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), Reproductor.class);
                Bundle b = new Bundle();
                Cursor c=(Cursor)ls.getItemAtPosition(i);
                b.putString("ruta", c.getString(c.getColumnIndex(MediaStore.Video.Media.DATA)));
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

