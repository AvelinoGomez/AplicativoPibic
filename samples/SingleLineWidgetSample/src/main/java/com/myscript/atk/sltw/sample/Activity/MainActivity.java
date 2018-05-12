// Copyright MyScript. All rights reserved.

package com.myscript.atk.sltw.sample.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myscript.atk.core.Rectangle;
import com.myscript.atk.sltw.SingleLineWidgetApi;
import com.myscript.atk.sltw.sample.Adapter.ReferenciasErro;
import com.myscript.atk.sltw.sample.BuildConfig;
import com.myscript.atk.sltw.sample.CRUD.ReadPalavras;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.CRUD.UpdateUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Historico;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;
import com.myscript.certificate.MyCertificate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.os.Environment.DIRECTORY_PICTURES;


public class MainActivity extends Activity implements
        SingleLineWidgetApi.OnConfiguredListener,
        SingleLineWidgetApi.OnTextChangedListener {

  private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
  private FirebaseDatabase firebaseDatabase;

  private FirebaseStorage storage = FirebaseStorage.getInstance();
  private StorageReference storageReference = storage.getReferenceFromUrl("gs://aplicativo-pibic.appspot.com");

  String dataJogo;
  ReferenciasErro refErro;
  View balao;
  View layoutTeclado;
  Context context;
  ArrayList<Palavra> listaPalavraClasse;
  ArrayList<String> listaPalavras;
  Integer vidas = 3;
  AlertDialog mensagem;
  Historico historico;
  Usuarios usuario;
  Integer jogos;
  String uid;
  float xInicial;
  Animation anim;
  float yInicial;
  private static final String TAG = "MainActivity";

  private SingleLineWidgetApi mWidget;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent intent = getIntent();
    Bundle dados = intent.getExtras();
    uid = dados.getString("uid").toString();

    refErro = new ReferenciasErro();

    ReadPalavras readPalavras = new ReadPalavras(getApplicationContext());
    listaPalavraClasse = readPalavras.getPalavras();
    listaPalavras = new ArrayList<>();
    layoutTeclado = (View)findViewById(R.id.layoutTeclado);

    balao = (View)findViewById(R.id.layout);

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date data = new Date();

      dataJogo = sdf.format(data);


    //TRANSFORMAR ARRAY PALAVRAS > ARRAY STRING//
    for(int i=0;i<listaPalavraClasse.size();i++){
      listaPalavras.add(listaPalavraClasse.get(i).getPalavra().toString());
    }

    context = getApplicationContext();

    Button botao = (Button)findViewById(R.id.confirmar);
    View Balao = (View) findViewById(R.id.layout);
    mWidget = (SingleLineWidgetApi) findViewById(R.id.singleLine_widget);
    TextView edtPontuacao = (TextView) findViewById(R.id.txtPontuacao);

    xInicial = Balao.getX();
    yInicial = Balao.getY();

    ImageView img = (ImageView)findViewById(R.id.imgTerceiraVida);
    Drawable drawable = getResources().getDrawable(R.mipmap.coracaocheio);
    img.setImageDrawable(drawable);
    ImageView img2 = (ImageView)findViewById(R.id.imgSegundaVida);
    img2.setImageDrawable(drawable);
    ImageView img3 = (ImageView)findViewById(R.id.imgPrimeiraVida);
    img3.setImageDrawable(drawable);

    /*//RESGATANDO USUARIO//
    firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid);
    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        usuario= dataSnapshot.getValue(Usuarios.class);
        System.out.println(usuario);
          jogos = Integer.valueOf(usuario.getJogos());
          jogos++;
          usuario.setJogos(jogos+"");
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        System.out.println("Erro na leitura do arquivo: " + databaseError.getCode());
      }
    });*/



    //RESGATANDO USUARIO//
    ReadUsuario r = new ReadUsuario(getApplicationContext());
    ArrayList<Usuarios> usuarios = r.getUsuarios();
    usuario = usuarios.get(0);
    jogos = Integer.valueOf(usuario.getJogos());
    jogos++;
    usuario.setJogos(jogos+"");
    UpdateUsuario u = new UpdateUsuario(getApplicationContext());
    u.updateUsuario(usuario);

    //RESGATANDO USUARIO//


    historico = new Historico();
    historico.setUid(usuario.getId());
    historico.setData(dataJogo);
    vidas = 3;

    edtPontuacao.setText("0");

    //RESGATAR PALAVRAS//


    //Gerar palavra de acordo com o nivel//
    gerarPalavra();

    //SETAR O Y QUE O BALÃO COMEÇARÁ A CAIR (FORA DA TELA)//
    Balao.setX(-135);

    //Animação do balão descer//
    if(vidas!=0) {
      balaoandando();
    }




    //Verifica a palavra quando o botão for clicado//
    botao.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        verificarpalavra(v,uid);
      }
    });

    //Verifica se o Manuscrito está usando um registro valido//
    if (!mWidget.registerCertificate( MyCertificate.getBytes())) {
      AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
      dlgAlert.setMessage("Please use a valid certificate.");
      dlgAlert.setTitle("Invalid certificate");
      dlgAlert.setCancelable(false);
      dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface dialog, int which)
        {
          //dismiss the dialog
        }
      });
      dlgAlert.create().show();
      return;
    }

    mWidget.setAutoScrollEnabled(false);//DESATIVAR A ROLAGEM AUTOMATICA DO RECONHECIMENTO..
    mWidget.setAutoTypesetEnabled(false);//DESATIVA A TRADUÇÃO AUTOMATICA DA PALAVRA
    mWidget.setOnConfiguredListener(this);
    mWidget.setOnTextChangedListener(this);

    //////////////////////////////////////////////////////////mWidget.setWritingAreaBackgroundResource(R.mipmap.moeda);

    // references assets directly from the APK to avoid extraction in application
    // file system
    mWidget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf");

    // The configuration is an asynchronous operation. Callbacks are provided to
    // monitor the beginning and end of the configuration process and update the UI
    // of the input method accordingly.

    //BACKGROUND ESCOLHIDO//
    if(usuario.getBackGround().equals("0")){
      mWidget.setWritingAreaBackgroundResource(R.drawable.background1);
    }else if(usuario.getBackGround().equals("1")){
      mWidget.setWritingAreaBackgroundResource(R.drawable.background2);
    }else{
      mWidget.setWritingAreaBackgroundResource(R.drawable.background3);
    }

    //COR TRAÇADO//

    mWidget.setInkColor(Integer.valueOf(usuario.getCorLapis()));


    //LINGUAGEM DO MYSCRIPT: pt_BR
    mWidget.configure("pt_BR", "cur_text");
  }

  @Override
  public void onBackPressed(){
    IrParaTelaInicial(uid);
  }

  @Override
  protected void onDestroy() {
    mWidget.setOnTextChangedListener(null);
    mWidget.setOnConfiguredListener(null);

    super.onDestroy();
  }

  public void onClearButtonClick(View v) {
    mWidget.clear();
  }

  @Override
  public void onConfigured(SingleLineWidgetApi widget, boolean success) {
    if (!success) {
      Log.e(TAG, "Unable to configure the Single Line Widget: " + widget.getErrorString());
      return;
    }
    if (BuildConfig.DEBUG)
      Log.d(TAG, "Single Line Widget configured!");
  }

  @Override
  public void onTextChanged(SingleLineWidgetApi widget, String s, boolean intermediate) {

    if (BuildConfig.DEBUG) {
      Log.d(TAG, "Single Line Widget recognition: " + widget.getText());
    }

  }

    public void balaoandando(){


        TextView txtPontuacao = (TextView)findViewById(R.id.txtPontuacao);

        setRandomY(balao,200);
        anim = new TranslateAnimation(balao.getX(),1500,balao.getY(),balao.getY());
        anim.setFillAfter(false);
        anim.setDuration(11000);

      balao.startAnimation(anim);

      anim.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
                balaoestourou(balao);
                vidas = perdeuVida(vidas);
                if (vidas != 0) {
                    gerarPalavra();
                    balaoandando();
                }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });

    }

    public void balaoestourou(View view){
        view.clearAnimation();
        view.setY(-135);
    }

  public void verificarpalavra(View view,String uid){

    View txt = (View)findViewById(R.id.layout);
    TextView texto = (TextView)txt.findViewById(R.id.texto);
    TextView txtPontuacao = (TextView)findViewById(R.id.txtPontuacao);
    Integer pontuacao;
    Integer pts;
    String palavra;
    String a;
    palavra = mWidget.getText().toLowerCase();
    a = texto.getText().toString();
    a = a.toLowerCase();

    //IDENTIFICAR QUE O LAYOUT CHEGOU NO FINAL DA ANIMAÇÃO//

    if (a.equals(palavra)) {
      Toast.makeText(getApplicationContext(), "Parabêns, você acertou", Toast.LENGTH_SHORT).show();

      UpdateUsuario u = new UpdateUsuario(context);

      pontuacao = Integer.valueOf(txtPontuacao.getText().toString());
      pontuacao = pontuacao + 1;
      txtPontuacao.setText(pontuacao+"");

      int pontuacaoNova = 1 + Integer.valueOf(usuario.getPontuacao());
      usuario.setPontuacao(pontuacaoNova+"");
      u.updateUsuario(usuario);

      mWidget.clear();
      gerarPalavra();
      balaoandando();

    }
    else if(mWidget.getText()=="" || mWidget.getText().length()==0){

    }
    else{

        if(vidas==3){
            historico.setPalavraErro1(a);
        }else if(vidas==2){
            historico.setPalavraErro2(a);
        }else{
            historico.setPalavraErro3(a);
        }

      vidas = perdeuVida(vidas);
      if(vidas!=0) {
        gerarPalavra();
        balaoandando();
      }else{
          historico.setPontuacao(txtPontuacao.getText().toString());
      }

  }}



  public int perdeuVida(int vidas) {
    View txt = (View) findViewById(R.id.layout);
    TextView texto = (TextView) txt.findViewById(R.id.texto);

    vidas--;

    if (vidas == 2) {
      ImageView img = (ImageView) findViewById(R.id.imgTerceiraVida);
      Drawable drawable = getResources().getDrawable(R.mipmap.coracaovazio);
      img.setImageDrawable(drawable);

       printarLayout(layoutTeclado,"Erro1");

      Toast.makeText(getApplicationContext(), "Você perdeu uma vida, tente novamente", Toast.LENGTH_SHORT).show();
    } else if (vidas == 1) {
      ImageView img = (ImageView) findViewById(R.id.imgSegundaVida);
      Drawable drawable = getResources().getDrawable(R.mipmap.coracaovazio);
      img.setImageDrawable(drawable);

        printarLayout(layoutTeclado,"Erro2");


      Toast.makeText(getApplicationContext(), "Você perdeu uma vida, tente novamente", Toast.LENGTH_SHORT).show();
    } else if (vidas == 0) {
      ImageView img = (ImageView) findViewById(R.id.imgPrimeiraVida);
      Drawable drawable = getResources().getDrawable(R.mipmap.coracaovazio);
      img.setImageDrawable(drawable);

        printarLayout(layoutTeclado, "Erro3");

      balao.setX(99999);
      salvarHistorico();

      MensagemFinal(uid);

    }

    mWidget.clear();
    txt.setY(-260);

    return vidas;

  }


  void printarLayout(View view,String nomeArquivo){

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      Date data = new Date();

      String dataFormatada = sdf.format(data);



      view.setDrawingCacheEnabled(true);
      view.buildDrawingCache();
      Bitmap b1 = view.getDrawingCache();
      Rect frame = new Rect();
      getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
      int statusBarHeight = frame.top;
      int width = view.getWidth();
      int height = view.getHeight();

      Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
      view.destroyDrawingCache();
      view.setDrawingCacheEnabled(false);

      if(nomeArquivo.equals("Erro1")){
          historico.setLocalErro1("gs://aplicativo-pibic.appspot.com/"+historico.getUid()+"/"+dataJogo+"/"+"Erro1");
          refErro.setErro1(b);
      }else if(nomeArquivo.equals("Erro2")){
          historico.setLocalErro2("gs://aplicativo-pibic.appspot.com/"+historico.getUid()+"/"+dataJogo+"/"+"Erro2");
          refErro.setErro2(b);
      }else{
          historico.setLocalErro3("gs://aplicativo-pibic.appspot.com/"+historico.getUid()+"/"+dataJogo+"/"+"Erro3");
          refErro.setErro3(b);
      }

      /*
      //SALVAR ARQUIVO//
    FileOutputStream out = null;
    try {

        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
      out = new FileOutputStream(getExternalFilesDir(DIRECTORY_PICTURES)+dataFormatada+nomeArquivo);


      b.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
      // PNG is a lossless format, the compression factor (100) is ignored
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }*/

  }

    public void gerarPalavra(){
        View balao = (View)findViewById(R.id.layout);
      TextView text = (TextView)balao.findViewById(R.id.texto);
      int i=0;

          int tamanho = listaPalavras.size();
          if(tamanho== 0) {
            Toast.makeText(context,"Nenhuma palavra cadastrada no banco!",Toast.LENGTH_SHORT);
            finish();
          }
          else {
            Random random = new Random();

            int j = random.nextInt((tamanho - 0) + 1);

            if (j < 0) {
              j = j * -1;
            }

            j = j % tamanho;


            String palavra;
            palavra = listaPalavras.get(j);
            text.setText(palavra);
          }
        }

  public void setRandomY(View v, int maxNum){
    float j;
    Random rand = new Random();

    j = (rand.nextFloat()*maxNum);

    v.setY(0+j);
  }

  private void MensagemFinal(String uid) {
    //LayoutInflater é utilizado para inflar nosso layout em uma view.
    //-pegamos nossa instancia da classe
    LayoutInflater li = getLayoutInflater();

    final String uid2 = uid;

    //inflamos o layout alerta.xml na view
    View view = li.inflate(R.layout.alerta, null);
    final TextView txtPontuacao = (TextView)findViewById(R.id.txtPontuacao);
    TextView txtPontuacaoMsg = (TextView)view.findViewById(R.id.txtPontuacaoMsg);


    txtPontuacaoMsg.setText("Parabens voce ganhou: " + txtPontuacao.getText() + " Creditos!");

      //ATUALIZAR A PONTUAÇÃO
      atualizarPontuacao(uid2,txtPontuacao);
      salvarHistorico();

    //definimos para o botão do layout um clickListener
    view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
      public void onClick(View arg0) {
        //desfaz o alerta.
        mensagem.dismiss();
        IrParaTelaInicial(uid2);
      }
    });

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Parabêns!!");
    builder.setView(view);

    mensagem = builder.create();
    mensagem.show();
  }

  void IrParaTelaInicial(String uid){
    Bundle bundle = new Bundle();
    bundle.putString("uid",uid);
    Intent intent = new Intent(this, telamenu.class);
    intent.putExtras(bundle);
    startActivity(intent);
    finish();
  }

  public void uparBitmap (int erro){
      Bitmap bmp;
      if(erro==1){
         bmp = refErro.getErro1();

      }else if(erro == 2){
          bmp = refErro.getErro2();
      }else{
          bmp = refErro.getErro3();
      }
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
      byte[] byteArray = stream.toByteArray();

      DatabaseReference firebaseHistorico;

      firebaseHistorico = ConfiguracaoFirebase.getFirebase().child("Historico").child(historico.getUid()).child(dataJogo);
      firebaseHistorico.setValue(historico);

      StorageReference filePath = storageReference.child(historico.getUid()).child(dataJogo).child("Erro"+erro);

      filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              //Toast.makeText(context, "Erro 1 Inserido com Sucesso!", Toast.LENGTH_SHORT).show();
          }
      });
  }


  public void salvarHistorico(){

     uparBitmap(1);
     uparBitmap(2);
     uparBitmap(3);


  }

  public void atualizarPontuacao(final String uid2, final TextView txtPontuacao){
      firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid2);
      firebase.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              Usuarios usuario = dataSnapshot.getValue(Usuarios.class);
              int somaAux1 = Integer.valueOf(usuario.getPontuacao().toString());
              int somaAux2 = Integer.valueOf(txtPontuacao.getText().toString());
              somaAux1 = somaAux1+somaAux2;
              usuario.setPontuacao(String.valueOf(somaAux1));
              jogos = Integer.valueOf(usuario.getJogos());
              jogos++;
              usuario.setJogos(jogos+"");
              UpdateUsuario u = new UpdateUsuario(context);
              u.updateUsuario(usuario);
              firebase.setValue(usuario);

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
              Toast.makeText(MainActivity.this, "Erro de conexão!", Toast.LENGTH_SHORT).show();
              IrParaTelaInicial(uid2);
          }
      });
  }

  /*public ArrayList<Palavra> resgatarPalavrasBD(){

    firebase = FirebaseDatabase.getInstance().getReference();
    firebase.child("Palavras").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          listaPalavras.add(postSnapshot.getValue(Palavra.class));
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

    return listaPalavras;
    }*/



}
