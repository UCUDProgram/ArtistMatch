package ArtistMatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings implements Screen, InputProcessor, ApplicationListener{

	private ArtistMatch game;
	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private Table table;
	private Skin skin;
	private String selection;
	private float screenHeight, screenWidth, scaleX, scaleY;
	private final static String[] difficulty = {"Easy","Medium","Hard", "Expert"};
	
	public Settings(ArtistMatch game){
		this.game = game;
	}

	@Override
	public void create() {
		skin = new Skin (Gdx.files.internal("uiskin.json"));
		table = new Table(skin);
		stage = new Stage();
		batch = new SpriteBatch();
		font = new BitmapFont();
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		scaleX = screenWidth/640;
		scaleY = screenHeight/480;
		selection = difficulty[game.getDifficulty()];
		addButtons();
		table.setFillParent(true);
		table.center();
		stage.addActor(table);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.WHITE);
		selection = difficulty[game.getDifficulty()];
		font.draw(batch, "The current difficulty is " + selection, screenWidth / 10, 100);
		batch.end();
		stage.draw();
	}
	
	
	/*
	 * Add Buttons to the Screen
	 */
	public void addButtons(){
		final TextButton easybutton = new TextButton(difficulty[0],skin);
		easybutton.setName(difficulty[0]);
		table.add(easybutton).width(easybutton.getWidth()*scaleX).height(easybutton.getHeight()*scaleY);
		table.row();
		easybutton.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setDifficulty(0);
				System.out.println(game.getDifficulty());
			}
		});
		
		final TextButton mediumbutton = new TextButton(difficulty[1],skin);
		mediumbutton.setName(difficulty[1]);
		table.add(mediumbutton).width(mediumbutton.getWidth()*scaleX).height(mediumbutton.getHeight()*scaleY);
		table.row();
		mediumbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.setDifficulty(1);
				System.out.println(game.getDifficulty());
				}
		});	
		
		final TextButton hardbutton = new TextButton(difficulty[2],skin);
		hardbutton.setName(difficulty[2]);
		table.add(hardbutton).width(hardbutton.getWidth()*scaleX).height(hardbutton.getHeight()*scaleY);
		table.row();
		hardbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.setDifficulty(2);
				System.out.println(game.getDifficulty());
				}
		});	
		
		final TextButton expertbutton = new TextButton(difficulty[3],skin);
		expertbutton.setName(difficulty[3]);
		table.add(expertbutton).width(expertbutton.getWidth()*scaleX).height(expertbutton.getHeight()*scaleY);
		table.row();
		expertbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.setDifficulty(3);
				System.out.println(game.getDifficulty());
				}
		});	
		
		final TextButton backbutton = new TextButton("Main Menu",skin);
		backbutton.setName("Main Menu");
		table.add(backbutton).width(backbutton.getWidth()*scaleX).height(backbutton.getHeight()*scaleY);
		table.row();
		backbutton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.switchScreens(2);
				}
		});	
		
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
	public void render() {
		// TODO Auto-generated method stub
		
	}	
}
