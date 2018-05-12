package com.myscript.atk.sltw.sample.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.myscript.atk.sltw.sample.Entidades.Historico;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import org.w3c.dom.Text;

import java.io.File;

public class Admin_ErrosDoUsuario extends AppCompatActivity {

    Historico historico;

    TextView txtData;
    TextView txtPalavra1;
    ImageView imgPalavra1;
    TextView txtPalavra2;
    ImageView imgPalavra2;
    TextView txtPalavra3;
    ImageView imgPalavra3;

    String filho;
    Bitmap img;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__erros_do_usuario);

        historico = (Historico) getIntent().getSerializableExtra("historico");

        instanciarComponentes();

        preencherCamposComponentes();

        downloadImg1Firebase(historico.getLocalErro1());
        downloadImg2Firebase(historico.getLocalErro2());
        downloadImg3Firebase(historico.getLocalErro3());

    }

    public void instanciarComponentes(){
        txtData = (TextView)findViewById(R.id.aaedu_txtData);
        txtPalavra1 = (TextView)findViewById(R.id.aaedu_txtPalavra1);
        imgPalavra1 = (ImageView)findViewById(R.id.aaedu_imgPalavra1);
        txtPalavra2 = (TextView)findViewById(R.id.aaedu_txtPalavra2);
        imgPalavra2 = (ImageView)findViewById(R.id.aaedu_imgPalavra2);
        txtPalavra3 = (TextView)findViewById(R.id.aaedu_txtPalavra3);
        imgPalavra3 = (ImageView)findViewById(R.id.aaedu_imgPalavra3);
    }

    public void preencherCamposComponentes(){
        txtData.setText(historico.getData());
        txtPalavra1.setText(historico.getPalavraErro1());
        txtPalavra2.setText(historico.getPalavraErro2());
        txtPalavra3.setText(historico.getPalavraErro3());
    }

    public void downloadImg1Firebase(String local){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(local);

        String filho = local.substring(34);

        //Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        /*if(filho.substring(filho.length()-1).toString().equals("1")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }else if(filho.substring(filho.length()-1).toString().equals("2")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }else if(filho.substring(filho.length()-1).toString().equals("3")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }*/


        StorageReference storageRef = storage.getReference().child(filho);

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                img = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
                imgPalavra1.setImageBitmap(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Admin_ErrosDoUsuario.this, "NAO CONSEGUIU!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadImg2Firebase(String local){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(local);

        String filho = local.substring(34);

        //Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        /*if(filho.substring(filho.length()-1).toString().equals("1")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }else if(filho.substring(filho.length()-1).toString().equals("2")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }else if(filho.substring(filho.length()-1).toString().equals("3")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }*/

        //Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        StorageReference storageRef = storage.getReference().child(filho);

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                img = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
                imgPalavra2.setImageBitmap(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Admin_ErrosDoUsuario.this, "NAO CONSEGUIU!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadImg3Firebase(String local){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(local);

        String filho = local.substring(34);

        //Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        /*if(filho.substring(filho.length()-1).toString().equals("1")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }else if(filho.substring(filho.length()-1).toString().equals("2")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }else if(filho.substring(filho.length()-1).toString().equals("3")) {
            Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        }*/

        //Toast.makeText(this, ""+filho, Toast.LENGTH_SHORT).show();
        StorageReference storageRef = storage.getReference().child(filho);

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                img = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
                imgPalavra3.setImageBitmap(img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Admin_ErrosDoUsuario.this, "NAO CONSEGUIU!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
