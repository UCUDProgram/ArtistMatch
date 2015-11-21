package ArtistMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class LevelSelect implements Screen, InputProcessor, ApplicationListener {
	private ArtistMatch game;
	private SpriteBatch batch;
	private String[] artists;
	private float screenHeight, screenWidth, scaleX, scaleY;
	private Skin skin;
	private Stage stage;
	private Table table,table1,table2;
	private Texture background,gameTitle;
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
		FileHandle title = Gdx.files.internal("Universal/Game Title Screen.png");
		gameTitle = new Texture(title);
		scaleX = screenWidth/640;
		scaleY = screenHeight/480;
		background = setBackgroundImage();
		initializeButtonsArray();
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
		Gdx.input.setInputProcessor(this);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
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
//		Random number generator to choose a random image as the ball image
		int randomBackground = (int) (Math.random() * backgroundSelection.size() );
		FileHandle backgroundImage = Gdx.files.internal(backgroundSelection.get(randomBackground));
		return new Texture(backgroundImage) ;
	}
	
	/*
	 * Initialize the Buttons Used in the array
	 */
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
	 * Adds the first half of buttons to the artist buttons to the screen
	 */
	public void addLeftButtons(){
		for(int i = 0;i< (artists.length / 2) ;i++){
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
	
	/*
	 * Adds the second half of buttons to the artist buttons to the screen
	 */
	public void addRightButtons(){
		for(int i = (artists.length / 2)  ;i< artists.length ;i++){
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
	
	/*
	 * Adds the back button to the screen
	 */
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