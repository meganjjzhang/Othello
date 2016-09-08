package com.hku.zhangjingjing.othello;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        Button newgame = (Button) findViewById(R.id.newgame_button);
        newgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent loadgameIntent = new Intent(view.getContext(), GameActivity.class);
                startActivity(loadgameIntent);
            }


        });

        // Operations of the button: Load Game


        // Operations of the button: History
        Button about = (Button) findViewById(R.id.about_button);
        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent loadgameIntent = new Intent(view.getContext(), AboutActivity.class);
                startActivity(loadgameIntent);
            }


        });


    }
}