package com.lol.fraud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Button extends Rectangle {
    String text;
    GlyphLayout layout;
    Alignment align = null;
    Color color;
    int id;
    Button(BitmapFont font, String text, float x, float y, float w, float h, Color color, int id){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = text;
        this.layout = new GlyphLayout();
        layout.setText(font,text);
        this.color = color;
        this.id = id;
    }


    public boolean isClicked(Vector2 mouse){
        return this.contains(mouse);
    }
    public void draw(SpriteBatch batch, ShapeDrawer sd){
        sd.filledRectangle(this,color);
    }
    public void drawText(SpriteBatch batch, BitmapFont font){
        if(align == null){
            font.draw(batch,layout,x+width/2-layout.width/2,y+height/2+layout.height/2);
        }else if(align==Alignment.LEFT){
            font.draw(batch,layout,x,y+height/2+layout.height/2);
        }else if(align==Alignment.RIGHT){
            font.draw(batch,layout,x+width-layout.width,y+height/2+layout.height/2);
        }else if(align==Alignment.CENTER){
            font.draw(batch,layout,x+width/2-layout.width/2,y+height/2+layout.height/2);
        }

    }
}
