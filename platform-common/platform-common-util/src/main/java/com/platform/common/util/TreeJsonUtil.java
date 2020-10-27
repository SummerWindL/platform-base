package com.platform.common.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.var;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class TreeJsonUtil {

	
	/**
	 * 将数据库返回的省市区县有序结果集，转成符合前端VUE组件所需的嵌套JSONArray
	 * @param jsonstr
	 * @return
	 */
	public static JSONArray toAreacodeJson(String jsonstr) {

		JSONArray areacodeJarr = new JSONArray ();		//	定义要返回给前端使用的省市区县树状菜单JSONArray
		JSONArray dbAreacodeJarr = JSONArray.fromObject(jsonstr);
		
		JSONObject areacodeJobj = null;
		
		for (int i=0; i<dbAreacodeJarr.size(); i++) {
			JSONObject dbAreacodeJobj = dbAreacodeJarr.getJSONObject(i);		//	取从数据库里返回的省市区县树状菜单 JSONObject
			
			//	不处理ROOT节点
			if (!"000000".equalsIgnoreCase(dbAreacodeJobj.getString("areacode"))) {
				//	定义要返回给前端使用的省市区县树状菜单 JSONObject
				areacodeJobj = new JSONObject ();												
				//	为省市区县树状菜单赋值
				areacodeJobj.put("id", dbAreacodeJobj.getString("areacode"));
				areacodeJobj.put("label", dbAreacodeJobj.getString("areaname"));
				
				//	处理一级菜单节点
				if ("000000".equalsIgnoreCase(dbAreacodeJobj.getString("parentareacode"))) {
					areacodeJarr.add(areacodeJobj);
				}else if ("0000".equalsIgnoreCase(dbAreacodeJobj.getString("parentareacode").substring(2, 6))) {
					//	处理二级菜单节点，将二级菜单填充到当前一级菜单的 children 中
					JSONObject areacodeL1Jobj = areacodeJarr.getJSONObject(areacodeJarr.size()-1);
					
					if (!areacodeL1Jobj.containsKey("children")) {
						areacodeL1Jobj.put("children", new JSONArray ());
					}
					
					JSONArray childrenJarr = areacodeL1Jobj.getJSONArray("children");
					childrenJarr.add(areacodeJobj);
				}else {
					//	处理三级菜单节点，将三级菜单填充到当前二级菜单的 children 中
					JSONArray areacodeL1ChildrenJarr = areacodeJarr.getJSONObject(areacodeJarr.size()-1).getJSONArray("children");
					JSONObject areacodeL2Jobj = areacodeL1ChildrenJarr.getJSONObject(areacodeL1ChildrenJarr.size()-1);
					
					if (!areacodeL2Jobj.containsKey("children")) {
						areacodeL2Jobj.put("children", new JSONArray ());
					}
					
					JSONArray childrenJarr = areacodeL2Jobj.getJSONArray("children");
					childrenJarr.add(areacodeJobj);					
				}
			}
		}
	
		return areacodeJarr;
	}
	

	/**
	 * 将数据库返回的省市区县+医疗机构有序结果集，转成符合前端VUE组件所需的嵌套JSONArray
	 * @param dbJobjArr
	 * @return
	 */
	public static JSONArray toAreacodeHospJson(JSONObject []  dbJobjArr) {

		List areacodeJarr = new ArrayList();		//	定义要返回给前端使用的省市区县+医疗机构树状菜单JSONArray
		Map areacodeJobj = null;
		String curAreacode = null;
		
		for (int i=0; i<dbJobjArr.length; i++) {
			JSONObject dbJobj = dbJobjArr[i];		//	取从数据库里返回的省市区县+医疗机构树状菜单 JSONObject
			JSONObject hospJobj = null;		//	医疗机构叶子节点
			
			if (null == curAreacode || !curAreacode.equalsIgnoreCase(dbJobj.getString("areacode"))) {
				curAreacode = dbJobj.getString("areacode");
				
				//	不处理ROOT节点
				if (!"000000".equalsIgnoreCase(curAreacode)) {
					//	定义要返回给前端使用的省市区县树状菜单 JSONObject
					areacodeJobj = new HashMap ();												
					//	为省市区县树状菜单赋值
					areacodeJobj.put("id", curAreacode);
					areacodeJobj.put("label", dbJobj.getString("areaname"));
					areacodeJobj.put("is_hosp", false);		//	这不是一个医疗机构节点
					areacodeJobj.put("is_area", true);		//	这是一个省市区县节点
					
					//	处理一级菜单节点
					if ("000000".equalsIgnoreCase(dbJobj.getString("parentareacode"))) {
						areacodeJarr.add(areacodeJobj);
					}else if ("0000".equalsIgnoreCase(dbJobj.getString("parentareacode").substring(2, 6))) {
						//	处理二级菜单节点，将二级菜单填充到当前一级菜单的 children 中
						Map areacodeL1Jobj = (Map) areacodeJarr.get(areacodeJarr.size()-1);
						
						if (!areacodeL1Jobj.containsKey("children")) {
							areacodeL1Jobj.put("children", new ArrayList ());
						}
						
						List childrenJarr = (List) areacodeL1Jobj.get("children");
						childrenJarr.add(areacodeJobj);
					}else {
						//	处理三级菜单节点，将三级菜单填充到当前二级菜单的 children 中
						List areacodeL1ChildrenJarr = (List) ((Map)areacodeJarr.get(areacodeJarr.size()-1)).get("children");
						Map areacodeL2Jobj = (Map) areacodeL1ChildrenJarr.get(areacodeL1ChildrenJarr.size()-1);
						
						if (!areacodeL2Jobj.containsKey("children")) {
							areacodeL2Jobj.put("children", new JSONArray ());
						}
						
						List childrenJarr = (List) areacodeL2Jobj.get("children");
						childrenJarr.add(areacodeJobj);					
					}
				}
			}
			
			//	如果当前节点是医疗机构，分两步进行
			//	第一步，构造医疗机构叶子节点
			if (dbJobj.containsKey("hospcode") && !"null".equalsIgnoreCase(dbJobj.getString("hospcode"))) {
				//	定义要返回给前端使用的医疗机构树状菜单节点 JSONObject
				hospJobj = new JSONObject ();												

				//	为省市区县树状菜单赋值
				hospJobj.put("id", dbJobj.getString("hospcode"));
				hospJobj.put("label", String.format(
											"%s【%s】",
											dbJobj.getString("hospname"),
											dbJobj.getString("hospcode")
										)
				);
				
				hospJobj.put("is_hosp", true);		//	这是一个医疗机构节点
				hospJobj.put("is_area", false);		//	这不是一个省市区县节点
				
				if (!areacodeJobj.containsKey("children")) {
					areacodeJobj.put("children", new ArrayList ());
				}
				
				List childrenJarr = (List) areacodeJobj.get("children");
				childrenJarr.add(hospJobj);			
			} 
		}
	
		return JSONArray.fromObject(areacodeJarr);
	}

	/**
	 * 将数据库返回的平台功能菜单有序结果集，转成符合前端VUE组件所需的嵌套JSONArray
	 * @param jsonstr
	 * @return
	 */
	/* public static JSONArray toMenuJson(String jsonstr) {

		JSONArray menuJarr = new JSONArray ();	//	定义要返回给前端使用的功能菜单JSONArray
		JSONArray dbMenuJarr = JSONArray.fromObject(jsonstr);

		Map<String, JSONObject> childrenMap = new HashMap<>();
		
		JSONObject menuJobj = null;
		
		for (int i=0; i<dbMenuJarr.size(); i++) {
			JSONObject dbMenuJobj = dbMenuJarr.getJSONObject(i);		//	取从数据库里返回的功能菜单 JSONObject

			String functionid = dbMenuJobj.getString("functionid");

			//	不处理ROOT节点
			if (!"000000".equalsIgnoreCase(functionid)) {
				//	定义要返回给前端使用的功能菜单 JSONObject
				menuJobj = new JSONObject ();
				//	为功能菜单赋值
				menuJobj.put("title", dbMenuJobj.getString("functionname"));
				
				String icon = dbMenuJobj.getString("functionicon");
				icon = null == icon ? "" : icon;
				menuJobj.put("icon", icon);
				
				menuJobj.put("path", "/");		//	TODO	暂定义为： /
				
				String functionpage = dbMenuJobj.getString("functionpage");
				functionpage = null == functionpage ? "" : functionpage;
				menuJobj.put("name", functionpage);
				
				//	处理一级菜单节点
				if ("000000".equalsIgnoreCase(dbMenuJobj.getString("parentfunctionid"))) {
					menuJarr.add(menuJobj);
				}else {
					//	处理二级菜单节点，将二级菜单填充到当前一级菜单的 children 中
					JSONObject menuL1Jobj = menuJarr.getJSONObject(menuJarr.size()-1);
					
					if (!menuL1Jobj.containsKey("children")) {
						menuL1Jobj.put("children", new JSONArray ());
					}
					
					JSONArray childrenJarr = menuL1Jobj.getJSONArray("children");
					childrenJarr.add(menuJobj);
				}
			}
		}
	
		return menuJarr;
	} */

	public static JSONArray toMenuJson(String jsonstr) {

		JSONArray menuJarr = new JSONArray();	//	定义要返回给前端使用的功能菜单JSONArray
		JSONArray dbMenuJarr = JSONArray.fromObject(jsonstr);

		Map<String, List<Integer>> childrenMap = new HashMap<>();

		for (int i=0; i<dbMenuJarr.size(); i++) {
			JSONObject dbMenuJobj = dbMenuJarr.getJSONObject(i);		//	取从数据库里返回的功能菜单 JSONObject

			String pid = dbMenuJobj.getString("parentfunctionid");
			List<Integer> childrenList = childrenMap.get(pid);
			if (CollectionUtils.isEmpty(childrenList)) {
				childrenList = new ArrayList<>();
				childrenMap.put(pid, childrenList);
			}
			childrenList.add(i);
		}
		menuJarr = constructMenuChildren(dbMenuJarr, childrenMap, "000000");

		return menuJarr;
	}

	private static JSONArray constructMenuChildren(JSONArray dbMenuArr, Map<String, List<Integer>> childrenMap, String parentfunctionid) {

		List<Integer> childrenList = childrenMap.get(parentfunctionid);
		JSONArray childrenArray = null;

		if (CollectionUtils.isEmpty(childrenList)) {
		    return childrenArray;
        }

		childrenArray = new JSONArray();
		for (Integer i : childrenList) {

			JSONObject dbMenuObj = dbMenuArr.getJSONObject(i);

			String functionid = dbMenuObj.getString("functionid");

			JSONObject menuJobj = new JSONObject ();
			//	为功能菜单赋值
			menuJobj.put("title", dbMenuObj.getString("functionname"));

			String icon = dbMenuObj.getString("functionicon");
			icon = null == icon ? "" : icon;
			menuJobj.put("icon", icon);

			menuJobj.put("path", "/");		//	TODO	暂定义为： /

			String functionpage = dbMenuObj.getString("functionpage");
			functionpage = null == functionpage ? "" : functionpage;
			menuJobj.put("name", functionpage);

			// 新增functionlevel，pcsystype
			String functionlevel = dbMenuObj.getString("functionlevel");
			functionlevel = null == functionlevel ? "" : functionlevel;
			menuJobj.put("level", functionlevel);

			String pcsystype = dbMenuObj.getString("pcsystype");
			pcsystype = null == pcsystype ? "" : pcsystype;
			menuJobj.put("pcsystype", pcsystype);

			JSONArray children = constructMenuChildren(dbMenuArr, childrenMap, functionid);
			if (children != null) {
				menuJobj.put("children", children);
			}

			childrenArray.add(menuJobj);
		}

		System.out.println(parentfunctionid + "," + childrenArray);

		return childrenArray;
	}
	
}
