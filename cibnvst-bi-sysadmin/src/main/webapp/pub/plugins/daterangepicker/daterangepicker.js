/**
 * @version: 1.3.21
 * @author: Dan Grossman http://www.dangrossman.info/
 * @copyright: Copyright (c) 2012-2015 Dan Grossman. All rights reserved.
 * @license: Licensed under the MIT license. See http://www.opensource.org/licenses/mit-license.php
 * @website: https://www.improvely.com/
 */
!function (t, e) {
    if ("function" == typeof define && define.amd) define(["moment", "jquery", "exports"], function (a, i, s) {
        t.daterangepicker = e(t, s, a, i)
    }); else if ("undefined" != typeof exports) {
        var a, i = require("moment");
        try {
            a = require("jquery")
        } catch (s) {
            if (a = window.jQuery, !a) throw Error("jQuery dependency not found")
        }
        e(t, exports, i, a)
    } else t.daterangepicker = e(t, {}, t.moment, t.jQuery || t.Zepto || t.ender || t.$)
}(this, function (t, e, a, i) {
    var s = function (t, e, a) {
        this.id = i(".daterangepicker").size() + 1, this.parentEl = "body", this.element = i(t), this.isShowing = !1, this.activeCompare = !1, this.dateTemplate = e.dateTemplate, this.tplHourSelect = e.getTplHourSelect ? e.getTplHourSelect() : '<select class="hour"><option value="0">0时</option><option value="1">1时</option><option value="2">2时</option><option value="3">3时</option><option value="4">4时</option><option value="5">5时</option><option value="6">6时</option><option value="7">7时</option><option value="8">8时</option><option value="9">9时</option><option value="10">10时</option><option value="11">11时</option><option value="12">12时</option><option value="13">13时</option><option value="14">14时</option><option value="15">15时</option><option value="16">16时</option><option value="17">17时</option><option value="18">18时</option><option value="19">19时</option><option value="20">20时</option><option value="21">21时</option><option value="22">22时</option><option value="23">23时</option></select>';
        var s, n = "daterangepicker-chk-" + this.id;
        s = e.getDRPTemplate ? e.getDRPTemplate(n) : '<div class="daterangepicker dropdown-menu"><div class="title"><div>时间范围</div><div>起始时间</div><div>终止时间</div></div><div class="calendar-wrap"><div class="calendar first left"></div><div class="calendar second right"></div><div class="ranges"><div class="range_inputs"><div class="daterangepicker_start_input"><label for="daterangepicker_start"></label><input class="input-mini" type="text" name="daterangepicker_start" value="" /></div><div class="daterangepicker_end_input"><label for="daterangepicker_end"></label><input class="input-mini" type="text" name="daterangepicker_end" value="" /></div><button class="applyBtn" disabled="disabled"></button>&nbsp;<button class="cancelBtn"></button></div></div></div><div class="toolbar"><span class="item time-extend"><label class="sa-checkbox"><input id="' + n + '-time-extend" type="checkbox"><label for="' + n + '-time-extend"></label><span></span></label>&nbsp;<span class="icon-help"></span></span><span class="item compare"><label class="sa-checkbox"><input id="' + n + '1" type="checkbox"><label for="' + n + '1"></label>对比日期</label><span style="display:none;"><div class="dropdown"><button type="button" class="btn btn-default dropdown-toggle btn-small" data-toggle="dropdown" aria-expanded="false"><span class="selected" data-value="last period">上一时段</span><span class="caret"></span></button><ul class="dropdown-menu pull-right sa-small" role="menu"><li><a data-value="last period">上一时段</a></li><li><a data-value="last year">去年同期</a></li><li style="display:none;"><a data-value="custom">自定义</a></li></ul></div><input type="text" class="sa-small"/>&nbsp;-&nbsp;<input type="text" class="sa-small"/></span></span><span class="item relative-date"><label class="sa-checkbox"><input id="' + n + '2" type="checkbox"><label for="' + n + '2"></label>静态时间</label><span>过去 1 天 - 过去 3 天</span></span><span class="item"><button class="cancelBtn btn btn-link btn-small"></button>&nbsp;<button class="applyBtn btn btn-primary btn-small" disabled="disabled"></button></span></div></div>', ("object" != typeof e || null === e) && (e = {}), this.parentEl = i("object" == typeof e && e.parentEl && i(e.parentEl).length ? e.parentEl : this.parentEl), this.container = i(s).appendTo(this.parentEl), e && e.containerClass && this.container.addClass(e.containerClass), e.allowRelative && e.relativeValue && this.container.find(".toolbar .item.relative-date :checkbox").prop("checked", e.relativeValue && 0 === sensorsdata.convertTimeRange(e.relativeValue, !1).length), this.setOptions(e, a), this.updateInputText(), this.element.data("startDate", this.startDate), this.element.data("endDate", this.endDate), this.element.data("compareStartDate", this.compareStartDate), this.element.data("compareEndDate", this.compareEndDate), this.element.data("relativeValue", this.relativeValue), this.container.find(".toolbar .item.relative-date .sa-checkbox").toggle(!!this.allowStatic), this.showInput === !1 && this.container.find(".range_inputs").hide(), this.container.find(".toolbar .item.compare").toggle(this.showCompare), this.container.find(".toolbar .item.relative-date").toggle(this.allowRelative);
        var r = this.container.find(".toolbar .item.time-extend").show();
        i.isEmptyObject(this.timeExtend) ? r.hide() : (r.find(":checkbox").prop("checked", this.timeExtend.isLimit), r.find("label span").text(this.timeExtend.text), r.find("> span").toggle(!!this.timeExtend.tooltip).attr("title", this.timeExtend.tooltip).tooltip(), this.element.data("isLimit", this.timeExtend.isLimit)), this.container.find(".calendar").on("click.daterangepicker", ".prev", i.proxy(this.clickPrev, this)).on("click.daterangepicker", ".next", i.proxy(this.clickNext, this)).on("click.daterangepicker", "td.available", i.proxy(this.clickDate, this)).on("mouseenter.daterangepicker", "td.available", i.proxy(this.hoverDate, this)).on("mouseleave.daterangepicker", "td.available", i.proxy(this.updateFormInputs, this)).on("change.daterangepicker", "select.yearselect", i.proxy(this.updateMonthYear, this)).on("change.daterangepicker", "select.monthselect", i.proxy(this.updateMonthYear, this)).on("change.daterangepicker", "select.hourselect,select.minuteselect,select.secondselect,select.ampmselect", i.proxy(this.updateTime, this)), this.container.find(".toolbar").on("change.daterangepicker", ".item.compare :checkbox", i.proxy(this.changeCompareCheckbox, this)).on("change.daterangepicker", ".item.relative-date :checkbox", i.proxy(this.changeRelativeCheckbox, this)).on("click.daterangepicker", ".dropdown ul li a", i.proxy(this.clickCompareRange, this)).on("focusout.daterangepicker", 'input[type="text"]', i.proxy(this.focusoutCompareInput, this)).on("click.daterangepicker", "button.applyBtn", i.proxy(this.clickApply, this)).on("click.daterangepicker", "button.cancelBtn", i.proxy(this.clickCancel, this));
        this.container.find(".ranges").on("click.daterangepicker", "button.applyBtn", i.proxy(this.clickApply, this)).on("click.daterangepicker", "button.cancelBtn", i.proxy(this.clickCancel, this)).on("click.daterangepicker", ".daterangepicker_start_input,.daterangepicker_end_input", i.proxy(this.showCalendars, this)).on("change.daterangepicker", ".daterangepicker_start_input,.daterangepicker_end_input", i.proxy(this.inputsChanged, this)).on("keydown.daterangepicker", ".daterangepicker_start_input,.daterangepicker_end_input", i.proxy(this.inputsKeydown, this)).on("click.daterangepicker", "li", i.proxy(this.clickRange, this)).on("mouseenter.daterangepicker", "li", i.proxy(this.enterRange, this)).on("mouseleave.daterangepicker", "li", i.proxy(this.updateFormInputs, this)), this.element.is("input") ? this.element.on({
            "click.daterangepicker": i.proxy(this.show, this),
            "focus.daterangepicker": i.proxy(this.show, this),
            "keyup.daterangepicker": i.proxy(this.updateFromControl, this),
            "keydown.daterangepicker": i.proxy(this.keydown, this)
        }) : this.element.on("click.daterangepicker", i.proxy(this.toggle, this))
    };
    s.prototype = {
        constructor: s, getOptions: function () {
            var t = {};
            return t.leftCalendarTitle = this.leftCalendarTitle, t.rightCalendarTitle = this.rightCalendarTitle, t.format = this.format, t.separator = this.separator, t.chosenLabel = this.chosenLabel, t.relativeValue = this.relativeValue, t.rangeLimit = this.rangeLimit, t.rangeLimitUnit = this.rangeLimitUnit, t.showInput = this.showInput, t.startDate = a.isMoment(this.startDate) ? this.startDate.clone() : this.startDate, t.endDate = a.isMoment(this.endDate) ? this.endDate.clone() : this.endDate, t.compareStartDate = a.isMoment(this.compareStartDate) ? this.compareStartDate.clone() : this.compareStartDate, t.compareEndDate = a.isMoment(this.compareEndDate) ? this.compareEndDate.clone() : this.compareEndDate, t.minDate = a.isMoment(this.minDate) ? this.minDate.clone() : this.minDate, t.maxDate = a.isMoment(this.maxDate) ? this.maxDate.clone() : this.maxDate, t.dateLimit = this.dateLimit, t.ranges = i.extend(!0, {}, this.ranges), t.activeRanges = i.extend(!0, [], this.activeRanges), t.allowStatic = this.allowStatic, t.allowRelative = this.allowRelative, t.showCompare = this.showCompare, t.timeExtend = this.timeExtend, t.showDropdowns = this.showDropdowns, t.showHour = this.showHour, t.inputElement = this.inputElement, t.allowSetInputLength = this.allowSetInputLength, t
        }, setHourDisplay: function (t) {
            this.showHour = !!t, !this.activeCompare && this.showHour ? (this.startDate.startOf("day"), this.endDate.startOf("day").hour(23)) : (this.startDate.startOf("day"), this.endDate.startOf("day")), this.compareStartDate && (this.compareStartDate.startOf("day"), this.compareEndDate.startOf("day")), this.element.data("startDate", this.startDate), this.element.data("endDate", this.endDate), this.element.data("compareStartDate", this.compareStartDate), this.element.data("compareEndDate", this.compareEndDate), this.updateCalendars()
        }, setOptions: function (t, e) {
            if (this.allowSetInputLength = t.allowSetInputLength, this.inputElement = t.inputElement, this.leftCalendarTitle = t.leftCalendarTitle, this.rightCalendarTitle = t.rightCalendarTitle, this.startDate = a().startOf("day"), this.endDate = a().startOf("day"), this.compareStartDate = null, this.compareEndDate = null, this.timeZone = a().utcOffset(), this.minDate = !1, this.maxDate = a().startOf("day"), this.dateLimit = !1, this.allowStatic = !0, this.allowRelative = !1, this.timeExtend = {}, this.showCompare = !1, this.showDropdowns = !1, this.showHour = !1, this.showWeekNumbers = !1, this.timePicker = !1, this.timePickerSeconds = !1, this.timePickerIncrement = 1, this.timePicker12Hour = !0, this.singleDatePicker = !1, this.ranges = {}, this.opens = "right", this.element.hasClass("pull-right") && (this.opens = "left"), this.drops = "down", this.element.hasClass("dropup") && (this.drops = "up"), this.buttonClasses = [], this.applyClass = "", this.cancelClass = "", this.format = "YYYY-MM-DD", this.hourFormat = "YYYY-MM-DD H时", this.separator = " 至 " | t.separator, this.chosenLabel = "", this.relativeValue = "", this.showInput = !1, this.rangeLimit = 0, this.rangeLimitUnit = "day", this.locale = {
                    applyLabel: "确定",
                    cancelLabel: "取消",
                    fromLabel: "从",
                    toLabel: "到",
                    weekLabel: "周",
                    customRangeLabel: "",
                    daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
                    monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    firstDay: a.localeData()._week.dow
                }, this.cb = function () {
                }, "boolean" == typeof t.showHour && (this.showHour = t.showHour), "string" == typeof t.startDate && (this.startDate = a(t.startDate, this.format)), "object" == typeof t.startDate && (this.startDate = a(t.startDate)), "string" == typeof t.endDate && (this.endDate = a(t.endDate, this.format)), "object" == typeof t.endDate && (this.endDate = a(t.endDate)), "string" == typeof t.compareStartDate && (this.compareStartDate = a(t.compareStartDate, this.format)), t.compareStartDate && "object" == typeof t.compareStartDate && (this.compareStartDate = a(t.compareStartDate)), "string" == typeof t.compareEndDate && (this.compareEndDate = a(t.compareEndDate, this.format)), t.compareEndDate && "object" == typeof t.compareEndDate && (this.compareEndDate = a(t.compareEndDate)), i.isEmptyObject(t.ranges) || (this.ranges = t.ranges), t.relativeValue) {
                this.relativeValue = t.relativeValue, t.chosenLabel = this.chosenLabel = "";
                var s = sensorsdata.convertTimeRange(this.relativeValue);
                if (s.length >= 2) {
                    this.startDate = s[0], this.endDate = s[1];
                    var n = sensorsdata.convertRelativeTimeLabel(this.relativeValue);
                    this.ranges[n] && (this.chosenLabel = n)
                }
            }
            if ("string" == typeof t.chosenLabel && i.isArray(this.ranges[t.chosenLabel])) {
                this.chosenLabel = t.chosenLabel;
                var s = this.ranges[t.chosenLabel];
                this.relativeValue || (this.relativeValue = sensorsdata.CONSTSET.relativeTime[this.chosenLabel]);
                var r = "";
                if (a.isMoment(this.compareStartDate) && a.isMoment(this.compareEndDate)) {
                    var o = this.buildCompareRange();
                    for (var h in o) if (this.compareStartDate.format("YYYY-MM-DD") === o[h][0].format("YYYY-MM-DD") && this.compareEndDate.format("YYYY-MM-DD") === o[h][1].format("YYYY-MM-DD")) {
                        r = h;
                        break
                    }
                }
                if (this.startDate = s[0], this.endDate = s[1], !this.activeCompare && this.showHour && this.endDate.hour(23), r) {
                    var o = this.buildCompareRange();
                    this.compareStartDate = o[r][0], this.compareEndDate = o[r][1]
                }
            }
            if ("string" == typeof t.format && (this.format = t.format), "string" == typeof t.separator && (this.separator = t.separator), "number" == typeof t.rangeLimit && (this.rangeLimit = t.rangeLimit), "string" == typeof t.rangeLimitUnit && (this.rangeLimitUnit = t.rangeLimitUnit), "boolean" == typeof t.showInput && (this.showInput = t.showInput), "string" == typeof t.minDate && (this.minDate = a(t.minDate, this.format)), "string" == typeof t.maxDate && (this.maxDate = a(t.maxDate, this.format)), "object" == typeof t.minDate && (this.minDate = a(t.minDate)), "object" == typeof t.maxDate && (this.maxDate = a(t.maxDate)), "string" == typeof t.applyClass && (this.applyClass = t.applyClass), "string" == typeof t.cancelClass && (this.cancelClass = t.cancelClass), "object" == typeof t.dateLimit && (this.dateLimit = t.dateLimit), "object" == typeof t.locale && ("object" == typeof t.locale.daysOfWeek && (this.locale.daysOfWeek = t.locale.daysOfWeek.slice()), "object" == typeof t.locale.monthNames && (this.locale.monthNames = t.locale.monthNames.slice()), "number" == typeof t.locale.firstDay && (this.locale.firstDay = t.locale.firstDay), "string" == typeof t.locale.applyLabel && (this.locale.applyLabel = t.locale.applyLabel), "string" == typeof t.locale.cancelLabel && (this.locale.cancelLabel = t.locale.cancelLabel), "string" == typeof t.locale.fromLabel && (this.locale.fromLabel = t.locale.fromLabel), "string" == typeof t.locale.toLabel && (this.locale.toLabel = t.locale.toLabel), "string" == typeof t.locale.weekLabel && (this.locale.weekLabel = t.locale.weekLabel), "string" == typeof t.locale.customRangeLabel && (this.locale.customRangeLabel = t.locale.customRangeLabel)), "string" == typeof t.opens && (this.opens = t.opens), "string" == typeof t.drops && (this.drops = t.drops), "boolean" == typeof t.showWeekNumbers && (this.showWeekNumbers = t.showWeekNumbers), "string" == typeof t.buttonClasses && (this.buttonClasses = [t.buttonClasses]), "object" == typeof t.buttonClasses && (this.buttonClasses = t.buttonClasses), "boolean" == typeof t.allowStatic && (this.allowStatic = t.allowStatic), "boolean" == typeof t.allowRelative && (this.allowRelative = t.allowRelative), "object" == typeof t.timeExtend && (this.timeExtend = t.timeExtend), "boolean" == typeof t.showCompare && (this.showCompare = t.showCompare), "boolean" == typeof t.showDropdowns && (this.showDropdowns = t.showDropdowns), "boolean" == typeof t.singleDatePicker && (this.singleDatePicker = t.singleDatePicker, this.singleDatePicker && (this.endDate = this.startDate.clone())), "boolean" == typeof t.timePicker && (this.timePicker = t.timePicker), "boolean" == typeof t.timePickerSeconds && (this.timePickerSeconds = t.timePickerSeconds), "number" == typeof t.timePickerIncrement && (this.timePickerIncrement = t.timePickerIncrement), "boolean" == typeof t.timePicker12Hour && (this.timePicker12Hour = t.timePicker12Hour), 0 != this.locale.firstDay) for (var l = this.locale.firstDay; l > 0;) this.locale.daysOfWeek.push(this.locale.daysOfWeek.shift()), l--;
            var c, d, s;
            if (void 0 === t.startDate && void 0 === t.endDate && i(this.element).is("input[type=text]")) {
                var p = i(this.element).val(), m = p.split(this.separator);
                c = d = null, 2 == m.length ? (c = a(m[0], this.format), d = a(m[1], this.format)) : this.singleDatePicker && "" !== p && (c = a(p, this.format), d = a(p, this.format)), null !== c && null !== d && (this.startDate = c, this.endDate = d)
            }
            if ("string" == typeof t.timeZone || "number" == typeof t.timeZone ? (this.timeZone = "string" == typeof t.timeZone && void 0 !== a.tz ? -1 * a.tz.zone(t.timeZone).parse(new Date) : t.timeZone, this.startDate.utcOffset(this.timeZone), this.endDate.utcOffset(this.timeZone)) : this.timeZone = a(this.startDate).utcOffset(), "object" == typeof t.ranges) {
                this.ranges = [];
                var f = t.activeRanges || [];
                for (s in t.ranges) c = "string" == typeof t.ranges[s][0] ? a(t.ranges[s][0], this.format) : a(t.ranges[s][0]), d = "string" == typeof t.ranges[s][1] ? a(t.ranges[s][1], this.format) : a(t.ranges[s][1]), this.ranges[s] = [c, d], c.isSame(this.startDate) && d.isSame(this.endDate) && (this.oldChosenLabel = s);
                var u = 0, D = "<ul>";
                for (s in this.ranges) {
                    u++;
                    var g = f.length > 0 && -1 === f.indexOf(s) ? "disabled" : "";
                    D += '<li class="' + g + '">' + s + "</li>"
                }
                D += "</ul>", this.container.find(".ranges ul").remove(), this.container.find(".ranges").prepend(D), this.container.find(".range-label-time").after(D)
            }
            if ("function" == typeof e && (this.cb = e), this.timePicker || (this.startDate = this.startDate.startOf(!this.activeCompare && this.showHour ? "hour" : "day")), this.singleDatePicker ? (this.opens = "right", this.container.addClass("single"), this.container.find(".calendar.right").show(), this.container.find(".calendar.left").hide(), this.timePicker ? this.container.find(".ranges .daterangepicker_start_input, .ranges .daterangepicker_end_input").hide() : this.container.find(".ranges").hide(), this.container.find(".calendar.right").hasClass("single") || this.container.find(".calendar.right").addClass("single")) : (this.container.removeClass("single"), this.container.find(".calendar.right").removeClass("single"), this.container.find(".ranges").show()), this.oldStartDate = this.startDate.clone(), this.oldEndDate = this.endDate.clone(), this.oldChosenLabel = this.chosenLabel, this.leftCalendar = {
                    month: a([this.startDate.year(), this.startDate.month(), 1, this.startDate.hour(), this.startDate.minute(), this.startDate.second()]),
                    calendar: []
                }, this.rightCalendar = {
                    month: a([this.endDate.year(), this.endDate.month(), 1, this.endDate.hour(), this.endDate.minute(), this.endDate.second()]),
                    calendar: []
                }, "right" == this.opens || "center" == this.opens) {
                var v = this.container.find(".calendar.first"), b = this.container.find(".calendar.second");
                b.hasClass("single") && (b.removeClass("single"), v.addClass("single")), v.removeClass("left").addClass("right"), b.removeClass("right").addClass("left"), this.singleDatePicker && (v.show(), b.hide())
            }
            void 0 !== t.ranges || this.singleDatePicker || this.container.addClass("show-calendar"), this.container.removeClass("opensleft opensright").addClass("opens" + this.opens), this.compareStartDate && this.compareEndDate && this.activeCompareInput(), this.updateView(), this.updateCalendars();
            var y = this.container;
            i.each(this.buttonClasses, function (t, e) {
                y.find("button").addClass(e)
            }), this.container.find(".daterangepicker_start_input label").html(this.locale.fromLabel), this.container.find(".daterangepicker_end_input label").html(this.locale.toLabel), this.applyClass.length && this.container.find(".applyBtn").addClass(this.applyClass), this.cancelClass.length && this.container.find(".cancelBtn").addClass(this.cancelClass), this.container.find(".applyBtn").html(this.locale.applyLabel), this.container.find(".cancelBtn").html(this.locale.cancelLabel)
        }, setStartDate: function (t) {
            "string" == typeof t && (this.startDate = a(t, this.format).utcOffset(this.timeZone)), "object" == typeof t && (this.startDate = a(t)), this.timePicker || (this.startDate = this.startDate.startOf(!this.activeCompare && this.showHour ? "hour" : "day")), this.oldStartDate = this.startDate.clone(), this.updateView(), this.updateCalendars(), this.updateInputText()
        }, setEndDate: function (t) {
            "string" == typeof t && (this.endDate = a(t, this.format).utcOffset(this.timeZone)), "object" == typeof t && (this.endDate = a(t)), !this.timePicker, this.oldEndDate = this.endDate.clone(), this.updateView(), this.updateCalendars(), this.updateInputText()
        }, updateView: function (t, e) {
            t = t || (this.activeCompare ? this.compareStartDate : this.startDate), e = e || (this.activeCompare ? this.compareEndDate : this.endDate), this.leftCalendar.month.month(t.month()).year(t.year()).hour(t.hour()).minute(t.minute()), this.rightCalendar.month.month(e.month()).year(e.year()).hour(e.hour()).minute(e.minute()), this.updateFormInputs()
        }, updateFormInputs: function () {
            if (this.container.find("input[name=daterangepicker_start]").val(this.startDate.format(this.format)), this.container.find("input[name=daterangepicker_end]").val(this.endDate.format(this.format)), this.activeCompare) {
                var t = this.buildCompareRange(), e = "custom";
                for (var a in t) if (t[a][0].isSame(this.compareStartDate) && t[a][1].isSame(this.compareEndDate)) {
                    e = a;
                    break
                }
                var i = this.container.find(".toolbar .item.compare .dropdown"),
                    s = i.find('ul li a[data-value="' + e + '"]');
                i.find("span.selected").attr("data-value", e).text(s.text());
                var n = this.container.find('.toolbar input[type="text"]');
                n.eq(0).val(this.compareStartDate.format(this.format)), n.eq(1).val(this.compareEndDate.format(this.format))
            }
            if (this.allowRelative) {
                var r = this.container.find(".toolbar .item.relative-date :checkbox").is(":checked");
                this.changeRelative(r)
            }
            this.startDate.isSame(this.endDate) || this.startDate.isBefore(this.endDate) ? this.container.find("button.applyBtn").removeAttr("disabled") : this.container.find("button.applyBtn").attr("disabled", "disabled")
        }, updateFromControl: function () {
            if (this.element.is("input") && this.element.val().length) {
                var t = this.element.val().split(this.separator), e = null, i = null;
                2 === t.length && (e = a(t[0], this.format).utcOffset(this.timeZone), i = a(t[1], this.format).utcOffset(this.timeZone)), (this.singleDatePicker || null === e || null === i) && (e = a(this.element.val(), this.format).utcOffset(this.timeZone), i = e), i.isBefore(e) || (this.oldStartDate = this.startDate.clone(), this.oldEndDate = this.endDate.clone(), this.startDate = e, this.endDate = i, this.startDate.isSame(this.oldStartDate) && this.endDate.isSame(this.oldEndDate) || this.notify(), this.updateCalendars())
            }
        }, keydown: function (t) {
            (9 === t.keyCode || 13 === t.keyCode) && this.hide()
        }, notify: function () {
            this.updateView(), this.cb(this.startDate, this.endDate, this.chosenLabel)
        }, move: function () {
            var t, e = {top: 0, left: 0}, a = i(window).width();
            this.parentEl.is("body") || (e = {
                top: this.parentEl.offset().top - this.parentEl.scrollTop(),
                left: this.parentEl.offset().left - this.parentEl.scrollLeft()
            }, a = this.parentEl[0].clientWidth + this.parentEl.offset().left), t = "up" == this.drops ? this.element.offset().top - this.container.outerHeight() - e.top : this.element.offset().top + this.element.outerHeight() - e.top, this.container["up" == this.drops ? "addClass" : "removeClass"]("dropup"), "left" == this.opens ? (this.container.css({
                top: t,
                right: a - this.element.offset().left - this.element.outerWidth(),
                left: "auto"
            }), this.container.offset().left < 0 && this.container.css({
                right: "auto",
                left: 9
            })) : "center" == this.opens ? (this.container.css({
                top: t,
                left: this.element.offset().left - e.left + this.element.outerWidth() / 2 - this.container.outerWidth() / 2,
                right: "auto"
            }), this.container.offset().left < 0 && this.container.css({
                right: "auto",
                left: 9
            })) : (this.container.css({
                top: t,
                left: this.element.offset().left - e.left,
                right: "auto"
            }), this.container.offset().left + this.container.outerWidth() > i(window).width() && this.container.css({
                left: "auto",
                right: 0
            }))
        }, toggle: function () {
            this.element.data("daterangepicker-disabled") !== !0 && (this.element.hasClass("active") ? this.hide() : this.show())
        }, show: function () {
            this.isShowing || (this.element.addClass("active"), this.container.show(), this.move(), this._outsideClickProxy = i.proxy(function (t) {
                this.outsideClick(t)
            }, this), i(document).on("mousedown.daterangepicker", this._outsideClickProxy).on("touchend.daterangepicker", this._outsideClickProxy).on("click.daterangepicker", "[data-toggle=dropdown]", this._outsideClickProxy).on("focusin.daterangepicker", this._outsideClickProxy), this.isShowing = !0, this.element.trigger("show.daterangepicker", this))
        }, outsideClick: function (t) {
            var e = i(t.target);
            "focusin" == t.type || e.closest(this.element).length || e.closest(this.container).length || e.closest(".calendar-table").length || (this.hide(), this.element.trigger("outsideClick.daterangepicker", this))
        }, hide: function () {
            this.isShowing && (i(document).off(".daterangepicker"), this.element.removeClass("active"), this.container.hide(), this.startDate.isSame(this.oldStartDate) && this.endDate.isSame(this.oldEndDate) || this.notify(), this.oldStartDate = this.startDate.clone(), this.oldEndDate = this.endDate.clone(), this.isShowing = !1, this.element.trigger("hide.daterangepicker", this))
        }, enterRange: function (t) {
            var e = t.target.innerHTML;
            if (e == this.locale.customRangeLabel) this.updateView(); else {
                var a = this.ranges[e];
                this.container.find("input[name=daterangepicker_start]").val(a[0].format(this.format)), this.container.find("input[name=daterangepicker_end]").val(a[1].format(this.format))
            }
        }, showCalendars: function () {
            this.container.addClass("show-calendar"), this.move(), this.element.trigger("showCalendar.daterangepicker", this)
        }, hideCalendars: function () {
            this.container.removeClass("show-calendar"), this.element.trigger("hideCalendar.daterangepicker", this)
        }, inputsChanged: function (t) {
            var e = i(t.target), s = a(e.val(), this.format);
            if (s.isValid()) {
                var n, r;
                "daterangepicker_start" === e.attr("name") ? (n = !1 !== this.minDate && s.isBefore(this.minDate) ? this.minDate : s, r = this.endDate) : (n = this.startDate, r = !1 !== this.maxDate && s.isAfter(this.maxDate) ? this.maxDate : s), this.setCustomDates(n, r)
            }
        }, inputsKeydown: function (t) {
            13 === t.keyCode && (this.inputsChanged(t), this.notify())
        }, updateInputText: function () {
            var t = this.inputElement || this.element, e = this.format,
                a = this.startDate.format(e) + this.separator + this.endDate.format(e);
            if (this.allowRelative) {
                var s = this.container.find(".toolbar .item.relative-date span").text();
                if (this.container.find(".toolbar .item.relative-date :checkbox").is(":checked") && (s = "静态时间"), a += " | " + s, this.allowSetInputLength) {
                    var n = i("<span>").hide().text(a).appendTo("body").css({"font-size": t.css("font-size")});
                    t.width(n.width()), n.remove()
                }
            } else this.singleDatePicker && (a = this.endDate.format(e));
            a && (t.is("input") ? (t.val(a), t.trigger("change")) : (t.text(a), t.trigger("change")))
        }, clickRange: function (t) {
            var e = t.target.innerHTML;
            if (this.activeCompare || (this.chosenLabel = e), e == this.locale.customRangeLabel) this.showCalendars(); else {
                var a = this.ranges[e], s = this.reviewRange(a[0], a[1]);
                i.isArray(s) && 2 === s.length && (a = s), this.activeCompare ? (this.compareStartDate = a[0], this.compareEndDate = a[1]) : (this.startDate = a[0], this.endDate = a[1], !this.activeCompare && this.showHour && this.endDate.hour(23)), this.timePicker || (this.startDate = this.startDate.startOf(!this.activeCompare && this.showHour ? "hour" : "day")), this.leftCalendar.month.month(a[0].month()).year(a[0].year()).hour(a[0].hour()).minute(a[0].minute()), this.rightCalendar.month.month(a[1].month()).year(a[1].year()).hour(a[1].hour()).minute(a[1].minute()), this.updateFormInputs(), this.updateCalendars(), this.clickApply()
            }
        }, clickPrev: function (t) {
            var e = i(t.target).parents(".calendar");
            e.hasClass("left") ? this.leftCalendar.month.subtract(1, "month") : this.rightCalendar.month.subtract(1, "month"), this.updateCalendars()
        }, clickNext: function (t) {
            var e = i(t.target).parents(".calendar");
            e.hasClass("left") ? this.leftCalendar.month.add(1, "month") : this.rightCalendar.month.add(1, "month"), this.updateCalendars()
        }, hoverDate: function (t) {
            var e = i(t.target).attr("data-title"), a = e.substr(1, 1), s = e.substr(3, 1),
                n = i(t.target).parents(".calendar");
            n.hasClass("left") ? this.container.find("input[name=daterangepicker_start]").val(this.leftCalendar.calendar[a][s].format(this.format)) : this.container.find("input[name=daterangepicker_end]").val(this.rightCalendar.calendar[a][s].format(this.format))
        }, setCustomDates: function (t, e) {
            this.activeCompare || (this.chosenLabel = this.locale.customRangeLabel), this.activeCompare ? (this.compareStartDate = t, this.compareEndDate = e) : (this.startDate = t, this.endDate = e), this.updateView(t, e), this.updateCalendars()
        }, updateRanges: function (t) {
            if (!this.chosenLabel || !this.ranges[this.chosenLabel] || !t[this.chosenLabel]) return !1;
            var e = this.ranges[this.chosenLabel], a = t[this.chosenLabel];
            return e[0].isSame(a[0]) && e[1].isSame(a[1]) ? !1 : (this.ranges = t, this.startDate = a[0], this.endDate = a[1], this.updateView(), this.updateCalendars(), this.updateInputText(), !0)
        }, setRangeLimit: function (t, e, i) {
            this.rangeLimit = t, this.rangeLimitUnit = e || this.rangeLimitUnit;
            var s = this.reviewRange(this.startDate, this.endDate, i);
            this.startDate = s[0], this.endDate = s[1], a.isMoment(this.compareStartDate) && a.isMoment(this.compareEndDate) && (s = this.reviewRange(this.compareStartDate, this.compareEndDate, i), this.compareStartDate = s[0], this.compareEndDate = s[1]), this.updateView(), this.updateCalendars(), this.updateInputText(), this.compareStartDate && (this.compareStartDate.startOf("day"), this.compareEndDate.startOf("day")), this.element.data("startDate", this.startDate), this.element.data("endDate", this.endDate), this.element.data("compareStartDate", this.compareStartDate), this.element.data("compareEndDate", this.compareEndDate), this.element.data("relativeValue", this.relativeValue)
        }, reviewRange: function (t, e, i) {
            if (this.rangeLimit <= 0) return [t, e];
            var s = sensorsdata.getBeginDate(this.rangeLimitUnit, t),
                n = sensorsdata.getBeginDate(this.rangeLimitUnit, e), r = n.diff(s, this.rangeLimitUnit);
            if (r >= this.rangeLimit) {
                if (i === !1) {
                    e = s.add(this.rangeLimit - 1, this.rangeLimitUnit);
                    var o = a().startOf("day");
                    e.isAfter(o) && (e = o)
                } else t = n.subtract(this.rangeLimit - 1, this.rangeLimitUnit);
                this.element.trigger("truncate.daterangepicker", this)
            }
            return [t, e]
        }, clickDate: function (t) {
            var e = i(t.target).attr("data-title"), s = e.substr(1, 1), n = e.substr(3, 1),
                r = i(t.target).parents(".calendar"), o = 0, h = 23;
            if (!this.activeCompare && this.showHour) {
                var l = this.container.find(".calendar.left tfoot option:selected").val();
                o = parseInt(l, 10), this.startDate.startOf("day").hour(l), l = this.container.find(".calendar.right tfoot option:selected").val(), h = parseInt(l, 10)
            }
            var c, d, p = [];
            if (r.hasClass("left")) {
                if (c = this.leftCalendar.calendar[s][n], d = this.activeCompare ? this.compareEndDate : this.endDate, !this.activeCompare && this.showHour && (c.hour(o), d.hour(h)), "object" == typeof this.dateLimit) {
                    var m = a(c).add(this.dateLimit).startOf("day");
                    d.isAfter(m) && (d = m)
                }
                0 === o && 23 === h && (p = this.reviewRange(c, d, !1))
            } else {
                if (c = this.activeCompare ? this.compareStartDate : this.startDate, d = this.rightCalendar.calendar[s][n], !this.activeCompare && this.showHour && (c.hour(o), d.hour(h)), "object" == typeof this.dateLimit) {
                    var f = a(d).subtract(this.dateLimit).startOf("day");
                    c.isBefore(f) && (c = f)
                }
                0 === o && 23 === h && (p = this.reviewRange(c, d, !0))
            }
            return i.isArray(p) && 2 === p.length && (c = p[0], d = p[1]), this.singleDatePicker && r.hasClass("left") ? d = c.clone() : this.singleDatePicker && r.hasClass("right") && (c = d.clone()), this.activeCompare ? r.find("td.compare").removeClass("active") : r.find("td").removeClass("active"), i(t.target).addClass("active"), this.activeCompare && i(t.target).addClass("compare"), this.setCustomDates(c, d), this.singleDatePicker && !this.timePicker && this.clickApply(), !1
        }, buildCompareRange: function () {
            var t = this.startDate.clone().startOf("day"), e = this.endDate.clone().startOf("day"),
                a = e.diff(t) / 864e5;
            return e = t.clone().subtract(1, "day"), t = e.clone().subtract(a, "day"), {
                "last period": [t, e],
                "last year": [this.startDate.clone().startOf("day").subtract(1, "year"), this.endDate.clone().startOf("day").subtract(1, "year")]
            }
        }, changeCompareRange: function (t) {
            this.compareStartDate || this.compareEndDate || "custom" !== t || (t = "last period");
            var e = this.buildCompareRange();
            "custom" !== t && (this.compareStartDate = e[t][0], this.compareEndDate = e[t][1]);
            var a = this.container.find('.toolbar input[type="text"]');
            a.eq(0).val(this.compareStartDate.format(this.format)), a.eq(1).val(this.compareEndDate.format(this.format)), this.updateView(this.compareStartDate, this.compareEndDate), this.updateCalendars()
        }, changeTimeExtend: function (t) {
            this.element.data("isLimit", i(t.target).is(":checked"))
        }, changeCompareCheckbox: function (t) {
            var e = i(t.target), a = e.is(":checked");
            if (a) {
                this.activeCompareInput();
                var s = e.parents("span.item:first").find(".dropdown span.selected").attr("data-value");
                this.changeCompareRange(s)
            } else this.disactiveCompareInput(), this.updateView(this.startDate, this.endDate), this.updateCalendars();
            !this.activeCompare && this.showHour && this.container.find("table tfoot select").toggle(!a)
        }, clickCompareRange: function (t) {
            var e = i(t.target), a = e.attr("data-value");
            e.parents("div.dropdown:first").find("button span.selected").attr("data-value", a).text(e.text()), this.changeCompareRange(a)
        }, focusoutCompareInput: function (t) {
            var e = i(t.target), s = i.trim(e.val()), n = 0 === e.prevAll('input[type="text"]').size();
            if (!a(s, this.format).isValid()) return void e.val((n ? this.compareStartDate : this.compareEndDate).format(this.format));
            var r, o, h = [];
            if (n) {
                if (r = a(s, this.format), r.format("YYYY-MM-DD") === this.compareStartDate.format("YYYY-MM-DD")) return;
                if (o = this.compareEndDate, "object" == typeof this.dateLimit) {
                    var l = a(r).add(this.dateLimit).startOf("day");
                    o.isAfter(l) && (o = l)
                }
                h = this.reviewRange(r, o, !1)
            } else {
                if (r = this.compareStartDate, o = a(s, this.format), o.format("YYYY-MM-DD") === this.compareEndDate.format("YYYY-MM-DD")) return;
                if ("object" == typeof this.dateLimit) {
                    var c = a(o).subtract(this.dateLimit).startOf("day");
                    r.isBefore(c) && (r = c)
                }
                h = this.reviewRange(r, o, !0)
            }
            i.isArray(h) && 2 === h.length && (r = h[0], o = h[1]), this.setCustomDates(r, o)
        }, activeCompareInput: function () {
            var t = this.container.find(".toolbar");
            t.find(".item.compare :checkbox").prop("checked", !0), t.find(".item.compare span:first").toggle(!0), this.container.addClass("compare"), this.container.find(".ranges ul li").addClass("disabled"), this.activeCompare = !0
        }, disactiveCompareInput: function () {
            this.container.find(".toolbar .item.compare span:first").toggle(!1), this.container.removeClass("compare"), this.container.find(".ranges ul li").removeClass("disabled"), this.activeCompare = !1
        }, changeRelativeCheckbox: function (t) {
            this.changeRelative(i(t.target).is(":checked"))
        }, changeRelative: function () {
            var t = this.container.find(".toolbar .item.relative-date"), e = t.find("span");
            if (t.find(":checkbox").is(":checked")) this.relativeValue = this.startDate.format(this.format) + this.separator + this.endDate.format(this.format), e.text(this.relativeValue); else if (this.chosenLabel) e.text(this.chosenLabel), this.relativeValue = sensorsdata.CONSTSET.relativeTime[this.chosenLabel]; else {
                var i = a().startOf("day"), s = i.diff(this.startDate, "day"), n = i.diff(this.endDate, "day");
                this.relativeValue = s + "," + n + ",day", e.text(sensorsdata.convertRelativeTimeLabel(this.relativeValue))
            }
        }, clickApply: function () {
            if (this.activeCompare || (this.compareStartDate = null, this.compareEndDate = null), this.showHour && !this.activeCompare) {
                var t = this.container.find(".calendar.left tfoot option:selected").val();
                t = parseInt(t, 10), this.startDate.startOf("day").hour(t), t = this.container.find(".calendar.right tfoot option:selected").val(), t = parseInt(t, 10), this.endDate.startOf("day").hour(t)
            }
            this.compareStartDate && (this.compareStartDate.startOf("day"), this.compareEndDate.startOf("day")), this.element.data("startDate", this.startDate), this.element.data("endDate", this.endDate), this.element.data("compareStartDate", this.compareStartDate), this.element.data("compareEndDate", this.compareEndDate), this.element.data("relativeValue", this.relativeValue), this.timeExtend.isLimit = this.container.find(".toolbar .item.time-extend :checkbox").is(":checked"), this.element.data("isLimit", this.timeExtend.isLimit), this.updateInputText(), this.hide(), this.element.trigger("apply.daterangepicker", this, "1223")
        }, clickCancel: function () {
            this.startDate = this.oldStartDate, this.endDate = this.oldEndDate, this.chosenLabel = this.oldChosenLabel, this.updateView(), this.updateCalendars(), this.hide(), this.element.trigger("cancel.daterangepicker", this)
        }, updateMonthYear: function (t) {
            var e = i(t.target).closest(".calendar").hasClass("left"), a = e ? "left" : "right",
                s = this.container.find(".calendar." + a), n = parseInt(s.find(".monthselect").val(), 10),
                r = s.find(".yearselect").val();
            e || this.singleDatePicker || (r < this.startDate.year() || r == this.startDate.year() && n < this.startDate.month()) && (n = this.startDate.month(), r = this.startDate.year()), this.minDate && (r < this.minDate.year() || r == this.minDate.year() && n < this.minDate.month()) && (n = this.minDate.month(), r = this.minDate.year()), this.maxDate && (r > this.maxDate.year() || r == this.maxDate.year() && n > this.maxDate.month()) && (n = this.maxDate.month(), r = this.maxDate.year()), this[a + "Calendar"].month.month(n).year(r), this.updateCalendars()
        }, updateTime: function (t) {
            var e = i(t.target).closest(".calendar"), a = e.hasClass("left"),
                s = parseInt(e.find(".hourselect").val(), 10), n = parseInt(e.find(".minuteselect").val(), 10), r = 0;
            if (this.timePickerSeconds && (r = parseInt(e.find(".secondselect").val(), 10)), this.timePicker12Hour) {
                var o = e.find(".ampmselect").val();
                "PM" === o && 12 > s && (s += 12), "AM" === o && 12 === s && (s = 0)
            }
            if (a) {
                var h = this.startDate.clone();
                h.hour(s), h.minute(n), h.second(r), this.startDate = h, this.leftCalendar.month.hour(s).minute(n).second(r), this.singleDatePicker && (this.endDate = h.clone())
            } else {
                var l = this.endDate.clone();
                l.hour(s), l.minute(n), l.second(r), this.endDate = l, this.singleDatePicker && (this.startDate = l.clone()), this.rightCalendar.month.hour(s).minute(n).second(r)
            }
            this.updateView(), this.updateCalendars()
        }, updateCalendars: function () {
            if (this.leftCalendar.calendar = this.buildCalendar(this.leftCalendar.month.month(), this.leftCalendar.month.year(), this.leftCalendar.month.hour(), this.leftCalendar.month.minute(), this.leftCalendar.month.second(), "left"), this.rightCalendar.calendar = this.buildCalendar(this.rightCalendar.month.month(), this.rightCalendar.month.year(), this.rightCalendar.month.hour(), this.rightCalendar.month.minute(), this.rightCalendar.month.second(), "right"), this.activeCompare) {
                var t = this.renderCalendar(this.leftCalendar.calendar, this.compareStartDate, this.minDate, this.maxDate, "left", this.leftCalendarTitle, this.startDate);
                this.container.find(".calendar.left").html(t)
            } else {
                var t = this.renderCalendar(this.leftCalendar.calendar, this.startDate, this.minDate, this.maxDate, "left", this.leftCalendarTitle);
                this.container.find(".calendar.left").html(t)
            }
            if (this.activeCompare) {
                var e = this.compareStartDate.isBefore(this.startDate) ? this.compareStartDate : this.startDate,
                    t = this.renderCalendar(this.rightCalendar.calendar, this.compareEndDate, this.singleDatePicker ? this.minDate : e, this.maxDate, "right", this.rightCalendarTitle, this.endDate);
                this.container.find(".calendar.right").html(t)
            } else {
                var t = this.renderCalendar(this.rightCalendar.calendar, this.endDate, this.minDate, this.maxDate, "right", this.rightCalendarTitle);
                this.container.find(".calendar.right").html(t)
            }
            this.container.find(".ranges li").removeClass("active");
            {
                var i = 0, s = this, n = this.chosenLabel || this.optionChosenLabel;
                this.container.find(".ranges ul")
            }
            s.showCalendars();
            var r = this.startDate, o = this.endDate;
            this.activeCompare && (r = this.compareStartDate, o = this.compareEndDate);
            var h = -1, l = -1;
            for (var c in this.ranges) {
                if (this.timePicker) {
                    if (r.isSame(this.ranges[c][0]) && o.isSame(this.ranges[c][1])) return void(this.chosenLabel = this.container.find(".ranges li:eq(" + i + ")").addClass("active").html())
                } else if (r.format("YYYY-MM-DD HH") == this.ranges[c][0].format("YYYY-MM-DD HH") && o.format("YYYY-MM-DD HH") == this.ranges[c][1].format("YYYY-MM-DD HH")) {
                    -1 === h && (h = i);
                    var d = this.container.find(".ranges li:eq(" + i + ")").html();
                    d === this.chosenLabel && (l = i)
                }
                i++
            }
            0 > l ? 0 > h || (this.chosenLabel = this.container.find(".ranges li:eq(" + h + ")").addClass("active").html()) : this.chosenLabel = this.container.find(".ranges li:eq(" + l + ")").addClass("active").html(), this.activeCompare && (this.chosenLabel = n), this.allowRelative && this.chosenLabel && (this.relativeValue = sensorsdata.CONSTSET.relativeTime[this.chosenLabel]);
            var s = this, p = function () {
                var t = s.container.find(".calendar.left tfoot option:selected").val(),
                    e = s.container.find(".calendar.right tfoot option:selected").val();
                if (s.activeCompare) {
                    var i = s.container.find('.toolbar .compare input[type="text"]'), n = a(i.eq(0).val(), s.format);
                    n.hour(t), i.eq(0).val(n.format(s.hourFormat)), n = a(i.eq(1).val(), s.format), n.hour(e), i.eq(1).val(n.format(s.hourFormat))
                } else ("0" !== t || "23" !== e) && (s.chosenLabel = "", s.container.find(".ranges ul li.active").removeClass("active"))
            };
            this.container.find(".calendar.left tfoot select").unbind("change").bind("change", p), this.container.find(".calendar.right tfoot select").unbind("change").bind("change", p)
        }, buildCalendar: function (t, e, i, s, n, r) {
            var o, h = a([e, t]).daysInMonth(), l = a([e, t, 1]), c = a([e, t, h]),
                d = a(l).subtract(1, "month").month(), p = a(l).subtract(1, "month").year(),
                m = a([p, d]).daysInMonth(), f = l.day(), u = [];
            for (u.firstDay = l, u.lastDay = c, o = 0; 6 > o; o++) u[o] = [];
            var D = m - f + this.locale.firstDay + 1;
            D > m && (D -= 7), f == this.locale.firstDay && (D = m - 6);
            var g, v, b = a([p, d, D, 12, s, n]).utcOffset(this.timeZone);
            for (o = 0, g = 0, v = 0; 42 > o; o++, g++, b = a(b).add(24, "hour")) o > 0 && g % 7 === 0 && (g = 0, v++), u[v][g] = b.clone().hour(i), b.hour(12), this.minDate && u[v][g].format("YYYY-MM-DD") == this.minDate.format("YYYY-MM-DD") && u[v][g].isBefore(this.minDate) && "left" == r && (u[v][g] = this.minDate.clone()), this.maxDate && u[v][g].format("YYYY-MM-DD") == this.maxDate.format("YYYY-MM-DD") && u[v][g].isAfter(this.maxDate) && "right" == r && (u[v][g] = this.maxDate.clone());
            return u
        }, renderDropdowns: function (t, e, a) {
            for (var i = t.month(), s = t.year(), n = a && a.year() || s + 5, r = e && e.year() || s - 50, o = '<select class="monthselect">', h = s == r, l = s == n, c = 0; 12 > c; c++) h && c < e.month() || l && c > a.month() || (o += "<option value='" + c + "'" + (c === i ? " selected='selected'" : "") + ">" + this.locale.monthNames[c] + "</option>");
            o += "</select>";
            for (var d = '<select class="yearselect">', p = r; n >= p; p++) d += '<option value="' + p + '"' + (p === s ? ' selected="selected"' : "") + ">" + p + "</option>";
            return d += "</select>", o + d
        }, renderCalendar: function (t, e, s, n, r, o) {
            var h = function (t, e, a, i) {
                var s = "";
                return t.format("YYYY-MM-DD") == e.format("YYYY-MM-DD") ? (s += " active ", t.format("YYYY-MM-DD") == a.format("YYYY-MM-DD") && (s += " start-date "), t.format("YYYY-MM-DD") == i.format("YYYY-MM-DD") && (s += " end-date ")) : a > t || t > i || (s += " in-range ", t.isSame(a) && (s += " start-date "), t.isSame(i) && (s += " end-date ")), s
            }, l = '<div class="calendar-date">';
            l += '<table class="table-condensed">', l += "<thead>", o && (l += '<tr><th></th><th colspan="5">' + o + "</th><th></th></tr>"), l += "<tr>", this.showWeekNumbers && (l += "<th></th>"), l += !s || s.isBefore(t.firstDay) ? '<th class="prev available"><i class="fa fa-arrow-left icon icon-arrow-left glyphicon glyphicon-arrow-left"></i></th>' : "<th></th>";
            var c = t[1][1].format(this.dateTemplate || "YYYY年M月");
            this.showDropdowns && (c = this.renderDropdowns(t[1][1], s, n)), l += '<th colspan="5" class="month">' + c + "</th>", l += !n || n.isAfter(t.lastDay) ? '<th class="next available"><i class="fa fa-arrow-right icon icon-arrow-right glyphicon glyphicon-arrow-right"></i></th>' : "<th></th>", l += "</tr>", l += "<tr>", this.showWeekNumbers && (l += '<th class="week">' + this.locale.weekLabel + "</th>"), i.each(this.locale.daysOfWeek, function (t, e) {
                l += "<th>" + e + "</th>"
            }), l += "</tr>", l += "</thead>", l += "<tbody>";
            for (var d = "left" === r ? this.startDate : this.endDate, p = "left" === r ? this.compareStartDate : this.compareEndDate, m = "left" === r ? this.leftCalendar.month.month() : this.rightCalendar.month.month(), f = a().startOf("day"), u = 0; 6 > u; u++) {
                l += "<tr>", this.showWeekNumbers && (l += '<td class="week">' + t[u][0].week() + "</td>");
                for (var D = 0; 7 > D; D++) if (m === t[u][D].month()) {
                    var g = "available ";
                    if (g += t[u][D].month() == t[1][1].month() ? "" : "off", s && t[u][D].isBefore(s, "day") || n && t[u][D].isAfter(n, "day")) g = " off disabled "; else {
                        var v = h(t[u][D], d, this.startDate, this.endDate);
                        if (this.activeCompare) {
                            var b = h(t[u][D], p, this.compareStartDate, this.compareEndDate);
                            v && b ? v += b + " both " : b && (v += b + " compare ")
                        }
                        g += v
                    }
                    var y = "r" + u + "c" + D, C = t[u][D].isSame(f) ? "today " : "";
                    l += '<td class="' + C + g.replace(/\s+/g, " ").replace(/^\s?(.*?)\s?$/, "$1") + '" data-title="' + y + '">' + t[u][D].date() + "</td>"
                } else l += "<td></td>";
                l += "</tr>"
            }
            l += "</tbody>", this.showHour && !this.activeCompare && (l += '<tfoot><tr><td colspan="7">', l += this.tplHourSelect, l += "</td></tr></tfoot>"), l += "</table>", l += "</div>";
            var k;
            if (this.timePicker) {
                l += '<div class="calendar-time">', l += '<select class="hourselect">';
                var w = 0, Y = 23;
                s && ("left" == r || this.singleDatePicker) && e.format("YYYY-MM-DD") == s.format("YYYY-MM-DD") && (w = s.hour(), e.hour() < w && e.hour(w), !this.timePicker12Hour || 12 > w || e.hour() < 12 || (w -= 12), this.timePicker12Hour && 12 == w && (w = 1)), n && ("right" == r || this.singleDatePicker) && e.format("YYYY-MM-DD") == n.format("YYYY-MM-DD") && (Y = n.hour(), e.hour() > Y && e.hour(Y), !this.timePicker12Hour || 12 > Y || e.hour() < 12 || (Y -= 12));
                var x = 0, L = 23, S = e.hour();
                for (this.timePicker12Hour && (x = 1, L = 12, 12 > S || (S -= 12), 0 === S && (S = 12)), k = x; L >= k; k++) l += k == S ? '<option value="' + k + '" selected="selected">' + k + "</option>" : w > k || k > Y ? '<option value="' + k + '" disabled="disabled" class="disabled">' + k + "</option>" : '<option value="' + k + '">' + k + "</option>";
                l += "</select> : ", l += '<select class="minuteselect">';
                var M = 0, E = 59;
                for (s && ("left" == r || this.singleDatePicker) && e.format("YYYY-MM-DD h A") == s.format("YYYY-MM-DD h A") && (M = s.minute(), e.minute() < M && e.minute(M)), n && ("right" == r || this.singleDatePicker) && e.format("YYYY-MM-DD h A") == n.format("YYYY-MM-DD h A") && (E = n.minute(), e.minute() > E && e.minute(E)), k = 0; 60 > k; k += this.timePickerIncrement) {
                    var P = k;
                    10 > P && (P = "0" + P), l += k == e.minute() ? '<option value="' + k + '" selected="selected">' + P + "</option>" : M > k || k > E ? '<option value="' + k + '" disabled="disabled" class="disabled">' + P + "</option>" : '<option value="' + k + '">' + P + "</option>"
                }
                if (l += "</select> ", this.timePickerSeconds) {
                    for (l += ': <select class="secondselect">', k = 0; 60 > k; k += this.timePickerIncrement) {
                        var P = k;
                        10 > P && (P = "0" + P), l += k == e.second() ? '<option value="' + k + '" selected="selected">' + P + "</option>" : '<option value="' + k + '">' + P + "</option>"
                    }
                    l += "</select>"
                }
                if (this.timePicker12Hour) {
                    l += '<select class="ampmselect">';
                    var O = "", R = "";
                    !s || "left" != r && !this.singleDatePicker || e.format("YYYY-MM-DD") != s.format("YYYY-MM-DD") || s.hour() < 12 || (O = ' disabled="disabled" class="disabled"'), n && ("right" == r || this.singleDatePicker) && e.format("YYYY-MM-DD") == n.format("YYYY-MM-DD") && n.hour() < 12 && (R = ' disabled="disabled" class="disabled"'), l += e.hour() < 12 ? '<option value="AM" selected="selected"' + O + '>AM</option><option value="PM"' + R + ">PM</option>" : '<option value="AM"' + O + '>AM</option><option value="PM" selected="selected"' + R + ">PM</option>", l += "</select>"
                }
                l += "</div>"
            }
            if (this.showHour && !this.activeCompare) {
                var H = e.hour(), I = i(l);
                return I.find('table tfoot option[value="' + H + '"]').prop("selected", !0), I
            }
            return l
        }, remove: function () {
            this.container.remove(), this.element.off(".daterangepicker"), this.element.removeData("daterangepicker")
        }
    }, i.fn.daterangepicker = function (t, e) {
        return this.each(function () {
            var a = i(this);
            a.data("daterangepicker") && a.data("daterangepicker").remove(), a.data("daterangepicker", new s(a, t, e))
        }), this
    }
});