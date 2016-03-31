package com.bigbass1997.gamesoflife.skins;

import com.bigbass1997.gamesoflife.fonts.FontID;

public class SkinID {
	
	public FontID fontID;
	
	public SkinID(FontID fontID){
		this.fontID = fontID;
	}
	
	public String toString(){
		return "skin." + fontID.toString();
	}
}
