package ArtistMatch;

import com.badlogic.gdx.graphics.Texture;

class Box{
	private String aString;
	private char displaySelection;
	private Texture image;
	private float boxXStart, boxYStart,drawXLoc,drawYLoc, boxScale;
	boolean drawable;
	private int gameDiff;
	
	/*
	 * Constructor for the Box class where the X & Y Coordinate changes
	 */
	public Box(String aStr, float xBoxLoc,float yBoxLoc, char dis,float dXloc, float dYloc, int diff){
		aString = aStr;
		boxXStart = xBoxLoc;
		boxYStart = yBoxLoc;
		displaySelection = dis;
		drawXLoc = dXloc;
		drawYLoc = dYloc;
		gameDiff = diff;
		boxScale = setBoxScale();
		image = new Texture("YouDee.png");
		drawable = true;
	}
	
	/*
	 * Constructor for Box
	 * Purpose is to aid in defining box size, in the level and set size,
	 */
	public Box(int diff){
		aString = "";
		boxXStart = 0;
		boxYStart = 0;
		displaySelection = '0';
		drawXLoc = 0;
		drawYLoc = 0;
		gameDiff = diff;
		boxScale = setBoxScale();
		image = new Texture("YouDee.png");
		drawable = false;
	}
	
//	public Texture setBoxImage(){
//		
//	}
	
	
	public String getaString() {
		return aString;
	}

	public char getDisplaySelection() {
		return displaySelection;
	}

	public Texture getImage() {
		return image;
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
	
}