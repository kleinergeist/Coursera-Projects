package FemaleGenitalMutilation;


import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import processing.core.PGraphics;


public abstract class CommonPolygonMarker extends SimplePolygonMarker {

	// Records whether this marker has been clicked (most recently)
	protected boolean clicked = false;
	
	String id;
	
	 public CommonPolygonMarker(List<Location> locations,
           HashMap<String,Object> properties) {
		this.addLocations(locations);
		this.setProperties(properties);
	}
	
	public String getId() {
		return id;
	}

	public boolean getClicked() {
		System.out.println("gotClicked");

		return clicked;
	}
	
	public void setClicked(boolean state) {
		clicked = state;
		System.out.println("SetClicked");
	}
	

	public void draw(PGraphics pg,int x,int y) {
	System.out.println("common");
		if(!hidden) {
			drawMarker(pg);
			if (selected) {
				showTitle(pg,x,y);
			}
		}
	}
	public abstract void showTitle(PGraphics pg,int x,int y);
	public abstract void drawMarker(PGraphics pg);
}