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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class MainMenu implements Screen, InputProcessor, ApplicationListener {
	private ArtistMatch game;
	private SpriteBatch batch;
	private BitmapFont font;
	private String[] Levels;
	private float screenHeight, screenWidth, scaleX, scaleY;
	private Skin skin;
	private Stage stage;
	private Table table;
	private Window window;
	
	public MainMenu(ArtistMatch game){
		this.game = game;
	}
	 public void create(){
		 	skin = new Skin (Gdx.files.internal("uiskin.json"));
			table = new Table(skin);
			stage = new Stage();
			screenHeight = Gdx.graphics.getHeight(); 
			screenWidth = Gdx.graphics.getWidth();
			initializeButtonsArray();
			addButtons(Levels);
			window = new Window("Main Menu", skin);
			window.setTitleAlignment(Align.top);
			window.setFillParent(true);
			window.add(table).center();
			stage.addActor(window);
			
			
			batch = new SpriteBatch();
			font = new BitmapFont();
			
			scaleX = screenWidth/640;
			scaleY = screenHeight/480;
			
//			Levels = new String[3];
//			Levels[0] = "Play";
//			Levels[1] = "Instructions";
//			Levels[2] = "Credits";
			
			
//			Gdx.input.setInputProcessor(this);
			Gdx.input.setInputProcessor(stage);
	 }
	 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		font.setColor(Color.BLACK);
//		font.setScale(1,1);
//		font.draw(batch, "The Main Menu Screen Works", 0, 200);
		batch.end();
		stage.draw();
	}
	
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
	
//	public void initializeButtonsArray(){
//		Levels[0] = "Play";
//		Levels[1] = "Instructions";
//		Levels[2] = "Credits";
//	}
	
	public void addButtons(String[] array){
//		skin = new Skin (Gdx.files.internal("uiskin.json"));
		for(int i =0; i<array.length; i++){
			final TextButton button = new TextButton("Level " + array[i],skin);
			button.setName(array[i]);
			table.add(button).width(button.getWidth()*scaleX).height(button.getHeight()*scaleY);
			table.row();
			button.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y){
//					Code Here for passing the String to the new screen
//					selected = button.getName();
					
				}
			});
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
