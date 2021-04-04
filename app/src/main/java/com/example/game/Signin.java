package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity {
    EditText ed1; EditText ed2;
    private FirebaseAuth mAuth; String email; String pass;
    public  void  signup(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //add in database
                            // Write a message to the database
// Write a message to the database
                            //String curid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            go();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void loggin(View view){
        ed1=(EditText)findViewById(R.id.emailtext);
        email=ed1.getText().toString();
        ed2=(EditText)findViewById(R.id.editText2);
        pass=ed2.getText().toString();
       mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("h","noj");
                            go();
                        } else {
                         signup();

                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            go();
        }
         //pass should be long enough else it failssssssssssss

    }

    public void go(){


           //move to after login screen
        Toast.makeText(this,"logged in",Toast.LENGTH_SHORT).show();
       //FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getApplicationContext(),onlinegame.class);

        startActivity(intent);

    }
}
