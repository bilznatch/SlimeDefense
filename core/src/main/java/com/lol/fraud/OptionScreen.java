package com.lol.fraud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

/** First screen of the application. Displayed after the application is created. */
public class OptionScreen implements Screen {

    AutoBattler game;
    SpriteBatch batch = AutoBattler.batch;
    ShapeDrawer sd = AutoBattler.sd;
    Assets assets = AutoBattler.assets;
    ShaderList shaders = AutoBattler.shaders;
    Camera camera = new OrthographicCamera(1920,1080);
    Viewport viewport = new FitViewport(1920,1080,camera);
    BitmapFont font = assets.font;
    GlyphLayout layout;
    Vector2 mouse = new Vector2();
    Button backButton = new Button(font,"Back",400,200,300,80,Color.DARK_GRAY,0);

    OptionScreen(AutoBattler game){
        this.game = game;
    }

    @Override
    public void show() {
        layout = new GlyphLayout();
        layout.setText(font,"Loading...");
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CORAL.cpy().lerp(Color.BLACK,0.2f));
        batch.setProjectionMatrix(camera.combined);
        viewport.apply();
        setMouse();
        batch.begin();
        backButton.draw(batch,sd);
        batch.setShader(shaders.fontShader);
        shaders.fontShader.setSmoothing(1/8f);
        font.draw(batch,"Jokes on you, there are no options.",400,400);
        backButton.drawText(batch,font);
        batch.setShader(null);
        batch.end();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(backButton.isClicked(mouse)){
                game.goToScreen(0);
            }
        }
    }

    public void setMouse(){
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mouse);
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
}