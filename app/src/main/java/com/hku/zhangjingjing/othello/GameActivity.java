package com.hku.zhangjingjing.othello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hku.zhangjingjing.othello.Bean.chess_pieces;

import java.util.HashMap;
import java.util.Stack;


public class GameActivity extends Activity{

    Button button_home;
    Button button_new_game;
    TextView text_congratulation_message;
    LinearLayout layout_table_above;
    LinearLayout row00;
    LinearLayout row01;
    LinearLayout row02;
    LinearLayout row03;
    LinearLayout row04;
    LinearLayout row05;
    LinearLayout row06;
    LinearLayout row07;
    TextView text_white_piece_number;
    TextView text_black_piece_number;
    ImageView img_white_pieces;
    ImageView img_black_pieces;
    LinearLayout layout_home_new_retract;
    LinearLayout layout_table;
    LinearLayout getLayout_table_below;
    LinearLayout layout_hint_sound;
    ImageView img_next_turn;
    Button button_hint;
    Button button_retract;
    Button button_sound;
    Button button_effect;
    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    HashMap<Integer, Integer> soundPoolMap;

    boolean isCurrentColorBlack;
    int hint_on = 1;
    int sound_on = 1;
    int effect_on = 1;
    int black_piece_number;
    int white_piece_number;
    int soundID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button_hint = (Button)findViewById(R.id.hint_button);
        button_sound = (Button)findViewById(R.id.sound_button);
        button_effect = (Button)findViewById(R.id.effect_button);
        button_home = (Button)findViewById(R.id.home_button);
        button_new_game = (Button)findViewById(R.id.new_game_button);
        layout_home_new_retract = (LinearLayout)findViewById(R.id.layout_home_new_retract);
        layout_table = (LinearLayout)findViewById(R.id.table_layout);
        layout_table_above = (LinearLayout)findViewById(R.id.table_layout_above);
        layout_hint_sound = (LinearLayout)findViewById(R.id.layout_hint_sound);
        getLayout_table_below= (LinearLayout)findViewById(R.id.table_layout_below);
        row00 = (LinearLayout)findViewById(R.id.row0);
        row01 = (LinearLayout)findViewById(R.id.row1);
        row02 = (LinearLayout)findViewById(R.id.row2);
        row03 = (LinearLayout)findViewById(R.id.row3);
        row04 = (LinearLayout)findViewById(R.id.row4);
        row05 = (LinearLayout)findViewById(R.id.row5);
        row06 = (LinearLayout)findViewById(R.id.row6);
        row07 = (LinearLayout)findViewById(R.id.row7);
        img_white_pieces = (ImageView)findViewById(R.id.white_pieces_img);
        img_black_pieces = (ImageView)findViewById(R.id.black_pieces_img);
        img_next_turn = (ImageView)findViewById(R.id.next_turn_img);
        text_white_piece_number = (TextView)findViewById(R.id.white_pieces_number_text);
        text_black_piece_number = (TextView)findViewById(R.id.black_pieces_number_text);
        text_congratulation_message = (TextView)findViewById(R.id.congratudation_message);
        mediaPlayer = MediaPlayer.create(this, R.raw.the_long_and_winding_street);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(soundID, soundPool.load(this, R.raw.effect, 1));

/*
        this part is to change the size of conponent automatically according to the size of screen.
*/
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        int chess_width = screen_width/10;//set the chess's width by detect screen width.
        final int chess_height = chess_width;
        int table_width = chess_width*8;
        int chess_margin = 1;
        int button_height = chess_height*2;
        //set "table"'s width and height
        LayoutParams par_table = (LayoutParams) layout_table.getLayoutParams();
        par_table.width = table_width;
        par_table.height = table_width;
        layout_table.setLayoutParams(par_table);
        //set the LinearLayout's height
        LayoutParams par_linear_above = (LayoutParams) layout_home_new_retract.getLayoutParams();
        par_linear_above.height = chess_height;
        layout_home_new_retract.setLayoutParams(par_linear_above);
        //set the LinearLayout's width above the table
        LayoutParams par_table_above = (LayoutParams) layout_table_above.getLayoutParams();
        par_table_above.width = table_width;
        par_table_above.height = chess_height;
        layout_table_above.setLayoutParams(par_table_above);
        //set the LinearLayout's height below the table
        LayoutParams par_table_below = (LayoutParams) getLayout_table_below.getLayoutParams();
        par_table_below.width = table_width;
        par_table_below.height = chess_height;
        getLayout_table_below.setLayoutParams(par_table_below);
        //set the LinearLayout's height
        LayoutParams par_linear_below = (LayoutParams) layout_hint_sound.getLayoutParams();
        par_linear_below.height = button_height;
        par_linear_below.width = table_width;
        layout_hint_sound.setLayoutParams(par_linear_below);
        //set each row's height
        LayoutParams par_row0 = (LayoutParams) row00.getLayoutParams();
        par_row0.height = chess_height;
        LayoutParams par_row1 = (LayoutParams) row01.getLayoutParams();
        par_row1.height = chess_height;
        LayoutParams par_row2 = (LayoutParams) row02.getLayoutParams();
        par_row2.height = chess_height;
        LayoutParams par_row3 = (LayoutParams) row03.getLayoutParams();
        par_row3.height = chess_height;
        LayoutParams par_row4 = (LayoutParams) row04.getLayoutParams();
        par_row4.height = chess_height;
        LayoutParams par_row5 = (LayoutParams) row05.getLayoutParams();
        par_row5.height = chess_height;
        LayoutParams par_row6 = (LayoutParams) row06.getLayoutParams();
        par_row6.height = chess_height;
        LayoutParams par_row7 = (LayoutParams) row07.getLayoutParams();
        par_row7.height = chess_height;

/*
        this part is to Initial chess board
*/
        LinearLayout.LayoutParams par_img;
        ImageView img;
        final chess_pieces[][] chessArray = new chess_pieces[8][8];
        final Stack<chess_pieces[][]> test=new Stack<>();

        for(int x=0;x < 8;x++){
            for(int y=0;y < 8;y++){
                img = (ImageView)findViewById(getId(x,y));
                img.setImageResource(R.drawable.transparent);
                par_img = (LayoutParams) img.getLayoutParams();
                par_img.setMargins(chess_margin, chess_margin, chess_margin, chess_margin);
                chessArray[x][y] = new chess_pieces();
                chessArray[x][y].chess_pieces(x, y);
            }
        }

        

        //initial the chess board with 4 initial pieces, 2 black and 2 white pieces without push into stack
        changeColortoWhite(3, 3, chessArray);
        changeColortoWhite(4, 4, chessArray);
        changeColortoBlack(3, 4, chessArray);
        changeColortoBlack(4, 3, chessArray);
        changeColortoPotentialBlack(2, 3, chessArray);
        changeColortoPotentialBlack(3, 2, chessArray);
        changeColortoPotentialBlack(4, 5, chessArray);
        changeColortoPotentialBlack(5, 4, chessArray);

        button_hint.setBackgroundResource(R.drawable.bulb_yes);
        mediaPlayer.start();
        button_sound.setBackgroundResource(R.drawable.sound_yes);
        button_effect.setBackgroundResource(R.drawable.effect_yes);

        //initial the amount of chess
        text_black_piece_number.setText(": 2");
        text_white_piece_number.setText(": 2");
        black_piece_number = 2;
        white_piece_number = 2;

        //first player is Black one
        isCurrentColorBlack = true;
        img_next_turn.setImageResource(R.drawable.black_chess);


        //give every view a Click Listener
        for(int x=0;x < 8;x++){
            for(int y=0;y < 8;y++){
                final int px = x;
                final int py = y;
                ImageView imgForClick;
                imgForClick = (ImageView)findViewById(getId(x,y));
                imgForClick.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        //after each click, system will give a response
                        //1. judge whether this move is valid
                        if(isAValidMove(isCurrentColorBlack,px,py,chessArray)){

                            if(effect_on == 1){
                                AudioManager audioManager =
                                        (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                                float curVolume =
                                        audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                float maxVolume =
                                        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                float leftVolume = curVolume/maxVolume;
                                float rightVolume = curVolume/maxVolume;
                                int priority = 1;
                                int no_loop = 0;
                                float normal_playback_rate = 1f;
                                soundPool.play(soundID, leftVolume, rightVolume, priority, no_loop,
                                        normal_playback_rate);
                            }

                            //store current chess board status
                            chess_pieces[][] chessArrayForPush = new chess_pieces[8][8];
                            for(int i=0;i<8;i++){
                                for(int j=0;j<8;j++){
                                    chessArrayForPush[i][j] = new chess_pieces();
                                    chessArrayForPush[i][j].chess_pieces(i, j);
                                    chessArrayForPush[i][j].setColor(chessArray[i][j].isColor());
                                    chessArrayForPush[i][j].setPotentialColor(chessArray[i][j].getPotentialColor());
                                }
                            }
                            test.push(chessArrayForPush);

                            //if current move belong to black chess,then:
                            if(isCurrentColorBlack){
                                //change current chess into black
                                newBlackChess(px, py,chessArray);
                                black_piece_number++;
                                //change all the chess's color accordingly
                                flipUL(isCurrentColorBlack,px,py, chessArray,black_piece_number,white_piece_number);
                                flipU(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                                flipUR(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                                flipL(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                                flipR(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                                flipDL(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                                flipD(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                                flipDR(isCurrentColorBlack, px, py,  chessArray, black_piece_number,white_piece_number);
                            }
                            else{
                                //if current move belong to white chess,then:
                                newWhiteChess(px,py,chessArray);
                                white_piece_number++;
                                flipUL(isCurrentColorBlack,px,py, chessArray,white_piece_number,black_piece_number);
                                flipU(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                                flipUR(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                                flipL(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                                flipR(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                                flipDL(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                                flipD(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                                flipDR(isCurrentColorBlack, px, py,  chessArray, white_piece_number,black_piece_number);
                            }

                            text_black_piece_number.setText(": "+black_piece_number);
                            text_white_piece_number.setText(": "+white_piece_number);
                            //assuming the color of next move will change
                            //reverse the statement of isCurrentColorBlack
                            if(isCurrentColorBlack)isCurrentColorBlack = false;
                            else if(!isCurrentColorBlack)isCurrentColorBlack = true;
                            //judge which one wins
                            if(white_piece_number+black_piece_number == 64){
                                if(white_piece_number > black_piece_number){
                                    text_congratulation_message.setText("White Win!");
                                }
                                else if(white_piece_number < black_piece_number)
                                    text_congratulation_message.setText("Black Win!");
                                else
                                    text_congratulation_message.setText("Game Draw");
                            }
                            //searchAndSetPotentialChess will find next potential move
                            //if return true:current chess color could has next move,if return false:can't
                            else if(searchAndSetPotentialChess(isCurrentColorBlack, chessArray,true)){
                                detectHint(hint_on,chessArray);
                                if(isCurrentColorBlack)
                                    img_next_turn.setImageResource(R.drawable.black_chess);
                                else
                                    img_next_turn.setImageResource(R.drawable.white_chess);
                            }
                            else{
                                //no potential chess, come to an end
                                if(isCurrentColorBlack)
                                    text_congratulation_message.setText("White Win!");
                                else
                                    text_congratulation_message.setText("Black Win!");

                            }

                        }
                    }
                });
            }
        }

        button_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
//                Intent gohomeIntent = new Intent(arg0.getContext(),WelcomeActivity.class);//???
//                startActivity(gohomeIntent);
                GameActivity.this.finish();
                soundPool.stop(soundID);
            }
        });

        button_new_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        changeColortoTransparent(i,j,chessArray);
                        chessArray[i][j] = new chess_pieces();
                        chessArray[i][j].chess_pieces(i, j);
                    }
                }
                //initial the chess board with 4 initial pieces, 2 black and 2 white pieces without push into stack
                changeColortoWhite(3, 3, chessArray);
                changeColortoWhite(4, 4, chessArray);
                changeColortoBlack(3, 4, chessArray);
                changeColortoBlack(4, 3, chessArray);
                changeColortoPotentialBlack(2, 3, chessArray);
                changeColortoPotentialBlack(3, 2, chessArray);
                changeColortoPotentialBlack(4, 5, chessArray);
                changeColortoPotentialBlack(5, 4, chessArray);

                //initial the amount of chess
                text_black_piece_number.setText(": 2");
                text_white_piece_number.setText(": 2");
                black_piece_number = 2;
                white_piece_number = 2;

                //store current chess board status
                chess_pieces[][] chessArrayForPush = new chess_pieces[8][8];
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessArrayForPush[i][j] = new chess_pieces();
                        chessArrayForPush[i][j].chess_pieces(i, j);
                        chessArrayForPush[i][j].setColor(chessArray[i][j].isColor());
                        chessArrayForPush[i][j].setPotentialColor(chessArray[i][j].getPotentialColor());
                    }
                }
                while(!test.empty()){
                    test.pop();
                }
                test.push(chessArrayForPush);
                //first player is Black one
                isCurrentColorBlack = true;
                img_next_turn.setImageResource(R.drawable.black_chess);
                detectHint(hint_on,chessArray);
            }
        });



        button_hint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                hint_on = (hint_on+1)%2;
                detectHint(hint_on,chessArray);
            }
        });

        button_sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                sound_on = (sound_on+1)%2;
                if(sound_on == 1){
                    mediaPlayer.start();
                    button_sound.setBackgroundResource(R.drawable.sound_yes);
                }
                else{
                    mediaPlayer.pause();
                    button_sound.setBackgroundResource(R.drawable.sound_no);
                }

            }
        });
        button_effect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                effect_on = (effect_on+1)%2;
                if(effect_on != 1){
                    soundPool.pause(soundID);
                    button_effect.setBackgroundResource(R.drawable.effect_no);
                }
                else
                    button_effect.setBackgroundResource(R.drawable.effect_yes);
            }
        });
        button_retract = (Button)findViewById(R.id.retract_button);
        button_retract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(!test.empty()) {
                    chess_pieces[][] lastBoard = new chess_pieces[8][8];
                    lastBoard = test.pop();
                    //judge the statement of isCurrentColorBlack
                    outterLoop:for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (lastBoard[i][j].getPotentialColor() == 0) {
                                isCurrentColorBlack = false;
                                break outterLoop;
                            }
                            else if (lastBoard[i][j].getPotentialColor() == 1) {
                                isCurrentColorBlack = true;
                                break outterLoop;
                            }
                        }
                    }
                    //draw the last board
                    black_piece_number = 0;
                    white_piece_number = 0;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            chessArray[i][j] = lastBoard[i][j];
                            if (lastBoard[i][j].isColor() == 0) {
                                newWhiteChess(i, j, lastBoard);
                                white_piece_number++;
                            }
                            if (lastBoard[i][j].isColor() == 1) {
                                newBlackChess(i, j, lastBoard);
                                black_piece_number++;
                            }
                        }
                    }
                    text_black_piece_number.setText(": "+black_piece_number);
                    text_white_piece_number.setText(": " + white_piece_number);
                    if(isCurrentColorBlack)
                        img_next_turn.setImageResource(R.drawable.black_chess);
                    else
                        img_next_turn.setImageResource(R.drawable.white_chess);
                    detectHint(hint_on,lastBoard);
                }
                else
                    System.out.println("test.empty()=true");
            }
        });
    }

    //convert digits to Id.
    //int x, int y
    //return: R.id.chess
    private int getId(int x, int y){
        String strImgViewtnId = "chess" + x + y;
        return getResources().getIdentifier(strImgViewtnId, "id", "com.hku.zhangjingjing.othello");
    }

    private void newBlackChess(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.black_chess);
        chessArray[x][y].setColor(1);
        chessArray[x][y].setPotentialColor(-1);
    }

    private void newWhiteChess(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.white_chess);
        chessArray[x][y].setColor(0);
        chessArray[x][y].setPotentialColor(-1);
    }

    private void autoNewBlackChess(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.black_chess);
        chessArray[x][y].setColor(1);
        chessArray[x][y].setPotentialColor(-1);
    }

    private void autoNewWhiteChess(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.white_chess);
        chessArray[x][y].setColor(0);
        chessArray[x][y].setPotentialColor(-1);
    }

    private void changeColortoWhite(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.white_chess);
        chessArray[x][y].setColor(0);
    }

    private void changeColortoPotentialWhite(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.white_chess_t);
        chessArray[x][y].setPotentialColor(0);
    }

    private void changeColortoBlack(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.black_chess);
        chessArray[x][y].setColor(1);
    }

    private void changeColortoPotentialBlack(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.black_chess_t);
        chessArray[x][y].setPotentialColor(1);
    }

    private void changeColortoTransparent(int x, int y,chess_pieces[][] chessArray){
        ImageView img;
        img = (ImageView)findViewById(getId(x,y));
        img.setImageResource(R.drawable.transparent);
        chessArray[x][y].setColor(2);
    }

    private boolean isAValidMove(boolean isCurrentColorBlack,int x, int y,chess_pieces[][] chessArray){
        if(isCurrentColorBlack)
            return (chessArray[x][y].getPotentialColor() == 1);
        else
            return (chessArray[x][y].getPotentialColor() == 0);
    }

    private void flipUL(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){//current color is black
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fy>0&&fx>0;f++){
                fy--;
                fx--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {//find a
                    flag = 1;
                    System.out.println("flag UL");
                    break;
                }

            }
            for(int i=0;yy>0&&xx>0;i++){
                xx--;
                yy--;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipUL,x:"+x+",y:"+y);
                    autoNewBlackChess(xx, yy, chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fy>0&&fx>0;f++){
                fy--;
                fx--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag UL");
                    break ;
                }

            }
            for(int i=0;yy>0&&xx>0;i++){
                xx--;
                yy--;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipUL,x:"+x+",y:"+y);
                    autoNewWhiteChess(xx,yy,chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipU(boolean isCurrentColorBlack, int x, int y, chess_pieces[][] chessArray, int piece_number, int another_piece_number) {
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx>0;f++){
                fx--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag U");
                    break ;
                }
            }
            for(int i=0;xx>0;i++){
                xx--;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipU,x:"+x+",y:"+y);
                    autoNewBlackChess(xx, yy, chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx>0;f++){
                fx--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag U");
                    break ;
                }

            }
            for(int i=0;xx>0;i++){
                xx--;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipU,x:"+x+",y:"+y);
                    autoNewWhiteChess(xx, yy, chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipUR(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx>0&&fy<7;f++){
                fx--;
                fy++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag UR");
                    System.out.println("flag UR, fx="+fx+",fy="+fy);
                    break ;
                }
            }
            for(int i=0;xx>0&&yy<7;i++){
                yy++;
                xx--;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipUR,x:"+x+",y:"+y);
                    autoNewBlackChess(xx,yy,chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx>0&&fy<7;f++){
                fx--;
                fy++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag UR, fx="+fx+",fy="+fy);
                    break ;
                }
            }
            for(int i=0;xx>0&&yy<7;i++){
                yy++;
                xx--;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipUR,x:"+x+",y:"+y);
                    autoNewWhiteChess(xx,yy,chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipL(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fy>0;f++){
                fy--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag L");
                    break ;
                }
            }
            for(int i=0;yy>0;i++){
                yy--;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipL,x:"+x+",y:"+y);
                    autoNewBlackChess(xx,yy,chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fy>0;f++){
                fy--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag L");
                    break ;
                }
            }
            for(int i=0;yy>0;i++){
                yy--;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipL,x:"+x+",y:"+y);
                    autoNewWhiteChess(xx,yy,chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipR(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fy<7;f++){
                fy++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag R");
                    break ;
                }

            }
            for(int i=0;yy<7;i++){
                yy++;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipR,x:"+x+",y:"+y);
                    autoNewBlackChess(xx, yy, chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fy<7;f++){
                fy++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag R");
                    break ;
                }
            }
            for(int i=0;yy<7;i++){
                yy++;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipR,x:"+x+",y:"+y);
                    autoNewWhiteChess(xx,yy,chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipDL(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx<7&&fy>0;f++){
                fx++;
                fy--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag DL");
                    break ;
                }
            }
            for(int i=0;xx<7&&yy>0;i++){
                yy--;
                xx++;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipDL,x:"+x+",y:"+y);
                    autoNewBlackChess(xx,yy,chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx<7&&fy>0;f++){
                fx++;
                fy--;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag DL");
                    break ;
                }
            }
            for(int i=0;xx<7&&yy>0;i++){
                yy--;
                xx++;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipDL,x:" + x + ",y:" + y);
                    autoNewWhiteChess(xx, yy, chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipD(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx<7;f++){
                fx++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag D");
                    break ;
                }
            }
            for(int i=0;xx<7;i++){
                xx++;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipD,x:"+x+",y:"+y);
                    autoNewBlackChess(xx,yy,chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx<7;f++){
                fx++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag D");
                    break ;
                }

            }
            for(int i=0;xx<7;i++){
                xx++;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipD,x:" + x + ",y:" + y);
                    autoNewWhiteChess(xx, yy, chessArray);
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }
    private void flipDR(boolean isCurrentColorBlack,int x,int y, chess_pieces[][] chessArray,int piece_number, int another_piece_number){
        int xx=x,yy=y;
        if(isCurrentColorBlack){
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx<7&&fy<7;f++){
                fx++;
                fy++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 1) {
                    flag = 1;
                    System.out.println("flag DR");
                    break ;
                }
            }
            for(int i=0;xx<7&&yy<7;i++){
                xx++;
                yy++;
                if(chessArray[xx][yy].isColor() == 0 && flag == 1){
                    System.out.println("flipDR,x:"+x+",y:"+y+",xx:"+xx+",yy:"+yy);
                    autoNewBlackChess(xx,yy,chessArray);
                    setBlackPieceNumber(++piece_number);
                    setWhitePieceNumber(--another_piece_number);
                }
                else break;
            }
        }
        else{
            int flag=0;
            int fx=x,fy=y;
            for(int f=0;fx<7&&fy<7;f++){
                fx++;
                fy++;
                if (chessArray[fx][fy].isColor() == 2)//if meets a blank, stop test
                    break;
                if(chessArray[fx][fy].isColor() == 0) {
                    flag = 1;
                    System.out.println("flag DR");
                    break ;
                }
            }
            for(int i=0;xx<7&&yy<7;i++){
                xx++;
                yy++;
                if(chessArray[xx][yy].isColor() == 1 && flag == 1){
                    System.out.println("flipDR,x:"+x+",y:"+y+",xx:"+xx+",yy:"+yy);
                    System.out.println("before autoNewWhiteChess,[4][2].isColor="+chessArray[4][2].isColor());
                    autoNewWhiteChess(xx, yy, chessArray);
                    System.out.println("after autoNewWhiteChess,[4][2].isColor=" + chessArray[4][2].isColor());
                    setWhitePieceNumber(++piece_number);
                    setBlackPieceNumber(--another_piece_number);
                }
                else break;
            }
        }
    }

    //return the color:-1=error;0=white,1=black,2=don't have a chess right now
    private int findTheColorOfLU(int x,int y,chess_pieces[][] chessArray){
        x--;
        y--;
        if(x>=0 && y>=0){
            return chessArray[x][y].isColor();
        }
        else return -1;
    }
    private int findTheColorOfU(int x,int y,chess_pieces[][] chessArray){
        x--;
        if(x>=0){
            return chessArray[x][y].isColor();
        }
        else return -1;
    }
    private int findTheColorOfRU(int x,int y,chess_pieces[][] chessArray){
        y++;
        x--;
        if(y<8 && x>=0){
            return chessArray[x][y].isColor();
        }
        else return -1;
    }
    private int findTheColorOfL(int x,int y,chess_pieces[][] chessArray){
        y--;
        if(y>=0){
            return chessArray[x][y].isColor();
        }
        else return -1;
    }
    private int findTheColorOfR(int x,int y,chess_pieces[][] chessArray){
        y++;
        if(y<8){
            return chessArray[x][y].isColor();
        }
        else return -1;
    }
    private int findTheColorOfLD(int x,int y,chess_pieces[][] chessArray){
        y--;
        x++;
        if(y>=0 && x<8){
            return chessArray[x][y].isColor();
        }
        else return -1;
    }
    private int findTheColorOfD(int x,int y,chess_pieces[][] chessArray){
        x++;
        if(x<8){
            return chessArray[x][y].isColor();
        }
        else
            return -1;
    }
    private int findTheColorOfRD(int x,int y,chess_pieces[][] chessArray){
        x++;
        y++;
        if(x<8 && y<8){
            return chessArray[x][y].isColor();
        }
        else
            return -1;
    }

    //searchAndSetPotentialChess will find next potential move
    //return true:current chess color could has next move,false:can't
    private boolean searchAndSetPotentialChess(boolean isCurrentColorBlack, chess_pieces[][] chessArray,boolean firstTime) {
        //reset the status of potential color
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessArray[i][j].setPotentialColor(-1);
            }
        }
        boolean flag = false;
        //flag=true means current color could move in the next turn, and nothing has to change,
        //if flag=false,change isCurrentColorBlack and judge potential chess again
        if (isCurrentColorBlack) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessArray[i][j].isColor() == 0) {//if curent color searched out is white, find the surroundings which is transparant
                        //  System.out.println("if(chessArray[i][j].isColor() == 0),i=" + i + "j=" + j);
                        if (findTheColorOfLU(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii < 8 && jj < 8; k++) {//find the black downwards
                                if (findTheColorOfRD(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfRD(ii, jj, chessArray) == 1) {
                                    chessArray[i - 1][j - 1].setPotentialColor(1);
                                    flag = true;
                                    //   System.out.println("LU,i=" + i + "j=" + j);
                                }
                                ii++;
                                jj++;
                            }
                        }
                        if (findTheColorOfU(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            for (int k = 0; ii < 8; k++) {//find the black downwards
                                if (findTheColorOfD(ii, j, chessArray) == 2)
                                    break;
                                if (findTheColorOfD(ii, j, chessArray) == 1) {
                                    chessArray[i - 1][j].setPotentialColor(1);
                                    flag = true;
                                    //     System.out.println("U,i=" + i + "j=" + j);
                                }
                                ii++;
                            }
                        }
                        if (findTheColorOfRU(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii < 8 && jj >= 0; k++) {//find the black downwards
                                if (findTheColorOfLD(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfLD(ii, jj, chessArray) == 1) {
                                    chessArray[i - 1][j + 1].setPotentialColor(1);
                                    flag = true;
                                    //      System.out.println("RU,i=" + i + "j=" + j);
                                }
                                ii++;
                                jj--;
                            }
                        }
                        if (findTheColorOfL(i, j, chessArray) == 2) {//if a place is blank
                            int jj = j;
                            for (int k = 0; jj < 8; k++) {//find the black upwards
                                if (findTheColorOfR(i, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfR(i, jj, chessArray) == 1) {
                                    chessArray[i][j - 1].setPotentialColor(1);
                                    flag = true;
                                    //       System.out.println("L,i=" + i + "j=" + j);
                                }
                                jj++;
                            }
                        }
                        if (findTheColorOfR(i, j, chessArray) == 2) {//if a place is blank
                            int jj = j;
                            for (int k = 0; jj >= 0; k++) {//find the black leftwards
                                if (findTheColorOfL(i, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfL(i, jj, chessArray) == 1) {
                                    chessArray[i][j + 1].setPotentialColor(1);
                                    flag = true;
                                    //          System.out.println("R,i=" + i + "j=" + j);
                                }
                                jj--;
                            }
                        }
                        if (findTheColorOfLD(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii >= 0 && jj < 8; k++) {//find the black rightwards
                                if (findTheColorOfRU(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfRU(ii, jj, chessArray) == 1) {
                                    chessArray[i + 1][j - 1].setPotentialColor(1);
                                    flag = true;
                                    //         System.out.println("LD,i=" + i + "j=" + j);
                                }
                                ii--;
                                jj++;
                            }
                        }
                        if (findTheColorOfD(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            for (int k = 0; k < 6; k++) {//find the black rightwards
                                if (findTheColorOfU(ii, j, chessArray) == 2)
                                    break;
                                if (findTheColorOfU(ii, j, chessArray) == 1) {
                                    chessArray[i + 1][j].setPotentialColor(1);
                                    flag = true;
                                    //         System.out.println("D,i=" + i + "j=" + j);
                                }
                                ii--;
                            }
                        }
                        if (findTheColorOfRD(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii >= 0 && jj >= 0; k++) {//find the black rightwards
                                if (findTheColorOfLU(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfLU(ii, jj, chessArray) == 1) {
                                    chessArray[i + 1][j + 1].setPotentialColor(1);
                                    flag = true;
                                    //        System.out.println("RD,i=" + i + "j=" + j);
                                }
                                jj--;
                                ii--;
                            }
                        }
                    }
                }
            }
            //flag=true means current color could move in the next turn, and nothing has to change,
            //if flag=false,change isCurrentColorBlack and judge potential chess again
            if (flag)
                return true;
            else if (firstTime) {
                isCurrentColorBlack = false;
                searchAndSetPotentialChess(isCurrentColorBlack, chessArray, false);
            } else
                return false;
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessArray[i][j].isColor() == 1) {//if curent color searched out is black, find the surroundings which is transparant
                        if (findTheColorOfLU(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii < 8 && jj < 8; k++) {//find the white downwards
                                if (findTheColorOfRD(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfRD(ii, jj, chessArray) == 0) {
                                    chessArray[i - 1][j - 1].setPotentialColor(0);
                                    flag = true;
                                    //         System.out.println("LU,i=" + i + "j=" + j);
                                }
                                ii++;
                                jj++;
                            }
                        }
                        if (findTheColorOfU(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            for (int k = 0; ii < 8; k++) {//find the white downwards
                                if (findTheColorOfD(ii, j, chessArray) == 2)
                                    break;
                                if (findTheColorOfD(ii, j, chessArray) == 0) {
                                    chessArray[i - 1][j].setPotentialColor(0);
                                    flag = true;
                                    //           System.out.println("U,i=" + i + "j=" + j);
                                }
                                ii++;
                            }
                        }
                        if (findTheColorOfRU(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii < 8 && jj >= 0; k++) {//find the white downwards
                                if (findTheColorOfLD(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfLD(ii, jj, chessArray) == 0) {
                                    chessArray[i - 1][j + 1].setPotentialColor(0);
                                    flag = true;
                                    //        System.out.println("R,i=" + i + "j=" + j);
                                }
                                ii++;
                                jj--;
                            }
                        }
                        if (findTheColorOfL(i, j, chessArray) == 2) {//if a place is blank
                            int jj = j;
                            for (int k = 0; jj < 8; k++) {//find the white upwards
                                if (findTheColorOfR(i, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfR(i, jj, chessArray) == 0) {
                                    chessArray[i][j - 1].setPotentialColor(0);
                                    flag = true;
                                    //          System.out.println("L,i=" + i + "j=" + j);
                                }
                                jj++;
                            }
                        }
                        if (findTheColorOfR(i, j, chessArray) == 2) {//if a place is blank
                            int jj = j;
                            for (int k = 0; jj >= 0; k++) {//find the white leftwards
                                if (findTheColorOfL(i, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfL(i, jj, chessArray) == 0) {
                                    chessArray[i][j + 1].setPotentialColor(0);
                                    flag = true;
                                    //              System.out.println("R,i=" + i + "j=" + j);
                                }
                                jj--;
                            }
                        }
                        if (findTheColorOfLD(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii >= 0 && jj < 8; k++) {//find the white rightwards
                                if (findTheColorOfRU(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfRU(ii, jj, chessArray) == 0) {
                                    chessArray[i + 1][j - 1].setPotentialColor(0);
                                    flag = true;
                                    //               System.out.println("LD,i=" + i + "j=" + j);
                                }
                                ii--;
                                jj++;
                            }
                        }
                        if (findTheColorOfD(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            for (int k = 0; k < 6; k++) {//find the white rightwards
                                if (findTheColorOfU(ii, j, chessArray) == 2)
                                    break;
                                if (findTheColorOfU(ii, j, chessArray) == 0) {
                                    chessArray[i + 1][j].setPotentialColor(0);
                                    flag = true;
                                    //            System.out.println("D,i=" + i + "j=" + j);
                                }
                                ii--;
                            }
                        }
                        if (findTheColorOfRD(i, j, chessArray) == 2) {//if a place is blank
                            int ii = i;
                            int jj = j;
                            for (int k = 0; ii >= 0 && jj >= 0; k++) {//find the white rightwards
                                if (findTheColorOfLU(ii, jj, chessArray) == 2)
                                    break;
                                if (findTheColorOfLU(ii, jj, chessArray) == 0) {
                                    chessArray[i + 1][j + 1].setPotentialColor(0);
                                    flag = true;
                                    //            System.out.println("RD,i=" + i + "j=" + j);
                                }
                                jj--;
                                ii--;
                            }
                        }

                    }
                }
            }
            if (flag)
                return true;
            else if (firstTime) {
                isCurrentColorBlack = true;
                searchAndSetPotentialChess(isCurrentColorBlack, chessArray, false);
            }
            else
                return false;
        }
        return true;

    }

    private void setBlackPieceNumber(int pieceNumber){
        this.black_piece_number = pieceNumber;
    }

    private void setWhitePieceNumber(int pieceNumber){
        this.white_piece_number = pieceNumber;
    }

    private void detectHint(int hint_on, chess_pieces[][] chessArray){
        if (hint_on == 1) {//show hint
            button_hint.setBackgroundResource(R.drawable.bulb_yes);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessArray[i][j].getPotentialColor() == 0)
                        changeColortoPotentialWhite(i, j, chessArray);
                    else if (chessArray[i][j].getPotentialColor() == 1)
                        changeColortoPotentialBlack(i, j, chessArray);
                    if (chessArray[i][j].getPotentialColor() == -1 && chessArray[i][j].isColor() == 2)
                        changeColortoTransparent(i, j, chessArray);
                }
            }
        } else if (hint_on == 0) {//hide hint
            button_hint.setBackgroundResource(R.drawable.bulb_no);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessArray[i][j].isColor() == 2)
                        changeColortoTransparent(i, j, chessArray);
                }
            }
        }
    }

}
