package ArtistMatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

class Box{
	private String aString, boxString;
	private char displaySelection;
	private Texture boxImage;
	private float boxXStart, boxYStart,drawXLoc,drawYLoc, boxScale;
	boolean drawable;
	private int gameDiff;
	
	/*
	 * Constructor for the Box class where the X & Y Coordinate changes
	 */
//	public Box(String aStr, float xBoxLoc,float yBoxLoc, char dis,float dXloc, float dYloc, int diff){
//		aString = aStr;
//		boxXStart = xBoxLoc;
//		boxYStart = yBoxLoc;
//		displaySelection = dis;
//		drawXLoc = dXloc;
//		drawYLoc = dYloc;
//		gameDiff = diff;
//		boxScale = setBoxScale();
//		
////		FileHandle handle = Gdx.files.internal("Taylor/Taylor-Swift-Change-Box.png");
////		boxImage = new Texture(handle);
//		
//		boxImage = new Texture("YouDee.png");
//		drawable = true;
//	}
	
	/*
	 * Constructor for the Box class 
	 * The X & Y Coordinate, box string, display char, difficulty, Box Image & box draw x&y locations
	 */
	public Box(String aStr, float xBoxLoc,float yBoxLoc, char dis,float dXloc, float dYloc, int diff, String BoxImg){
		aString = aStr;
		boxXStart = xBoxLoc;
		boxYStart = yBoxLoc;
		displaySelection = dis;
		drawXLoc = dXloc;
		drawYLoc = dYloc;
		gameDiff = diff;
		boxString = BoxImg;
		FileHandle handle = Gdx.files.internal(boxString);
		Texture image = new Texture(handle);
		boxImage = image;
		boxScale = setBoxScale();
		drawable = true;
	}
	
	
	
	/*
	 * Constructor for Box
	 * Purpose is to aid in defining box size, in the level and set size,
	 */
	public Box(int diff, String bImage){
		aString = "";
		boxXStart = 0;
		boxYStart = 0;
		displaySelection = '0';
		drawXLoc = 0;
		drawYLoc = 0;
		gameDiff = diff;
		boxScale = setBoxScale();
		String boxI = bImage;
		FileHandle bHandle = Gdx.files.internal(boxI);
		Texture imge = new Texture(bHandle);
		boxImage = imge;
		drawable = false;
	}
	
	/*
	 * Sets the Box's Scale, which is the size that the box will be drawn on the screen
	 */
	public float setBoxScale(){
		if (gameDiff ==0)
			return .25f;
		else if (gameDiff ==0)
			return .2f;
		else if (gameDiff ==0)
			return .18f;
		else
			return .12f;
	}
	
	
//	Getters & Setters for Box Class
	public String getaString() {
		return aString;
	}

	public char getDisplaySelection() {
		return displaySelection;
	}

	public Texture getImage() {
		return boxImage;
	}

	public float getBoxXStart() {
		return boxXStart;
	}

	public float getBoxYStart() {
		return boxYStart;
	}

	public float getDrawXLoc() {
		return drawXLoc;
	}

	public float getDrawYLoc() {
		return drawYLoc;
	}

	public float getBoxScale() {
		return boxScale;
	}

	public boolean isDrawable() {
		return drawable;
	}

	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}
	
	
	
}