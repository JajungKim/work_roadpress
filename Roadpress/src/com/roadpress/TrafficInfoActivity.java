package com.roadpress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.roadpress.manager.TrafficInfoXmlManager;
import com.roadpress.manager.XmlManager;

public class TrafficInfoActivity extends Activity {
	private Button button01;
	private XmlManager xmlManager;
	Map<String, String> param = new HashMap<String, String>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_info);
		
		button01 = (Button) findViewById(R.id.button01);
		button01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//MinX=126.800000&MaxX=127.890000&MinY=34.900000&MaxY=35.100000
				param.clear();
				param.put("MinX", "126.800000");
				param.put("MaxX", "127.890000");
				param.put("MinY", "34.900000");
				param.put("MaxY", "35.100000");
				xmlManager = new TrafficInfoXmlManager(param, handler);
				try {
					xmlManager.parsing();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
		});
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) { 
			List<Map<String, String>> getDataList = xmlManager.getDataList();
			Log.d("Data List Size", "" + getDataList.size()); 
			for(int i = 0; i < getDataList.size(); i++) {
				Map<String,String> data = getDataList.get(i);
				Log.d("============" + (i + 1)+ "==========", "START>>>>>>>>>>>>>>>>>>>>");
				Log.d("XML Parsing Result", "Result" + " >>> " + "roadsectionid : " + data.get("roadsectionid"));
				Log.d("XML Parsing Result", "Result" + " >>> " + "avgspeed : " + data.get("avgspeed"));
				Log.d("XML Parsing Result", "Result" + " >>> " + "startnodeid : " + data.get("startnodeid"));
				Log.d("XML Parsing Result", "Result" + " >>> " + "roadnametext : " + data.get("roadnametext"));
				Log.d("XML Parsing Result", "Result" + " >>> " + "traveltime : " + data.get("traveltime"));
				Log.d("XML Parsing Result", "Result" + " >>> " + "endnodeid : " + data.get("endnodeid"));
				Log.d("============" + (i + 1)+ "==========", "END  >>>>>>>>>>>>>>>>>>>>");
			}
		}
	};
	
	/*
	class XmlParser implements Runnable {
		private List<TrafficInfoVO> dataList = null;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = factory.newPullParser();
				
				URL url = new URL("http://openapi.its.go.kr:8080/api/TrafficInfo?key=1376635351296&ReqType=2&MinX=126.800000&MaxX=127.890000&MinY=34.900000&MaxY=35.100000");
				parser.setInput(url.openStream(), "utf-8");
				
				int eventType = parser.getEventType();
				String tag     = "";
				String text    = "";
				int    setType = -1;
				TrafficInfoVO trafficInfoVO = null;
				
				
				while (eventType != XmlPullParser.END_DOCUMENT){
					switch(eventType){
				      case XmlPullParser.START_DOCUMENT:
				    	  break;
				      case XmlPullParser.END_DOCUMENT:
				    	  break;
				      case XmlPullParser.START_TAG:
				         tag = parser.getName();
				         if("data".equals(tag)) {
				        	 if(trafficInfoVO != null && trafficInfoVO.getRoadsectionid().equals("") == false) {
				        		 dataList.add(trafficInfoVO);
				        	 }
				        	 trafficInfoVO = new TrafficInfoVO();
				         } else if("datacount".equals(tag)) {
				        	 setType = 0;
				         } else if("roadsectionid".equals(tag)) {
				        	 setType = 1;
				         } else if("avgspeed".equals(tag)) {
				        	 setType = 2;
				         } else if("startnodeid".equals(tag)) {
				        	 setType = 3;
				         } else if("roadnametext".equals(tag)) {
				        	 setType = 4;
				         } else if("traveltime".equals(tag)) {
				        	 setType = 5;
				         } else if("endnodeid".equals(tag)) {
				        	 setType = 6;
				         }
				         break;
				      case XmlPullParser.TEXT:
				        text = parser.getText().trim();
				        
				        if(setType == 0) {
				        	dataList = new ArrayList<TrafficInfoVO>(Integer.parseInt(text));
				        } else if(setType == 1) {
				        	trafficInfoVO.setRoadsectionid(text);
				        } else if(setType == 2) {
				        	trafficInfoVO.setAvgspeed(text);
				        } else if(setType == 3) {
				        	trafficInfoVO.setStartnodeid(text);
				        } else if(setType == 4) {
				        	trafficInfoVO.setRoadnametext(text);
				        } else if(setType == 5) {
				        	trafficInfoVO.setTraveltime(text);
				        } else if(setType == 6) {
				        	trafficInfoVO.setEndnodeid(text);
				        }
				        
				        setType = -1;
				        break;
				      case XmlPullParser.END_TAG:
					     break;   
				      }
				      eventType = parser.next();
				   }
			} catch(Exception e) {
				Log.e(TAG, "error", e);
			}
			
			
		}
		
	}
	*/
}
