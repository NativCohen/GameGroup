package com.example.gamegroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnUp, btnIn, btnSubmit,btnVid;
    FirebaseAuth mAuth;
    Dialog dia;
    EditText edEmail, edPassword;
    HeadsetReceiver receiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUp = findViewById(R.id.btnUp);
        btnUp.setOnClickListener(this);
        btnIn = findViewById(R.id.btnIn);
        btnIn.setOnClickListener(this);
        btnVid=findViewById(R.id.btnVid);
        btnVid.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        receiver=new HeadsetReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent go = new Intent(this, GameGroupMenuActivity.class);
            startActivity(go);
        }
    }

    private void signIn(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication succeed.",
                                    Toast.LENGTH_SHORT).show();

                            dia.dismiss();
                            onStart();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id==R.id.itemAboutApp){
            Intent aboutApp = new Intent(MainActivity.this,AboutApp.class);
            startActivity(aboutApp);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menumain,menu);


        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == btnUp){
            Intent go = new Intent(this, SignUpActivity.class);
            startActivity(go);
        }
        else if(v == btnIn){
            dia = new Dialog(this);
            dia.setContentView(R.layout.sign_in_dialoge);
            dia.setCancelable(true);

            edPassword = dia.findViewById(R.id.edPass);
            edEmail = dia.findViewById(R.id.edEmail);
            btnSubmit = dia.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(this);

            dia.show();
        }
        else if (v==btnVid){
            Intent vid = new Intent(this,VideoActivity.class);
            startActivity(vid);
        }
        else if(v == btnSubmit){
            String email = edEmail.getText().toString();
            String pass = edPassword.getText().toString();

            if (email.length() > 0 && pass.length() > 0){
                signIn(email, pass);
            }

        }


    }
}