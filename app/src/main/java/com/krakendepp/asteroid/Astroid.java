package com.krakendepp.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

//Astroid creating class
public class Astroid {

    Context context ;
    Bitmap astroid;                         //Astroid pic
    int x,y,velocity,screen_width,screen_height; //Astroid position ,velocity and screen size
    boolean destroied =false;             //Wheather the astroied destroy or not
    Random random;


    //constructor
    public Astroid (Context context,int Screen_width ,int Screen_height){
        this.context=context;
        this.screen_width=Screen_width;
        this.screen_height=Screen_height;
        astroid = BitmapFactory.decodeResource(context.getResources(),R.drawable.astroid);
        //scaled the astroid
        astroid = Bitmap.createScaledBitmap(astroid, Screen_width/15,2*Screen_width/15,true);
        random =new Random();
        Restart();}


    public Bitmap Get_astroidpic(){
        return astroid;
    }
    public int Astroid_width(){return astroid.getWidth();}
    public int Astroid_height(){return astroid.getHeight();}
    public int Astroid_posx(){return x;}
    public int Astroid_posy(){return y;}


    //move astroid to downside
    public void Move(){y+=velocity;}


    //Initiate astroid
    private void Restart(){
        //pick random position to start
        x= random.nextInt(this.screen_width-this.Astroid_width());
        //pick random velocity
        velocity=screen_height/200+random.nextInt(screen_height/200);;
        //start from top of the screen
        y= (int) (-1*Astroid_height());
    }


    // Method to check whether the astroid is destroyed or not

    public boolean Check_destroy(int bullet_posx,int bullet_posy,int bullet_hegiht,int bullet_width){
        boolean x_intersected=false;
        boolean y_intersected=false;

        //check bullet and astroid is intersected in horizontally
        if((x<=bullet_posx && x+this.Astroid_width()>bullet_posx) ||
                (x>=bullet_posx && x<=bullet_posx+bullet_width)){
            x_intersected=true;}

        //check bullet and astroid is intersected in vertically
        if((y<=bullet_posy && y+this.Astroid_height()>bullet_posy) ||
                (y>=bullet_posy && y<=bullet_posy+bullet_hegiht)){
            y_intersected=true;}


        //if a bullet is intersected with both direction that bullet has hit on astroid
        if(x_intersected && y_intersected){
            return true;}

        else{return false;}
    }
}
