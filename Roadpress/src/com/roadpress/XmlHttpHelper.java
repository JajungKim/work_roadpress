package com.roadpress;

import org.xmlpull.v1.XmlPullParser;

public class XmlHttpHelper implements Runnable{
	Process process;
	String url;
	String errormessage;
	int errorcode;
	XmlPullParser parser;
	
	enum XmlTag {
		datacount(0),
		body(1),
		context(2);
		
		public final int code;
		
		private XmlTag(int code){
			this.code = code;
		}
	}

	class XmlTagx {
		public static final String 
		datacount = "datacount",
		body = "body",
		context = "context";
	}
	
	
	interface Process {
		void onRun(XmlPullParser parser) throws Exception;
	}
	
	public XmlHttpHelper(String url){
		String tagName = "";
		switch(XmlTag.valueOf(tagName)){
		case datacount:break;
		
		}
		
		
		this.url = url;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			process.onRun(parser);
		} catch (Exception e) {
			// 
			errormessage = "";
			errorcode = -1;
		}
		
	}
	
	// url
	// abstract process();
	// setProcess(Process process);
	
	public static void main(String[] args){
		XmlHttpHelper xmlHelper = new XmlHttpHelper("http://~~~");
		xmlHelper.setProcess(new Process() {
			
			@Override
			public void onRun(XmlPullParser parser) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		
		xmlHelper.run();
	}
	
}
