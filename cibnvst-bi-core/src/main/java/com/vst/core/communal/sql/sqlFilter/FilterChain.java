package com.vst.core.communal.sql.sqlFilter;

import java.io.Serializable;

import org.json.simple.JSONObject;

/**
 * @author weiwei(joslyn)
 * @date 2017-9-26 下午02:14:51
 * @decription 筛选拦截器
 * @version 
 */
public class FilterChain implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1227621812016024407L;

	/**
	 * 拦截配置的id
	 */
	private String sqlId;
	
	/**
	 * 过滤条件集合
	 */
	private Filter filter;
	
	/**
	 * 操作行为集合
	 */
	private Action action;
	
	/**
	 * 构造器
	 */
	public FilterChain(String sqlId, Filter filter, Action action) {
		this.sqlId = sqlId;
		this.filter = filter;
		this.action = action;
	}

	/**
	 * 拦截方法
	 * @param json
	 * @return 通过拦截器校验返回true，否则返回false
	 */
	public boolean doChain(JSONObject json){
		boolean flag = true;
		if(filter != null){
			flag = filter.doFilter(json);
			if(flag){ // 如果校验通过，再查看处理类型
				if(filter.getYesType() == 1){// 如果处理类型是：匹配上后通过
					if(action != null && action.getOperateType() != 1){// 如果动作配置不为空，并且不等于1
						action.doAction(json);
					}
				}else{// 如果处理类型是：匹配上后不通过
					flag = false;
				}
			}else{
				if(filter.getNoType() == 1){// 如果处理类型是：未匹配上后通过
					flag = true;
				}
			}
		}
		return flag;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
}
