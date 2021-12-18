package com.lol.fraud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

/** First screen of the application. Displayed after the application is created. */
public class MenuScreen implements Screen, InputProcessor {
	AutoBattler game;
	SpriteBatch batch = AutoBattler.batch;
	ShapeDrawer sd = AutoBattler.sd;
	Assets assets = AutoBattler.assets;
	ShaderList shaders = AutoBattler.shaders;
	Camera camera = new OrthographicCamera(1920,1080);
	Viewport viewport = new FitViewport(1920,1080,camera);
	BitmapFont font = assets.font;
	GlyphLayout layout;
	long startTime;
	Vector2 mouse = new Vector2();
	ArrayList<Button> buttons = new ArrayList<>();

	MenuScreen(AutoBattler game){
		this.game = game;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		layout = new GlyphLayout();
		layout.setText(font,"Loading...");
		viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
		startTime = System.currentTimeMillis();
		if(buttons.isEmpty()){
			buttons.add(new Button(font,"Play",300,800,300,80,Color.DARK_GRAY,0));
			buttons.add(new Button(font,"Options",300,600,300,80,Color.DARK_GRAY,1));
			buttons.add(new Button(font,"Exit",300,400,300,80,Color.DARK_GRAY,2));
		}
		font.getData().setScale(1);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.CORAL.cpy().lerp(Color.BLACK,0.2f));
		batch.setProjectionMatrix(camera.combined);
		viewport.apply();
		setMouse();
		batch.begin();
		renderButtons();
		batch.setShader(shaders.fontShader);
		shaders.fontShader.setSmoothing(1/8f);
		renderButtonText();
		batch.setShader(null);
		batch.end();
		checkButtons();
	}

	public void setMouse(){
		mouse.set(Gdx.input.getX(), Gdx.input.getY());
		viewport.unproject(mouse);
	}

	public void checkButtons(){
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			for (Button b : buttons) {
				if (b.isClicked(mouse)) {
					if(b.id==0){
						this.game.goToScreen(1);
					}else if(b.id==1){
						this.game.goToScreen(2);
					}else{
						Gdx.app.exit();
					}
				}
			}
		}
	}

	public void renderButtons(){
		for(Button b: buttons){
			b.draw(batch, sd);
		}
	}
	public void renderButtonText(){
		for(Button b: buttons){
			b.drawText(batch,font);
		}
	}

	@Override
	public void resize(int width, int height) {
		// Resize your screen here. The parameters represent the new window size.
		viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
	}

	@Override
	public void pause() {
		// Invoked when your application is paused.
	}

	@Override
	public void resume() {
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}