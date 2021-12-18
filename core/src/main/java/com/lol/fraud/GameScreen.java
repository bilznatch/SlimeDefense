package com.lol.fraud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen, InputProcessor {

    AutoBattler game;
    SpriteBatch batch = AutoBattler.batch;
    ShapeDrawer sd = AutoBattler.sd;
    Assets assets = AutoBattler.assets;
    ShaderList shaders = AutoBattler.shaders;
    OrthographicCamera camera;
    OrthographicCamera uicamera;
    FitViewport viewport;
    FitViewport UIViewport;
    BitmapFont font = assets.font;
    GlyphLayout layout;
    Vector2 mouse;
    Board board;
    Vector2 cameraMove;
    Tile hoverTile;
    Tower buildSelection;
    Color c;
    int selected;
    int score;

    GameScreen(AutoBattler game){
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(1920,1080);
        uicamera = new OrthographicCamera(1920,1080);
        viewport = new FitViewport(1920,1080,camera);
        UIViewport = new FitViewport(1920,1080,uicamera);
        board = new Board();
        mouse = new Vector2();
        layout = new GlyphLayout();
        cameraMove = new Vector2(0,0);
        buildSelection = new Tower(0,0,1);
        c = new Color(0.8f,0.2f,0.2f,0.4f);
        selected = 1;
        score=0;
        layout.setText(font,"");
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        UIViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        viewport.apply();
        setMouse();
        batch.begin();
        board.draw(batch,sd);
        if(hoverTile!=null){
            hoverTile.draw(batch,sd);
        }
        batch.end();
        batch.setProjectionMatrix(uicamera.combined);
        UIViewport.apply();
        drawUI();
        moveCamera();
        camera.update();
        hoverTile = board.getTile(mouse);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(hoverTile!=null){
                buildSelection.setToTower(selected);
                board.buildTower(hoverTile,new Tower(buildSelection));
            }
        }else if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            if(hoverTile!=null){
                    board.sellTower(hoverTile);
            }
        }
        score = (int)(board.gameLength+board.killed)*100;
    }

    public void drawUI(){
        batch.begin();
        //Info Boxes
        c.set(0.8f,0.2f,0.2f,0.4f);
        sd.filledRectangle(0,0,1920,96,c);
        //Tower Selection Boxes
        c.set(0.1f,0.1f,0.1f,0.4f);
        sd.filledRectangle(0,96,200,1000,c);
        c.set(0.8f,0.65f,0.2f,0.8f);
        sd.filledRectangle(20,865-190*(selected-1),160,160,c);
        c.set(0.1f,0.1f,0.1f,0.8f);
        sd.filledRectangle(0,1030,200,50,c);
        sd.filledRectangle(25,110,150,150,c);
        sd.filledRectangle(25,300,150,150,c);
        sd.filledRectangle(25,490,150,150,c);
        sd.filledRectangle(25,680,150,150,c);
        sd.filledRectangle(25,870,150,150,c);
        //Text rendering
        batch.setShader(shaders.fontShader);
        shaders.fontShader.setSmoothing(1/8f);
        font.getData().setScale(0.5f);
        font.draw(batch,"Path Length: " + board.pathLength,10,32);
        font.draw(batch,"Gold: " + board.player.gold,10,64);
        font.draw(batch,"Life: " + board.player.hp,10,96);
        font.draw(batch,"Score: " + score,1000,32);
        font.draw(batch,"Towers",50,1070);
        font.draw(batch,"Laser",50,900);
        font.draw(batch,"Ice",50,780);
        font.draw(batch,"Fire",50,600);
        font.draw(batch,"Machine Gun",50,350);
        font.draw(batch,"Stun",50,200);
        batch.setShader(null);
        batch.end();
    }

    public void moveCamera(){
        if(cameraMove.x>0){
            if(camera.position.x<1000){
                camera.position.x+=cameraMove.x;
            }
        }else if(cameraMove.x<0){
            if(camera.position.x>100){
                camera.position.x+=cameraMove.x;
            }
        }
        if(cameraMove.y>0){
            if(camera.position.y<1000){
                camera.position.y+=cameraMove.y;
            }
        }else if(cameraMove.y<0){
            if(camera.position.y>100){
                camera.position.y+=cameraMove.y;
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
        UIViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
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
        if(keycode==Input.Keys.A){
            cameraMove.x-=5;
        }
        if(keycode==Input.Keys.D){
            cameraMove.x+=5;
        }
        if(keycode==Input.Keys.S){
            cameraMove.y-=5;
        }
        if(keycode==Input.Keys.W){
            cameraMove.y+=5;
        }
        if(keycode==Input.Keys.DOWN){
            if(selected<5){
                selected++;
            }else{
                selected=1;
            }
        }
        if(keycode==Input.Keys.UP){
            if(selected>1){
                selected--;
            }else{
                selected=5;
            }
        }
        if(keycode==Input.Keys.ESCAPE){
            game.goToScreen(0);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode==Input.Keys.A){
            cameraMove.x+=5;
        }
        if(keycode==Input.Keys.D){
            cameraMove.x-=5;
        }
        if(keycode==Input.Keys.S){
            cameraMove.y+=5;
        }
        if(keycode==Input.Keys.W){
            cameraMove.y-=5;
        }
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
        if(amountY>0){
            if(camera.zoom+0.1f<2){
                camera.zoom+=0.1f;
            }else{
                camera.zoom=2;
            }
        }else if(amountY<0){
            if(camera.zoom-0.1f>0.1f){
                camera.zoom-=0.1f;
            }else{
                camera.zoom=0.1f;
            }
        }
        return false;
    }
}