
package FemaleGenitalMutilation;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

import parsing.ParseFeed;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.awt.Color;
import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;

public class femaleCircumcisionMap extends PApplet{

	private Marker lastSelected;
	private Marker lastClicked;
	UnfoldingMap map;
	HashMap<String,CountryMarker> FGMbyCountry;
	List<Feature> countriesOnMap;
	List<CountryMarker> countryMarkers = new ArrayList<CountryMarker>();
	private ArrayList<Marker> countryOnMapMarkers;
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	public void setup() {

		size(1150,800,OPENGL);
		map = new UnfoldingMap(this,400, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		MapUtils.createDefaultEventDispatcher(this, map);
		
		countriesOnMap = GeoJSONReader.loadData(this, "countries.geo.json");
		countryOnMapMarkers = (ArrayList)MapUtils.createSimpleMarkers(countriesOnMap);
		
		map.setZoomRange(1, 5);
		map.zoomAndPanTo(3, new Location(0, 25)); 
	
		FGMbyCountry = loadFGMFromCSV("csvfgm.txt");
		
		for(Marker marker:countryOnMapMarkers) {
		String countryId = marker.getId();
			if (FGMbyCountry.containsKey(countryId)) {
				HashMap<String,Object> properties = new HashMap<String,Object>();
				properties.putAll(FGMbyCountry.get(countryId).getProperties());
				properties.putAll(marker.getProperties());
				properties.put("Location", marker.getLocation());
				FGMbyCountry.get(countryId).setProperties(properties);
				marker = FGMbyCountry.get(countryId);
				marker = (CommonPolygonMarker)FGMbyCountry.get(countryId);
		}
	}
			
		
		map.addMarkers(countryOnMapMarkers);
		shadeCountries();
		addKey();
		setText();
		textFont(createFont("Arial",12));

	}
	public void setText() {
		String message = "FEMALE GENITAL MUTILATION";
		fill(99,99,99);
		textAlign(CENTER);
		  textFont(createFont("Arial Bold",18));
		  for(int i = 1; i <= message.length(); i++){
		    text(message.substring(i-1,i), 1100, 50 + i *22);
		  }
	}
	
	public void draw() {
		map.draw();
		showInfo();
		showInfoIfHover();
	}
	

	private void shadeCountries() {
		for (Marker marker : countryOnMapMarkers) {

			String countryId = marker.getId();
			
			if (FGMbyCountry.containsKey(countryId)) {
				float fgm_perc = Float.parseFloat(FGMbyCountry.get(countryId).getProperty("prevalence overall").toString());
				int colorLevel = (int) map(fgm_perc, 0, 100, 0, 255);
				marker.setColor(color(colorLevel, 100, 255-colorLevel));
			} else {
				marker.setColor(color(99, 99, 99));

			}
			
		}
	}
	
	public void mouseMoved()
	{
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		selectMarkerIfHover(countryOnMapMarkers);
	}
	
	private void showInfoIfHover() {
	
		if(mouseX>415&&mouseX<440&&mouseY>65&&mouseY<90) {
			fill(255,255,255);
			rect(415,90,200,220);
			textAlign(LEFT);
			fill(0,0,0);
			textFont(createFont("Arial Bold",12));
			text("Female genital mutilation (FGM), \nalso known as female genital \ncutting and female circumcision, \nis the ritual cutting or removal \nof some or all of the external \nfemale genitalia. The practice \nis found in Africa, Asia and \nthe Middle East, and within \ncommunities from countries in \nwhich FGM is common.",420,110);
		}
	}
	
	private void selectMarkerIfHover(List<Marker> countryOnMapMarkers)
	{
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : countryOnMapMarkers) 
		{
			if (lastClicked==null) {
			if (m.isInside(map,  mouseX, mouseY)&&FGMbyCountry.containsKey(m.getId())) {
				lastSelected = m;
				m.setSelected(true);
				System.out.println(m.isSelected()+" "+ m.getId());
			
				return;
			}
		}
		}
	}
	
	public void mouseClicked()
	{
		
		if (mouseX>50&&mouseX<150) {

			if (mouseY<425&&mouseY>125) {
				unhideMarkers();

				for(Marker m:countryOnMapMarkers) {
					if(FGMbyCountry.containsKey(m.getId())) {
				
					if (!((125+Float.parseFloat(FGMbyCountry.get(m.getId()).getProperty("prevalence overall").toString())*3)>(mouseY-125)*0.8+125)||!((125+Float.parseFloat(FGMbyCountry.get(m.getId()).getProperty("prevalence overall").toString())*3)<(mouseY-125)*1.2+125)) {
						m.setColor(color(99,99,99));
					} 
					}
					
				}
				fill(0,0,0);
				line(50,mouseY,150,mouseY);
				int perc = (int)((double)(mouseY-125)/3);
				int perc8 = (int)((double)perc*0.8);
				if (perc8<0) perc8=0;
				int perc12 = (int)((double)perc*1.2);
				if (perc12>100) perc12=100;
				text(perc8+"-"+perc12+"%",210,mouseY);
				//TODO percentage
				
			}
		}
		else if (lastClicked != null) {

			unhideMarkers();
			lastClicked = null;
			
		}
		else if (lastClicked == null) 
		{

			checkCountriesForClick();
			
		}
		

		
	}
	
	private void checkCountriesForClick()
	{
		if (lastClicked != null) return;
		for (Marker m : countryOnMapMarkers) {
			if (!m.isHidden() && m.isInside(map, mouseX, mouseY)&&FGMbyCountry.containsKey(m.getId())) {
				lastClicked = m;
				fill(255,255,255);
				rect(400, 50, 650, 600);
				
				map.zoomAndPanTo(5, m.getLocation());
			    
				fill(255,255,255);
				rect(25,50,350,600);
				
				addCountryInfo(m.getId());

				for (Marker mhide : countryOnMapMarkers) {
					if (mhide != lastClicked) {
						mhide.setColor(color(150,150,150));
					}
				}
				
				return;
			}
		}
		unhideMarkers();
	}
	
	private void addCountryInfo(String id) {

		CountryMarker cm = FGMbyCountry.get(id);
		fill(0,0,0);
		textAlign(CENTER);
		textSize(32);
		if(cm.getTitle().length()>15) {
			textSize(20);
		}
		text(cm.getTitle(),200,100);
		
		textAlign(CENTER);
		textSize(18);
		text("Prevalence of FGM in women "+cm.getProperty("prevalence overall").toString()+"%",200,150);
		
	line(170,170,225,170);	
	
	text("Urban",135,190);
	text("Rural",260,190);

	
	fill(150,150,150);
	rect(175,170,20,Float.parseFloat(cm.getProperty("prevalence urban").toString()));
	text(cm.getProperty("prevalence urban").toString(),185,Float.parseFloat(cm.getProperty("prevalence urban").toString())+190);
	
	fill(139,69,19);
	rect(205,170,20,Float.parseFloat(cm.getProperty("prevalence rural").toString()));
	text(cm.getProperty("prevalence rural").toString(),215,Float.parseFloat(cm.getProperty("prevalence rural").toString())+190);

	
	
	fill(0,0,0);
	line(130,290,270,290);	
	
	
	fill(128,128,0);
	rect(130,290,20,Float.parseFloat(cm.getProperty("prevalence poorest").toString()));
	text(cm.getProperty("prevalence poorest").toString(),140,Float.parseFloat(cm.getProperty("prevalence poorest").toString())+310);

	fill(159,149,0);
	rect(160,290,20,Float.parseFloat(cm.getProperty("prevalence second").toString()));
	text(cm.getProperty("prevalence second").toString(),170,Float.parseFloat(cm.getProperty("prevalence second").toString())+310);

	fill(191,171,0);
	rect(190,290,20,Float.parseFloat(cm.getProperty("prevalence middle").toString()));
	text(cm.getProperty("prevalence middle").toString(),200,Float.parseFloat(cm.getProperty("prevalence middle").toString())+310);

	fill(214,187,0);
	rect(220,290,20,Float.parseFloat(cm.getProperty("prevalence fourth").toString()));
	text(cm.getProperty("prevalence fourth").toString(),230,Float.parseFloat(cm.getProperty("prevalence fourth").toString())+310);

	fill(255,215,0);
	rect(250,290,20,Float.parseFloat(cm.getProperty("prevalence richest").toString()));
	text(cm.getProperty("prevalence richest").toString(),260,Float.parseFloat(cm.getProperty("prevalence richest").toString())+310);

		setGradient(70,450,260,100,1);
		fill(99,99,99);
		text("Poorest",75,570);
		text("Richest",325,570);
	}
	
	private void unhideMarkers() {
		shadeCountries();
		map.zoomAndPanTo(3, new Location(0,25));
		addKey();
	}

	private HashMap<String, CountryMarker> loadFGMFromCSV(String fileName) {
		HashMap<String, CountryMarker> fgmbycountrymap = new HashMap<String, CountryMarker>();

		Reader in;
		
		try {
			in = new FileReader("csvfgm.txt");
			 CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180);

			 for (CSVRecord record : parser) {
			    String name = record.get(0);
			    String code3 = record.get(1);
			
			    int prevalenceOverall = Integer.parseInt(record.get(2));
			    
			    int prevalenceUrban = Integer.parseInt(record.get(3));
			    int prevalenceRural = Integer.parseInt(record.get(4));
			    
			    int prevalencePoorest = Integer.parseInt(record.get(5));
			    int prevalenceSecond = Integer.parseInt(record.get(6));
			    int prevalenceMiddle = Integer.parseInt(record.get(7));
			    int prevalenceFourth = Integer.parseInt(record.get(8));
			    int prevalenceRichest = Integer.parseInt(record.get(9));
			    
			    HashMap <String,Object> propertyHashMap = new HashMap<String,Object>();
			   
			    propertyHashMap.put("name", name);
			    propertyHashMap.put("id",code3);
			    propertyHashMap.put("prevalence overall", prevalenceOverall);
			    
			    propertyHashMap.put("prevalence urban", prevalenceUrban);
			    propertyHashMap.put("prevalence rural", prevalenceRural);

			    propertyHashMap.put("prevalence poorest", prevalencePoorest);
			    propertyHashMap.put("prevalence second", prevalenceSecond);
			    propertyHashMap.put("prevalence middle", prevalenceMiddle);
			    propertyHashMap.put("prevalence fourth", prevalenceFourth);
			    propertyHashMap.put("prevalence richest", prevalenceRichest);

			    
			    for (Marker m:countryOnMapMarkers) {
			    	if (m.getId().equals(code3)) {
			    		ArrayList<Location> locations = new ArrayList<Location>();
			    		locations.add(m.getLocation());
			    		 CountryMarker cm = new CountryMarker(locations,propertyHashMap);
			    		 fgmbycountrymap.put(code3,cm);
			    	}
			    }
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fgmbycountrymap;
	}
	
	public void showInfo(){
		fill(255,255,255);
		rect(415,65,25,25);
		textAlign(CENTER);
		fill(0,0,0);
		text("?",427,80);
		
	}

	public void addKey() {
		fill(255,255,255);
		rect(25,50,350,600);
		
		textAlign(LEFT);
		fill(0,0,0);
		textSize(20);
		text("KEY",175,90);
		setGradient(50, 125, 100, 300,0);
		
		fill(0,0,0);
		textSize(14);
		
		for(int i=0;i<125;i+=25) {
			String s = "~"+i+"%";
			text(s,160,(float)(125+(425-125)*0.01*i));
		}
		
		fill(255,255,255);
		rect(50,475,25,25);
		textAlign(CENTER);
		fill(0,0,0);
		text("?",62,490);
		textAlign(LEFT);
		text("Information",90,490);
		
		 	
	}
	
	void setGradient(int x, int y, float w, float h,int key) {

		  noFill();
		  switch(key) {
		  case 0:
		    for (int i = y; i <= y+h; i++) {
		      float inter = map(i, y, y+h, 0, 1);
		      int c = lerpColor(color(0,100,255), color(255,100,0), inter);
		      stroke(c);
		      line(x, i, x+w, i);
		     
		    }
		    break;
		  
		  case 1:
			  for (int i = x; i <= x+w; i++) {
			      float inter = map(i, x, x+w, 0, 1);
			      int c = lerpColor(color(128,128,0), color(255,215,0), inter);
			      stroke(c);
			      line(i, y, i, y+h);
			      
			    }
			  break;
		  }
	
	}
}
