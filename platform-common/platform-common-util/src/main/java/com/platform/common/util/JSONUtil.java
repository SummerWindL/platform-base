/**
 * 2015年10月13日 下午5:57:32
 */
package com.platform.common.util;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class JSONUtil {

	public static JSONObject parseXml(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer ();
		JSON json = xmlSerializer.read(xml);
		return (JSONObject)json;
	}

	public static JSONArray parseJSONArrayFromXml(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer ();
		JSON json = xmlSerializer.read(xml);
		return (JSONArray)json;
	}


	/**
	 * 将字符串的JSON数组，转成字符串列表
	 * 主要用于病症JSON数组
	 * @param jarrStr
	 * @return
	 */
	public static List<String> convert2StringList(String jarrStr){
		JSONArray jarr = JSONArray.fromObject(jarrStr);
		List <String> strList = new ArrayList<String>();
		for (int i=0; i<jarr.size(); i++)
		{
			strList.add(jarr.getString(i));
		}
		return strList;
	}

	public static void main (String [] args) {
		String xml = "<Request>\n" +
				" <BODY>\n" +
				"<Item>\n" +
				"<usercode>1845</usercode>\n" +
				"</Item>\n" +
				"<Item>\n" +
				"<usercode>1681</usercode>\n" +
				"<username>徐权</username>\n" +
				"</Item>\n" +
				"<Item>\n" +
				"<usercode>1798</usercode>\n" +
				"<username>周艳霞</username>\n" +
				"</Item>\n" +
				" </BODY>\n" +
				"</Request>";

		System.out.println(JSONUtil.parseXml(xml));
	}

}
