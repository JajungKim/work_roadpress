package com.roadpress.helper;

import java.util.Iterator;
import java.util.Map;

import com.roadpress.manager.AccidentInfoXmlManager;
import com.roadpress.manager.CCTVInfoXmlManager;
import com.roadpress.manager.ConstructionInfoXmlManager;
import com.roadpress.manager.TrafficInfoXmlManager;
import com.roadpress.manager.XmlManager;

public class RoadPressUrlMakeHelper {
	private static final String HTTP                  = "http://";
	private static final String HOST_ADDR             = "openapi.its.go.kr";
	private static final String HOST_PORT             = ":8080";
	private static final String URI_DIR               = "/api/";
	private static final String DEFAULT_PARAM         = "?key=1376635351296&ReqType=2";
	private static final String API_TRAFFICINFO       = URI_DIR + "TrafficInfo";      //교통정보
	private static final String API_EVENTIDENTITY     = URI_DIR + "EventIdentity";    //공사정보
	private static final String API_INCIDENTIDENTITY  = URI_DIR + "IncidentIdentity"; //사고정보
	private static final String API_CCTVINFO          = URI_DIR + "CCTVInfo";         //CCTV 영상 정보
	
	private String apiUrl;
	private String urlParam;
	
	public RoadPressUrlMakeHelper(Map<String, String> param) {
		apiUrl = new StringBuilder(HTTP).append(HOST_ADDR).append(HOST_PORT).toString();
		setUrlParam(param);
	}
	
	private void setUrlParam(Map<String, String> param) {
		Iterator<String> it = param.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		
		while(it.hasNext()) {
			String key = it.next();
			sb.append("&").append(key).append("=").append(param.get(key));
		}
		
		urlParam = sb.toString();
	}
	
	public String getApiUrl(XmlManager xmlManager) {
		StringBuilder sb = new StringBuilder(apiUrl);
		if(xmlManager instanceof TrafficInfoXmlManager) {
			sb.append(API_TRAFFICINFO);
		} else if(xmlManager instanceof AccidentInfoXmlManager) {
			sb.append(API_INCIDENTIDENTITY);
		} else if(xmlManager instanceof ConstructionInfoXmlManager) {
			sb.append(API_EVENTIDENTITY);
		} else if(xmlManager instanceof CCTVInfoXmlManager) {
			sb.append(API_CCTVINFO);
		}
		sb.append(DEFAULT_PARAM)
          .append(urlParam);
		
		return sb.toString();
	}
}
