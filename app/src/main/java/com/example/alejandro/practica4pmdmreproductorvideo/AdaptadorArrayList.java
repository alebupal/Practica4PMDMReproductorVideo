package com.example.alejandro.practica4pmdmreproductorvideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Alejandro on 21/01/2015.
 */
public class AdaptadorArrayList extends ArrayAdapter<Video> {


    private Context contexto;
    private ArrayList<Video> lista;
    private int recurso;
    private static LayoutInflater i;


    public static class ViewHolder{
        public TextView tvTitulo,tvFormato, tvPeso;
        public ImageView iv;
        public int posicion;
    }

    public AdaptadorArrayList(Context context, int resource, ArrayList<Video> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista=objects;
        this.recurso=resource;
        this.i=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
            convertView=i.inflate(recurso,null);
            vh=new ViewHolder();
            vh.tvTitulo=(TextView)convertView.findViewById(R.id.tvTitulo);
            vh.tvPeso=(TextView)convertView.findViewById(R.id.tvPeso);
            vh.tvFormato=(TextView)convertView.findViewById(R.id.tvFormato);
            vh.iv=(ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(vh);

        }else{
            vh=(ViewHolder)convertView.getTag();

        }
        vh.posicion=position;
        Log.v("video", "nombre: " + lista.get(position).getNombre() + " peso: " + lista.get(position).getPeso());
        String ruta=lista.get(position).getRuta();
        vh.tvTitulo.setText(lista.get(position).getNombre());
        vh.tvFormato.setText(obtenerFormato(ruta));
        vh.tvPeso.setText(lista.get(position).getPeso());

        Bitmap bmThumbnail= ThumbnailUtils.createVideoThumbnail(ruta, MediaStore.Video.Thumbnails.MICRO_KIND);
        //vh.iv.setImageBitmap(bmThumbnail);
        Picasso.with(getContext()).load(getImageUri(getContext(),bmThumbnail)).into(vh.iv);
        return convertView;
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
