package ArtistMatch;

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

import java.util.List;
import java.util.ArrayList;

public class SubMenu implements Screen, InputProcessor, ApplicationListener{
	private ArtistMatch game;
	private SpriteBatch batch;
	private BitmapFont font;
	private float screenHeight, screenWidth,scaleX,scaleY;
	private Skin skin;
	private Table table,table1,table2;
	private Stage stage;
//	private 
	
	public SubMenu(ArtistMatch game){
		this.game = game;
	}

	public void create(){
		batch = new SpriteBatch();
		font = new BitmapFont();
		stage = new Stage();
		skin = new Skin (Gdx.files.internal("uiskin.json"));
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		scaleX = screenWidth/640;
		scaleY = screenHeight/480;
		table = new Table(skin);
		table1 = new Table(skin);
		table2 = new Table(skin);
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
		Gdx.input.setInputProcessor(this);
		Gdx.input.setInputProcessor(stage);
	}
		
	@Override
	public void render(float delta) {
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.WHITE);
		font.setScale(1,1);
//		font.draw(batch, "The Sub Menu Screen Works", 0, 100);
		batch.end();
		stage.draw();
	}
	
	public void addLeftButtons(){
		for(int i = 1;i<= 5;i++){
		final TextButton button = new TextButton("Question " + Integer.toString(i),skin);
		button.setName(Integer.toString(i));
		table.add(button).width(button.getWidth()*scaleX).height(button.getHeight()*scaleY);
		table.row();
		button.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setQuestion(Integer.parseInt(button.getName() ) );
				System.out.println(game.getQuestion());
				game.switchScreens(5);
			}
		});
		}
	}
	
	public void addRightButtons(){
		for(int i = 6;i<= 10;i++){
		final TextButton button = new TextButton("Question " + Integer.toString(i),skin);
		button.setName(Integer.toString(i));
		table1.add(button).width(button.getWidth()*scaleX).height(button.getHeight()*scaleY);
		table1.row();
		button.addListener(new ClickListener(){
//			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setQuestion(Integer.parseInt(button.getName() ) );
				System.out.println(game.getQuestion());
				game.switchScreens(5);
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
				game.switchScreens(3);
				System.out.println(game.getQuestion());
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
