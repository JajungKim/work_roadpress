package com.roadpress.trafficinfo.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.roadpress.R;
import com.roadpress.trafficinfo.vo.TrafficInfoVO;

public class TrafficInfoActivity extends Activity {
	
	static final String TAG = "TrafficInfoActivity";
	
	private Button button01;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_info);
		
		button01 = (Button) findViewById(R.id.button01);
		button01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Thread thread = new Thread(new XmlParser());
				thread.start();
			}
		});
	}
	
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
}
