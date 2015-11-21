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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class MainMenu implements Screen, InputProcessor, ApplicationListener {
	private ArtistMatch game;
	private SpriteBatch batch;
	private String[] Levels;
	private float screenHeight, screenWidth, scaleX, scaleY;
	private Texture background, gameTitle;
	private Skin skin;
	private Stage stage;
	private Table table;
	
	public MainMenu(ArtistMatch game){
		this.game = game;
	}
	 public void create(){
		 	background = new Texture("ArtistMatch Background.png");
		 	FileHandle title = Gdx.files.internal("Universal/Game Title Screen.png");
			gameTitle = new Texture(title);
		 	skin = new Skin (Gdx.files.internal("uiskin.json"));
			table = new Table(skin);
			stage = new Stage();
			screenHeight = Gdx.graphics.getHeight(); 
			screenWidth = Gdx.graphics.getWidth();
			scaleX = screenWidth/640;
			scaleY = screenHeight/480;
			initializeButtonsArray();
			addButtons(Levels);
			table.setFillParent(true);
			table.center();
			stage.addActor(table);
			batch = new SpriteBatch();
			Gdx.input.setInputProcessor(this);
			Gdx.input.setInputProcessor(stage);
	 }
	 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, screenWidth, screenHeight);
		batch.draw(gameTitle, 0,( 6 * (screenHeight / 7) ), screenWidth, (screenHeight / 7)  );
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		batch.end();
		stage.draw();
	}
	
	/*
	 * Adds the Levels Buttons to the Screen	
	 */
	public void initializeButtonsArray(){
		try{Element root = new XmlReader().parse(Gdx.files.internal("Levels.xml"));
		Element level = root.getChildByName("MainMenu");
		Array<Element> answerE = level.getChildrenByName("level");
		int count = answerE.size;
		Levels = new String[count];
		for (int i = 0; i <answerE.size; i++){
			Levels[i]=answerE.get(i).getText();
		}
		}
		catch(IOException e){
		}
	}
	
	/*
	 * Add Buttons to the Screen
	 */
	public void addButtons(String[] array){
		final TextButton playbutton = new TextButton(array[0],skin);
		playbutton.setName(array[0]);
		table.add(playbutton).width(playbutton.getWidth()*scaleX).height(playbutton.getHeight()*scaleY);
		table.row();
		playbutton.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.switchScreens(3);
			}
		});
		
		final TextButton insbutton = new TextButton(array[1],skin);
		insbutton.setName(array[1]);
		table.add(insbutton).width(insbutton.getWidth()*scaleX).height(insbutton.getHeight()*scaleY);
		table.row();
		insbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
					game.switchScreens(6);
				}
		});	
		
		final TextButton settingbutton = new TextButton(array[3],skin);
		settingbutton.setName(array[3]);
		table.add(settingbutton).width(settingbutton.getWidth()*scaleX).height(settingbutton.getHeight()*scaleY);
		table.row();
		settingbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
					game.switchScreens(7);
				}
		});	

		
		
		
		
//		final TextButton creditbutton = new TextButton(array[2],skin);
//		creditbutton.setName(array[2]);
//		table.add(creditbutton).width(creditbutton.getWidth()*scaleX).height(creditbutton.getHeight()*scaleY);
//		table.row();
//		creditbutton.addListener(new ClickListener(){
//			public void clicked(InputEvent event, float x, float y){
////				Checks to make sure that the submenu screen is working
//					game.switchScreens(4);
//				}
//		});
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
		stage.dispose();
		skin.dispose();
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


}
