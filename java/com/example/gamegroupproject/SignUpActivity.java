package com.example.gamegroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText edEmail, edPass,edName,edCity;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        edName=findViewById(R.id.edName);
        edCity=findViewById(R.id.edCity);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        String city = edCity.getText().toString();

        if (email.length() > 0 && pass.length() > 0){
            createAccount(name,email, pass,city);
        }
    }
    private void createAccount(String name,String email, String password,String city) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Authentication succeed.",
                                    Toast.LENGTH_SHORT).show();
                            createMember(name,city);
                            finish();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(task.getException().getMessage());
                        }
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id==R.id.itemAboutApp){
            Intent aboutApp = new Intent(SignUpActivity.this,AboutApp.class);
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

    private void createMember(String name,String city) {
        String uid = FirebaseAuth.getInstance().getUid().toString();
        Member m = new Member(name,city,uid);
        DatabaseReference memberRef= FirebaseDatabase.getInstance().getReference("members").child(uid);
        memberRef.setValue(m);
    }
}