package com.lol.fraud;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AutoBattler extends Game {
	public static SpriteBatch batch;
	public static ShapeDrawer sd;
	public static Assets assets;
	public static ShaderList shaders;
	MenuScreen menuScreen;
	GameScreen gameScreen;
	OptionScreen optionScreen;
	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new Assets();
		sd = new ShapeDrawer(batch,assets.whitePixel);
		shaders = new ShaderList();
		setScreen(new PreloadingScreen(this));
	}
	public void goToScreen(int id){
		if(id==0){
			if(menuScreen==null){
				menuScreen = new MenuScreen(this);
				setScreen(menuScreen);
			}else{
				setScreen(menuScreen);
			}
		}else if(id==1){
			if(gameScreen==null){
				gameScreen = new GameScreen(this);
				setScreen(gameScreen);
			}else{
				setScreen(gameScreen);
			}
		}else if(id==2){
			if(optionScreen==null){
				optionScreen = new OptionScreen(this);
				setScreen(optionScreen);
			}else{
				setScreen(optionScreen);
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		assets.textureAtlas.dispose();
		shaders.fontShader.dispose();
		shaders.rainbowShader.dispose();
	}
}