package ArtistMatch;

import java.io.IOException;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TitleScreen implements Screen, InputProcessor, ApplicationListener{
	private ArtistMatch game;
	private SpriteBatch batch;
	private BitmapFont font;
	private float screenHeight, screenWidth;
	private Texture background,gameTitle;
	private Texture[] musImages;
	
	
	public TitleScreen(ArtistMatch game){
		this.game = game;
	}
	
	public void create(){
		FileHandle file = Gdx.files.internal("Universal/ArtistMatch Background.png");
		background = new Texture(file);
		FileHandle title = Gdx.files.internal("Universal/Game Title Screen.png");
		gameTitle = new Texture(title);
	 	initializeMusicImages();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(background, 0, 0, screenWidth, screenHeight);
		batch.draw(gameTitle, 0,( 6 * (screenHeight / 7) ), screenWidth, (screenHeight / 7)  );
		font.drawWrapped(batch, "Touch to Proceed to Main Menu", (screenWidth / 3), (3 * (screenHeight / 7) ), (screenWidth / 3) );
		drawMusicImages();
		batch.end();
	}

	/*
	 * Gets the images, for the Title Screen, and stores them in an array
	 */
	public void initializeMusicImages(){
		try{Element root = new XmlReader().parse(Gdx.files.internal("gameImages.xml"));
		Element source = root.getChildByName("Universal");
		Array<Element> images = source.getChildrenByName("select");
		int count = images.size;
		musImages = new Texture[count];
		for (int i = 0; i <images.size; i++){
			String imgLoc = images.get(i).getText();
			FileHandle musicImage = Gdx.files.internal(imgLoc);
			musImages[i] = new Texture(musicImage);
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Draws each element in the Image Array, at a specific Position on the screen
	 */
	public void drawMusicImages(){
		for(int i =0; i < musImages.length;i++){
			if( (i>= 0) && (i <3) ){
				batch.draw(musImages[i], (i *(screenWidth / 3) ), (4* (screenHeight / 7) ), (screenWidth / 3),(2* (screenHeight / 7) ) ) ;
			}
			if (i == 3){
				batch.draw(musImages[i], 0, (2* (screenHeight / 7) ), (screenWidth / 3),(2* (screenHeight / 7) ) ) ;
			}
			if (i == 4){
				batch.draw(musImages[i], (2 * (screenWidth / 3) ), (2* (screenHeight / 7) ), (screenWidth / 3),(2* (screenHeight / 7) ) ) ;
			}
			if( (i>= 5) && (i <8) ){
				int ind = i-2;
				int index = ind %3 ; 
				batch.draw(musImages[i], ( index * (screenWidth / 3) ), 0, (screenWidth / 3),(2* (screenHeight / 7) ) ) ;
			}
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		game.switchScreens(2);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
//		dispose();
		game.switchScreens(2);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
