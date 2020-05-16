package FemaleGenitalMutilation;


import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PConstants;
import processing.core.PGraphics;

public  class CountryMarker extends CommonPolygonMarker{
	
	CountryMarker(List<Location> locations, 
			HashMap<String, Object> properties) {
		super(locations, properties);
		// TODO Auto-generated constructor stub
	}



	String title;
	
	public String getTitle() {
		return (String) getProperty("name");
	}
	
	public void setClicked(boolean state) {
		clicked = state;
		
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void drawMarker(PGraphics pg) {
		// save previous styling
		determineColor(pg);
		pg.pushStyle();
		//pg.fill(markerColor, 100, 255-markerColor);
		System.out.println("markerDrawn");
		// reset to previous styling
		pg.popStyle();
		
	}
	
	public void determineColor(PGraphics pg) {
		float fgm_perc = Float.parseFloat(getProperty("prevalence overall").toString());
		System.out.println(fgm_perc + " "+getTitle());
		int colorLevel = (int) femaleCircumcisionMap.map(fgm_perc, 0, 100, 0, 255);
		pg.fill(colorLevel, 100, 255-colorLevel);
	}
	

	public void showTitle(PGraphics pg,int x,int y) {
		// TODO Auto-generated method stub
		String title = getTitle();
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(title) +6, 18, 5);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(title, x + 3 , y +18);
		
		
		pg.popStyle();
		
	}


	




}
