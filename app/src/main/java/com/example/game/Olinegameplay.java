package com.example.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Olinegameplay extends AppCompatActivity {
    int[][] winpos = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
    boolean game = true;

    int active = 0;   // 0 is bart and 1 is homer;
    int[] state = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 2 means empty and all are empty before clicking
    FirebaseDatabase database;
    String gamecode; int move;    int sw=0; int first=0;
    public void start(View view) {


        MediaPlayer m = MediaPlayer.create(this, R.raw.arrow); //see our animation duration and audio both have same length for uniformity
        m.start();

        VideoView v = (VideoView) findViewById(R.id.videoView);
        v.setVisibility(View.GONE);
        Button b = (Button) findViewById(R.id.stbutton);
        b.setVisibility(View.INVISIBLE);
        GridLayout board = (GridLayout) findViewById(R.id.gridLayoutt);
        board.setVisibility(View.VISIBLE);
        board.setTranslationX(-1500);
        board.setScaleX(0f);
        board.setScaleY(0f);
        board.animate().translationXBy(1500).rotation(3600).scaleX(1f).scaleY(1f).setDuration(2000);
        for (int i = 0; i < board.getChildCount(); i++) {
            ImageView image = (ImageView) board.getChildAt(i);
            image.setVisibility(View.VISIBLE);
        }
        first=0;
        sw=1;
        getmove();

    }

  public void getmove() {
      DatabaseReference ref = database.getReference().child("games").child(gamecode);
      ref.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              // This method is called once with the initial value and again
              // whenever data at this location is updated.
             int move = dataSnapshot.getValue(int.class);


              DatabaseReference mref = database.getReference().child("games").child(gamecode);
              //movee(move);
              if(move!=0) {
                  sw++;
                  ImageView tap = (ImageView) findViewById(move);


                  int n = Integer.parseInt(tap.getTag().toString());

                  if (state[n - 1] == 2 && game) {
                      state[n - 1] = active; //here now active is who has tapped // here n starts from 1 but index from 0
                      tap.setTranslationY(-1500);
                      if (active == 0) {                                 // very imp that use settransY...it only sets the position relative to the current position for used for translation afterwards
                          tap.setImageResource(R.drawable.bart);                                     // every method is executed line wise;
                          active = 1;
                      } else {
                          tap.setImageResource(R.drawable.homer);
                          active = 0;
                      }
                      tap.animate().translationYBy(1500).rotation(3600).setDuration(300);
                      //MediaPlayer m = MediaPlayer.create(this, R.raw.click);
                      // m.start();


                      for (int w[] : winpos) {
                          if (state[w[0] - 1] == state[w[1] - 1] && state[w[1] - 1] == state[w[2] - 1] && state[w[0] - 1] != 2) {
                              String mes;
                              if (active == 1)
                                  mes = "bart";
                              else
                                  mes = "homer"; //as active is changed now but we need previous active which has clicked..

                              game = false;
                              //DatabaseReference uref = database.getReference().child("games").child(gamecode);
                             // mref.setValue(0);
                              Button again = (Button) findViewById(R.id.button);
                              TextView winner = (TextView) findViewById((R.id.textView));
                              winner.setText(mes + " has won");
                              winner.setVisibility(View.VISIBLE);
                              again.setVisibility(View.VISIBLE);

                          }
                          if (state[0] != 2 && state[1] != 2 && state[2] != 2 && state[3] != 2 && state[4] != 2 && state[5] != 2 &&
                                  state[6] != 2 && state[7] != 2 && state[8] != 2) {

                              game = false;
                             // DatabaseReference uref = database.getReference().child("games").child(gamecode);
                              //mref.setValue(0);
                              Button again = (Button) findViewById(R.id.button);
                              TextView winner = (TextView) findViewById((R.id.textView));
                              winner.setText("Draw");
                              winner.setVisibility(View.VISIBLE);
                              again.setVisibility(View.VISIBLE);


                          }
                      }


                  }

              }

          }

          @Override
          public void onCancelled(DatabaseError error) {
              // Failed to read value
              failed();
          }



      });
  }

    public void failed(){
        Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
    }




    public void drop(View view) {
        if(first==0)first=sw;

         if(((sw-first)%2==0)||(sw-first==0)){
             //first=1;
        DatabaseReference mref = database.getReference().child("games").child(gamecode);
        ImageView tap = (ImageView) view; //see here view parameter of function is is used as it is the tapped view
        mref.setValue(tap.getId());
        // getmove();}   //no need of this
    }
    }


    public void tryagain(View view) {
        sw=1; first=0;
        Button again = (Button) findViewById(R.id.button);
        TextView winner = (TextView) findViewById((R.id.textView));
        winner.setVisibility(View.INVISIBLE);
        again.setVisibility(View.INVISIBLE);
        androidx.gridlayout.widget.GridLayout g = (GridLayout) findViewById((R.id.gridLayoutt));
        for (int i = 0; i < g.getChildCount(); i++) {
            ImageView image = (ImageView) g.getChildAt(i);                 // USE ANDROID X WALA GRIDLAYOUT -----VERY VERY IMPORTANT ELSE APP CRASH
            image.setImageDrawable(null);
        }

        // it is for loop through all grid objects
        // else write independently for each image view id
        for (int i = 0; i < state.length; i++) {
            state[i] = 2;
        }

        game = true;

        active = 0;
        //getmove();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        androidx.gridlayout.widget.GridLayout g = (GridLayout) findViewById((R.id.gridLayoutt));
        for (int i = 0; i < g.getChildCount(); i++) {
            ImageView image = (ImageView) g.getChildAt(i);                 // USE ANDROID X WALA GRIDLAYOUT -----VERY VERY IMPORTANT ELSE APP CRASH
            image.setImageDrawable(null);
            image.setId(View.generateViewId());
        }

        database = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            gamecode = extras.getString("KEY");
        }
        Log.i("gamecodejoined",gamecode);
        VideoView video = (VideoView) findViewById(R.id.videoView);
        video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.trans);  //both are played once
        video.start();
        MediaPlayer m = MediaPlayer.create(this, R.raw.marbles); //see our video and audio both have same length for uniformity
        m.start();


    }
}
