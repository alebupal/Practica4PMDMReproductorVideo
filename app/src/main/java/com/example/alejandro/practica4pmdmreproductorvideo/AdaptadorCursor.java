package com.example.alejandro.practica4pmdmreproductorvideo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;


public class AdaptadorCursor extends CursorAdapter{

    private static  LayoutInflater i;
    private TextView tvTitulo, tvFormato, tvPeso;
    private ImageView iv;



    public AdaptadorCursor(Context context,Cursor c) {
        super(context, c,true);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.lista_detalle, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        iv=(ImageView)view.findViewById(R.id.imageView);
        tvTitulo =(TextView)view.findViewById(R.id.tvTitulo);
        tvFormato =(TextView)view.findViewById(R.id.tvFormato);
        tvPeso =(TextView)view.findViewById(R.id.tvPeso);

        String ruta=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        //Bitmap bmThumbnail=ThumbnailUtils.createVideoThumbnail(cursor.getString(cursor.getColumnIndex("_Data")),cursor.getInt(cursor.getColumnIndex("_Id")));
        //Bitmap bmThumbnail= ThumbnailUtils.createVideoThumbnail(ruta, MediaStore.Video.Thumbnails.MICRO_KIND);

        //Picasso.with(context).load(getImageUri(context,bmThumbnail)).into(iv);

        tvTitulo.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
        tvFormato.setText(obtenerFormato(ruta));
        tvPeso.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String obtenerFormato(String ruta){
        String formato = ruta.substring(ruta.length()-3);
        return formato;
    }

}
