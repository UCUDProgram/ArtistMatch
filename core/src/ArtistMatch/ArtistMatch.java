package ArtistMatch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// ApplicationAdapter
public class ArtistMatch extends Game {

	private float[] screenSize;
	private Screen main, aGame,tScreen,instruct,subMenu,lSelect,setting, current;
	private String artist;
	private int score,question,difficulty;
	
	public ArtistMatch(){
	}
	
	@Override
	public void create() {
		screenSize = new float[4];
		screenSize[0]=0;
		screenSize[1]=0;
		screenSize[2]=0;
		screenSize[3]=0;
//		difficulty = 0;
		switchScreens(1);
		
	}

	@Override
	public void render() {
		current.render(0);
	}
	
	public void switchScreens(int next){
		
		// Title Screen
		if(next==1){
			current = new TitleScreen(this);
			((TitleScreen) current).create();
			tScreen = current;
		}
		
		// Main Menu Screen
		if(next==2){
			current = new MainMenu(this);
			((MainMenu) current).create();
			main = current;
		}
		
		// Level Select Screen
		if(next==3){
			current = new LevelSelect(this);
			((LevelSelect) current).create();
			lSelect = current;
		}
		
		// Submenu Screen
		if(next==4){
			current = new SubMenu(this);
			((SubMenu) current).create();
			subMenu = current;
		}
		
		// Game Screen
		if(next==5){
			current = new ArtistGame(this);
			((ArtistGame) current).create();
			aGame = current;
		}
		
		// Instructions Screen
		if(next==6){
			current = new Instructions(this);
			((Instructions) current).create();
			instruct = current;
		}
		
		// Settings Screen
		if(next==7){
			current = new Settings(this);
			((Settings) current).create();
			setting = current;
		}
		
		
		setScreen(current);
	}
	
	public float[] getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(float[] screenSize) {
		this.screenSize = screenSize;
	}

	public Screen getCurrent() {
		return current;
	}

	public void setCurrent(Screen current) {
		this.current = current;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(int question) {
		this.question = question;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	
	
	
	
	
}
