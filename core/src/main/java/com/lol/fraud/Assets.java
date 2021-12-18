package com.lol.fraud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.*;

public class Assets {
    AssetManager manager;
    TextureAtlas textureAtlas;
    Map<String,TextureRegion> textures = new HashMap<String,TextureRegion>();
    TextureRegion whitePixel;
    Texture whiteTex;
    Pixmap whitePixmap;
    ShapeDrawer sd;
    BitmapFont font;
    Texture fontTex;
    Assets(){
        whitePixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        whitePixmap.setColor(Color.WHITE);
        whitePixmap.fill();
        whiteTex = new Texture(whitePixmap);
        whitePixel = new TextureRegion(whiteTex);
        this.manager = new AssetManager();
        loadFonts();
        loadAssets();
    }
    public void loadAssets(){
        manager.load("Textures/textureatlas.atlas",TextureAtlas.class);
    }
    public float update(){
        manager.update();
        return manager.getProgress();
    }
    public void loadTextures(){
        sd = AutoBattler.sd;
        textureAtlas = manager.get("Textures/textureatlas.atlas",TextureAtlas.class);
        for(TextureAtlas.AtlasRegion ar: textureAtlas.getRegions()){
            textures.put(ar.name,ar);
            System.out.println(ar.name);
        }
        whitePixel = textures.get("plainwhite");
        whiteTex.dispose();
        sd.setTextureRegion(whitePixel);
    }
    public void loadFonts(){
            fontTex = new Texture(Gdx.files.internal("Fonts/newfont.png"),true);
            fontTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
            font = new BitmapFont(Gdx.files.internal("Fonts/newfont.fnt"), new TextureRegion(fontTex), false);
            font.setUseIntegerPositions(false);
    }
}
