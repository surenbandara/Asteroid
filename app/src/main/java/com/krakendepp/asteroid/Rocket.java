package com.krakendepp.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

//Using this class  create rocket
public  class Rocket {
    Context context ;
    Bitmap rocket;                              //rocket picture
    int x,y,velocity,screen_width,screen_height; //rocket position and moving velocity
    boolean Live =true;                          //Whether the rocket still live or not
    Random random;
    int buttonsize ;

    public Rocket (Context context,int Screen_width ,int Screen_height,int buttonsize){
        this.context=context;
        this.screen_width=Screen_width;
        this.screen_height=Screen_height;
        this.buttonsize=buttonsize;
        rocket = BitmapFactory.decodeResource(context.getResources(),R.drawable.rocket);
        rocket = Bitmap.createScaledBitmap(rocket, Screen_width/15,2*Screen_width/15,true);
        random =new Random();
        Restart();}


    public Bitmap Get_rocketpic(){
        return rocket;
    }
    public int Rocket_width(){return rocket.getWidth();}
    public int Rocket_height(){return rocket.getHeight();}
    public int Rocket_posx(){return x;}
    public int Rocket_posy(){return y;}

    //move right
    public void Rocket_moveright(){
        if(x<this.screen_width-this.Rocket_width()-velocity ){
        x+=velocity;}}

    //move left
    public void Rocket_moveleft(){if( x>velocity){
        x-=velocity;}}


    //check the rocket has destroyed or not
    public boolean Check_destroy(int astroid_posx,int astroid_posy,int astroid_hegiht,int astroid_width){
        boolean x_intersected=false;
        boolean y_intersected=false;

        //check for horizontal intersected with an astroid and the rocket
        if((x<=astroid_posx && x+this.Rocket_width()>astroid_posx) ||
                (x>=astroid_posx && x<=astroid_posx+astroid_width)){
            x_intersected=true;}

        //check for vertical intersected with an astroid and the rocket
        if((y<=astroid_posy && y+this.Rocket_height()>astroid_posy) ||
                (y>=astroid_posy && y<=astroid_posy+astroid_hegiht)){
            y_intersected=true;}

        //if rocket is intersected with in both direction rocket  destroyed
        if(x_intersected && y_intersected){
            return false;}

        else{return true;}
    }

    //Initiator
    private void Restart(){
        x= (int) screen_width/2;
        y= (int) (screen_height-Rocket_height()-buttonsize*1.3);
        velocity=screen_width/100;
    }
}
