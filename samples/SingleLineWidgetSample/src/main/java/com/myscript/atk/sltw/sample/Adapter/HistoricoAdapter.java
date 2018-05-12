package com.myscript.atk.sltw.sample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myscript.atk.sltw.sample.Entidades.Historico;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avelino on 11/05/2018.
 */

public class HistoricoAdapter extends ArrayAdapter<Historico> {

    private Context context;
    private ArrayList<Historico> lista;
    String uid;

    public HistoricoAdapter(Context context, ArrayList<Historico> lista) {
        super(context, 0,lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Historico historicoPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_lista_personalizada_historico,null);

        TextView txtData = (TextView)convertView.findViewById(R.id.alph_txtData);
        TextView txtPalavra1 = (TextView)convertView.findViewById(R.id.alph_txtPalavra1);
        TextView txtPalavra2 = (TextView)convertView.findViewById(R.id.alph_txtPalavra2);
        TextView txtPalavra3 = (TextView)convertView.findViewById(R.id.alph_txtPalavra3);

        uid = historicoPosicao.getData();

        txtData.setText(historicoPosicao.getData());
        txtPalavra1.setText(historicoPosicao.getPalavraErro1());
        txtPalavra2.setText(historicoPosicao.getPalavraErro2());
        txtPalavra3.setText(historicoPosicao.getPalavraErro3());

        return convertView;
    }


}
