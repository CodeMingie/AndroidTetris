package TetrisGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by panda on 8/10/2015.
 */
public class MyTextButton {
    private String name;
    private String text;
    BitmapFont font;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public MyTextButton(String name, String text, int size, int x, int y) {
        this.name = name;
        this.text = text;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ARCADE.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        this.SetPos(x, y);
    }

    public void SetPos(int x, int y) {
        this.startX = x;
        this.startY = y;

        BitmapFont.TextBounds bounds = font.getBounds(text);
        this.endX = startX + (int) bounds.width;
        this.endY = startY + (int) bounds.height;
    }

    public void SetText(String text) {
        this.text = text;
        BitmapFont.TextBounds bounds = font.getBounds(text);
        this.endX = startX + (int) bounds.width;
        this.endY = startY + (int) bounds.height;
    }

    public void Draw(SpriteBatch batch) {
        font.setColor(Color.BLACK);
        font.drawMultiLine(batch, text, startX, startY);
    }

    public void Draw(SpriteBatch batch, int wrappedWidth)
    {
        font.setColor(Color.BLACK);
        font.drawWrapped(batch, text, startX, startY, wrappedWidth);
    }

    public void SetColor(Color c) {
        font.setColor(c);
    }

    public Boolean IsPressed(int x, int y) {
        y = Gdx.graphics.getHeight() - y;

        if (this.startX < x && this.endX > x && this.startY < y && this.endY > y)
            return true;

        return false;
    }

}
