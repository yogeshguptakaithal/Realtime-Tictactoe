package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class onlinegame extends AppCompatActivity {

    String gamecode=null; String em,email,jem;  //em is player email with comma and jem is player to join......
    private FirebaseAuth mAuth;
    public void joingame(View view){        //here i am not checking if it exist or not...
                                            // try using showing all users available and chose to click
        findViewById(R.id.create).setVisibility(View.GONE);
        findViewById(R.id.join).setVisibility(View.GONE);
        findViewById(R.id.joinemail).setVisibility(View.VISIBLE);
        findViewById(R.id.joinbt).setVisibility(View.VISIBLE);

    }

    public void joinplay(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        jem="";
        EditText editText=(EditText)findViewById(R.id.joinemail);
        for(char c:editText.getText().toString().toCharArray()){
            if(c=='.')jem+=',';
            else jem+=c;

        }
        DatabaseReference ref = database.getReference().child("users").child(jem).child("curr game");
        final DatabaseReference myRef = database.getReference().child("users").child(em).child("curr game");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 gamecode = dataSnapshot.getValue(String.class);
                myRef.setValue(gamecode);
                Intent intent=new Intent(getApplicationContext(),Olinegameplay.class);
                intent.putExtra("KEY",gamecode);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                failed();
            }
        });


    }
   public void failed(){
       Toast.makeText(this,"failed to join",Toast.LENGTH_SHORT).show();
   }

    public void creategame(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference gameRef = database.getReference().child("games").push();
        gamecode=gameRef.getKey();
        DatabaseReference myRef = database.getReference().child("users").child(em).child("curr game");
        // ref is created new if not exist or if exist then it was used and not new created
        myRef.setValue(gamecode);
        gameRef.setValue(0);
        Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(),Olinegameplay.class);
        intent.putExtra("KEY",gamecode);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinegame);
        mAuth = FirebaseAuth.getInstance();
        email=mAuth.getCurrentUser().getEmail();

        //EditText editText=(EditText)findViewById(R.id.emailtext);
        //email=editText.getText().toString();
        em="";
        for(char c:email.toCharArray()){
            if(c=='.')em+=',';
            else em+=c;
        }
    }
}
