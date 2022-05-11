package com.example.gamegroupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GameGroupMenuActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    FirebaseAuth mAuth;
    Button btnOut,btnCreate,btnEnter,btnDelete;
    Event event;
    Dialog dia;
    Dialog dia2;
    String place;
    Button btnSubmit;
    EditText edGroupName,edCountOfPlayers,edDate,edCity,edMaxPlayers;
    ListView lv;
    List<Event>groups;
    DatabaseReference myRef;
    EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_group_menu);
        btnOut=findViewById(R.id.btnOut);
        btnOut.setOnClickListener(this);
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        edGroupName = findViewById(R.id.edGroupName);
        edDate=findViewById(R.id.edDate);
        edCity=findViewById(R.id.edCity);
        edMaxPlayers=findViewById(R.id.edMaxPlayers);
        lv=findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        groups=new ArrayList<>();
        myRef=FirebaseDatabase.getInstance().getReference("events");
        readData();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent go = new Intent(this, MainActivity.class);
            startActivity(go);
        }
    }
    public void createGroup(String groupName,int countOfPlayers,String date,String city){
        String uid = FirebaseAuth.getInstance().getUid().toString();
        DatabaseReference ref=myRef.push();
        Event m = new Event(groupName,countOfPlayers,date,1,city ,ref.getKey(),uid);
        ref.setValue(m);

    }
    public void readData(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                 groups = new ArrayList<>();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Event q = data.getValue(Event.class);
                    groups.add(q);
                }

                adapter = new EventAdapter(GameGroupMenuActivity.this, 0, 0, groups);
                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnOut){
            mAuth.signOut();
            onStart();
        }
        else if (v==btnEnter){
            if (event.maxOrNot()){
                Toast.makeText(this, "Already Full Game.",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                event.currentMembers++;
                myRef.child(event.id).setValue(event);
            }
        }
        else if (v==btnDelete){
            myRef.child(event.getId()).removeValue();
            dia2.dismiss();
        }
        else if(v == btnCreate){
            dia = new Dialog(this);
            dia.setContentView(R.layout.create_group_dialoge);
            dia.setCancelable(true);

            edGroupName = dia.findViewById(R.id.edGroupName);
            edDate = dia.findViewById(R.id.edDate);
            edCity=dia.findViewById(R.id.edCity);
            edMaxPlayers=dia.findViewById(R.id.edMaxPlayers);

            btnSubmit = dia.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(this);

            dia.show();
        }
        else if(v == btnSubmit) {
            String GroupName = edGroupName.getText().toString();
            String Date = edDate.getText().toString();
            String City = edCity.getText().toString();
            String MaxPlayers = edMaxPlayers.getText().toString();

            if (GroupName.length() > 0 && edMaxPlayers.length() > 0 && Date.length() > 0 && City.length() > 0) {
                createGroup(GroupName, Integer.parseInt(MaxPlayers), Date, City);
                dia.dismiss();
            }


        }

}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        event=groups.get(i);
        dia2 = new Dialog(this);
        dia2.setContentView(R.layout.dialog_accept);
        dia2.setCancelable(true);
        btnDelete = dia2.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        if (!mAuth.getUid().equals(event.getUserId())){
            btnDelete.setVisibility(View.INVISIBLE);
        }

        btnEnter = dia2.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        dia2.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id==R.id.itemAboutApp){
            Intent aboutApp = new Intent(GameGroupMenuActivity.this,AboutApp.class);
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
    }

