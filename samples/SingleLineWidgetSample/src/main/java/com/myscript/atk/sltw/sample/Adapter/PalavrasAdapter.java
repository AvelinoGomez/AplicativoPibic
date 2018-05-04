package com.myscript.atk.sltw.sample.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

/**
 * Created by Avelino on 16/03/2018.
 */

public class PalavrasAdapter extends ArrayAdapter<Palavra>{

        private Context context;
        private ArrayList<Palavra> lista;
        View mView;

    private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;



        public PalavrasAdapter(Context context, ArrayList<Palavra>lista) {
            super(context, 0,lista);
            this.context = context;
            this.lista = lista;
        }

        @NonNull
        @Override
        public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
            Palavra palavraPosicao = this.lista.get(position);

            convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_lista_personalizada_palavras,null);


            final TextView txtNome = (TextView)convertView.findViewById(R.id.alpp_txtPalavra);
            String palavra;
            palavra = palavraPosicao.getPalavra();
            txtNome.setText(palavra);

            Button btn_Fechar = (Button)convertView.findViewById(R.id.alpp_btnExcluirPalavra);
            btn_Fechar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    excluirPalavraFirebase(txtNome.getText().toString());
                    Toast.makeText(context, "Palavra deletada, atualize a lista", Toast.LENGTH_LONG).show();
                    txtNome.setBackgroundColor(Color.rgb(255,0,0));
                }
            });


            return convertView;
        }

        public void excluirPalavraFirebase(String palavra) {
            firebase = FirebaseDatabase.getInstance().getReference();
            firebase.child("Palavras").child(palavra).removeValue();
        }
}
