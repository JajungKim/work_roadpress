package com.roadpress.trafficinfo.vo;


public class TrafficInfoVO {
	public enum Data { DEFAULT, DATACOUNT, ROADSECTIONID, AVGSPEED, STARTNODEID, ROADNAMETEXT, TRAVELTIME, ENDNODEID}
	
	private String roadsectionid;
	private String avgspeed;
	private String startnodeid;
	private String roadnametext;
	private String traveltime;
	private String endnodeid;
	
	public String getRoadsectionid() {
		return roadsectionid;
	}
	public void setRoadsectionid(String roadsectionid) {
		this.roadsectionid = roadsectionid;
	}
	public String getAvgspeed() {
		return avgspeed;
	}
	public void setAvgspeed(String avgspeed) {
		this.avgspeed = avgspeed;
	}
	public String getStartnodeid() {
		return startnodeid;
	}
	public void setStartnodeid(String startnodeid) {
		this.startnodeid = startnodeid;
	}
	public String getRoadnametext() {
		return roadnametext;
	}
	public void setRoadnametext(String roadnametext) {
		this.roadnametext = roadnametext;
	}
	public String getTraveltime() {
		return traveltime;
	}
	public void setTraveltime(String traveltime) {
		this.traveltime = traveltime;
	}
	public String getEndnodeid() {
		return endnodeid;
	}
	public void setEndnodeid(String endnodeid) {
		this.endnodeid = endnodeid;
	}
}
