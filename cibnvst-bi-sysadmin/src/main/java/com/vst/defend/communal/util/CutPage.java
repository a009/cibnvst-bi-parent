package com.vst.defend.communal.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vst.common.pojo.VstSysButton;
import com.vst.common.tools.io.ToolsIO;

/**
 * @author weiwei
 * @date 2014-7-10 下午04:07:09
 * @description 分页类
 * @version
 */
public class CutPage implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7675039328473897956L;

	/**
	 * 当前页，默认是1
	 */
	private int _currPage = 1;

	/**
	 * 每页显示数量，默认是10
	 */
	private int _singleCount = 10;

	/**
	 * 总页数
	 */
	private int _totalPages;

	/**
	 * 总记录数
	 */
	private int _totalResults;

	/**
	 * 返回结果
	 */
	private List<Object> _dataList;

	/**
	 * 前台传过来的参数
	 */
	private Map<String, String> _param = new HashMap<String, String>();

	/**
	 * 前台传过来的查询参数
	 */
	private Map<String, String> _queryParam = new HashMap<String, String>();

	/**
	 * 传往后台的参数
	 */
	private Map<String, Object> _castParam = new HashMap<String, Object>();
	
	/**
	 * 按钮list
	 */
	private List<VstSysButton> _buttonList;

	/**
	 * 是否点击查询
	 */
	private boolean _isQuery;

	public int get_currPage() {
		return _currPage <= 0 ? 1 : _currPage;
	}

	public void set_currPage(int currPage) {
		_currPage = currPage <= 0 ? 1 : currPage;
	}

	public int get_singleCount() {
		return _singleCount;
	}

	public void set_singleCount(int singleCount) {
		_singleCount = singleCount;
	}

	public int get_totalPages() {
		return _totalPages;
	}

	public void set_totalPages(int totalPages) {
		if (_currPage > totalPages) {
			_currPage = totalPages;
		} else if (_currPage < 1) {
			_currPage = 1;
		}
		_totalPages = totalPages;
	}

	public int get_totalResults() {
		return _totalResults;
	}

	public void set_totalResults(int totalResults) {
		if (totalResults % _singleCount == 0) {
			_totalPages = totalResults / _singleCount;
		} else {
			_totalPages = totalResults / _singleCount + 1;
		}
		_totalResults = totalResults;
	}

	public List<Object> get_dataList() {
		return _dataList;
	}

	public void set_dataList(List<Object> dataList) {
		_dataList = dataList;
	}

	public Map<String, String> get_param() {
		return _param;
	}

	public void set_param(Map<String, String> param) {
		_param = param;
	}

	public Map<String, Object> get_castParam() {
		if (!_param.isEmpty()) {
			_castParam.putAll(_param);
		}
		
		return _castParam;
	}

	public void set_castParam(Map<String, Object> castParam) {
		_castParam = castParam;
	}
	
	public Map<String, Object> get_castQueryParam() {
		Map<String, Object> result = null;
		
		if (!_queryParam.isEmpty()) {
			result = new HashMap<String, Object>(_queryParam.size());
			for(String key : _queryParam.keySet()){
				result.put(key, _queryParam.get(key).replace("'", "\\'").replace("#", "").replace("$", ""));
			}
		}
		
		return result;
	}

	public boolean is_isQuery() {
		return _isQuery;
	}

	public void set_isQuery(boolean isQuery) {
		_isQuery = isQuery;
	}

	public Map<String, String> get_queryParam() {
		return _queryParam;
	}

	public void set_queryParam(Map<String, String> queryParam) {
		_queryParam = queryParam;
	}
	
	/**
	 * 深克隆
	 */
	@Override
	public CutPage clone(){
		// 输出字节流
		ByteArrayOutputStream out = null;
		// 输出对象流
		ObjectOutputStream oos = null;
		// 输入字节流
		ByteArrayInputStream in = null;
		// 输入对象流
		ObjectInputStream ois = null;
		try {
			out = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(out);
			oos.writeObject(this);
			
			in = new ByteArrayInputStream(out.toByteArray());
			ois = new ObjectInputStream(in);
			
			return (CutPage) ois.readObject();
		} catch (Exception e) {
		} finally {
			// 关流
			ToolsIO.closeStream(ois, in, oos, out);
		}
		return null;
	}

	public List<VstSysButton> get_buttonList() {
		return _buttonList;
	}

	public void set_buttonList(List<VstSysButton> buttonList) {
		_buttonList = buttonList;
	}

	@Override
	public String toString() {
		return "CutPage [_buttonList=" + _buttonList + ", _castParam="
				+ _castParam + ", _currPage=" + _currPage + ", _dataList="
				+ _dataList + ", _isQuery=" + _isQuery + ", _param=" + _param
				+ ", _queryParam=" + _queryParam + ", _singleCount="
				+ _singleCount + ", _totalPages=" + _totalPages
				+ ", _totalResults=" + _totalResults + "]";
	}
}
