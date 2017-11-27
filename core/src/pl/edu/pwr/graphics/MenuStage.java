package pl.edu.pwr.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import pl.edu.pwr.ForwardableTimer;
import pl.edu.pwr.Timer;

public class MenuStage extends Stage {
    public MenuStage(int width, int height, int midpointX, ForwardableTimer stepTimer){
        this.width = width;
        this.height = height;
        this.midpointX = midpointX;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        float xPos = midpointX + ((width - buttonWidth) / 2.0f);
        float yPos = height - buttonHeight - buttonSpacing;

        startButton = createButton("START");
        startButton.setPosition(xPos, yPos);
        yPos -= buttonHeight + buttonSpacing;
        startButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                stepTimer.start();
            }
        });

        pauseButton = createButton("PAUSE");
        pauseButton.setPosition(xPos, yPos);
        yPos -= buttonHeight + buttonSpacing;
        pauseButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                if(stepTimer.isRunning()){
                    stepTimer.pause();
                }
                else {
                    stepTimer.resume();
                }
            }
        });

        stopButton = createButton("STOP");
        stopButton.setPosition(xPos, yPos);
        yPos -= buttonHeight + buttonSpacing;
        stopButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                stepTimer.cancel();
            }
        });

        jumpButton = createButton("JUMP");
        jumpButton.setPosition(xPos, yPos);
        yPos -= buttonHeight + buttonSpacing;
        jumpButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                stepTimer.jumpTicks(50);
            }
        });

        fastForwardButton = createButton("FAST FORWARD");
        fastForwardButton.setPosition(xPos, yPos);
        yPos -= buttonHeight + buttonSpacing;
        fastForwardButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                if(stepTimer.isFastForwarding()){
                    stepTimer.stopFastForward();
                }
                else{
                    stepTimer.startFastForward();
                }
            }
        });

        resetButton = createButton("RESET");
        resetButton.setPosition(xPos, yPos);
        resetButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y) {
                stepTimer.reset();
            }
        });

        this.addActor(startButton);
        this.addActor(pauseButton);
        this.addActor(stopButton);
        this.addActor(jumpButton);
        this.addActor(fastForwardButton);
        this.addActor(resetButton);
    }

    private int width;
    private int height;
    private int midpointX;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private TextButton startButton;
    private TextButton pauseButton;
    private TextButton stopButton;
    private TextButton jumpButton;
    private TextButton fastForwardButton;
    private TextButton resetButton;

    private final static float buttonWidth = 150.0f;
    private final static float buttonHeight = 50.0f;
    private final static float buttonSpacing = 10.0f;
    private final static Color menuBarColor = new Color(0.949f, 0.949f, 0.949f, 1.0f);

    @Override
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(menuBarColor);
        shapeRenderer.rect(midpointX, 0, width, height);
        shapeRenderer.end();
        
        batch.begin();
        startButton.draw(batch, 1.0f);
        pauseButton.draw(batch, 1.0f);
        stopButton.draw(batch, 1.0f);
        jumpButton.draw(batch, 1.0f);
        fastForwardButton.draw(batch, 1.0f);
        resetButton.draw(batch, 1.0f);
        batch.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    private TextButton createButton(String label){
        // Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        // Create a texture
        Pixmap pixmap = new Pixmap((int)buttonWidth, (int)buttonHeight, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        return new TextButton(label, skin);
    }
}
