package ArtistMatch;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LevelSelect implements Screen, InputProcessor, ApplicationListener {
	private ArtistMatch game;
	private SpriteBatch batch;
	private BitmapFont font;
	private String[] artists;
	private float screenHeight, screenWidth, scaleX, scaleY;
	private Skin skin;
	private Stage stage;
	private Table table,table1,table2;
	int artistCount;
	
	/*
	 * NEED TO REDO/REWORK 
	 * A FUNCTION THAT PARSES THE ARTIST'S NAME INTO A BETTER FORMAT (INCLUDING SPACES AND OTHER CHARACTERS)
	 */
	
	public LevelSelect(ArtistMatch game){
		this.game = game;
	}
	
//	@Override
	public void create() {
		skin = new Skin (Gdx.files.internal("uiskin.json"));
		table = new Table(skin);
		table1 = new Table(skin);
		table2 = new Table(skin);
		stage = new Stage();
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		
		scaleX = screenWidth/640;
		scaleY = screenHeight/480;
		initializeButtonsArray();
//		artistCount = artists.size();
		addLeftButtons();
		addRightButtons();
		addBackButton();
		
		table.setFillParent(true);
		table.left();
		stage.addActor(table);
		
		table1.setFillParent(true);
		table1.right();
		stage.addActor(table1);
		
		table2.setFillParent(true);
		table2.bottom();
		stage.addActor(table2);
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		batch.end();
		stage.draw();
		
//		Gdx.gl.glClearColor(0, 0, 0, 0);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		font.setColor(Color.WHITE);
//		font.setScale(1, 1);
//		font.draw(batch, "The Level Select Screen Works", 0, 100);
//		batch.end();
	}

	public void initializeButtonsArray(){
		try{Element root = new XmlReader().parse(Gdx.files.internal("Levels.xml"));
		Element level = root.getChildByName("LevelSelect");
		Array<Element> answerE = level.getChildrenByName("artist");
		int count = answerE.size;
		artists = new String[count];
		for (int i = 0; i <answerE.size; i++){
			artists[i]=answerE.get(i).getText();
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * NEED TO MODIFY TO INCLUDE THE FORMAT STRING FOR THE BUTTON DISPLAY
	 * AND KEEP THE XML FILE NAME FOR LEVEL NAME
	 */
	
	public void addLeftButtons(){
		for(int i = 0;i< 5;i++){
		final TextButton button = new TextButton(artists[i],skin);
		button.setName(artists[i]);
		table.add(button).width(button.getWidth()*scaleX).height(button.getHeight()*scaleY);
		table.row();
		button.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setArtist(button.getName());
				game.switchScreens(4);
				System.out.println(game.getArtist());
			}
		});
		}
	}
	
	public void addRightButtons(){
		for(int i = 5  ;i< 10 ;i++){
		final TextButton button = new TextButton(artists[i],skin);
		button.setName(artists[i]);
		table1.add(button).width(button.getWidth()*scaleX).height(button.getHeight()*scaleY);
		table1.row();
		button.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setArtist(button.getName());
				game.switchScreens(4);
				System.out.println(game.getArtist());
			}
		});
		}
	}
	
	public void addBackButton(){
		final TextButton button = new TextButton("Previous Screen",skin);
		button.setName("Back");
		table2.add(button).width(button.getWidth()*scaleX).height(button.getHeight()*scaleY);
		table2.row();
		button.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreens(2);
			}
		});
	}
	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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


}