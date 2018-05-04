package com.myscript.atk.sltw.sample.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.Helper.Base64Custom;
import com.myscript.atk.sltw.sample.Helper.Preferencias;
import com.myscript.atk.sltw.sample.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadEmail;
    private EditText edtCadSenha;
    private EditText edtCadConfirmaSenha;
    private EditText edtCadDiaNascimento;
    private EditText edtCadMesNascimento;
    private EditText edtCadAnoNascimento;
    private Button btnCadGravar;
    public Usuarios usuarios;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        edtCadNome = (EditText)findViewById(R.id.edtNomeCadastro);
        edtCadSobrenome = (EditText)findViewById(R.id.edtSobrenomeCadastro);
        edtCadEmail = (EditText)findViewById(R.id.edtEmailCadastro);
        edtCadSenha = (EditText)findViewById(R.id.edtSenhaCadastro);
        edtCadConfirmaSenha = (EditText)findViewById(R.id.edtConfirmaSenhaCadastro);
        edtCadDiaNascimento = (EditText)findViewById(R.id.edtDiaNascimento);
        edtCadMesNascimento = (EditText)findViewById(R.id.edtMesNascimento);
        edtCadAnoNascimento = (EditText)findViewById(R.id.edtAnoNascimento);
        btnCadGravar = (Button)findViewById(R.id.btnCadastrar);

        btnCadGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha.getText().toString().equals(edtCadConfirmaSenha.getText().toString())){
                        if(isDateValid(edtCadDiaNascimento.getText().toString(),edtCadMesNascimento.getText().toString(),edtCadAnoNascimento.getText().toString())){
                              if(!edtCadNome.equals("") && !edtCadSobrenome.equals("")) {
                                  if(!edtCadEmail.equals("")) {
                                      usuarios = new Usuarios();
                                      usuarios.setNome(edtCadNome.getText().toString());
                                      usuarios.setSobrenome(edtCadSobrenome.getText().toString());
                                      usuarios.setEmail(edtCadEmail.getText().toString());
                                      usuarios.setSenha(edtCadSenha.getText().toString());
                                      usuarios.setDiaNascimento(edtCadDiaNascimento.getText().toString());
                                      usuarios.setMesNascimento(edtCadMesNascimento.getText().toString());
                                      usuarios.setAnoNascimento(edtCadAnoNascimento.getText().toString());
                                      usuarios.setPontuacao("0");
                                      usuarios.setJogos("0");
                                      usuarios.setAdm("0");
                                      usuarios.setBackGround("0");
                                      usuarios.setMensagem("0");
                                      usuarios.setCorLapis("0");

                                      cadastrarUsuario();
                                  }else Toast.makeText(CadastroActivity.this, "Digite um Email!", Toast.LENGTH_LONG).show();
                              }else Toast.makeText(CadastroActivity.this, "Digite Nome e Sobrenome!", Toast.LENGTH_LONG).show();
                        }else Toast.makeText(CadastroActivity.this, "Data de Nascimento não é valida!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CadastroActivity.this, "As senhas não correspondem!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Usuario cadastrado com sucesso",Toast.LENGTH_LONG);

                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();

                    usuarios.setId(usuarioFirebase.getUid());
                    salvarUsuario(usuarios);

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario,usuarios.getNome());

                    abrirLoginUsuario();

                }else{

                    Toast.makeText(CadastroActivity.this,"Erro ao cadastrar usuario",Toast.LENGTH_LONG);


                    try{

                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        Toast.makeText(CadastroActivity.this,"Digite uma senha mais forte",Toast.LENGTH_LONG);
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(CadastroActivity.this,"O Email digitado é invalido!",Toast.LENGTH_LONG);
                    }catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(CadastroActivity.this,"Este usuario já foi cadastrado!",Toast.LENGTH_LONG);
                    }catch (Exception e){
                        Toast.makeText(CadastroActivity.this,"Erro ao efetuar cadastro!",Toast.LENGTH_LONG);
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this,login_activity.class);
        startActivity(intent);
        finish();
    }

    /*public void salvarUsuario(Usuarios usuario){

        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios");
            firebase.child(usuario.getId()).setValue(usuario);
            Toast.makeText(this, "Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void salvarUsuario(Usuarios usuario){

        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios");
            firebase.child(usuario.getId()).setValue(usuario);
            Toast.makeText(this, "Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Falha na conexão", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public boolean isDateValid(String dia,String mes,String ano){
        String dateFormat = dia+"/"+mes+"/"+ano;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        df.setLenient(false);
        try{
            df.parse(dateFormat);
            return true;
        }catch (ParseException ex){
            return false;
        }


    }

}
