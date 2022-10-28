<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="share/taglib.jsp" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">

    <title>JSON 在线编辑器, edit and format JSON online </title>
	<meta name="description" content="JSON Editor Online is a web-based tool to view, edit, and format JSON. It shows your data side by side in a clear, editable treeview and in a code editor.">
    <meta name="keywords" content="json, editor, formatter, online, format, parser, json editor, json editor online, online json editor, javascript, javascript object notation, tools, tool, json tools, treeview, open source, free, json parser, json parser online, json formatter, json formatter online, online json formatter, online json parser, format json online">
	<meta name="Copyright" content="sojson.com" />
    <link rel="stylesheet" type="text/css" href="${ctx}/pub/plugins/jsonEdit/css/app-min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/pub/plugins/jsonEdit/css/jsoneditor-min.css">
    <script type="text/javascript" src="${ctx}/pub/plugins/jsonEdit/js/jsoneditor-min.js"></script>
    <script type="text/javascript" src="${ctx}/pub/plugins/jsonEdit/js/ace-min.js"></script>
    <script type="text/javascript" src="${ctx}/pub/plugins/jsonEdit/js/app-min.js"></script>
</head>

<body>

<div id="header">
    <a href="#" class="header">
        <img alt="JSON Editor Online" title="JSON Editor Online" src="${ctx}/pub/plugins/jsonEdit/img/jsonEdit_logo.png" id="logo">
    </a>

    <div id="menu">
        <ul>
            <li>
                <a id="clear" title="Clear contents">清空</a>
            </li>
            <li>
			
                <a id="open" title="Open file from disk">
                    	打开
                    <span id="openMenuButton" title="Open file from disk or url">
                    &#x25BC;
                    </span>
                </a>
                <ul id="openMenu">
                   <li>
                        <a id="menuOpenFile" title="Open file from disk">打开文件</a>
                    </li>
                    <li>
                        <a id="menuOpenUrl" title="Open file from url">加载URL</a>
                    </li>
                </ul>
            </li>
            <li>
                <a id="save" title="Save file to disk">保存</a>
            </li>
        </ul>
    </div>

</div>

<div id="auto">
    <div id="contents">
        <div id="codeEditor"></div>

        <div id="splitter">
            <div id="buttons">
                <div>
                    <button id="toTree" class="convert" title="Copy code to tree editor">
                        <div class="convert-right"></div>
                    </button>
                </div>
                <div>
                    <button id="toCode" class="convert" title="Copy tree to code editor">
                        <div class="convert-left"></div>
                    </button>
                </div>
            </div>
            <div id="drag">
            </div>
        </div>

        <div id="treeEditor"></div>

        <script type="text/javascript">
            app.load();
            app.resize();
        </script>

        
    </div>
</div>

<div id="footer">
    <div id="footer-inner">
        <a href="#" class="footer">VST在线JSON编辑工具</a>
        &bull;
        <a href="#" target="_blank" class="footer">Copyright 2011-2017</a>
    </div>
</div>

<script type="text/javascript">
    app.resize();
</script>

<script type="text/javascript" src="${ctx}/pub/plugins/jsonEdit/js/jsonlint.js"></script>

</body>
</html>

