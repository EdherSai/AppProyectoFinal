package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {
    private List<MyData> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private int []imagenes = {R.drawable.editar,R.drawable.borrar};

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
        TextView textView = null;
        ImageView imageView = null;
        ImageView imageView2 = null;
        ImageView imageView3 = null;
        view = layoutInflater.inflate(R.layout.activity_list_view, null );
        textView = view.findViewById(R.id.textViewId);
        imageView2 = view.findViewById(R.id.ImgEditar);
        imageView3 = view.findViewById(R.id.ImgBorrar);
        textView.setText(list.get(i).getName());
        imageView = view.findViewById(R.id.imageViewId);
        imageView.setImageResource(list.get(i).getImage());
        imageView2.setImageResource(imagenes[0]);
        imageView3.setImageResource(imagenes[1]);

        return view;
    }
}
