package FemaleGenitalMutilation;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;

public class CountryMarkerNew extends SimplePolygonMarker {
	boolean clicked;
	
	CountryMarkerNew(List<Location>locations,HashMap<String,Object> properties) {
		this.addLocations(locations);
		this.setProperties(properties);
		System.out.println("CountryMarker set");
	}
	public void setClicked(boolean state) {
		 clicked = state;
		
	}
	
	
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return getProperty("Title").toString();
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void draw(PGraphics pg) {

		pg.pushStyle();
		
		pg.fill(0,0,0);
		pg.rect(200,200,200,200);

	
		pg.popStyle();

	}


}
