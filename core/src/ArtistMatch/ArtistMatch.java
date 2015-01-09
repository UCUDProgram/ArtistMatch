package ArtistMatch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.myUD.game.Map;
//import com.myUD.game.Menu;
//import com.myUD.game.Question;
//import com.myUD.game.SubMenu;
//import com.myUD.game.Users;

// ApplicationAdapter
public class ArtistMatch extends Game {
	SpriteBatch batch;
	Texture img;
	private float[] screenSize;
	private Screen main, aGame,tScreen,instruct,subMenu,lSelect,current;
	private int levelNumber;
	private String genre;
	
	public ArtistMatch(){
	}
	
	@Override
	public void create() {
		screenSize = new float[4];
		screenSize[0]=0;
		screenSize[1]=0;
		screenSize[2]=0;
		screenSize[3]=0;
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

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	
	
	
}
