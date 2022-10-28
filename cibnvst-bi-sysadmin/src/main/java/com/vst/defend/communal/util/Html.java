package com.vst.defend.communal.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei
 * @date 2015-6-25 下午04:59:18
 * @description 
 * @version	
 */
public class Html {

	/**
	 * 页面dom元素
	 */
	private Document document;

	/**
	 * 构造器
	 * @param html
	 */
	private Html(String html) {
		document = Jsoup.parse(html);
	}
	
	/**
	 * 创建html页面
	 * @param document
	 * @return
	 */
	public static Html create(String html){
		if(html == null){
			throw new NullPointerException("page content is null.");
		}
		return new Html(html.replaceAll("&nbsp;", ""));
	}
	
	/**
	 * 获取dom元素
	 * @return
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * 获取符合条件的元素的文本域内容
	 * @param css
	 * @return
	 */
	public List<String> selectAllText(String css){
		List<String> result = new ArrayList<String>();
		if(!ToolsString.isEmpty(css)){
			Elements es = document.select(css); 
			for(int i = 0; i < es.size(); i++){
				String temp = es.get(i).text();
				if(!ToolsString.isEmpty(temp)){
					result.add(temp);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取所匹配的元素属性值
	 * @param css
	 * @param attrName
	 * @return
	 */
	public List<String> selectAllAttrValue(String css, String attrName){
		List<String> result = new ArrayList<String>();
		
		if(!ToolsString.isEmpty(css) && !ToolsString.isEmpty(attrName)){
			Elements es = document.select(css); 
			for(int i = 0; i < es.size(); i++){
				String temp = es.get(i).attr(attrName);
				if(!ToolsString.isEmpty(temp)){
					result.add(temp);
				}
			}
		}
		
		return result;
	}

	@Override
	public String toString() {
		return document.toString();
	}
}
