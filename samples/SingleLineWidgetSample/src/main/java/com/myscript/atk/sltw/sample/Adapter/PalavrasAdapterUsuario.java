package com.myscript.atk.sltw.sample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

/**
 * Created by Avelino on 19/03/2018.
 */

public class PalavrasAdapterUsuario extends ArrayAdapter<Palavra> {

    private Context context;
    private ArrayList<Palavra> lista;



    public PalavrasAdapterUsuario(Context context, ArrayList<Palavra>lista) {
        super(context, 0,lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Palavra palavraPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_lista_personalizada_palavras_usuario,null);

        TextView txtNome = (TextView)convertView.findViewById(R.id.alppu_txtPalavra);
        String palavra;
        palavra = palavraPosicao.getPalavra();
        txtNome.setText(palavra);

        return convertView;
    }
}