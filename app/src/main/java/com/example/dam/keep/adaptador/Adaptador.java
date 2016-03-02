package com.example.dam.keep.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dam.keep.R;
import com.example.dam.keep.pojo.Keep;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.KeepViewHolder> {

    private List<Keep> keepArrayList;

    public Adaptador(ArrayList<Keep> keepArrayList) {
        this.keepArrayList = keepArrayList;
    }

    @Override
    public KeepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        KeepViewHolder pvh = new KeepViewHolder(itemView);
        return pvh;
    }

    @Override
    public void onBindViewHolder(KeepViewHolder holder, int position) {
        Keep p = keepArrayList.get(position);
        holder.bindKeep(p);
    }

    @Override
    public int getItemCount() {
        return keepArrayList.size();
    }

    public static class KeepViewHolder extends RecyclerView.ViewHolder{
        public TextView contenido, id;
        public KeepViewHolder(View itemView) {
            super(itemView);
            contenido = (TextView) itemView.findViewById(R.id.txContenido);
            id = (TextView)itemView.findViewById(R.id.tvId);
        }
        public void bindKeep(Keep ke) {
            contenido.setText(ke.getContenido());
            id.setText(String.valueOf(ke.getId()));
        }
    }
}
