package com.vst.common.tools.http;

/**
 * @author weiwei
 * @date 2015-6-25 下午06:34:32
 * @description
 * @version
 */
public class Request{

	/**
	 * 请求地址
	 */
	private String url;
	
	/**
	 * 请求方法
	 */
	private String method;
	
	/**
	 * 页码编码
	 */
	private String charset;
	
	/**
	 * 域名
	 */
	private String domain;
	
	/**
	 * 来源
	 */
	private String referer;
	
	/**
	 * 请求代理
	 */
	private String userAgent;
	
	/**
	 * 接受类型
	 */
	private String accept;
	
	/**
	 * 请求数据类型
	 */
	private String xRequestWith;
	
	/**
	 * 设置cookie
	 */
	private String cookie;
	
	public Request() {
	
	}

	public Request(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Request request = (Request) o;
		return url.equals(request.url);
	}

	public Request setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public Request setMethod(String method) {
		this.method = method;
		return this;
	}

	public String getCharset() {
		return charset;
	}

	public Request setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public String getDomain() {
		return domain;
	}

	public Request setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	public String getReferer() {
		return referer;
	}

	public Request setReferer(String referer) {
		this.referer = referer;
		return this;
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public Request setUserAgent(String userAgent) {
		this.userAgent = userAgent;
		return this;
	}
	
	public String getAccept() {
		return accept;
	}

	public Request setAccept(String accept) {
		this.accept = accept;
		return this;
	}
	
	public String getxRequestWith() {
		return xRequestWith;
	}

	public Request setxRequestWith(String xRequestWith) {
		this.xRequestWith = xRequestWith;
		return this;
	}
	
	public String getCookie() {
		return cookie;
	}

	public Request setCookie(String cookie) {
		this.cookie = cookie;
		return this;
	}

	@Override
	public String toString() {
		return "Request [accept=" + accept + ", charset=" + charset
				+ ", cookie=" + cookie + ", domain=" + domain + ", method="
				+ method + ", referer=" + referer + ", url=" + url
				+ ", userAgent=" + userAgent + ", xRequestWith=" + xRequestWith
				+ "]";
	}
}
