package TetrisGame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

public class GameScreen implements Screen, InputProcessor, GestureDetector.GestureListener {

    private TetrisGame game;
    private TextureRegion region;
    private ImageProvider imageProvider;
    private SpriteBatch batch;
    private TetrisModel model;
    private Drawer drawer;
    private long nextTickTime;
    private boolean isUpdating;
    private int intervalInSeconds;
    private ArrayList<TextButton> textButtons;
    private TextButton scoreBtn;
    private TextButton pauseBtn;
    private TextButton exitBtn;
    //private MediaPlayer player;
    private Music mp3Sound;

    public GameScreen(TetrisGame game) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        batch = new SpriteBatch();
        this.game = game;
        this.imageProvider = game.getImageProvider();
        this.region = imageProvider.getBackground();
        this.model = new TetrisModel();
        drawer = new Drawer();
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);

        intervalInSeconds = 1;
        nextTickTime = com.badlogic.gdx.utils.TimeUtils.millis() + 7000;
        isUpdating = false;

        textButtons = new ArrayList<TextButton>();

        scoreBtn = new TextButton("ScoreButton", "Score: ", 80, 200, 1700);
        textButtons.add(scoreBtn);

        pauseBtn = new TextButton("PauseButton", "Auto", 80, 500, 1700);
        textButtons.add(pauseBtn);

        exitBtn = new TextButton("ExitButton", "Exit", 80, 800, 1700);
        textButtons.add(exitBtn);

        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("data/tetris.mp3"));
        mp3Sound.play();
        //Media pick = new Media(Gdx.files.internal("data/Kalimba.mp3").path());
        //player = new MediaPlayer(pick);
        this.model.isAuto = true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public void show() {

    }

    private void UpdateModel() {
        long now = com.badlogic.gdx.utils.TimeUtils.millis();

        //Model store deleted rows
        //Seconds passing counted
        //After 5 move on
        if (nextTickTime != 0 && nextTickTime <= now) {
            if (!isUpdating) {
                isUpdating = true;
                this.model.Tick();
                nextTickTime = now + (intervalInSeconds * 1000);
                isUpdating = false;
            }
        }
    }

    @Override
    public void render(float delta) {

        UpdateModel();

        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawer.DrawBackground();
        drawer.DrawGameGrid(model.grid, model.getColorMap());
        drawer.DrawPiece(model.currentPiece);

        batch.begin();

        for (int i = 0; i < textButtons.size(); i++) {
            textButtons.get(i).Draw(batch);
        }

        batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

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
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (exitBtn.IsPressed(Math.round(x), Math.round(y)))
            Gdx.app.exit();

        if (pauseBtn.IsPressed(Math.round(x), Math.round(y))) {
            if (this.model.isAuto)
                this.model.isAuto = false;
            else
                this.model.isAuto = true;
        }

        if (this.model.isAuto)
            return true;


        this.model.RotatePiece();
        int yPos = Gdx.graphics.getHeight() - Math.round(y);
        int xPos = Math.round(x);
        Gdx.app.debug("tap", xPos + " " + yPos);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        if (this.model.isAuto)
            return true;

        Gdx.app.debug("fling", velocityX + " " + velocityY);
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 10)
                this.model.MovePieceRight();
            else
                this.model.MovePieceLeft();
        } else {
            if (velocityY > 200) {
                this.model.MovePieceToBottom();
            } else if (velocityY > 10) {
                this.model.MovePieceDown();
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
