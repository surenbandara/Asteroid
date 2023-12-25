package com.krakendepp.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;


//class for bullet
//method and attributes are same as astroid class
public class Bullet {
    Context context ;
    Bitmap bullet;
    int x,y,velocity,screen_width,screen_height,start_posx,start_posy;
    boolean destroied =false;
    Random random;

    public Bullet (Context context,int Screen_width ,int Screen_height,int start_posx,int start_posy){
        this.context=context;
        this.screen_width=Screen_width;
        this.screen_height=Screen_height;
        this.start_posx=start_posx;
        this.start_posy=start_posy;

        bullet = BitmapFactory.decodeResource(context.getResources(),R.drawable.bullet);
        bullet = Bitmap.createScaledBitmap(bullet, Screen_width/30,2*Screen_width/30,true);
        random =new Random();
        Restart();
    }
    public Bitmap Get_bulletpic(){
        return bullet;
    }
    public int Bullet_width(){return bullet.getWidth();}
    public int Bullet_height(){return bullet.getHeight();}
    public int Bullet_posx(){return x;}
    public int Bullet_posy(){return y;}



    public void Move(){y-=velocity;}

    private void Restart(){
        x= start_posx-Bullet_width()/2;
        y=start_posy-Bullet_height();
        velocity=screen_height/100;


    }
}
