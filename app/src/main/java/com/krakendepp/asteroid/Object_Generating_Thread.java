package com.krakendepp.asteroid;

import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

//thread to create objects
public class Object_Generating_Thread extends Thread{
    CopyOnWriteArrayList<Astroid> astroids;    //astroid arraylist with concurrent access capability
    CopyOnWriteArrayList<Bullet> bullets;       //bullet arraylist with concurrent access capability
    Context context;
    int Screen_width ,Screen_height;
    int bullet_posx,bullet_posy;               //bullet initial positions.
    boolean create_astroid,create_bullet=false; //two modes



    public  Object_Generating_Thread(Context context, CopyOnWriteArrayList<Astroid> astroids,CopyOnWriteArrayList<Bullet> bullets, int Screen_width , int Screen_height){
        this.astroids= astroids;
        this.bullets=bullets;
        this.context=context;
        this.Screen_height=Screen_height;
        this.Screen_width=Screen_width;
    }


    //trigger creating astroid
    public void trigger_astroid(){
        create_astroid=true;
    }

    //trigger creating bullet
    public void trigger_bullet(int bullet_posx,int bullet_posy){
        this.bullet_posx=bullet_posx;
        this.bullet_posy=bullet_posy;
        create_bullet=true;
    }

    public void run(){
        while (true){
            //create object according to each mode and add them to relevent array list
            if(create_astroid){
                astroids.add(new Astroid(context,Screen_width,Screen_height));
            create_astroid=false;}
            if(create_bullet){
                bullets.add(new Bullet(context,Screen_width,Screen_height,bullet_posx,bullet_posy));
                create_bullet=false;
            }
        }
    }
}
