package com.hku.zhangjingjing.othello;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AboutActivity extends AppCompatActivity {

    Button button_home;
    LinearLayout layout_linear;
    LinearLayout layout_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        button_home = (Button)findViewById(R.id.home_button);
        layout_linear = (LinearLayout)findViewById(R.id.linear);
        layout_home = (LinearLayout)findViewById(R.id.linear_home);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        int chess_width = screen_width/10;//set the chess's width by detect screen width.
        int table_width = chess_width*8;
        LinearLayout.LayoutParams par_home = (LinearLayout.LayoutParams) layout_home.getLayoutParams();
        par_home.setMargins(chess_width,0,0,0);
        layout_home.setLayoutParams(par_home);
        LinearLayout.LayoutParams par_about = (LinearLayout.LayoutParams) layout_linear.getLayoutParams();
        par_about.width = table_width;
        par_about.setMargins(chess_width, 0, chess_width, 0);
        layout_linear.setLayoutParams(par_about);



        button_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
//                Intent gohomeIntent = new Intent(arg0.getContext(),WelcomeActivity.class);//???
//                startActivity(gohomeIntent);
                AboutActivity.this.finish();
            }
        });

    }

}
