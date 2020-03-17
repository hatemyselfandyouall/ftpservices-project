package com.insigma.webtool.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by yinjh on 2019/1/14.
 */
public class TreeUtil {

    /**
     * 将JSONArray数组转为树状结构
     *
     * @param arr 需要转化的数据
     * @param id 数据唯一的标识键值
     * @param pid 父id唯一标识键值
     * @param child 子节点键值
     * @return JSONArray
     */
    public static JSONArray listToTree(JSONArray arr, String id, String pid,
                                       String child) {
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        // 将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        // 遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            // 单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            if(aVal.get(pid) != null) {
                // 在hash中取出key为单条记录中pid的值
                JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
                // 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
                if (hashVP != null) {
                    // 检查是否有child属性
                    if (hashVP.get(child) != null) {
                        JSONArray ch = (JSONArray) hashVP.get(child);
                        ch.add(aVal);
                        hashVP.put(child, ch);
                    } else {
                        JSONArray ch = new JSONArray();
                        ch.add(aVal);
                        hashVP.put(child, ch);
                    }
                } else {
                    r.add(aVal);
                }
            } else {
                r.add(aVal);
            }
        }
        return r;
    }
    
    /**
     * 取第一个机构的idpath
     * @param list
     * @return
     */
    public static String getFirstOrgListIdPath(JSONArray list) {
    	Object firstObj = list.get(0);
    	if(firstObj != null) {
    		//省
    		JSONObject firstObject = (JSONObject)firstObj;
    		Object firstChildrenArr = firstObject.get("children");
    		if(firstChildrenArr != null) {
    			JSONArray firstChildrenArray = (JSONArray)firstChildrenArr;
    			if(firstChildrenArray != null) {
    				//市
    				Object firstChildrenObj = firstChildrenArray.get(0);
    				if(firstChildrenObj != null) {
    					JSONObject firstChildrenObject = (JSONObject)firstChildrenObj;
    					Object secondChildrenArr = firstChildrenObject.get("children");
    					if(secondChildrenArr != null) {
    						JSONArray secondChildrenArray = (JSONArray)secondChildrenArr;
    						//区
    						Object secondChildrenObj =  secondChildrenArray.get(0);
    						if(secondChildrenObj != null) {
    							JSONObject secondChildrenObject = (JSONObject)secondChildrenObj;
    							Object thridChildrenArr = secondChildrenObject.get("children");
    							if(thridChildrenArr != null) {
    								JSONArray thridChildrenArray = (JSONArray)thridChildrenArr;
    								//机构
    								Object thridChildrenObj = thridChildrenArray.get(0);
    								if(thridChildrenObj != null) {
    									JSONObject thridChildrenObject = (JSONObject)thridChildrenObj;
    									Object idpathObj = thridChildrenObject.get("idpath");
    									if(idpathObj != null) {
    										return idpathObj+"";
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	return "";
    }

}
