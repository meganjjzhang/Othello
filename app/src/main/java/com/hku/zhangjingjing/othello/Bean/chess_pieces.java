package com.hku.zhangjingjing.othello.Bean;

/**
 * Created by Megan on 26/10/15.
 */
public class chess_pieces {

    private int color;//chess color?0=white,1=black,2=don't have a chess right now
    private int potentialColor;//0=could be white,1=could be black,-1=could not be black or white

    public void chess_pieces(int positon_x,int positon_y){
        this.color = 2;
        this.potentialColor = -1;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int isColor() {
        return color;
    }

    public void setPotentialColor(int potentialColor){
        this.potentialColor = potentialColor;
    }

    public int getPotentialColor(){
        return potentialColor;
    }
}
