function QueryParams() {}
function Notify() {
	this.dom = {};
	var e = this;
	jsoneditor.util.addEventListener(document, "keydown",
	function(t) {
		e.onKeyDown(t)
	})
}
function Splitter(e) {
	if (!e || !e.container) throw new Error("params.container undefined in Splitter constructor");
	var t = this;
	jsoneditor.util.addEventListener(e.container, "mousedown",
	function(e) {
		t.onMouseDown(e)
	}),
	this.container = e.container,
	this.snap = Number(e.snap) || 200,
	this.width = void 0,
	this.value = void 0,
	this.onChange = e.change ? e.change: function() {},
	this.params = {}
}
QueryParams.prototype.getQuery = function() {
	for (var e = window.location.search.substring(1), t = e.split("&"), i = {},
	o = 0, n = t.length; n > o; o++) {
		var s = t[o].split("=");
		if (s.length == 2) {
			var r = decodeURIComponent(s[0]),
			a = decodeURIComponent(s[1]);
			i[r] = a
		}
	}
	return i
},
QueryParams.prototype.setQuery = function(e) {
	var t = "";
	for (var i in e) if (e.hasOwnProperty(i)) {
		var o = e[i];
		void 0 != o && (t.length && (t += "&"), t += encodeURIComponent(i), t += "=", t += encodeURIComponent(e[i]))
	}
	window.location.search = t.length ? "#" + t: ""
},
QueryParams.prototype.getValue = function(e) {
	var t = this.getQuery();
	return t[e]
},
QueryParams.prototype.setValue = function(e, t) {
	var i = this.getQuery();
	i[e] = t,
	this.setQuery(i)
};
var ajax = function() {
	function e(e, t, i, o, n) {
		try {
			var s = new XMLHttpRequest;
			if (s.onreadystatechange = function() {
				s.readyState == 4 && n(s.responseText, s.status)
			},
			s.open(e, t, !0), o) for (var r in o) o.hasOwnProperty(r) && s.setRequestHeader(r, o[r]);
			s.send(i)
		} catch(a) {
			n(a, 0)
		}
	}
	function t(t, i, o) {
		e("GET", t, null, i, o)
	}
	function i(t, i, o, n) {
		e("POST", t, i, o, n)
	}
	return {
		fetch: e,
		get: t,
		post: i
	}
} (),
FileRetriever = function(e) {
	e = e || {},
	this.options = {
		maxSize: e.maxSize != void 0 ? e.maxSize: 1048576,
		html5: e.html5 != void 0 ? e.html5: !0
	},
	this.timeout = Number(e.timeout) || 3e4,
	this.headers = {
		Accept: "application/json"
	},
	this.scriptUrl = e.scriptUrl || "fileretriever.php",
	this.notify = e.notify || void 0,
	this.defaultFilename = "document.json",
	this.dom = {}
};
FileRetriever.prototype._hide = function(e) {
	e.style.visibility = "hidden",
	e.style.position = "absolute",
	e.style.left = "-1000px",
	e.style.top = "-1000px",
	e.style.width = "0",
	e.style.height = "0"
},
FileRetriever.prototype.remove = function() {
	var e = this.dom;
	for (var t in e) if (e.hasOwnProperty(t)) {
		var i = e[t];
		i.parentNode && i.parentNode.removeChild(i)
	}
	this.dom = {}
},
FileRetriever.prototype._getFilename = function(e) {
	return e ? e.replace(/^.*[\\\/]/, "") : ""
},
FileRetriever.prototype.setUrl = function(e) {
	this.url = e
},
FileRetriever.prototype.getFilename = function() {
	return this.defaultFilename
},
FileRetriever.prototype.getUrl = function() {
	return this.url
},
FileRetriever.prototype.loadUrl = function(e, t) {
	this.setUrl(e);
	var i = void 0;
	this.notify && (i = this.notify.showNotification("loading url..."));
	var o = this,
	n = function(e, n) {
		t && (t(e, n), t = void 0),
		o.notify && i && (o.notify.removeMessage(i), i = void 0)
	},
	s = this.scriptUrl;
	ajax.get(e, o.headers,
	function(t, i) {
		if (200 == i) n(null, t);
		else {
			var r, a = s + "?url=" + encodeURIComponent(e);
			ajax.get(a, o.headers,
			function(t, i) {
				200 == i ? n(null, t) : 404 == i ? (console.log('Error: url "' + e + '" not found', i, t), r = new Error('Error: url "' + e + '" not found'), n(r, null)) : (console.log('Error: failed to load url "' + e + '"', i, t), r = new Error('Error: failed to load url "' + e + '"'), n(r, null))
			})
		}
	}),
	setTimeout(function() {
		n(new Error("Error loading url (time out)"))
	},
	this.timeout)
},
FileRetriever.prototype.loadFile = function(e) {
	var t = void 0,
	i = this,
	o = function() {
		i.notify && !t && (t = i.notify.showNotification("loading file...")),
		setTimeout(function() {
			n(new Error("Error loading url (time out)"))
		},
		i.timeout)
	},
	n = function(o, n) {
		e && (e(o, n), e = void 0),
		i.notify && t && (i.notify.removeMessage(t), t = void 0)
	},
	s = "fileretriever-upload-" + Math.round(Math.random() * 1e15),
	r = document.createElement("iframe");
	r.name = s,
	i._hide(r),
	r.onload = function() {
		var e = r.contentWindow.document.body.innerHTML;
		if (e) {
			var t = i.scriptUrl + "?id=" + e + "&filename=" + i.getFilename();
			ajax.get(t, i.headers,
			function(e, t) {
				if (200 == t) n(null, e);
				else {
					var o = new Error("Error loading file " + i.getFilename());
					n(o, null)
				}
			})
		}
	},
	document.body.appendChild(r);
	var a = navigator.appName == "Microsoft Internet Explorer";
	if (a) this.prompt({
		title: "Open file",
		titleSubmit: "Open",
		inputType: "file",
		inputName: "file",
		formAction: this.scriptUrl,
		formMethod: "POST",
		formTarget: s,
		callback: function(e) {
			e && o()
		}
	});
	else {
		var l = document.createElement("form");
		l.action = this.scriptUrl,
		l.method = "POST",
		l.enctype = "multipart/form-data",
		l.target = s,
		this._hide(l);
		var d = document.createElement("input");
		d.type = "file",
		d.name = "file",
		d.onchange = function() {
			o(),
			setTimeout(function() {
				var e = d.value;
				if (e.length) if (i.options.html5 && window.File && window.FileReader) {
					var t = d.files[0],
					o = new FileReader;
					o.onload = function(e) {
						var t = e.target.result;
						n(null, t)
					},
					o.readAsText(t)
				} else l.submit();
				else n(null, null)
			},
			0)
		},
		l.appendChild(d),
		document.body.appendChild(l),
		setTimeout(function() {
			d.click()
		},
		0)
	}
},
FileRetriever.prototype.loadUrlDialog = function(e) {
	var t = this;
	this.prompt({
		title: "Open url",
		titleSubmit: "Open",
		inputType: "text",
		inputName: "url",
		inputDefault: this.getUrl(),
		callback: function(i) {
			i ? t.loadUrl(i, e) : e()
		}
	})
},
FileRetriever.prototype.prompt = function(e) {
	var t = function() {
		p.parentNode && p.parentNode.removeChild(p),
		n.parentNode && n.parentNode.removeChild(n),
		jsoneditor.util.removeEventListener(document, "keydown", o)
	},
	i = function() {
		t(),
		e.callback && e.callback(null)
	},
	o = jsoneditor.util.addEventListener(document, "keydown",
	function(e) {
		e = e || window.event;
		var t = e.which || e.keyCode;
		27 == t && (i(), jsoneditor.util.preventDefault(e), jsoneditor.util.stopPropagation(e))
	}),
	n = document.createElement("div");
	n.className = "fileretriever-overlay",
	document.body.appendChild(n);
	var s = document.createElement("form");
	s.className = "fileretriever-form",
	s.target = e.formTarget || "",
	s.action = e.formAction || "",
	s.method = e.formMethod || "POST",
	s.enctype = "multipart/form-data",
	s.encoding = "multipart/form-data",
	s.onsubmit = function() {
		return a.value ? (setTimeout(function() {
			t()
		},
		0), e.callback && e.callback(a.value), e.formAction != void 0 && e.formMethod != void 0) : (alert("Enter a " + e.inputName + " first..."), !1)
	};
	var r = document.createElement("div");
	r.className = "fileretriever-title",
	r.appendChild(document.createTextNode(e.title || "Dialog")),
	s.appendChild(r);
	var a = document.createElement("input");
	a.className = "fileretriever-field",
	a.type = e.inputType || "text",
	a.name = e.inputName || "text",
	a.value = e.inputDefault || "";
	var l = document.createElement("div");
	l.className = "fileretriever-contents",
	l.appendChild(a),
	s.appendChild(l);
	var d = document.createElement("input");
	d.className = "fileretriever-cancel",
	d.type = "button",
	d.value = e.titleCancel || "Cancel",
	d.onclick = i;
	var h = document.createElement("input");
	h.className = "fileretriever-submit",
	h.type = "submit",
	h.value = e.titleSubmit || "Ok";
	var c = document.createElement("div");
	c.className = "fileretriever-buttons",
	c.appendChild(d),
	c.appendChild(h),
	s.appendChild(c);
	var u = document.createElement("div");
	u.className = "fileretriever-border",
	u.appendChild(s);
	var p = document.createElement("div");
	p.className = "fileretriever-background",
	p.appendChild(u),
	document.body.appendChild(p),
	a.focus(),
	a.select()
},
FileRetriever.prototype.saveFile = function(e, t) {
	var i = void 0;
	this.notify && (i = this.notify.showNotification("saving file..."));
	var o = this,
	n = function(e) {
		t && (t(e), t = void 0),
		o.notify && i && (o.notify.removeMessage(i), i = void 0)
	},
	s = document.createElement("a");
	this.options.html5 && s.download != void 0 ? (s.style.display = "none", s.href = "data:application/json;charset=utf-8," + encodeURIComponent(e), s.download = this.getFilename(), document.body.appendChild(s), s.click(), document.body.removeChild(s), n()) : e.length < this.options.maxSize ? ajax.post(o.scriptUrl, e, o.headers,
	function(e, t) {
		if (200 == t) {
			var i = document.createElement("iframe");
			i.src = o.scriptUrl + "?id=" + e + "&filename=" + o.getFilename(),
			o._hide(i),
			document.body.appendChild(i),
			n()
		} else n(new Error("Error saving file"))
	}) : n(new Error("Maximum allowed file size exceeded (" + this.options.maxSize + " bytes)")),
	setTimeout(function() {
		n(new Error("Error saving file (time out)"))
	},
	this.timeout)
},
Notify.prototype.showNotification = function(e) {
	return this.showMessage({
		type: "notification",
		message: e,
		closeButton: !1
	})
},
Notify.prototype.showError = function(e) {
	return this.showMessage({
		type: "error",
		message: e.message || e.toString(),
		closeButton: !0
	})
},
Notify.prototype.showMessage = function(e) {
	var t = this.dom.frame;
	if (!t) {
		var i = 500,
		o = 5,
		n = document.body.offsetWidth || window.innerWidth;
		t = document.createElement("div"),
		t.style.position = "absolute",
		t.style.left = (n - i) / 2 + "px",
		t.style.width = i + "px",
		t.style.top = o + "px",
		t.style.zIndex = "999",
		document.body.appendChild(t),
		this.dom.frame = t
	}
	var s = e.type || "notification",
	r = e.closeButton !== !1,
	a = document.createElement("div");
	a.className = s,
	a.type = s,
	a.closeable = r,
	a.style.position = "relative",
	t.appendChild(a);
	var l = document.createElement("table");
	l.style.width = "100%",
	a.appendChild(l);
	var d = document.createElement("tbody");
	l.appendChild(d);
	var h = document.createElement("tr");
	d.appendChild(h);
	var c = document.createElement("td");
	if (c.innerHTML = e.message || "", h.appendChild(c), r) {
		var u = document.createElement("td");
		u.style.textAlign = "right",
		u.style.verticalAlign = "top",
		h.appendChild(u);
		var p = document.createElement("button");
		p.innerHTML = "&times;",
		p.title = "Close message (ESC)",
		u.appendChild(p);
		var m = this;
		p.onclick = function() {
			m.removeMessage(a)
		}
	}
	return a
},
Notify.prototype.removeMessage = function(e) {
	var t = this.dom.frame;
	if (!e && t) {
		for (var i = t.firstChild; i && !i.closeable;) i = i.nextSibling;
		i && i.closeable && (e = i)
	}
	e && e.parentNode == t && e.parentNode.removeChild(e),
	t && t.childNodes.length == 0 && (t.parentNode.removeChild(t), delete this.dom.frame)
},
Notify.prototype.onKeyDown = function(e) {
	e = e || window.event;
	var t = e.which || e.keyCode;
	27 == t && (this.removeMessage(), jsoneditor.util.preventDefault(e), jsoneditor.util.stopPropagation(e))
},
Splitter.prototype.onMouseDown = function(e) {
	var t = this,
	i = e.which ? e.which == 1 : e.button == 1;
	i && (jsoneditor.util.addClassName(this.container, "active"), this.params.mousedown || (this.params.mousedown = !0, this.params.mousemove = jsoneditor.util.addEventListener(document, "mousemove",
	function(e) {
		t.onMouseMove(e)
	}), this.params.mouseup = jsoneditor.util.addEventListener(document, "mouseup",
	function(e) {
		t.onMouseUp(e)
	}), this.params.screenX = e.screenX, this.params.changed = !1, this.params.value = this.getValue()), jsoneditor.util.preventDefault(e), jsoneditor.util.stopPropagation(e))
},
Splitter.prototype.onMouseMove = function(e) {
	if (this.width != void 0) {
		var t = e.screenX - this.params.screenX,
		i = this.params.value + t / this.width;
		i = this.setValue(i),
		i != this.params.value && (this.params.changed = !0),
		this.onChange(i)
	}
	jsoneditor.util.preventDefault(e),
	jsoneditor.util.stopPropagation(e)
},
Splitter.prototype.onMouseUp = function(e) {
	if (jsoneditor.util.removeClassName(this.container, "active"), this.params.mousedown) {
		jsoneditor.util.removeEventListener(document, "mousemove", this.params.mousemove),
		jsoneditor.util.removeEventListener(document, "mouseup", this.params.mouseup),
		this.params.mousemove = void 0,
		this.params.mouseup = void 0,
		this.params.mousedown = !1;
		var t = this.getValue();
		this.params.changed || (0 == t && (t = this.setValue(.2), this.onChange(t)), 1 == t && (t = this.setValue(.8), this.onChange(t)))
	}
	jsoneditor.util.preventDefault(e),
	jsoneditor.util.stopPropagation(e)
},
Splitter.prototype.setWidth = function(e) {
	this.width = e
},
Splitter.prototype.setValue = function(e) {
	e = Number(e),
	this.width != void 0 && this.width > this.snap && (e < this.snap / this.width && (e = 0), e > (this.width - this.snap) / this.width && (e = 1)),
	this.value = e;
	try {
		localStorage.splitterValue = e
	} catch(t) {
		console.log(t)
	}
	return e
},
Splitter.prototype.getValue = function() {
	var e = this.value;
	if (void 0 == e) try {
		localStorage.splitterValue != void 0 && (e = Number(localStorage.splitterValue), e = this.setValue(e))
	} catch(t) {
		console.log(t)
	}
	return void 0 == e && (e = this.setValue(.5)),
	e
};
var treeEditor = null,
codeEditor = null,
app = {};
app.CodeToTree = function() {
	try {
		treeEditor.set(codeEditor.get())
	} catch(e) {
		app.notify.showError(app.formatError(e))
	}
},
app.treeToCode = function() {
	try {
		codeEditor.set(treeEditor.get())
	} catch(e) {
		app.notify.showError(app.formatError(e))
	}
},
app.load = function() {
	try {
		app.notify = new Notify,
		app.retriever = new FileRetriever({
			scriptUrl: "fileretriever.php",
			notify: app.notify
		});
		var e = {
			array: [1, 2, 3],
			"boolean": !0,
			"null": null,
			number: 123,
			object: {
				a: "b",
				c: "d",
				e: "f"
			},
			string: "Hello World"
		};
		if (window.QueryParams) {
			var t = new QueryParams,
			i = t.getValue("url");
			i && (e = {},
			app.openUrl(i));
			var o = t.getValue("referrer");
			"chrome_app" == o && ajax.get("chrome_app_counter.html?t=" + (new Date).valueOf(), {},
			function() {})
		}
		app.lastChanged = void 0;
		var n = document.getElementById("codeEditor");
		codeEditor = new jsoneditor.JSONEditor(n, {
			mode: "code",
			change: function() {
				app.lastChanged = codeEditor
			},
			error: function(e) {
				app.notify.showError(app.formatError(e))
			}
		}),
		codeEditor.set(e),
		n = document.getElementById("treeEditor"),
		treeEditor = new jsoneditor.JSONEditor(n, {
			mode: "tree",
			change: function() {
				app.lastChanged = treeEditor
			},
			error: function(e) {
				app.notify.showError(app.formatError(e))
			}
		}),
		treeEditor.set(e),
		app.splitter = new Splitter({
			container: document.getElementById("drag"),
			change: function() {
				app.resize()
			}
		});
		var s = document.getElementById("toTree");
		s.onclick = function() {
			this.focus(),
			app.CodeToTree()
		};
		var r = document.getElementById("toCode");
		r.onclick = function() {
			this.focus(),
			app.treeToCode()
		},
		jsoneditor.util.addEventListener(window, "resize", app.resize);
		var a = document.getElementById("clear");
		a.onclick = app.clearFile;
		var l = document.getElementById("menuOpenFile");
		l.onclick = function(e) {
			app.openFile(),
			jsoneditor.util.stopPropagation(e),
			jsoneditor.util.preventDefault(e)
		};
		var d = document.getElementById("menuOpenUrl");
		d.onclick = function(e) {
			app.openUrl(),
			jsoneditor.util.stopPropagation(e),
			jsoneditor.util.preventDefault(e)
		};
		var h = document.getElementById("save");
		h.onclick = app.saveFile,
		codeEditor.focus(),
		document.body.spellcheck = !1
	} catch(c) {
		app.notify.showError(c)
	}
},
app.openCallback = function(e, t) {
	if (e) app.notify.showError(e);
	else if (null != t) {
		codeEditor.setText(t);
		try {
			var i = jsoneditor.util.parse(t);
			treeEditor.set(i)
		} catch(e) {
			treeEditor.set({}),
			app.notify.showError(app.formatError(e))
		}
	}
},
app.openFile = function() {
	app.retriever.loadFile(app.openCallback)
},
app.openUrl = function(e) {
	e ? app.retriever.loadUrl(e, app.openCallback) : app.retriever.loadUrlDialog(app.openCallback)
},
app.saveFile = function() {
	app.lastChanged == treeEditor && app.treeToCode(),
	app.lastChanged = void 0;
	var e = codeEditor.getText();
	app.retriever.saveFile(e,
	function(e) {
		e && app.notify.showError(e)
	})
},
app.formatError = function(e) {
	var t = '<pre class="error">' + e.toString() + "</pre>";
	return "undefined" != typeof jsonlint && (t += '<a class="error" href="#" target="_blank">validated by jsonlint</a>'),
	t
},
app.clearFile = function() {
	var e = {};
	codeEditor.set(e),
	treeEditor.set(e)
},
app.resize = function() {
	var e = document.getElementById("menu"),
	t = document.getElementById("treeEditor"),
	i = document.getElementById("codeEditor"),
	o = document.getElementById("splitter"),
	n = document.getElementById("buttons"),
	s = document.getElementById("drag"),
	r = document.getElementById("ad"),
	a = 15,
	l = window.innerWidth || document.body.offsetWidth || document.documentElement.offsetWidth,
	d = r ? r.clientWidth: 0;
	if (d && (l -= d + a), app.splitter) {
		app.splitter.setWidth(l);
		var h = app.splitter.getValue(),
		c = h > 0,
		u = 1 > h,
		p = c && u;
		n.style.display = p ? "": "none";
		var m, f = o.clientWidth;
		if (c) if (u) {
			m = l * h - f / 2;
			var v = jsoneditor.util.getInternetExplorerVersion() == 8;
			s.innerHTML = v ? "|": "&#8942;",
			s.title = "Drag left or right to change the width of the panels"
		} else m = l * h - f,
		s.innerHTML = "&lsaquo;",
		s.title = "Drag left to show the tree editor";
		else m = 0,
		s.innerHTML = "&rsaquo;",
		s.title = "Drag right to show the code editor";
		i.style.display = 0 == h ? "none": "",
		i.style.width = Math.max(Math.round(m), 0) + "px",
		codeEditor.resize(),
		s.style.height = o.clientHeight - n.clientHeight - 2 * a - (p ? a: 0) + "px",
		s.style.lineHeight = s.style.height,
		t.style.display = 1 == h ? "none": "",
		t.style.left = Math.round(m + f) + "px",
		t.style.width = Math.max(Math.round(l - m - f - 2), 0) + "px"
	}
	e && (e.style.right = d ? a + (d + a) + "px": a + "px")
};