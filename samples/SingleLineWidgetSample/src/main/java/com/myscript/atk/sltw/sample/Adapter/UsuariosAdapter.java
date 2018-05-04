package com.myscript.atk.sltw.sample.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

/**
 * Created by Avelino on 14/03/2018.
 */

public class UsuariosAdapter extends ArrayAdapter<Usuarios> {

    private Context context;
    private ArrayList<Usuarios> lista;
    String uid;


    public UsuariosAdapter(Context context, ArrayList<Usuarios>lista) {
        super(context, 0,lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Usuarios usuarioPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_lista_personalizada_usuarios,null);

        TextView txtNome = (TextView)convertView.findViewById(R.id.alpu_txtNome);
        TextView txtEmail = (TextView)convertView.findViewById(R.id.alpu_txtEmail);
        String nome;
        uid = usuarioPosicao.getId();

        nome = usuarioPosicao.getNome()+" "+usuarioPosicao.getSobrenome();
        txtNome.setText(nome);
        txtEmail.setText(txtEmail.getText()+" "+usuarioPosicao.getEmail());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

}
