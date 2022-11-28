package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {
    private List<MyData> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private int []imagenes = {R.drawable.editar,R.drawable.borrar, R.drawable.usuario};

    public MyAdapter(List<MyData> list, Context context)
    {
        this.list = list;
        this.context = context;
        if( context != null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public boolean isEmptyOrNull( )
    {
        return list == null || list.size() == 0;
    }

    @Override
    public int getCount()
    {
        if(isEmptyOrNull())
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        if(isEmptyOrNull())
        {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        TextView Nombre = null;
        TextView Contra = null;
        ImageView foto = null;
        ImageButton editar = null;
        ImageButton borrar = null;
        view = layoutInflater.inflate(R.layout.activity_list_view, null );
        Nombre = view.findViewById(R.id.textViewId);
        editar = view.findViewById(R.id.ImgEditar);
        borrar = view.findViewById(R.id.ImgBorrar);
        foto = view.findViewById(R.id.imageViewId);
        foto.setImageResource(imagenes[2]);
        Nombre.setText(list.get(i).getName());
        Contra.setText(list.get(i).getContra());
        editar.setImageResource(imagenes[0]);
        borrar.setImageResource(imagenes[1]);

        return view;
    }
}

