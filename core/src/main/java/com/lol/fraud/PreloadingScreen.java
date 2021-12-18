package com.lol.fraud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

/** First screen of the application. Displayed after the application is created. */
public class PreloadingScreen implements Screen {
    AutoBattler game;
    SpriteBatch batch = AutoBattler.batch;
    ShapeDrawer sd = AutoBattler.sd;
    Assets assets = AutoBattler.assets;
    ShaderList shaders = AutoBattler.shaders;
    Camera camera = new OrthographicCamera(1920,1080);
    Viewport viewport = new FitViewport(1920,1080,camera);
    BitmapFont font = assets.font;
    GlyphLayout layout;
    float progress = 0;

    PreloadingScreen(AutoBattler game){
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
        ScreenUtils.clear(Color.DARK_GRAY);
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        progress = 100*assets.update();
        batch.begin();
        sd.circle(960, 540, 100, 5);
        sd.filledCircle(960, 540, progress);
        batch.setShader(shaders.fontShader);
        shaders.fontShader.setSmoothing(1/8f);
        font.draw(batch,layout,960-layout.width/2,750);
        batch.setShader(null);
        batch.end();
        if(progress>=100){
            assets.loadTextures();
            game.goToScreen(0);
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
        this.dispose();
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}