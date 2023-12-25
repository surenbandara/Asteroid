package com.krakendepp.asteroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends View {
    Context context;
    Bitmap background,gameover;       //background and gameover bitsmap
    Handler handler;
    Rocket rocket;
    Button left,right,shoot;         //three buttons
    int timer,bullet_timer;         //timer for count period time for astroid generating bullrt timer for count period time for bullets
    long UPDATE_MI =1;              //runnable updating period
    int Screen_width,Screen_height;
    int Full_Screen_width,Full_Screen_height;
    float touch1x,touch1y,touch2x,touch2y;  //bultitoucing points
    boolean touch1,touch2=false;           //check each touch
    boolean moveright,moveleft,shooting=false;   //capture each movement

    CopyOnWriteArrayList<Astroid> astroids,tobe_deleted_astroid;
    CopyOnWriteArrayList<Bullet> bullets,tobe_deleted_bullets;
    Object_Generating_Thread object_generating_thread;

    int score=0;


    final Runnable runnable =new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public Game(Context context){
        super(context);
        this.context =context;

        //get display info
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size_normal =new Point();
        display.getSize(size_normal);
        Screen_height=size_normal.y;
        Screen_width=size_normal.x;

        Point size_full =new Point();
        display.getRealSize(size_full);
        Full_Screen_height=size_full.y;
        Full_Screen_width=size_full.x;

        //initiate bullet timer
        this.bullet_timer=0;

        //creating arraylist for astroid and bullets
        astroids= new CopyOnWriteArrayList<Astroid>();
        tobe_deleted_astroid=new CopyOnWriteArrayList<Astroid>();
        bullets = new CopyOnWriteArrayList<Bullet>();
        tobe_deleted_bullets=new CopyOnWriteArrayList<Bullet>();

        //creating object creating thread and generate astroid
        object_generating_thread=new Object_Generating_Thread(context,astroids,bullets,Screen_width,Screen_height);
        object_generating_thread.start();
        object_generating_thread.trigger_astroid();

        //create button
        left=new Button(context,"left",Screen_width,Screen_height);
        right=new Button(context,"right",Screen_width,Screen_height);
        shoot=new Button(context,"shoot",Screen_width,Screen_height);


        rocket = new Rocket(context ,Screen_width,Screen_height,left.getButtonsize());

        //create background and gameover bitsmap
        background = BitmapFactory.decodeResource(context.getResources(),R.drawable.sky);
        background = Bitmap.createScaledBitmap(background, Full_Screen_width,Full_Screen_height,true);

        gameover = BitmapFactory.decodeResource(context.getResources(),R.drawable.gameover);
        gameover= Bitmap.createScaledBitmap(gameover, Full_Screen_width,(int) (Full_Screen_width/1.2),true);

        handler =new Handler();

    }
    @Override
    protected void onDraw(Canvas canvas){

        //check rocket is alive
        if(rocket.Live){


        //draw bacground
        canvas.drawBitmap(background,0,0,null);
        canvas.drawBitmap(rocket.Get_rocketpic(),rocket.Rocket_posx(),rocket.Rocket_posy(),null);


        //astroid moving, drawing and check astroid are destroyed by bullet
        for(Astroid i :astroids){
            if(i.Astroid_posy()<Full_Screen_height && !i.destroied){

                if(!rocket.Check_destroy(i.Astroid_posx(),i.Astroid_posy(),i.Astroid_height(),i.Astroid_width())){
                    rocket.Live=false;
                            break;}

                for(Bullet k :bullets){
                    if(i.Check_destroy(k.Bullet_posx(),k.Bullet_posy(),k.Bullet_height(),k.Bullet_width()))
                    {
                        score+=1;
                        i.destroied=true;
                        k.destroied=true;
                    }
                }
            canvas.drawBitmap(i.Get_astroidpic(),i.Astroid_posx(),i.Astroid_posy(),null);
            i.Move();}
            else{
                tobe_deleted_astroid.add(i);}}

        //delete destroyed astroid and out of range astroid from arraylist
        for(Astroid j :tobe_deleted_astroid){
            astroids.remove(j);}
        tobe_deleted_astroid.removeAll(tobe_deleted_astroid);






        //Bullet drawing and moving
        for(Bullet i :bullets){
            if(i.Bullet_posy()>0 && !i.destroied){
                canvas.drawBitmap(i.Get_bulletpic(),i.Bullet_posx(),i.Bullet_posy(),null);
                i.Move();}
            else{
                tobe_deleted_bullets.add(i);}}

        //delete destroyed bullet and out of range astroid from arraylist
        for(Bullet j :tobe_deleted_bullets){
            bullets.remove(j);}
        tobe_deleted_bullets.removeAll(tobe_deleted_bullets);



        //print each buttons
        canvas.drawBitmap(left.getButton_pic(),left.pos_x,left.pos_y,null);
        canvas.drawBitmap(right.getButton_pic(),right.pos_x,right.pos_y,null);
        canvas.drawBitmap(shoot.getButton_pic(),shoot.pos_x,shoot.pos_y,null);


        //Score board 
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Score: " + String.valueOf(score), canvas.getWidth() / 2, 100, paint);

        //Generating astroid
        timer+=1;
        if(timer%30==0){
            object_generating_thread.trigger_astroid();
        timer=0;
        }



        //check touch activity and detect which button is pressed and move right ,left or shoot
        if(touch1){

        if(!moveleft || !moveright){
        if((left.getPos_x()<touch1x && touch1x<left.getPos_x()+left.getButtonsize())
                &&(left.getPos_y()<touch1y && touch1y<left.getPos_y()+left.getButtonsize()))
        {
            rocket.Rocket_moveleft();
            moveleft=true;
        }

        else if((right.getPos_x()<touch1x && touch1x<right.getPos_x()+right.getButtonsize())
                &&(right.getPos_y()<touch1y && touch1y<right.getPos_y()+right.getButtonsize()))
        {
            rocket.Rocket_moveright();
            moveright=true;
        }}
        if((shoot.getPos_x()<touch1x && touch1x<shoot.getPos_x()+shoot.getButtonsize())
                &&(shoot.getPos_y()<touch1y && touch1y<shoot.getPos_y()+shoot.getButtonsize()))
        {   if(bullet_timer%10==0){

            object_generating_thread.trigger_bullet(rocket.Rocket_posx()+rocket.Rocket_width()/2,
                    rocket.Rocket_posy());
            bullet_timer=0;
        }
            bullet_timer+=1;}}


        if(touch2){
            if(!moveleft || !moveright){
                if((left.getPos_x()<touch2x && touch2x<left.getPos_x()+left.getButtonsize())
                        &&(left.getPos_y()<touch2y && touch2y<left.getPos_y()+left.getButtonsize()))
                {
                    rocket.Rocket_moveleft();
                    moveleft=true;
                }

                else if((right.getPos_x()<touch2x && touch2x<right.getPos_x()+right.getButtonsize())
                        &&(right.getPos_y()<touch2y && touch2y<right.getPos_y()+right.getButtonsize()))
                {
                    rocket.Rocket_moveright();
                    moveright=true;}
                }
                if((shoot.getPos_x()<touch2x && touch2x<shoot.getPos_x()+shoot.getButtonsize())
                        &&(shoot.getPos_y()<touch2y && touch2y<shoot.getPos_y()+shoot.getButtonsize()))
                {   if(bullet_timer%10==0){

                    object_generating_thread.trigger_bullet(rocket.Rocket_posx()+rocket.Rocket_width()/2,
                            rocket.Rocket_posy());
                    bullet_timer=0;
                }
                    bullet_timer+=1;
                }
        }

        handler.postDelayed(runnable,UPDATE_MI);
        }



        else{
            //if game over print game over
            canvas.drawBitmap(background,0,0,null);
            canvas.drawBitmap(gameover,0,Full_Screen_height*2/7,null);


            }



    }

    //listn to motion event
    @Override
    public boolean onTouchEvent(MotionEvent event){


        switch (event.getActionMasked()) {

            //first touch detect
            case MotionEvent.ACTION_DOWN:

                //press after game over detection
                if(!rocket.Live){
                    System.gc();

                    object_generating_thread.interrupt();
                    Intent i = new Intent(this.context,MainActivity.class);
                    context.startActivity(i);}



               touch1=true;
               touch1x=event.getX(event.getActionIndex());
               touch1y=event.getY(event.getActionIndex());

                break;
            case MotionEvent.ACTION_UP:

                if(!rocket.Live){
                    System.gc();

                    object_generating_thread.interrupt();
                    Intent i = new Intent(this.context,MainActivity.class);
                    context.startActivity(i);
                }

                touch1=false;
                touch2=false;
                moveleft=false;
                moveright=false;

                break;

                //Detect multitouching


            case MotionEvent.ACTION_POINTER_DOWN:

                if(event.getActionIndex()==0){
                    touch1=true;
                    touch1x=event.getX(event.getActionIndex());
                    touch1y=event.getY(event.getActionIndex());
                }
                else if(event.getActionIndex()==1){
                touch2=true;
                touch2x=event.getX(event.getActionIndex());
                touch2y=event.getY(event.getActionIndex());}
                break;
                
            case MotionEvent.ACTION_POINTER_UP:

                if(event.getActionIndex()==0){
                    touch1=false;
                    moveleft=false;
                    moveright=false;}

                else if(event.getActionIndex()==1){
                    touch2=false;
                    moveleft=false;
                    moveright=false;}

                break;
        }

        return true;
     }



}
