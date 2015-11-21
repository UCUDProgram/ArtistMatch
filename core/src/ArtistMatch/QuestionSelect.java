package ArtistMatch;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionSelect implements Screen, InputProcessor, ApplicationListener{

	private ArtistMatch game;
	private SpriteBatch batch;
	private float screenHeight, screenWidth,scaleX,scaleY;
	private Skin skin;
	private Table table,table1,table2;
	private Stage stage;
	private Texture background,gameTitle;
	
	public QuestionSelect(ArtistMatch game){
		this.game = game;
	}

	public void create(){
		batch = new SpriteBatch();
		FileHandle title = Gdx.files.internal("Universal/Game Title Screen.png");
		gameTitle = new Texture(title);
		stage = new Stage();
		skin = new Skin (Gdx.files.internal("uiskin.json"));
		screenHeight = Gdx.graphics.getHeight(); 
		screenWidth = Gdx.graphics.getWidth();
		background = setBackgroundImage();
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
		batch.draw(background, 0, 0, screenWidth, screenHeight);
		batch.draw(gameTitle, 0,( 6 * (screenHeight / 7) ), screenWidth, (screenHeight / 7)  );
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		batch.end();
		stage.draw();
	}
	
	/*
	 * Formats the artist's name to proper formatting, including spacing
	 * Used in the xml files, which gets the question, options & Correct answer(s)
	 * Any update to the Levels.xml file, with artists having more than 1 word,needs to be updated here, as well
	 */
	public String formatName(String artName){
		if (game.getArtist().equals("Michael Jackson"))
			return "MichaelJackson";
		else if (game.getArtist().equals("Whitney Houston"))
			return "WhitneyHouston";
		else if (game.getArtist().equals("Mariah Carey"))
			return "MariahCarey";
		else if (game.getArtist().equals("Carrie Underwood"))
			return "CarrieUnderwood";
		else if (game.getArtist().equals("Taylor Swift"))
			return "TaylorSwift";
		else if (game.getArtist().equals("The Beatles"))
			return "TheBeatles";
		else
			return artName;
	}
	
	/*
	 * Function to set the Image of the Background
	 */
	public Texture setBackgroundImage(){
		List<String> backgroundSelection = new ArrayList<String>();
		
//		Adds the universal background images to the List
		try{Element root = new XmlReader().parse(Gdx.files.internal("gameImages.xml"));
		Element selection = root.getChildByName("Universal");
		Array<Element> backgroundArray = selection.getChildrenByName("background");
		for (int i = 0; i < backgroundArray.size; i++)
			backgroundSelection.add(backgroundArray.get(i).getText());
		}
		catch(IOException e){
		}
		
//		Use of xml file readers to add strings to the balls List
		try{Element root = new XmlReader().parse(Gdx.files.internal("gameImages.xml"));
		Element selection = root.getChildByName("artist");
		Element artist = selection.getChildByName(formatName(game.getArtist()));
		Array<Element> backgroundSelectArray = artist.getChildrenByName("background");
		for (int i = 0; i < backgroundSelectArray.size; i++)
			backgroundSelection.add(backgroundSelectArray.get(i).getText());
		}
		catch(IOException e){
		}
		
//		Random number generator to choose a random image as the ball image
		int randomBackground = (int) (Math.random() * backgroundSelection.size() );
		FileHandle backgroundImage = Gdx.files.internal(backgroundSelection.get(randomBackground));
		return new Texture(backgroundImage) ;
	}
	
	/*
	 * Adds the first half of the buttons, representing question #, to the screen
	 */
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
	
	/*
	 * Adds the second half of the buttons, representing question #, to the screen
	 */
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
	
	/*
	 * Adds the back button, representing return to artist select screen, to the screen
	 */
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
