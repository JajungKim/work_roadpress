package com.roadpress.manager;

import java.util.List;
import java.util.Map;

public interface XmlManager {
	public void parsing() throws Exception;
	public List<Map<String, String>> getDataList();
}
