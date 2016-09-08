package com.hku.zhangjingjing.othello.Bean;

import java.util.*;

public class myStack{
    private chess_pieces[][][] chessArray;
    private int top;
    private final static int size = 100;
    public myStack(){
        chessArray = new chess_pieces[size][8][8];//array[][0]:position_x,array[][1]:position_y,array[][2]:chess color?0=white,1=black, array[][3]:auto change?0=no,1=yes
        top = 0; //stack is empty
    }

    public void push(chess_pieces[][] chessPieces){
        if(top == size){
            throw new StackOverflowError();
        }
        else {
            ++top;
            chessArray[top] = chessPieces;

        }
    }

    public void pop(){
        if(top == 0) {
            throw new EmptyStackException();
        }
        else
            //pop out every auto change chess until meet a manually change chess.
            --top;
    }

    public boolean isEmpty(){
        return top == 0;
    }
    //return the color of top of the stack
    public chess_pieces[][] peek(){
        if(top == 0){
            throw new EmptyStackException();
        }
        return chessArray[top];
    }

    public int getTop(){
        return top;
    }

}