package com.krakendepp.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//Button class, this class will handle each button seperatly

public class Button {
    Context context;
    String orientation;               // button orientation.
    Bitmap button_pic;                  // picture of the button
    int Screen_width,Screen_height;  //screem size
    int pos_x,pos_y;                //button position
    int buttonsize;                 //button size

    public Button(Context context,String orientation,int Screen_width,int Screen_height){

        this.context=context;
        this.orientation=orientation;
        this.Screen_height=Screen_height;
        this.Screen_width=Screen_width;
        this.buttonsize=Screen_width/5;

        if(orientation.equals("left")){
            pos_x= 0;
            pos_y=Screen_height-buttonsize;
            button_pic= BitmapFactory.decodeResource(context.getResources(),R.drawable.left);
            button_pic = Bitmap.createScaledBitmap(button_pic, buttonsize,buttonsize,true);
        }

        else if(orientation.equals("right")){
            pos_x= buttonsize;
            pos_y=Screen_height-buttonsize;
            button_pic= BitmapFactory.decodeResource(context.getResources(),R.drawable.right);
            button_pic = Bitmap.createScaledBitmap(button_pic, buttonsize,buttonsize,true);
        }

        else{
            pos_x=  4*buttonsize;
            pos_y=Screen_height-buttonsize;
            button_pic= BitmapFactory.decodeResource(context.getResources(),R.drawable.shoot);
            button_pic = Bitmap.createScaledBitmap(button_pic, buttonsize,buttonsize,true);
        }
    }

    public String getOrientation(){return  orientation;}
    public Bitmap getButton_pic(){return button_pic;}
    public int getButtonsize(){return buttonsize;}
    public int getPos_x(){return pos_x;}
    public int getPos_y(){return pos_y;}


}
