package com.roadpress.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.os.Handler;

import com.roadpress.helper.RoadPressUrlMakeHelper;
import com.roadpress.helper.XmlHttpHelper;
import com.roadpress.helper.XmlHttpHelper.ParserProcess;

public class TrafficInfoXmlManager implements XmlManager{
	private XmlHttpHelper xmlHttpHelper;
	private List<Map<String, String>> dataList;
	
	private enum TrafficInfoTag {
		defaultValue(-1),
		data(0),
		datacount(1),
		roadsectionid(2),
		avgspeed(3),
		startnodeid(4),
		roadnametext(5),
		traveltime(6),
		endnodeid(7);
		
		public final int key;
		
		private TrafficInfoTag(int key){
			this.key = key;
		}
	}
	
	public TrafficInfoXmlManager(Map<String, String> param, Handler handler) {
		xmlHttpHelper = new XmlHttpHelper(new RoadPressUrlMakeHelper(param).getApiUrl(this), handler);
	}

	public void parsing() throws Exception {
		
		ParserProcess parserProcess = new ParserProcess() {
			public void onRun(XmlPullParser parser) throws Exception {
				int eventType = parser.getEventType();
				String tag     = "";
				String text    = "";
				int    key     = TrafficInfoTag.defaultValue.key;
				
				Map<String, String> data = null;
				
				while (eventType != XmlPullParser.END_DOCUMENT){
					switch(eventType){
				      case XmlPullParser.START_DOCUMENT:
				    	  break;
				      case XmlPullParser.END_DOCUMENT:
				    	  break;
				      case XmlPullParser.START_TAG:
				         tag = parser.getName();
				         if(TrafficInfoTag.data.toString().equals(tag)) {
				        	 if(data != null && "".equals(data.get(TrafficInfoTag.roadsectionid)) == false) {
				        		 dataList.add(data);
				        	 }
				        	 data = new HashMap<String, String>();
				         } else if(TrafficInfoTag.datacount.toString().equals(tag)) {
				        	 key = TrafficInfoTag.datacount.key;
				         } else if(TrafficInfoTag.roadsectionid.toString().equals(tag)) {
				        	 key = TrafficInfoTag.roadsectionid.key;
				         } else if(TrafficInfoTag.avgspeed.toString().equals(tag)) {
				        	 key = TrafficInfoTag.avgspeed.key;
				         } else if(TrafficInfoTag.startnodeid.toString().equals(tag)) {
				        	 key = TrafficInfoTag.startnodeid.key;
				         } else if(TrafficInfoTag.roadnametext.toString().equals(tag)) {
				        	 key = TrafficInfoTag.roadnametext.key;
				         } else if(TrafficInfoTag.traveltime.toString().equals(tag)) {
				        	 key = TrafficInfoTag.traveltime.key;
				         } else if(TrafficInfoTag.endnodeid.toString().equals(tag)) {
				        	 key = TrafficInfoTag.endnodeid.key;
				         }
				         break;
				      case XmlPullParser.TEXT:
				        text = parser.getText().trim();
				        
				        if(key == TrafficInfoTag.datacount.key) {
				        	dataList = new ArrayList<Map<String, String>>(Integer.parseInt(text));
				        } else if(key == TrafficInfoTag.roadsectionid.key) {
				        	data.put(TrafficInfoTag.roadsectionid.toString(), text);
				        } else if(key == TrafficInfoTag.avgspeed.key) {
				        	data.put(TrafficInfoTag.avgspeed.toString(), text);
				        } else if(key == TrafficInfoTag.startnodeid.key) {
				        	data.put(TrafficInfoTag.startnodeid.toString(), text);
				        } else if(key == TrafficInfoTag.roadnametext.key) {
				        	data.put(TrafficInfoTag.roadnametext.toString(), text);
				        } else if(key == TrafficInfoTag.traveltime.key) {
				        	data.put(TrafficInfoTag.traveltime.toString(), text);
				        } else if(key == TrafficInfoTag.endnodeid.key) {
				        	data.put(TrafficInfoTag.endnodeid.toString(), text);
				        }
				        
				        key = TrafficInfoTag.defaultValue.key;
				        
				        break;
				      case XmlPullParser.END_TAG:
					     break;   
				      }
				      eventType = parser.next();
				   }
			}
		};
		xmlHttpHelper.setParserProcess(parserProcess);
		xmlHttpHelper.execute();
	}
	
	public List<Map<String, String>> getDataList() {
		return dataList;
	}
}
