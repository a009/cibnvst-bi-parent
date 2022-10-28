(function() {
	function e(t, n, r) {
		if (! (this instanceof e)) throw new Error('JSONEditor constructor called without "new".');
		arguments.length && this._create(t, n, r)
	}
	function t(e, n, r) {
		if (! (this instanceof t)) throw new Error('TreeEditor constructor called without "new".');
		this._create(e, n, r)
	}
	function n(e, t, r) {
		if (! (this instanceof n)) throw new Error('TextEditor constructor called without "new".');
		this._create(e, t, r)
	}
	function r(e, t) {
		this.editor = e,
		this.dom = {},
		this.expanded = !1,
		t && t instanceof Object ? (this.setField(t.field, t.fieldEditable), this.setValue(t.value, t.type)) : (this.setField(""), this.setValue(null))
	}
	function i(e) {
		this.editor = e,
		this.dom = {}
	}
	function s(e, t) {
		function n(e, t, i) {
			i.forEach(function(i) {
				if (i.type == "separator") {
					var s = document.createElement("div");
					s.className = "separator",
					u = document.createElement("li"),
					u.appendChild(s),
					e.appendChild(u)
				} else {
					var o = {},
					u = document.createElement("li");
					e.appendChild(u);
					var a = document.createElement("button");
					if (a.className = i.className, o.button = a, i.title && (a.title = i.title), i.click && (a.onclick = function() {
						r.hide(),
						i.click()
					}), u.appendChild(a), i.submenu) {
						var f = document.createElement("div");
						f.className = "icon",
						a.appendChild(f),
						a.appendChild(document.createTextNode(i.text));
						var l;
						if (i.click) {
							a.className += " default";
							var c = document.createElement("button");
							o.buttonExpand = c,
							c.className = "expand",
							c.innerHTML = '<div class="expand"></div>',
							u.appendChild(c),
							i.submenuTitle && (c.title = i.submenuTitle),
							l = c
						} else {
							var h = document.createElement("div");
							h.className = "expand",
							a.appendChild(h),
							l = a
						}
						l.onclick = function() {
							r._onExpandItem(o),
							l.focus()
						};
						var p = [];
						o.subItems = p;
						var d = document.createElement("ul");
						o.ul = d,
						d.className = "menu",
						d.style.height = "0",
						u.appendChild(d),
						n(d, p, i.submenu)
					} else a.innerHTML = '<div class="icon"></div>' + i.text;
					t.push(o)
				}
			})
		}
		this.dom = {};
		var r = this,
		i = this.dom;
		this.anchor = void 0,
		this.items = e,
		this.eventListeners = {},
		this.selection = void 0,
		this.visibleSubmenu = void 0,
		this.onClose = t ? t.close: void 0;
		var s = document.createElement("div");
		s.className = "jsoneditor-contextmenu",
		i.menu = s;
		var o = document.createElement("ul");
		o.className = "menu",
		s.appendChild(o),
		i.list = o,
		i.items = [];
		var u = document.createElement("button");
		i.focusButton = u;
		var a = document.createElement("li");
		a.style.overflow = "hidden",
		a.style.height = "0",
		a.appendChild(u),
		o.appendChild(a),
		n(o, this.dom.items, e),
		this.maxHeight = 0,
		e.forEach(function(t) {
			var n = (e.length + (t.submenu ? t.submenu.length: 0)) * 24;
			r.maxHeight = Math.max(r.maxHeight, n)
		})
	}
	function o(e) {
		this.editor = e,
		this.clear(),
		this.actions = {
			editField: {
				undo: function(e) {
					e.node.updateField(e.oldValue)
				},
				redo: function(e) {
					e.node.updateField(e.newValue)
				}
			},
			editValue: {
				undo: function(e) {
					e.node.updateValue(e.oldValue)
				},
				redo: function(e) {
					e.node.updateValue(e.newValue)
				}
			},
			appendNode: {
				undo: function(e) {
					e.parent.removeChild(e.node)
				},
				redo: function(e) {
					e.parent.appendChild(e.node)
				}
			},
			insertBeforeNode: {
				undo: function(e) {
					e.parent.removeChild(e.node)
				},
				redo: function(e) {
					e.parent.insertBefore(e.node, e.beforeNode)
				}
			},
			insertAfterNode: {
				undo: function(e) {
					e.parent.removeChild(e.node)
				},
				redo: function(e) {
					e.parent.insertAfter(e.node, e.afterNode)
				}
			},
			removeNode: {
				undo: function(e) {
					var t = e.parent,
					n = t.childs[e.index] || t.append;
					t.insertBefore(e.node, n)
				},
				redo: function(e) {
					e.parent.removeChild(e.node)
				}
			},
			duplicateNode: {
				undo: function(e) {
					e.parent.removeChild(e.clone)
				},
				redo: function(e) {
					e.parent.insertAfter(e.clone, e.node)
				}
			},
			changeType: {
				undo: function(e) {
					e.node.changeType(e.oldType)
				},
				redo: function(e) {
					e.node.changeType(e.newType)
				}
			},
			moveNode: {
				undo: function(e) {
					e.startParent.moveTo(e.node, e.startIndex)
				},
				redo: function(e) {
					e.endParent.moveTo(e.node, e.endIndex)
				}
			},
			sort: {
				undo: function(e) {
					var t = e.node;
					t.hideChilds(),
					t.sort = e.oldSort,
					t.childs = e.oldChilds,
					t.showChilds()
				},
				redo: function(e) {
					var t = e.node;
					t.hideChilds(),
					t.sort = e.newSort,
					t.childs = e.newChilds,
					t.showChilds()
				}
			}
		}
	}
	function u(e, t) {
		var n = this;
		this.editor = e,
		this.timeout = void 0,
		this.delay = 200,
		this.lastText = void 0,
		this.dom = {},
		this.dom.container = t;
		var r = document.createElement("table");
		this.dom.table = r,
		r.className = "search",
		t.appendChild(r);
		var i = document.createElement("tbody");
		this.dom.tbody = i,
		r.appendChild(i);
		var s = document.createElement("tr");
		i.appendChild(s);
		var o = document.createElement("td");
		s.appendChild(o);
		var u = document.createElement("div");
		this.dom.results = u,
		u.className = "results",
		o.appendChild(u),
		o = document.createElement("td"),
		s.appendChild(o);
		var a = document.createElement("div");
		this.dom.input = a,
		a.className = "frame",
		a.title = "Search fields and values",
		o.appendChild(a);
		var f = document.createElement("table");
		a.appendChild(f);
		var l = document.createElement("tbody");
		f.appendChild(l),
		s = document.createElement("tr"),
		l.appendChild(s);
		var c = document.createElement("button");
		c.className = "refresh",
		o = document.createElement("td"),
		o.appendChild(c),
		s.appendChild(o);
		var h = document.createElement("input");
		this.dom.search = h,
		h.oninput = function(e) {
			n._onDelayedSearch(e)
		},
		h.onchange = function(e) {
			n._onSearch(e)
		},
		h.onkeydown = function(e) {
			n._onKeyDown(e)
		},
		h.onkeyup = function(e) {
			n._onKeyUp(e)
		},
		c.onclick = function() {
			h.select()
		},
		o = document.createElement("td"),
		o.appendChild(h),
		s.appendChild(o);
		var p = document.createElement("button");
		p.title = "Next result (Enter)",
		p.className = "next",
		p.onclick = function() {
			n.next()
		},
		o = document.createElement("td"),
		o.appendChild(p),
		s.appendChild(o);
		var d = document.createElement("button");
		d.title = "Previous result (Shift+Enter)",
		d.className = "previous",
		d.onclick = function() {
			n.previous()
		},
		o = document.createElement("td"),
		o.appendChild(d),
		s.appendChild(o)
	}
	function a() {
		this.locked = !1
	}
	e.modes = {},
	e.prototype._create = function(e, t, n) {
		this.container = e,
		this.options = t || {},
		this.json = n || {};
		var r = this.options.mode || "tree";
		this.setMode(r)
	},
	e.prototype._delete = function() {},
	e.prototype.set = function(e) {
		this.json = e
	},
	e.prototype.get = function() {
		return this.json
	},
	e.prototype.setText = function(e) {
		this.json = util.parse(e)
	},
	e.prototype.getText = function() {
		return JSON.stringify(this.json)
	},
	e.prototype.setName = function(e) {
		this.options || (this.options = {}),
		this.options.name = e
	},
	e.prototype.getName = function() {
		return this.options && this.options.name
	},
	e.prototype.setMode = function(t) {
		var n, r, i = this.container,
		s = util.extend({},
		this.options);
		s.mode = t;
		var o = e.modes[t];
		if (!o) throw new Error('Unknown mode "' + s.mode + '"');
		try {
			if (o.data == "text" ? (r = this.getName(), n = this.getText(), this._delete(), util.clear(this), util.extend(this, o.editor.prototype), this._create(i, s), this.setName(r), this.setText(n)) : (r = this.getName(), n = this.get(), this._delete(), util.clear(this), util.extend(this, o.editor.prototype), this._create(i, s), this.setName(r), this.set(n)), typeof o.load == "function") try {
				o.load.call(this)
			} catch(u) {}
		} catch(u) {
			this._onError(u)
		}
	},
	e.prototype._onError = function(e) {
		if (typeof this.onError == "function" && (util.log("WARNING: JSONEditor.onError is deprecated. Use options.error instead."), this.onError(e)), !this.options || typeof this.options.error != "function") throw e;
		this.options.error(e)
	},
	t.prototype._create = function(e, t, n) {
		if ("undefined" == typeof JSON) throw new Error("Your browser does not support JSON. \n\nPlease install the newest version of your browser.\n(all modern browsers support JSON).");
		if (!e) throw new Error("No container element provided.");
		this.container = e,
		this.dom = {},
		this.highlighter = new a,
		this.selection = void 0,
		this._setOptions(t),
		this.options.history && !this.mode.view && (this.history = new o(this)),
		this._createFrame(),
		this._createTable(),
		this.set(n || {})
	},
	t.prototype._delete = function() {
		this.frame && this.container && this.frame.parentNode == this.container && this.container.removeChild(this.frame)
	},
	t.prototype._setOptions = function(e) {
		if (this.options = {
			search: !0,
			history: !0,
			mode: "tree",
			name: void 0
		},
		e) {
			for (var t in e) e.hasOwnProperty(t) && (this.options[t] = e[t]);
			e.enableSearch && (this.options.search = e.enableSearch, util.log('WARNING: Option "enableSearch" is deprecated. Use "search" instead.')),
			e.enableHistory && (this.options.history = e.enableHistory, util.log('WARNING: Option "enableHistory" is deprecated. Use "history" instead.')),
			e.mode == "editor" && (this.options.mode = "tree", util.log('WARNING: Mode "editor" is deprecated. Use "tree" instead.')),
			e.mode == "viewer" && (this.options.mode = "view", util.log('WARNING: Mode "viewer" is deprecated. Use "view" instead.'))
		}
		this.mode = {
			edit: this.options.mode != "view" && this.options.mode != "form",
			view: this.options.mode == "view",
			form: this.options.mode == "form"
		}
	},
	t.focusNode = void 0,
	t.prototype.set = function(e, t) {
		if (t && (util.log('Warning: second parameter "name" is deprecated. Use setName(name) instead.'), this.options.name = t), e instanceof Function || void 0 === e) this.clear();
		else {
			this.content.removeChild(this.table);
			var n = {
				field: this.options.name,
				value: e
			},
			i = new r(this, n);
			this._setRoot(i);
			var s = !1;
			this.node.expand(s),
			this.content.appendChild(this.table)
		}
		this.history && this.history.clear()
	},
	t.prototype.get = function() {
		return t.focusNode && t.focusNode.blur(),
		this.node ? this.node.getValue() : void 0
	},
	t.prototype.getText = function() {
		return JSON.stringify(this.get())
	},
	t.prototype.setText = function(e) {
		this.set(util.parse(e))
	},
	t.prototype.setName = function(e) {
		this.options.name = e,
		this.node && this.node.updateField(this.options.name)
	},
	t.prototype.getName = function() {
		return this.options.name
	},
	t.prototype.clear = function() {
		this.node && (this.node.collapse(), this.tbody.removeChild(this.node.getDom()), delete this.node)
	},
	t.prototype._setRoot = function(e) {
		this.clear(),
		this.node = e,
		this.tbody.appendChild(e.getDom())
	},
	t.prototype.search = function(e) {
		var t;
		return this.node ? (this.content.removeChild(this.table), t = this.node.search(e), this.content.appendChild(this.table)) : t = [],
		t
	},
	t.prototype.expandAll = function() {
		this.node && (this.content.removeChild(this.table), this.node.expand(), this.content.appendChild(this.table))
	},
	t.prototype.collapseAll = function() {
		this.node && (this.content.removeChild(this.table), this.node.collapse(), this.content.appendChild(this.table))
	},
	t.prototype._onAction = function(e, t) {
		if (this.history && this.history.add(e, t), this.options.change) try {
			this.options.change()
		} catch(n) {
			util.log("Error in change callback: ", n)
		}
	},
	t.prototype.startAutoScroll = function(e) {
		var t = this,
		n = this.content,
		r = util.getAbsoluteTop(n),
		i = n.clientHeight,
		s = r + i,
		o = 24,
		u = 50;
		this.autoScrollStep = r + o > e && n.scrollTop > 0 ? (r + o - e) / 3 : e > s - o && i + n.scrollTop < n.scrollHeight ? (s - o - e) / 3 : void 0,
		this.autoScrollStep ? this.autoScrollTimer || (this.autoScrollTimer = setInterval(function() {
			t.autoScrollStep ? n.scrollTop -= t.autoScrollStep: t.stopAutoScroll()
		},
		u)) : this.stopAutoScroll()
	},
	t.prototype.stopAutoScroll = function() {
		this.autoScrollTimer && (clearTimeout(this.autoScrollTimer), delete this.autoScrollTimer),
		this.autoScrollStep && delete this.autoScrollStep
	},
	t.prototype.setSelection = function(e) {
		e && ("scrollTop" in e && this.content && (this.content.scrollTop = e.scrollTop), e.range && util.setSelectionOffset(e.range), e.dom && e.dom.focus())
	},
	t.prototype.getSelection = function() {
		return {
			dom: t.domFocus,
			scrollTop: this.content ? this.content.scrollTop: 0,
			range: util.getSelectionOffset()
		}
	},
	t.prototype.scrollTo = function(e, t) {
		var n = this.content;
		if (n) {
			var r = this;
			r.animateTimeout && (clearTimeout(r.animateTimeout), delete r.animateTimeout),
			r.animateCallback && (r.animateCallback(!1), delete r.animateCallback);
			var i = n.clientHeight,
			s = n.scrollHeight - i,
			o = Math.min(Math.max(e - i / 4, 0), s),
			u = function() {
				var e = n.scrollTop,
				i = o - e;
				Math.abs(i) > 3 ? (n.scrollTop += i / 3, r.animateCallback = t, r.animateTimeout = setTimeout(u, 50)) : (t && t(!0), n.scrollTop = o, delete r.animateTimeout, delete r.animateCallback)
			};
			u()
		} else t && t(!1)
	},
	t.prototype._createFrame = function() {
		this.frame = document.createElement("div"),
		this.frame.className = "jsoneditor",
		this.container.appendChild(this.frame);
		var e = this,
		t = function(t) {
			e._onEvent(t)
		};
		this.frame.onclick = function(e) {
			e = e || window.event;
			var n = e.target || e.srcElement;
			t(e),
			n.nodeName == "BUTTON" && util.preventDefault(e)
		},
		this.frame.oninput = t,
		this.frame.onchange = t,
		this.frame.onkeydown = t,
		this.frame.onkeyup = t,
		this.frame.oncut = t,
		this.frame.onpaste = t,
		this.frame.onmousedown = t,
		this.frame.onmouseup = t,
		this.frame.onmouseover = t,
		this.frame.onmouseout = t,
		util.addEventListener(this.frame, "focus", t, !0),
		util.addEventListener(this.frame, "blur", t, !0),
		this.frame.onfocusin = t,
		this.frame.onfocusout = t,
		this.menu = document.createElement("div"),
		this.menu.className = "menu",
		this.frame.appendChild(this.menu);
		var n = document.createElement("button");
		n.className = "expand-all",
		n.title = "Expand all fields",
		n.onclick = function() {
			e.expandAll()
		},
		this.menu.appendChild(n);
		var r = document.createElement("button");
		if (r.title = "Collapse all fields", r.className = "collapse-all", r.onclick = function() {
			e.collapseAll()
		},
		this.menu.appendChild(r), this.history) {
			var i = document.createElement("span");
			i.innerHTML = "&nbsp;",
			this.menu.appendChild(i);
			var s = document.createElement("button");
			s.className = "undo",
			s.title = "Undo last action (Ctrl+Z)",
			s.onclick = function() {
				e._onUndo()
			},
			this.menu.appendChild(s),
			this.dom.undo = s;
			var o = document.createElement("button");
			o.className = "redo",
			o.title = "Redo (Ctrl+Shift+Z)",
			o.onclick = function() {
				e._onRedo()
			},
			this.menu.appendChild(o),
			this.dom.redo = o,
			this.history.onChange = function() {
				s.disabled = !e.history.canUndo(),
				o.disabled = !e.history.canRedo()
			},
			this.history.onChange()
		}
		this.options.search && (this.searchBox = new u(this, this.menu))
	},
	t.prototype._onUndo = function() {
		this.history && (this.history.undo(), this.options.change && this.options.change())
	},
	t.prototype._onRedo = function() {
		this.history && (this.history.redo(), this.options.change && this.options.change())
	},
	t.prototype._onEvent = function(e) {
		e = e || window.event;
		var n = e.target || e.srcElement;
		e.type == "keydown" && this._onKeyDown(e),
		e.type == "focus" && (t.domFocus = n);
		var i = r.getNodeFromTarget(n);
		i && i.onEvent(e)
	},
	t.prototype._onKeyDown = function(e) {
		var n = e.which || e.keyCode,
		r = e.ctrlKey,
		i = e.shiftKey,
		s = !1;
		if (9 == n && setTimeout(function() {
			util.selectContentEditable(t.domFocus)
		},
		0), this.searchBox) if (r && 70 == n) this.searchBox.dom.search.focus(),
		this.searchBox.dom.search.select(),
		s = !0;
		else if (114 == n || r && 71 == n) {
			var o = !0;
			i ? this.searchBox.previous(o) : this.searchBox.next(o),
			s = !0
		}
		this.history && (r && !i && 90 == n ? (this._onUndo(), s = !0) : r && i && 90 == n && (this._onRedo(), s = !0)),
		s && (util.preventDefault(e), util.stopPropagation(e))
	},
	t.prototype._createTable = function() {
		var e = document.createElement("div");
		e.className = "outer",
		this.contentOuter = e,
		this.content = document.createElement("div"),
		this.content.className = "content",
		e.appendChild(this.content),
		this.table = document.createElement("table"),
		this.table.className = "content",
		this.content.appendChild(this.table);
		var t = util.getInternetExplorerVersion();
		8 == t && (this.content.style.overflow = "scroll");
		var n;
		this.colgroupContent = document.createElement("colgroup"),
		this.mode.edit && (n = document.createElement("col"), n.width = "24px", this.colgroupContent.appendChild(n)),
		n = document.createElement("col"),
		n.width = "24px",
		this.colgroupContent.appendChild(n),
		n = document.createElement("col"),
		this.colgroupContent.appendChild(n),
		this.table.appendChild(this.colgroupContent),
		this.tbody = document.createElement("tbody"),
		this.table.appendChild(this.tbody),
		this.frame.appendChild(e)
	},
	e.modes.tree = {
		editor: t,
		data: "json"
	},
	e.modes.view = {
		editor: t,
		data: "json"
	},
	e.modes.form = {
		editor: t,
		data: "json"
	},
	e.modes.editor = {
		editor: t,
		data: "json"
	},
	e.modes.viewer = {
		editor: t,
		data: "json"
	},
	n.prototype._create = function(e, t, n) {
		if ("undefined" == typeof JSON) throw new Error("Your browser does not support JSON. \n\nPlease install the newest version of your browser.\n(all modern browsers support JSON).");
		t = t || {},
		this.options = t,
		this.indentation = t.indentation ? Number(t.indentation) : 4,
		this.mode = t.mode == "code" ? "code": "text",
		this.mode == "code" && ("undefined" == typeof ace && (this.mode = "text", util.log("WARNING: Cannot load code editor, Ace library not loaded. Falling back to plain text editor")), util.getInternetExplorerVersion() == 8 && (this.mode = "text", util.log("WARNING: Cannot load code editor, Ace is not supported on IE8. Falling back to plain text editor")));
		var r = this;
		this.container = e,
		this.editor = void 0,
		this.textarea = void 0,
		this.width = e.clientWidth,
		this.height = e.clientHeight,
		this.frame = document.createElement("div"),
		this.frame.className = "jsoneditor",
		this.frame.onclick = function(e) {
			util.preventDefault(e)
		},
		this.menu = document.createElement("div"),
		this.menu.className = "menu",
		this.frame.appendChild(this.menu);
		var i = document.createElement("button");
		i.className = "format",
		i.title = "Format JSON data, with proper indentation and line feeds",
		this.menu.appendChild(i),
		i.onclick = function() {
			try {
				r.format()
			} catch(e) {
				r._onError(e)
			}
		};
		var s = document.createElement("button");
		if (s.className = "compact", s.title = "Compact JSON data, remove all whitespaces", this.menu.appendChild(s), s.onclick = function() {
			try {
				r.compact()
			} catch(e) {
				r._onError(e)
			}
		},
		this.content = document.createElement("div"), this.content.className = "outer", this.frame.appendChild(this.content), this.container.appendChild(this.frame), this.mode == "code") {
			this.editorDom = document.createElement("div"),
			this.editorDom.style.height = "100%",
			this.editorDom.style.width = "100%",
			this.content.appendChild(this.editorDom);
			var o = ace.edit(this.editorDom);
			o.setTheme("ace/theme/jsoneditor"),
			o.setShowPrintMargin(!1),
			o.setFontSize(13),
			o.getSession().setMode("ace/mode/json"),
			o.getSession().setUseSoftTabs(!0),
			o.getSession().setUseWrapMode(!0),
			this.editor = o;
			var u = document.createElement("a");
			u.appendChild(document.createTextNode("powered by ace")),
			u.href = "http://ace.ajax.org",
			u.target = "_blank",
			u.className = "poweredBy",
			u.onclick = function() {
				window.open(u.href, u.target)
			},
			this.menu.appendChild(u),
			t.change && o.on("change",
			function() {
				t.change()
			})
		} else {
			var a = document.createElement("textarea");
			a.className = "content",
			a.spellcheck = !1,
			this.content.appendChild(a),
			this.textarea = a,
			t.change && (this.textarea.oninput === null ? this.textarea.oninput = function() {
				t.change()
			}: this.textarea.onchange = function() {
				t.change()
			})
		}
		"string" == typeof n ? this.setText(n) : this.set(n)
	},
	n.prototype._delete = function() {
		this.frame && this.container && this.frame.parentNode == this.container && this.container.removeChild(this.frame)
	},
	n.prototype._onError = function(e) {
		if (typeof this.onError == "function" && (util.log("WARNING: JSONEditor.onError is deprecated. Use options.error instead."), this.onError(e)), !this.options || typeof this.options.error != "function") throw e;
		this.options.error(e)
	},
	n.prototype.compact = function() {
		var e = util.parse(this.getText());
		this.setText(JSON.stringify(e))
	},
	n.prototype.format = function() {
		var e = util.parse(this.getText());
		this.setText(JSON.stringify(e, null, this.indentation))
	},
	n.prototype.focus = function() {
		this.textarea && this.textarea.focus(),
		this.editor && this.editor.focus()
	},
	n.prototype.resize = function() {
		if (this.editor) {
			var e = !1;
			this.editor.resize(e)
		}
	},
	n.prototype.set = function(e) {
		this.setText(JSON.stringify(e, null, this.indentation))
	},
	n.prototype.get = function() {
		return util.parse(this.getText())
	},
	n.prototype.getText = function() {
		return this.textarea ? this.textarea.value: this.editor ? this.editor.getValue() : ""
	},
	n.prototype.setText = function(e) {
		this.textarea && (this.textarea.value = e),
		this.editor && this.editor.setValue(e, -1)
	},
	e.modes.text = {
		editor: n,
		data: "text",
		load: n.prototype.format
	},
	e.modes.code = {
		editor: n,
		data: "text",
		load: n.prototype.format
	},
	r.prototype.setParent = function(e) {
		this.parent = e
	},
	r.prototype.setField = function(e, t) {
		this.field = e,
		this.fieldEditable = 1 == t
	},
	r.prototype.getField = function() {
		return this.field === void 0 && this._getDomField(),
		this.field
	},
	r.prototype.setValue = function(e, t) {
		var n, i, s = this.childs;
		if (s) for (; s.length;) this.removeChild(s[0]);
		if (this.type = this._getType(e), t && t != this.type) {
			if ("string" != t || this.type != "auto") throw new Error('Type mismatch: cannot cast value of type "' + this.type + ' to the specified type "' + t + '"');
			this.type = t
		}
		if (this.type == "array") {
			this.childs = [];
			for (var o = 0,
			u = e.length; u > o; o++) n = e[o],
			void 0 === n || n instanceof Function || (i = new r(this.editor, {
				value: n
			}), this.appendChild(i));
			this.value = ""
		} else if (this.type == "object") {
			this.childs = [];
			for (var a in e) e.hasOwnProperty(a) && (n = e[a], void 0 === n || n instanceof Function || (i = new r(this.editor, {
				field: a,
				value: n
			}), this.appendChild(i)));
			this.value = ""
		} else this.childs = void 0,
		this.value = e
	},
	r.prototype.getValue = function() {
		if (this.type == "array") {
			var e = [];
			return this.childs.forEach(function(t) {
				e.push(t.getValue())
			}),
			e
		}
		if (this.type == "object") {
			var t = {};
			return this.childs.forEach(function(e) {
				t[e.getField()] = e.getValue()
			}),
			t
		}
		return this.value === void 0 && this._getDomValue(),
		this.value
	},
	r.prototype.getLevel = function() {
		return this.parent ? this.parent.getLevel() + 1 : 0
	},
	r.prototype.clone = function() {
		var e = new r(this.editor);
		if (e.type = this.type, e.field = this.field, e.fieldInnerText = this.fieldInnerText, e.fieldEditable = this.fieldEditable, e.value = this.value, e.valueInnerText = this.valueInnerText, e.expanded = this.expanded, this.childs) {
			var t = [];
			this.childs.forEach(function(n) {
				var r = n.clone();
				r.setParent(e),
				t.push(r)
			}),
			e.childs = t
		} else e.childs = void 0;
		return e
	},
	r.prototype.expand = function(e) {
		this.childs && (this.expanded = !0, this.dom.expand && (this.dom.expand.className = "expanded"), this.showChilds(), 0 != e && this.childs.forEach(function(t) {
			t.expand(e)
		}))
	},
	r.prototype.collapse = function(e) {
		this.childs && (this.hideChilds(), 0 != e && this.childs.forEach(function(t) {
			t.collapse(e)
		}), this.dom.expand && (this.dom.expand.className = "collapsed"), this.expanded = !1)
	},
	r.prototype.showChilds = function() {
		var e = this.childs;
		if (e && this.expanded) {
			var t = this.dom.tr,
			n = t ? t.parentNode: void 0;
			if (n) {
				var r = this.getAppend(),
				i = t.nextSibling;
				i ? n.insertBefore(r, i) : n.appendChild(r),
				this.childs.forEach(function(e) {
					n.insertBefore(e.getDom(), r),
					e.showChilds()
				})
			}
		}
	},
	r.prototype.hide = function() {
		var e = this.dom.tr,
		t = e ? e.parentNode: void 0;
		t && t.removeChild(e),
		this.hideChilds()
	},
	r.prototype.hideChilds = function() {
		var e = this.childs;
		if (e && this.expanded) {
			var t = this.getAppend();
			t.parentNode && t.parentNode.removeChild(t),
			this.childs.forEach(function(e) {
				e.hide()
			})
		}
	},
	r.prototype.appendChild = function(e) {
		if (this._hasChilds()) {
			if (e.setParent(this), e.fieldEditable = this.type == "object", this.type == "array" && (e.index = this.childs.length), this.childs.push(e), this.expanded) {
				var t = e.getDom(),
				n = this.getAppend(),
				r = n ? n.parentNode: void 0;
				n && r && r.insertBefore(t, n),
				e.showChilds()
			}
			this.updateDom({
				updateIndexes: !0
			}),
			e.updateDom({
				recurse: !0
			})
		}
	},
	r.prototype.moveBefore = function(e, t) {
		if (this._hasChilds()) {
			var n = this.dom.tr ? this.dom.tr.parentNode: void 0;
			if (n) {
				var r = document.createElement("tr");
				r.style.height = n.clientHeight + "px",
				n.appendChild(r)
			}
			e.parent && e.parent.removeChild(e),
			t instanceof i ? this.appendChild(e) : this.insertBefore(e, t),
			n && n.removeChild(r)
		}
	},
	r.prototype.moveTo = function(e, t) {
		if (e.parent == this) {
			var n = this.childs.indexOf(e);
			t > n && t++
		}
		var r = this.childs[t] || this.append;
		this.moveBefore(e, r)
	},
	r.prototype.insertBefore = function(e, t) {
		if (this._hasChilds()) {
			if (t == this.append) e.setParent(this),
			e.fieldEditable = this.type == "object",
			this.childs.push(e);
			else {
				var n = this.childs.indexOf(t);
				if ( - 1 == n) throw new Error("Node not found");
				e.setParent(this),
				e.fieldEditable = this.type == "object",
				this.childs.splice(n, 0, e)
			}
			if (this.expanded) {
				var r = e.getDom(),
				i = t.getDom(),
				s = i ? i.parentNode: void 0;
				i && s && s.insertBefore(r, i),
				e.showChilds()
			}
			this.updateDom({
				updateIndexes: !0
			}),
			e.updateDom({
				recurse: !0
			})
		}
	},
	r.prototype.insertAfter = function(e, t) {
		if (this._hasChilds()) {
			var n = this.childs.indexOf(t),
			r = this.childs[n + 1];
			r ? this.insertBefore(e, r) : this.appendChild(e)
		}
	},
	r.prototype.search = function(e) {
		var t, n = [],
		r = e ? e.toLowerCase() : void 0;
		if (delete this.searchField, delete this.searchValue, this.field != void 0) {
			var i = String(this.field).toLowerCase();
			t = i.indexOf(r),
			-1 != t && (this.searchField = !0, n.push({
				node: this,
				elem: "field"
			})),
			this._updateDomField()
		}
		if (this._hasChilds()) {
			if (this.childs) {
				var s = [];
				this.childs.forEach(function(t) {
					s = s.concat(t.search(e))
				}),
				n = n.concat(s)
			}
			if (void 0 != r) {
				var o = !1;
				s.length == 0 ? this.collapse(o) : this.expand(o)
			}
		} else {
			if (this.value != void 0) {
				var u = String(this.value).toLowerCase();
				t = u.indexOf(r),
				-1 != t && (this.searchValue = !0, n.push({
					node: this,
					elem: "value"
				}))
			}
			this._updateDomValue()
		}
		return n
	},
	r.prototype.scrollTo = function(e) {
		if (!this.dom.tr || !this.dom.tr.parentNode) for (var t = this.parent,
		n = !1; t;) t.expand(n),
		t = t.parent;
		this.dom.tr && this.dom.tr.parentNode && this.editor.scrollTo(this.dom.tr.offsetTop, e)
	},
	r.focusElement = void 0,
	r.prototype.focus = function(e) {
		if (r.focusElement = e, this.dom.tr && this.dom.tr.parentNode) {
			var t = this.dom;
			switch (e) {
			case "drag":
				t.drag ? t.drag.focus() : t.menu.focus();
				break;
			case "menu":
				t.menu.focus();
				break;
			case "expand":
				this._hasChilds() ? t.expand.focus() : t.field && this.fieldEditable ? (t.field.focus(), util.selectContentEditable(t.field)) : t.value && !this._hasChilds() ? (t.value.focus(), util.selectContentEditable(t.value)) : t.menu.focus();
				break;
			case "field":
				t.field && this.fieldEditable ? (t.field.focus(), util.selectContentEditable(t.field)) : t.value && !this._hasChilds() ? (t.value.focus(), util.selectContentEditable(t.value)) : this._hasChilds() ? t.expand.focus() : t.menu.focus();
				break;
			case "value":
			default:
				t.value && !this._hasChilds() ? (t.value.focus(), util.selectContentEditable(t.value)) : t.field && this.fieldEditable ? (t.field.focus(), util.selectContentEditable(t.field)) : this._hasChilds() ? t.expand.focus() : t.menu.focus()
			}
		}
	},
	r.select = function(e) {
		setTimeout(function() {
			util.selectContentEditable(e)
		},
		0)
	},
	r.prototype.blur = function() {
		this._getDomValue(!1),
		this._getDomField(!1)
	},
	r.prototype._duplicate = function(e) {
		var t = e.clone();
		return this.insertAfter(t, e),
		t
	},
	r.prototype.containsNode = function(e) {
		if (this == e) return ! 0;
		var t = this.childs;
		if (t) for (var n = 0,
		r = t.length; r > n; n++) if (t[n].containsNode(e)) return ! 0;
		return ! 1
	},
	r.prototype._move = function(e, t) {
		if (e != t) {
			if (e.containsNode(this)) throw new Error("Cannot move a field into a child of itself");
			e.parent && e.parent.removeChild(e);
			var n = e.clone();
			e.clearDom(),
			t ? this.insertBefore(n, t) : this.appendChild(n)
		}
	},
	r.prototype.removeChild = function(e) {
		if (this.childs) {
			var t = this.childs.indexOf(e);
			if ( - 1 != t) {
				e.hide(),
				delete e.searchField,
				delete e.searchValue;
				var n = this.childs.splice(t, 1)[0];
				return this.updateDom({
					updateIndexes: !0
				}),
				n
			}
		}
		return void 0
	},
	r.prototype._remove = function(e) {
		this.removeChild(e)
	},
	r.prototype.changeType = function(e) {
		var t = this.type;
		if (t != e) {
			if ("string" != e && "auto" != e || "string" != t && "auto" != t) {
				var n, r = this.dom.tr ? this.dom.tr.parentNode: void 0;
				n = this.expanded ? this.getAppend() : this.getDom();
				var i = n && n.parentNode ? n.nextSibling: void 0;
				this.hide(),
				this.clearDom(),
				this.type = e,
				"object" == e ? (this.childs || (this.childs = []), this.childs.forEach(function(e) {
					e.clearDom(),
					delete e.index,
					e.fieldEditable = !0,
					e.field == void 0 && (e.field = "")
				}), ("string" == t || "auto" == t) && (this.expanded = !0)) : "array" == e ? (this.childs || (this.childs = []), this.childs.forEach(function(e, t) {
					e.clearDom(),
					e.fieldEditable = !1,
					e.index = t
				}), ("string" == t || "auto" == t) && (this.expanded = !0)) : this.expanded = !1,
				r && (i ? r.insertBefore(this.getDom(), i) : r.appendChild(this.getDom())),
				this.showChilds()
			} else this.type = e; ("auto" == e || "string" == e) && (this.value = "string" == e ? String(this.value) : this._stringCast(String(this.value)), this.focus()),
			this.updateDom({
				updateIndexes: !0
			})
		}
	},
	r.prototype._getDomValue = function(e) {
		if (this.dom.value && this.type != "array" && this.type != "object" && (this.valueInnerText = util.getInnerText(this.dom.value)), this.valueInnerText != void 0) try {
			var t;
			if (this.type == "string") t = this._unescapeHTML(this.valueInnerText);
			else {
				var n = this._unescapeHTML(this.valueInnerText);
				t = this._stringCast(n)
			}
			if (t !== this.value) {
				var r = this.value;
				this.value = t,
				this.editor._onAction("editValue", {
					node: this,
					oldValue: r,
					newValue: t,
					oldSelection: this.editor.selection,
					newSelection: this.editor.getSelection()
				})
			}
		} catch(i) {
			if (this.value = void 0, 1 != e) throw i
		}
	},
	r.prototype._updateDomValue = function() {
		var e = this.dom.value;
		if (e) {
			var t = this.value,
			n = this.type == "auto" ? typeof t: this.type,
			r = "string" == n && util.isUrl(t),
			i = "";
			i = r && !this.editor.mode.edit ? "": "string" == n ? "green": "number" == n ? "red": "boolean" == n ? "darkorange": this._hasChilds() ? "": null === t ? "#004ED0": "black",
			e.style.color = i;
			var s = String(this.value) == "" && this.type != "array" && this.type != "object";
			if (s ? util.addClassName(e, "empty") : util.removeClassName(e, "empty"), r ? util.addClassName(e, "url") : util.removeClassName(e, "url"), "array" == n || "object" == n) {
				var o = this.childs ? this.childs.length: 0;
				e.title = this.type + " containing " + o + " items"
			} else "string" == n && util.isUrl(t) ? this.editor.mode.edit && (e.title = "Ctrl+Click or Ctrl+Enter to open url in new window") : e.title = "";
			this.searchValueActive ? util.addClassName(e, "highlight-active") : util.removeClassName(e, "highlight-active"),
			this.searchValue ? util.addClassName(e, "highlight") : util.removeClassName(e, "highlight"),
			util.stripFormatting(e)
		}
	},
	r.prototype._updateDomField = function() {
		var e = this.dom.field;
		if (e) {
			var t = String(this.field) == "" && this.parent.type != "array";
			t ? util.addClassName(e, "empty") : util.removeClassName(e, "empty"),
			this.searchFieldActive ? util.addClassName(e, "highlight-active") : util.removeClassName(e, "highlight-active"),
			this.searchField ? util.addClassName(e, "highlight") : util.removeClassName(e, "highlight"),
			util.stripFormatting(e)
		}
	},
	r.prototype._getDomField = function(e) {
		if (this.dom.field && this.fieldEditable && (this.fieldInnerText = util.getInnerText(this.dom.field)), this.fieldInnerText != void 0) try {
			var t = this._unescapeHTML(this.fieldInnerText);
			if (t !== this.field) {
				var n = this.field;
				this.field = t,
				this.editor._onAction("editField", {
					node: this,
					oldValue: n,
					newValue: t,
					oldSelection: this.editor.selection,
					newSelection: this.editor.getSelection()
				})
			}
		} catch(r) {
			if (this.field = void 0, 1 != e) throw r
		}
	},
	r.prototype.clearDom = function() {
		this.dom = {}
	},
	r.prototype.getDom = function() {
		var e = this.dom;
		if (e.tr) return e.tr;
		if (e.tr = document.createElement("tr"), e.tr.node = this, this.editor.mode.edit) {
			var t = document.createElement("td");
			if (this.parent) {
				var n = document.createElement("button");
				e.drag = n,
				n.className = "dragarea",
				n.title = "Drag to move this field (Alt+Shift+Arrows)",
				t.appendChild(n)
			}
			e.tr.appendChild(t);
			var r = document.createElement("td"),
			i = document.createElement("button");
			e.menu = i,
			i.className = "contextmenu",
			i.title = "Click to open the actions menu (Ctrl+M)",
			r.appendChild(e.menu),
			e.tr.appendChild(r)
		}
		var s = document.createElement("td");
		return e.tr.appendChild(s),
		e.tree = this._createDomTree(),
		s.appendChild(e.tree),
		this.updateDom({
			updateIndexes: !0
		}),
		e.tr
	},
	r.prototype._onDragStart = function(e) {
		e = e || window.event;
		var t = this;
		this.mousemove || (this.mousemove = util.addEventListener(document, "mousemove",
		function(e) {
			t._onDrag(e)
		})),
		this.mouseup || (this.mouseup = util.addEventListener(document, "mouseup",
		function(e) {
			t._onDragEnd(e)
		})),
		this.editor.highlighter.lock(),
		this.drag = {
			oldCursor: document.body.style.cursor,
			startParent: this.parent,
			startIndex: this.parent.childs.indexOf(this),
			mouseX: util.getMouseX(e),
			level: this.getLevel()
		},
		document.body.style.cursor = "move",
		util.preventDefault(e)
	},
	r.prototype._onDrag = function(e) {
		e = e || window.event;
		var t, n, s, o, u, a, f, l, c, h, p, d, v, m, g = util.getMouseY(e),
		y = util.getMouseX(e),
		b = !1;
		if (t = this.dom.tr, c = util.getAbsoluteTop(t), d = t.offsetHeight, c > g) {
			n = t;
			do n = n.previousSibling,
			f = r.getNodeFromTarget(n),
			h = n ? util.getAbsoluteTop(n) : 0;
			while (n && h > g);
			f && !f.parent && (f = void 0),
			f || (a = t.parentNode.firstChild, n = a ? a.nextSibling: void 0, f = r.getNodeFromTarget(n), f == this && (f = void 0)),
			f && (n = f.dom.tr, h = n ? util.getAbsoluteTop(n) : 0, g > h + d && (f = void 0)),
			f && (f.parent.moveBefore(this, f), b = !0)
		} else if (u = this.expanded && this.append ? this.append.getDom() : this.dom.tr, o = u ? u.nextSibling: void 0) {
			p = util.getAbsoluteTop(o),
			s = o;
			do l = r.getNodeFromTarget(s),
			s && (v = s.nextSibling ? util.getAbsoluteTop(s.nextSibling) : 0, m = s ? v - p: 0, l.parent.childs.length == 1 && l.parent.childs[0] == this && (c += 23)),
			s = s.nextSibling;
			while (s && g > c + m);
			if (l && l.parent) {
				var w = y - this.drag.mouseX,
				E = Math.round(w / 24 / 2),
				S = this.drag.level + E,
				x = l.getLevel();
				for (n = l.dom.tr.previousSibling; S > x && n;) {
					if (f = r.getNodeFromTarget(n), f != this && !f._isChildOf(this)) {
						if (! (f instanceof i)) break;
						var T = f.parent.childs;
						if (! (T.length > 1 || T.length == 1 && T[0] != this)) break;
						l = r.getNodeFromTarget(n),
						x = l.getLevel()
					}
					n = n.previousSibling
				}
				u.nextSibling != l.dom.tr && (l.parent.moveBefore(this, l), b = !0)
			}
		}
		b && (this.drag.mouseX = y, this.drag.level = this.getLevel()),
		this.editor.startAutoScroll(g),
		util.preventDefault(e)
	},
	r.prototype._onDragEnd = function(e) {
		e = e || window.event;
		var t = {
			node: this,
			startParent: this.drag.startParent,
			startIndex: this.drag.startIndex,
			endParent: this.parent,
			endIndex: this.parent.childs.indexOf(this)
		}; (t.startParent != t.endParent || t.startIndex != t.endIndex) && this.editor._onAction("moveNode", t),
		document.body.style.cursor = this.drag.oldCursor,
		this.editor.highlighter.unlock(),
		delete this.drag,
		this.mousemove && (util.removeEventListener(document, "mousemove", this.mousemove), delete this.mousemove),
		this.mouseup && (util.removeEventListener(document, "mouseup", this.mouseup), delete this.mouseup),
		this.editor.stopAutoScroll(),
		util.preventDefault(e)
	},
	r.prototype._isChildOf = function(e) {
		for (var t = this.parent; t;) {
			if (t == e) return ! 0;
			t = t.parent
		}
		return ! 1
	},
	r.prototype._createDomField = function() {
		return document.createElement("div")
	},
	r.prototype.setHighlight = function(e) {
		this.dom.tr && (this.dom.tr.className = e ? "highlight": "", this.append && this.append.setHighlight(e), this.childs && this.childs.forEach(function(t) {
			t.setHighlight(e)
		}))
	},
	r.prototype.updateValue = function(e) {
		this.value = e,
		this.updateDom()
	},
	r.prototype.updateField = function(e) {
		this.field = e,
		this.updateDom()
	},
	r.prototype.updateDom = function(e) {
		var t = this.dom.tree;
		t && (t.style.marginLeft = this.getLevel() * 24 + "px");
		var n = this.dom.field;
		if (n) {
			this.fieldEditable == 1 ? (n.contentEditable = this.editor.mode.edit, n.spellcheck = !1, n.className = "field") : n.className = "readonly";
			var r;
			r = this.index != void 0 ? this.index: this.field != void 0 ? this.field: this._hasChilds() ? this.type: "",
			n.innerHTML = this._escapeHTML(r)
		}
		var i = this.dom.value;
		if (i) {
			var s = this.childs ? this.childs.length: 0;
			i.innerHTML = this.type == "array" ? "[" + s + "]": this.type == "object" ? "{" + s + "}": this._escapeHTML(this.value)
		}
		this._updateDomField(),
		this._updateDomValue(),
		e && e.updateIndexes == 1 && this._updateDomIndexes(),
		e && e.recurse == 1 && this.childs && this.childs.forEach(function(t) {
			t.updateDom(e)
		}),
		this.append && this.append.updateDom()
	},
	r.prototype._updateDomIndexes = function() {
		var e = this.dom.value,
		t = this.childs;
		e && t && (this.type == "array" ? t.forEach(function(e, t) {
			e.index = t;
			var n = e.dom.field;
			n && (n.innerHTML = t)
		}) : this.type == "object" && t.forEach(function(e) {
			e.index != void 0 && (delete e.index, e.field == void 0 && (e.field = ""))
		}))
	},
	r.prototype._createDomValue = function() {
		var e;
		return this.type == "array" ? (e = document.createElement("div"), e.className = "readonly", e.innerHTML = "[...]") : this.type == "object" ? (e = document.createElement("div"), e.className = "readonly", e.innerHTML = "{...}") : !this.editor.mode.edit && util.isUrl(this.value) ? (e = document.createElement("a"), e.className = "value", e.href = this.value, e.target = "_blank", e.innerHTML = this._escapeHTML(this.value)) : (e = document.createElement("div"), e.contentEditable = !this.editor.mode.view, e.spellcheck = !1, e.className = "value", e.innerHTML = this._escapeHTML(this.value)),
		e
	},
	r.prototype._createDomExpandButton = function() {
		var e = document.createElement("button");
		return this._hasChilds() ? (e.className = this.expanded ? "expanded": "collapsed", e.title = "Click to expand/collapse this field (Ctrl+E). \nCtrl+Click to expand/collapse including all childs.") : (e.className = "invisible", e.title = ""),
		e
	},
	r.prototype._createDomTree = function() {
		var e = this.dom,
		t = document.createElement("table"),
		n = document.createElement("tbody");
		t.style.borderCollapse = "collapse",
		t.className = "values",
		t.appendChild(n);
		var r = document.createElement("tr");
		n.appendChild(r);
		var i = document.createElement("td");
		i.className = "tree",
		r.appendChild(i),
		e.expand = this._createDomExpandButton(),
		i.appendChild(e.expand),
		e.tdExpand = i;
		var s = document.createElement("td");
		s.className = "tree",
		r.appendChild(s),
		e.field = this._createDomField(),
		s.appendChild(e.field),
		e.tdField = s;
		var o = document.createElement("td");
		o.className = "tree",
		r.appendChild(o),
		this.type != "object" && this.type != "array" && (o.appendChild(document.createTextNode(":")), o.className = "separator"),
		e.tdSeparator = o;
		var u = document.createElement("td");
		return u.className = "tree",
		r.appendChild(u),
		e.value = this._createDomValue(),
		u.appendChild(e.value),
		e.tdValue = u,
		t
	},
	r.prototype.onEvent = function(e) {
		var t, n = e.type,
		r = e.target || e.srcElement,
		i = this.dom,
		s = this,
		o = this._hasChilds();
		if ((r == i.drag || r == i.menu) && ("mouseover" == n ? this.editor.highlighter.highlight(this) : "mouseout" == n && this.editor.highlighter.unhighlight()), "mousedown" == n && r == i.drag && this._onDragStart(e), "click" == n && r == i.menu) {
			var u = s.editor.highlighter;
			u.highlight(s),
			u.lock(),
			util.addClassName(i.menu, "selected"),
			this.showContextMenu(i.menu,
			function() {
				util.removeClassName(i.menu, "selected"),
				u.unlock(),
				u.unhighlight()
			})
		}
		if ("click" == n && r == i.expand && o) {
			var a = e.ctrlKey;
			this._onExpand(a)
		}
		var f = i.value;
		if (r == f) switch (n) {
		case "focus":
			t = this;
			break;
		case "blur":
		case "change":
			this._getDomValue(!0),
			this._updateDomValue(),
			this.value && (f.innerHTML = this._escapeHTML(this.value));
			break;
		case "input":
			this._getDomValue(!0),
			this._updateDomValue();
			break;
		case "keydown":
		case "mousedown":
			this.editor.selection = this.editor.getSelection();
			break;
		case "click":
			e.ctrlKey && this.editor.mode.edit && util.isUrl(this.value) && window.open(this.value, "_blank");
			break;
		case "keyup":
			this._getDomValue(!0),
			this._updateDomValue();
			break;
		case "cut":
		case "paste":
			setTimeout(function() {
				s._getDomValue(!0),
				s._updateDomValue()
			},
			1)
		}
		var l = i.field;
		if (r == l) switch (n) {
		case "focus":
			t = this;
			break;
		case "blur":
		case "change":
			this._getDomField(!0),
			this._updateDomField(),
			this.field && (l.innerHTML = this._escapeHTML(this.field));
			break;
		case "input":
			this._getDomField(!0),
			this._updateDomField();
			break;
		case "keydown":
		case "mousedown":
			this.editor.selection = this.editor.getSelection();
			break;
		case "keyup":
			this._getDomField(!0),
			this._updateDomField();
			break;
		case "cut":
		case "paste":
			setTimeout(function() {
				s._getDomField(!0),
				s._updateDomField()
			},
			1)
		}
		var c = i.tree;
		if (r == c.parentNode) switch (n) {
		case "click":
			var h = e.offsetX != void 0 ? e.offsetX < (this.getLevel() + 1) * 24 : util.getMouseX(e) < util.getAbsoluteLeft(i.tdSeparator);
			h || o ? l && (util.setEndOfContentEditable(l), l.focus()) : f && (util.setEndOfContentEditable(f), f.focus())
		}
		if (r == i.tdExpand && !o || r == i.tdField || r == i.tdSeparator) switch (n) {
		case "click":
			l && (util.setEndOfContentEditable(l), l.focus())
		}
		"keydown" == n && this.onKeyDown(e)
	},
	r.prototype.onKeyDown = function(e) {
		var t, n, s, o, u = e.which || e.keyCode,
		a = e.target || e.srcElement,
		f = e.ctrlKey,
		l = e.shiftKey,
		c = e.altKey,
		h = !1;
		if (13 == u) {
			if (a == this.dom.value)(!this.editor.mode.edit || e.ctrlKey) && util.isUrl(this.value) && (window.open(this.value, "_blank"), h = !0);
			else if (a == this.dom.expand) {
				var p = this._hasChilds();
				if (p) {
					var d = e.ctrlKey;
					this._onExpand(d),
					a.focus(),
					h = !0
				}
			}
		} else if (68 == u) f && (this._onDuplicate(), h = !0);
		else if (69 == u) f && (this._onExpand(l), a.focus(), h = !0);
		else if (77 == u) f && (this.showContextMenu(a), h = !0);
		else if (46 == u) f && (this._onRemove(), h = !0);
		else if (45 == u) f && !l ? (this._onInsertBefore(), h = !0) : f && l && (this._onInsertAfter(), h = !0);
		else if (35 == u) {
			if (c) {
				var v = this._lastNode();
				v && v.focus(r.focusElement || this._getElementName(a)),
				h = !0
			}
		} else if (36 == u) {
			if (c) {
				var m = this._firstNode();
				m && m.focus(r.focusElement || this._getElementName(a)),
				h = !0
			}
		} else if (37 == u) {
			if (c && !l) {
				var g = this._previousElement(a);
				g && this.focus(this._getElementName(g)),
				h = !0
			} else if (c && l) {
				if (this.expanded) {
					var y = this.getAppend();
					s = y ? y.nextSibling: void 0
				} else {
					var b = this.getDom();
					s = b.nextSibling
				}
				s && (n = r.getNodeFromTarget(s), o = s.nextSibling, S = r.getNodeFromTarget(o), n && n instanceof i && this.parent.childs.length != 1 && S && S.parent && (S.parent.moveBefore(this, S), this.focus(r.focusElement || this._getElementName(a))))
			}
		} else if (38 == u) c && !l ? (t = this._previousNode(), t && t.focus(r.focusElement || this._getElementName(a)), h = !0) : c && l && (t = this._previousNode(), t && t.parent && (t.parent.moveBefore(this, t), this.focus(r.focusElement || this._getElementName(a))), h = !0);
		else if (39 == u) {
			if (c && !l) {
				var w = this._nextElement(a);
				w && this.focus(this._getElementName(w)),
				h = !0
			} else if (c && l) {
				b = this.getDom();
				var E = b.previousSibling;
				E && (t = r.getNodeFromTarget(E), t && t.parent && t instanceof i && !t.isVisible() && (t.parent.moveBefore(this, t), this.focus(r.focusElement || this._getElementName(a))))
			}
		} else if (40 == u) if (c && !l) n = this._nextNode(),
		n && n.focus(r.focusElement || this._getElementName(a)),
		h = !0;
		else if (c && l) {
			n = this.expanded ? this.append ? this.append._nextNode() : void 0 : this._nextNode(),
			s = n ? n.getDom() : void 0,
			o = this.parent.childs.length == 1 ? s: s ? s.nextSibling: void 0;
			var S = r.getNodeFromTarget(o);
			S && S.parent && (S.parent.moveBefore(this, S), this.focus(r.focusElement || this._getElementName(a))),
			h = !0
		}
		h && (util.preventDefault(e), util.stopPropagation(e))
	},
	r.prototype._onExpand = function(e) {
		if (e) {
			var t = this.dom.tr.parentNode,
			n = t.parentNode,
			r = n.scrollTop;
			n.removeChild(t)
		}
		this.expanded ? this.collapse(e) : this.expand(e),
		e && (n.appendChild(t), n.scrollTop = r)
	},
	r.prototype._onRemove = function() {
		this.editor.highlighter.unhighlight();
		var e = this.parent.childs,
		t = e.indexOf(this),
		n = this.editor.getSelection();
		e[t + 1] ? e[t + 1].focus() : e[t - 1] ? e[t - 1].focus() : this.parent.focus();
		var r = this.editor.getSelection();
		this.parent._remove(this),
		this.editor._onAction("removeNode", {
			node: this,
			parent: this.parent,
			index: t,
			oldSelection: n,
			newSelection: r
		})
	},
	r.prototype._onDuplicate = function() {
		var e = this.editor.getSelection(),
		t = this.parent._duplicate(this);
		t.focus();
		var n = this.editor.getSelection();
		this.editor._onAction("duplicateNode", {
			node: this,
			clone: t,
			parent: this.parent,
			oldSelection: e,
			newSelection: n
		})
	},
	r.prototype._onInsertBefore = function(e, t, n) {
		var i = this.editor.getSelection(),
		s = new r(this.editor, {
			field: void 0 != e ? e: "",
			value: void 0 != t ? t: "",
			type: n
		});
		s.expand(!0),
		this.parent.insertBefore(s, this),
		this.editor.highlighter.unhighlight(),
		s.focus("field");
		var o = this.editor.getSelection();
		this.editor._onAction("insertBeforeNode", {
			node: s,
			beforeNode: this,
			parent: this.parent,
			oldSelection: i,
			newSelection: o
		})
	},
	r.prototype._onInsertAfter = function(e, t, n) {
		var i = this.editor.getSelection(),
		s = new r(this.editor, {
			field: void 0 != e ? e: "",
			value: void 0 != t ? t: "",
			type: n
		});
		s.expand(!0),
		this.parent.insertAfter(s, this),
		this.editor.highlighter.unhighlight(),
		s.focus("field");
		var o = this.editor.getSelection();
		this.editor._onAction("insertAfterNode", {
			node: s,
			afterNode: this,
			parent: this.parent,
			oldSelection: i,
			newSelection: o
		})
	},
	r.prototype._onAppend = function(e, t, n) {
		var i = this.editor.getSelection(),
		s = new r(this.editor, {
			field: void 0 != e ? e: "",
			value: void 0 != t ? t: "",
			type: n
		});
		s.expand(!0),
		this.parent.appendChild(s),
		this.editor.highlighter.unhighlight(),
		s.focus("field");
		var o = this.editor.getSelection();
		this.editor._onAction("appendNode", {
			node: s,
			parent: this.parent,
			oldSelection: i,
			newSelection: o
		})
	},
	r.prototype._onChangeType = function(e) {
		var t = this.type;
		if (e != t) {
			var n = this.editor.getSelection();
			this.changeType(e);
			var r = this.editor.getSelection();
			this.editor._onAction("changeType", {
				node: this,
				oldType: t,
				newType: e,
				oldSelection: n,
				newSelection: r
			})
		}
	},
	r.prototype._onSort = function(e) {
		if (this._hasChilds()) {
			var t = "desc" == e ? -1 : 1,
			n = this.type == "array" ? "value": "field";
			this.hideChilds();
			var r = this.childs,
			i = this.sort;
			this.childs = this.childs.concat(),
			this.childs.sort(function(e, r) {
				return e[n] > r[n] ? t: e[n] < r[n] ? -t: 0
			}),
			this.sort = 1 == t ? "asc": "desc",
			this.editor._onAction("sort", {
				node: this,
				oldChilds: r,
				oldSort: i,
				newChilds: this.childs,
				newSort: this.sort
			}),
			this.showChilds()
		}
	},
	r.prototype.getAppend = function() {
		return this.append || (this.append = new i(this.editor), this.append.setParent(this)),
		this.append.getDom()
	},
	r.getNodeFromTarget = function(e) {
		for (; e;) {
			if (e.node) return e.node;
			e = e.parentNode
		}
		return void 0
	},
	r.prototype._previousNode = function() {
		var e = null,
		t = this.getDom();
		if (t && t.parentNode) {
			var n = t;
			do n = n.previousSibling,
			e = r.getNodeFromTarget(n);
			while (n && e instanceof i && !e.isVisible())
		}
		return e
	},
	r.prototype._nextNode = function() {
		var e = null,
		t = this.getDom();
		if (t && t.parentNode) {
			var n = t;
			do n = n.nextSibling,
			e = r.getNodeFromTarget(n);
			while (n && e instanceof i && !e.isVisible())
		}
		return e
	},
	r.prototype._firstNode = function() {
		var e = null,
		t = this.getDom();
		if (t && t.parentNode) {
			var n = t.parentNode.firstChild;
			e = r.getNodeFromTarget(n)
		}
		return e
	},
	r.prototype._lastNode = function() {
		var e = null,
		t = this.getDom();
		if (t && t.parentNode) {
			var n = t.parentNode.lastChild;
			for (e = r.getNodeFromTarget(n); n && e instanceof i && !e.isVisible();) n = n.previousSibling,
			e = r.getNodeFromTarget(n)
		}
		return e
	},
	r.prototype._previousElement = function(e) {
		var t = this.dom;
		switch (e) {
		case t.value:
			if (this.fieldEditable) return t.field;
		case t.field:
			if (this._hasChilds()) return t.expand;
		case t.expand:
			return t.menu;
		case t.menu:
			if (t.drag) return t.drag;
		default:
			return null
		}
	},
	r.prototype._nextElement = function(e) {
		var t = this.dom;
		switch (e) {
		case t.drag:
			return t.menu;
		case t.menu:
			if (this._hasChilds()) return t.expand;
		case t.expand:
			if (this.fieldEditable) return t.field;
		case t.field:
			if (!this._hasChilds()) return t.value;
		default:
			return null
		}
	},
	r.prototype._getElementName = function(e) {
		var t = this.dom;
		for (var n in t) if (t.hasOwnProperty(n) && t[n] == e) return n;
		return null
	},
	r.prototype._hasChilds = function() {
		return this.type == "array" || this.type == "object"
	},
	r.TYPE_TITLES = {
		auto: 'Field type "auto". The field type is automatically determined from the value and can be a string, number, boolean, or null.',
		object: 'Field type "object". An object contains an unordered set of key/value pairs.',
		array: 'Field type "array". An array contains an ordered collection of values.',
		string: 'Field type "string". Field type is not determined from the value, but always returned as string.'
	},
	r.prototype.showContextMenu = function(e, t) {
		var n = this,
		i = r.TYPE_TITLES,
		o = [];
		if (o.push({
			text: "Type",
			title: "Change the type of this field",
			className: "type-" + this.type,
			submenu: [{
				text: "Auto",
				className: "type-auto" + (this.type == "auto" ? " selected": ""),
				title: i.auto,
				click: function() {
					n._onChangeType("auto")
				}
			},
			{
				text: "Array",
				className: "type-array" + (this.type == "array" ? " selected": ""),
				title: i.array,
				click: function() {
					n._onChangeType("array")
				}
			},
			{
				text: "Object",
				className: "type-object" + (this.type == "object" ? " selected": ""),
				title: i.object,
				click: function() {
					n._onChangeType("object")
				}
			},
			{
				text: "String",
				className: "type-string" + (this.type == "string" ? " selected": ""),
				title: i.string,
				click: function() {
					n._onChangeType("string")
				}
			}]
		}), this._hasChilds()) {
			var u = this.sort == "asc" ? "desc": "asc";
			o.push({
				text: "Sort",
				title: "Sort the childs of this " + this.type,
				className: "sort-" + u,
				click: function() {
					n._onSort(u)
				},
				submenu: [{
					text: "Ascending",
					className: "sort-asc",
					title: "Sort the childs of this " + this.type + " in ascending order",
					click: function() {
						n._onSort("asc")
					}
				},
				{
					text: "Descending",
					className: "sort-desc",
					title: "Sort the childs of this " + this.type + " in descending order",
					click: function() {
						n._onSort("desc")
					}
				}]
			})
		}
		if (this.parent && this.parent._hasChilds()) {
			o.push({
				type: "separator"
			});
			var a = n.parent.childs;
			n == a[a.length - 1] && o.push({
				text: "Append",
				title: "Append a new field with type 'auto' after this field (Ctrl+Shift+Ins)",
				submenuTitle: "Select the type of the field to be appended",
				className: "append",
				click: function() {
					n._onAppend("", "", "auto")
				},
				submenu: [{
					text: "Auto",
					className: "type-auto",
					title: i.auto,
					click: function() {
						n._onAppend("", "", "auto")
					}
				},
				{
					text: "Array",
					className: "type-array",
					title: i.array,
					click: function() {
						n._onAppend("", [])
					}
				},
				{
					text: "Object",
					className: "type-object",
					title: i.object,
					click: function() {
						n._onAppend("", {})
					}
				},
				{
					text: "String",
					className: "type-string",
					title: i.string,
					click: function() {
						n._onAppend("", "", "string")
					}
				}]
			}),
			o.push({
				text: "Insert",
				title: "Insert a new field with type 'auto' before this field (Ctrl+Ins)",
				submenuTitle: "Select the type of the field to be inserted",
				className: "insert",
				click: function() {
					n._onInsertBefore("", "", "auto")
				},
				submenu: [{
					text: "Auto",
					className: "type-auto",
					title: i.auto,
					click: function() {
						n._onInsertBefore("", "", "auto")
					}
				},
				{
					text: "Array",
					className: "type-array",
					title: i.array,
					click: function() {
						n._onInsertBefore("", [])
					}
				},
				{
					text: "Object",
					className: "type-object",
					title: i.object,
					click: function() {
						n._onInsertBefore("", {})
					}
				},
				{
					text: "String",
					className: "type-string",
					title: i.string,
					click: function() {
						n._onInsertBefore("", "", "string")
					}
				}]
			}),
			o.push({
				text: "Duplicate",
				title: "Duplicate this field (Ctrl+D)",
				className: "duplicate",
				click: function() {
					n._onDuplicate()
				}
			}),
			o.push({
				text: "Remove",
				title: "Remove this field (Ctrl+Del)",
				className: "remove",
				click: function() {
					n._onRemove()
				}
			})
		}
		var f = new s(o, {
			close: t
		});
		f.show(e)
	},
	r.prototype._getType = function(e) {
		return e instanceof Array ? "array": e instanceof Object ? "object": "string" == typeof e && typeof this._stringCast(e) != "string" ? "string": "auto"
	},
	r.prototype._stringCast = function(e) {
		var t = e.toLowerCase(),
		n = Number(e),
		r = parseFloat(e);
		return "" == e ? "": "null" == t ? null: "true" == t ? !0 : "false" == t ? !1 : isNaN(n) || isNaN(r) ? e: n
	},
	r.prototype._escapeHTML = function(e) {
		var t = String(e).replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/  /g, " &nbsp;").replace(/^ /, "&nbsp;").replace(/ $/, "&nbsp;"),
		n = JSON.stringify(t);
		return n.substring(1, n.length - 1)
	},
	r.prototype._unescapeHTML = function(e) {
		var t = '"' + this._escapeJSON(e) + '"',
		n = util.parse(t);
		return n.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nbsp;/g, " ")
	},
	r.prototype._escapeJSON = function(e) {
		for (var t = "",
		n = 0,
		r = e.length; r > n;) {
			var i = e.charAt(n);
			"\n" == i ? t += "\\n": "\\" == i ? (t += i, n++, i = e.charAt(n), '"\\/bfnrtu'.indexOf(i) == -1 && (t += "\\"), t += i) : t += '"' == i ? '\\"': i,
			n++
		}
		return t
	},
	i.prototype = new r,
	i.prototype.getDom = function() {
		var e = this.dom;
		if (e.tr) return e.tr;
		var t = document.createElement("tr");
		if (t.node = this, e.tr = t, this.editor.mode.edit) {
			e.tdDrag = document.createElement("td");
			var n = document.createElement("td");
			e.tdMenu = n;
			var r = document.createElement("button");
			r.className = "contextmenu",
			r.title = "Click to open the actions menu (Ctrl+M)",
			e.menu = r,
			n.appendChild(e.menu)
		}
		var i = document.createElement("td"),
		s = document.createElement("div");
		return s.innerHTML = "(empty)",
		s.className = "readonly",
		i.appendChild(s),
		e.td = i,
		e.text = s,
		this.updateDom(),
		t
	},
	i.prototype.updateDom = function() {
		var e = this.dom,
		t = e.td;
		t && (t.style.paddingLeft = this.getLevel() * 24 + 26 + "px");
		var n = e.text;
		n && (n.innerHTML = "(empty " + this.parent.type + ")");
		var r = e.tr;
		this.isVisible() ? e.tr.firstChild || (e.tdDrag && r.appendChild(e.tdDrag), e.tdMenu && r.appendChild(e.tdMenu), r.appendChild(t)) : e.tr.firstChild && (e.tdDrag && r.removeChild(e.tdDrag), e.tdMenu && r.removeChild(e.tdMenu), r.removeChild(t))
	},
	i.prototype.isVisible = function() {
		return this.parent.childs.length == 0
	},
	i.prototype.showContextMenu = function(e, t) {
		var n = this,
		i = r.TYPE_TITLES,
		o = [{
			text: "Append",
			title: "Append a new field with type 'auto' (Ctrl+Shift+Ins)",
			submenuTitle: "Select the type of the field to be appended",
			className: "insert",
			click: function() {
				n._onAppend("", "", "auto")
			},
			submenu: [{
				text: "Auto",
				className: "type-auto",
				title: i.auto,
				click: function() {
					n._onAppend("", "", "auto")
				}
			},
			{
				text: "Array",
				className: "type-array",
				title: i.array,
				click: function() {
					n._onAppend("", [])
				}
			},
			{
				text: "Object",
				className: "type-object",
				title: i.object,
				click: function() {
					n._onAppend("", {})
				}
			},
			{
				text: "String",
				className: "type-string",
				title: i.string,
				click: function() {
					n._onAppend("", "", "string")
				}
			}]
		}],
		u = new s(o, {
			close: t
		});
		u.show(e)
	},
	i.prototype.onEvent = function(e) {
		var t = e.type,
		n = e.target || e.srcElement,
		r = this.dom,
		i = r.menu;
		if (n == i && ("mouseover" == t ? this.editor.highlighter.highlight(this.parent) : "mouseout" == t && this.editor.highlighter.unhighlight()), "click" == t && n == r.menu) {
			var s = this.editor.highlighter;
			s.highlight(this.parent),
			s.lock(),
			util.addClassName(r.menu, "selected"),
			this.showContextMenu(r.menu,
			function() {
				util.removeClassName(r.menu, "selected"),
				s.unlock(),
				s.unhighlight()
			})
		}
		"keydown" == t && this.onKeyDown(e)
	},
	s.prototype._getVisibleButtons = function() {
		var e = [],
		t = this;
		return this.dom.items.forEach(function(n) {
			e.push(n.button),
			n.buttonExpand && e.push(n.buttonExpand),
			n.subItems && n == t.expandedItem && n.subItems.forEach(function(t) {
				e.push(t.button),
				t.buttonExpand && e.push(t.buttonExpand)
			})
		}),
		e
	},
	s.visibleMenu = void 0,
	s.prototype.show = function(e) {
		this.hide();
		var t = util.getWindowHeight(),
		n = e.offsetHeight,
		r = this.maxHeight,
		i = util.getAbsoluteLeft(e),
		o = util.getAbsoluteTop(e);
		t > o + n + r ? (this.dom.menu.style.left = i + "px", this.dom.menu.style.top = o + n + "px", this.dom.menu.style.bottom = "") : (this.dom.menu.style.left = i + "px", this.dom.menu.style.top = "", this.dom.menu.style.bottom = t - o + "px"),
		document.body.appendChild(this.dom.menu);
		var u = this,
		a = this.dom.list;
		this.eventListeners.mousedown = util.addEventListener(document, "mousedown",
		function(e) {
			e = e || window.event;
			var t = e.target || e.srcElement;
			t == a || u._isChildOf(t, a) || (u.hide(), util.stopPropagation(e), util.preventDefault(e))
		}),
		this.eventListeners.mousewheel = util.addEventListener(document, "mousewheel",
		function() {
			util.stopPropagation(event),
			util.preventDefault(event)
		}),
		this.eventListeners.keydown = util.addEventListener(document, "keydown",
		function(e) {
			u._onKeyDown(e)
		}),
		this.selection = util.getSelection(),
		this.anchor = e,
		setTimeout(function() {
			u.dom.focusButton.focus()
		},
		0),
		s.visibleMenu && s.visibleMenu.hide(),
		s.visibleMenu = this
	},
	s.prototype.hide = function() {
		this.dom.menu.parentNode && (this.dom.menu.parentNode.removeChild(this.dom.menu), this.onClose && this.onClose());
		for (var e in this.eventListeners) if (this.eventListeners.hasOwnProperty(e)) {
			var t = this.eventListeners[e];
			t && util.removeEventListener(document, e, t),
			delete this.eventListeners[e]
		}
		s.visibleMenu == this && (s.visibleMenu = void 0)
	},
	s.prototype._onExpandItem = function(e) {
		var t = this,
		n = e == this.expandedItem,
		r = this.expandedItem;
		if (r && (r.ul.style.height = "0", r.ul.style.padding = "", setTimeout(function() {
			t.expandedItem != r && (r.ul.style.display = "", util.removeClassName(r.ul.parentNode, "selected"))
		},
		300), this.expandedItem = void 0), !n) {
			var i = e.ul;
			i.style.display = "block",
			i.clientHeight,
			setTimeout(function() {
				t.expandedItem == e && (i.style.height = i.childNodes.length * 24 + "px", i.style.padding = "5px 10px")
			},
			0),
			util.addClassName(i.parentNode, "selected"),
			this.expandedItem = e
		}
	},
	s.prototype._onKeyDown = function(e) {
		e = e || window.event;
		var t, n, r, i, s = e.target || e.srcElement,
		o = e.which || e.keyCode,
		u = !1;
		27 == o ? (this.selection && util.setSelection(this.selection), this.anchor && this.anchor.focus(), this.hide(), u = !0) : 9 == o ? e.shiftKey ? (t = this._getVisibleButtons(), n = t.indexOf(s), 0 == n && (t[t.length - 1].focus(), u = !0)) : (t = this._getVisibleButtons(), n = t.indexOf(s), n == t.length - 1 && (t[0].focus(), u = !0)) : 37 == o ? (s.className == "expand" && (t = this._getVisibleButtons(), n = t.indexOf(s), r = t[n - 1], r && r.focus()), u = !0) : 38 == o ? (t = this._getVisibleButtons(), n = t.indexOf(s), r = t[n - 1], r && r.className == "expand" && (r = t[n - 2]), r || (r = t[t.length - 1]), r && r.focus(), u = !0) : 39 == o ? (t = this._getVisibleButtons(), n = t.indexOf(s), i = t[n + 1], i && i.className == "expand" && i.focus(), u = !0) : 40 == o && (t = this._getVisibleButtons(), n = t.indexOf(s), i = t[n + 1], i && i.className == "expand" && (i = t[n + 2]), i || (i = t[0]), i && (i.focus(), u = !0), u = !0),
		u && (util.stopPropagation(e), util.preventDefault(e))
	},
	s.prototype._isChildOf = function(e, t) {
		for (var n = e.parentNode; n;) {
			if (n == t) return ! 0;
			n = n.parentNode
		}
		return ! 1
	},
	o.prototype.onChange = function() {},
	o.prototype.add = function(e, t) {
		this.index++,
		this.history[this.index] = {
			action: e,
			params: t,
			timestamp: new Date
		},
		this.index < this.history.length - 1 && this.history.splice(this.index + 1, this.history.length - this.index - 1),
		this.onChange()
	},
	o.prototype.clear = function() {
		this.history = [],
		this.index = -1,
		this.onChange()
	},
	o.prototype.canUndo = function() {
		return this.index >= 0
	},
	o.prototype.canRedo = function() {
		return this.index < this.history.length - 1
	},
	o.prototype.undo = function() {
		if (this.canUndo()) {
			var e = this.history[this.index];
			if (e) {
				var t = this.actions[e.action];
				t && t.undo ? (t.undo(e.params), e.params.oldSelection && this.editor.setSelection(e.params.oldSelection)) : util.log('Error: unknown action "' + e.action + '"')
			}
			this.index--,
			this.onChange()
		}
	},
	o.prototype.redo = function() {
		if (this.canRedo()) {
			this.index++;
			var e = this.history[this.index];
			if (e) {
				var t = this.actions[e.action];
				t && t.redo ? (t.redo(e.params), e.params.newSelection && this.editor.setSelection(e.params.newSelection)) : util.log('Error: unknown action "' + e.action + '"')
			}
			this.onChange()
		}
	},
	u.prototype.next = function(e) {
		if (this.results != void 0) {
			var t = this.resultIndex != void 0 ? this.resultIndex + 1 : 0;
			t > this.results.length - 1 && (t = 0),
			this._setActiveResult(t, e)
		}
	},
	u.prototype.previous = function(e) {
		if (this.results != void 0) {
			var t = this.results.length - 1,
			n = this.resultIndex != void 0 ? this.resultIndex - 1 : t;
			0 > n && (n = t),
			this._setActiveResult(n, e)
		}
	},
	u.prototype._setActiveResult = function(e, t) {
		if (this.activeResult) {
			var n = this.activeResult.node,
			r = this.activeResult.elem;
			"field" == r ? delete n.searchFieldActive: delete n.searchValueActive,
			n.updateDom()
		}
		if (!this.results || !this.results[e]) return this.resultIndex = void 0,
		this.activeResult = void 0,
		void 0;
		this.resultIndex = e;
		var i = this.results[this.resultIndex].node,
		s = this.results[this.resultIndex].elem;
		"field" == s ? i.searchFieldActive = !0 : i.searchValueActive = !0,
		this.activeResult = this.results[this.resultIndex],
		i.updateDom(),
		i.scrollTo(function() {
			t && i.focus(s)
		})
	},
	u.prototype._clearDelay = function() {
		this.timeout != void 0 && (clearTimeout(this.timeout), delete this.timeout)
	},
	u.prototype._onDelayedSearch = function() {
		this._clearDelay();
		var e = this;
		this.timeout = setTimeout(function(t) {
			e._onSearch(t)
		},
		this.delay)
	},
	u.prototype._onSearch = function(e, t) {
		this._clearDelay();
		var n = this.dom.search.value,
		r = n.length > 0 ? n: void 0;
		if (r != this.lastText || t) if (this.lastText = r, this.results = this.editor.search(r), this._setActiveResult(void 0), void 0 != r) {
			var i = this.results.length;
			switch (i) {
			case 0:
				this.dom.results.innerHTML = "no&nbsp;results";
				break;
			case 1:
				this.dom.results.innerHTML = "1&nbsp;result";
				break;
			default:
				this.dom.results.innerHTML = i + "&nbsp;results"
			}
		} else this.dom.results.innerHTML = ""
	},
	u.prototype._onKeyDown = function(e) {
		e = e || window.event;
		var t = e.which || e.keyCode;
		27 == t ? (this.dom.search.value = "", this._onSearch(e), util.preventDefault(e), util.stopPropagation(e)) : 13 == t && (e.ctrlKey ? this._onSearch(e, !0) : e.shiftKey ? this.previous() : this.next(), util.preventDefault(e), util.stopPropagation(e))
	},
	u.prototype._onKeyUp = function(e) {
		e = e || window.event;
		var t = e.which || e.keyCode;
		27 != t && 13 != t && this._onDelayedSearch(e)
	},
	a.prototype.highlight = function(e) {
		this.locked || (this.node != e && (this.node && this.node.setHighlight(!1), this.node = e, this.node.setHighlight(!0)), this._cancelUnhighlight())
	},
	a.prototype.unhighlight = function() {
		if (!this.locked) {
			var e = this;
			this.node && (this._cancelUnhighlight(), this.unhighlightTimer = setTimeout(function() {
				e.node.setHighlight(!1),
				e.node = void 0,
				e.unhighlightTimer = void 0
			},
			0))
		}
	},
	a.prototype._cancelUnhighlight = function() {
		this.unhighlightTimer && (clearTimeout(this.unhighlightTimer), this.unhighlightTimer = void 0)
	},
	a.prototype.lock = function() {
		this.locked = !0
	},
	a.prototype.unlock = function() {
		this.locked = !1
	},
	util = {},
	Array.prototype.indexOf || (Array.prototype.indexOf = function(e) {
		for (var t = 0; t < this.length; t++) if (this[t] == e) return t;
		return - 1
	}),
	Array.prototype.forEach || (Array.prototype.forEach = function(e, t) {
		for (var n = 0,
		r = this.length; r > n; ++n) e.call(t || this, this[n], n, this)
	}),
	util.parse = function(e) {
		try {
			return JSON.parse(e)
		} catch(t) {
			throw util.validate(e),
			t
		}
	},
	util.validate = function(e) {
		"undefined" != typeof jsonlint ? jsonlint.parse(e) : JSON.parse(e)
	},
	util.extend = function(e, t) {
		for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n]);
		return e
	},
	util.clear = function(e) {
		for (var t in e) e.hasOwnProperty(t) && delete e[t];
		return e
	},
	util.log = function() {};
	var f = /^https?:\/\/\S+$/;
	util.isUrl = function(e) {
		return ("string" == typeof e || e instanceof String) && f.test(e)
	},
	util.getAbsoluteLeft = function(e) {
		for (var t = e.offsetLeft,
		n = document.body,
		r = e.offsetParent; null != r && e != n;) t += r.offsetLeft,
		t -= r.scrollLeft,
		r = r.offsetParent;
		return t
	},
	util.getAbsoluteTop = function(e) {
		for (var t = e.offsetTop,
		n = document.body,
		r = e.offsetParent; null != r && r != n;) t += r.offsetTop,
		t -= r.scrollTop,
		r = r.offsetParent;
		return t
	},
	util.getMouseY = function(e) {
		var t;
		return t = "pageY" in e ? e.pageY: e.clientY + document.documentElement.scrollTop
	},
	util.getMouseX = function(e) {
		var t;
		return t = "pageX" in e ? e.pageX: e.clientX + document.documentElement.scrollLeft
	},
	util.getWindowHeight = function() {
		return "innerHeight" in window ? window.innerHeight: Math.max(document.body.clientHeight, document.documentElement.clientHeight)
	},
	util.addClassName = function(e, t) {
		var n = e.className.split(" ");
		n.indexOf(t) == -1 && (n.push(t), e.className = n.join(" "))
	},
	util.removeClassName = function(e, t) {
		var n = e.className.split(" "),
		r = n.indexOf(t); - 1 != r && (n.splice(r, 1), e.className = n.join(" "))
	},
	util.stripFormatting = function(e) {
		for (var t = e.childNodes,
		n = 0,
		r = t.length; r > n; n++) {
			var i = t[n];
			i.style && i.removeAttribute("style");
			var s = i.attributes;
			if (s) for (var o = s.length - 1; o >= 0; o--) {
				var u = s[o];
				u.specified == 1 && i.removeAttribute(u.name)
			}
			util.stripFormatting(i)
		}
	},
	util.setEndOfContentEditable = function(e) {
		var t, n;
		document.createRange ? (t = document.createRange(), t.selectNodeContents(e), t.collapse(!1), n = window.getSelection(), n.removeAllRanges(), n.addRange(t)) : document.selection && (t = document.body.createTextRange(), t.moveToElementText(e), t.collapse(!1), t.select())
	},
	util.selectContentEditable = function(e) {
		if (e && e.nodeName == "DIV") {
			var t, n;
			window.getSelection && document.createRange ? (n = document.createRange(), n.selectNodeContents(e), t = window.getSelection(), t.removeAllRanges(), t.addRange(n)) : document.body.createTextRange && (n = document.body.createTextRange(), n.moveToElementText(e), n.select())
		}
	},
	util.getSelection = function() {
		if (window.getSelection) {
			var e = window.getSelection();
			if (e.getRangeAt && e.rangeCount) return e.getRangeAt(0)
		} else if (document.selection && document.selection.createRange) return document.selection.createRange();
		return null
	},
	util.setSelection = function(e) {
		if (e) if (window.getSelection) {
			var t = window.getSelection();
			t.removeAllRanges(),
			t.addRange(e)
		} else document.selection && e.select && e.select()
	},
	util.getSelectionOffset = function() {
		var e = util.getSelection();
		return e && "startOffset" in e && "endOffset" in e && e.startContainer && e.startContainer == e.endContainer ? {
			startOffset: e.startOffset,
			endOffset: e.endOffset,
			container: e.startContainer.parentNode
		}: null
	},
	util.setSelectionOffset = function(e) {
		if (document.createRange && window.getSelection) {
			var t = window.getSelection();
			if (t) {
				var n = document.createRange();
				n.setStart(e.container.firstChild, e.startOffset),
				n.setEnd(e.container.firstChild, e.endOffset),
				util.setSelection(n)
			}
		}
	},
	util.getInnerText = function(e, t) {
		var n = void 0 == t;
		if (n && (t = {
			text: "",
			flush: function() {
				var e = this.text;
				return this.text = "",
				e
			},
			set: function(e) {
				this.text = e
			}
		}), e.nodeValue) return t.flush() + e.nodeValue;
		if (e.hasChildNodes()) {
			for (var r = e.childNodes,
			i = "",
			s = 0,
			o = r.length; o > s; s++) {
				var u = r[s];
				if (u.nodeName == "DIV" || u.nodeName == "P") {
					var a = r[s - 1],
					f = a ? a.nodeName: void 0;
					f && "DIV" != f && "P" != f && "BR" != f && (i += "\n", t.flush()),
					i += util.getInnerText(u, t),
					t.set("\n")
				} else u.nodeName == "BR" ? (i += t.flush(), t.set("\n")) : i += util.getInnerText(u, t)
			}
			return i
		}
		return e.nodeName == "P" && util.getInternetExplorerVersion() != -1 ? t.flush() : ""
	},
	util.getInternetExplorerVersion = function() {
		if ( - 1 == l) {
			var e = -1;
			if (navigator.appName == "Microsoft Internet Explorer") {
				var t = navigator.userAgent,
				n = new RegExp("MSIE ([0-9]{1,}[.0-9]{0,})");
				n.exec(t) != null && (e = parseFloat(RegExp.$1))
			}
			l = e
		}
		return l
	};
	var l = -1;
	util.addEventListener = function(e, t, n, r) {
		if (e.addEventListener) return void 0 === r && (r = !1),
		"mousewheel" === t && navigator.userAgent.indexOf("Firefox") >= 0 && (t = "DOMMouseScroll"),
		e.addEventListener(t, n, r),
		n;
		var i = function() {
			return n.call(e, window.event)
		};
		return e.attachEvent("on" + t, i),
		i
	},
	util.removeEventListener = function(e, t, n, r) {
		e.removeEventListener ? (void 0 === r && (r = !1), "mousewheel" === t && navigator.userAgent.indexOf("Firefox") >= 0 && (t = "DOMMouseScroll"), e.removeEventListener(t, n, r)) : e.detachEvent("on" + t, n)
	},
	util.stopPropagation = function(e) {
		e || (e = window.event),
		e.stopPropagation ? e.stopPropagation() : e.cancelBubble = !0
	},
	util.preventDefault = function(e) {
		e || (e = window.event),
		e.preventDefault ? e.preventDefault() : e.returnValue = !1
	};
	var c = {
		JSONEditor: e,
		JSONFormatter: function() {
			throw new Error('JSONFormatter is deprecated. Use JSONEditor with mode "text" or "code" instead')
		},
		util: util
	},
	h = function() {
		for (var e = document.getElementsByTagName("script"), t = 0; t < e.length; t++) {
			var n = e[t].src;
			if (/(^|\/)jsoneditor([-\.]min)?.js$/.test(n)) {
				var r = n.split("?")[0],
				i = r.substring(0, r.length - 2) + "css",
				s = document.createElement("link");
				s.type = "text/css",
				s.rel = "stylesheet",
				s.href = i,
				document.getElementsByTagName("head")[0].appendChild(s);
				break
			}
		}
	};
	"undefined" != typeof module && "undefined" != typeof exports && (h(), module.exports = exports = c),
	"undefined" != typeof require && "undefined" != typeof define ? (h(), define(function() {
		return c
	})) : window.jsoneditor = c
})()