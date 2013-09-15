package com.roadpress.helper;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Handler;

public class XmlHttpHelper implements Runnable{
	private ParserProcess parserProcess;
	private String  apiUrl;
	private XmlPullParserFactory factory;
	private XmlPullParser parser;
	private URL    url;
	private Handler handler;
	private Thread thread;
	
	public interface ParserProcess {
		void onRun(XmlPullParser parser) throws Exception;
	}
	
	private void init() {
		try {
			factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			url     = new URL(apiUrl);
			thread  = new Thread(this);
		} catch (XmlPullParserException xe) {
			xe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public XmlHttpHelper(String url, Handler handler){				
		apiUrl = url;
		this.handler = handler;
		init();
	}
	
	public void setParserProcess(ParserProcess parserProcess) {
		this.parserProcess = parserProcess;
	}

	public void run() {
		try {
			parser.setInput(url.openStream(), "utf-8");
			parserProcess.onRun(parser);
			handler.sendEmptyMessage(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void execute() {
		thread.start();
	}
}
