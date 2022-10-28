(function () {
    var global_data={
        enlarge_hidden:function () {
            $("#enlarge_wrap").hide();
            $("#enlarge_bg").remove();
            $(".enlarge_img img").remove();
            $(".enlarge_title p").remove();
            $(".enlarge_img").css({
                width:"auto",
                hieght:"auto"
            });
        },
        enlarge_position:function () {
            $("#enlarge_wrap").css({
                top:$(window).height()/2-$("#enlarge_wrap").innerHeight()/2,
                left: $(window).width()/2-$("#enlarge_wrap").innerWidth()/2,
                'z-index':'10002'
            });
        },
        // 设置图片长宽不超过300
        minWH: function (minW, minH, w, h) {
            if (w > 0 && h > 0 & minW > 0 && minH > 0) {
                if (minW >= w && minH >= h) {
                    return {width: w, height: h}
                } else {
                    var normalW = 0;
                    var normalH = 0;
                    if (minW < w) {
                        normalH = (minW * h) / w;
                        if (normalH > minH) {
                            normalW = (minH * w) / h;
                            normalH = minH;
                        } else {
                            normalW = minW;
                        }
                    } else if (minH < h) {
                        normalW = (minH * w) / h;
                        if (normalW > minW) {
                            normalH = (minW * h) / w;
                            normalW = minW;
                        } else {
                            normalH = minH;
                        }
                    }
                    return {width: Math.round(normalW), height: Math.round(normalH)}
                }
            } else {
                return false;
            }
        },
        enlargeClick:function (string) {
            // 点击放大
            if($(string).length<1 || $.inArray(string, global_data.use)>-1) return;
            global_data.use.push(string);
            if($("#enlarge_wrap").length<1){
                $("body").append('<div id="enlarge_wrap" style=" display: none;position: fixed;top:0;left:0;z-index: 19990;padding: 10px;background: #ffffff;box-shadow: 10px 10px 10px #333;"> ' +
                    '<div class="enlarge_img" style="background:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAlSURBVDhPYzhz5sx/fJgQGDVgeBgApXECbIYi41EDhoEBZ/4DAMoUr64hn4eKAAAAAElFTkSuQmCC);"></div> ' +
                    '<div id="enlarge_close" style="position: absolute;top:-15px;right: -15px;width: 30px;height: 30px;background-image: url(data:image/png;base64,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);background-repeat: no-repeat;cursor: pointer;"></div> ' +
                    '<div class="enlarge_title" style="display:none;position: absolute;left: 0;bottom: -50px;background-color: #000000;border-radius: 15px;border: 2px solid #ffffff;padding: 0 15px;"></div> ' +
                    '</div>');
            }
            $(window).resize(function(){
                global_data.enlarge_position();
            });
            $(document).on("click", string, function () {

                $("body").append('<div id="enlarge_bg" style="background-color: rgb(119, 119, 119); opacity: 0.7; cursor: pointer; width: 100%; height: 100%; display: block; position: fixed;top: 0;left: 0;z-index: 10001;"></div>');
                $(".enlarge_img").append('<img src="'+this.src+'" alt="" style="width:100%;height:100%;">');
                $(".enlarge_title").append('<p style="height: 30px;padding: 0;margin:0;color: #ffffff;line-height:30px;font-size: 20px;">'+this.src+'</p>');

                var enlarge_img_w=$(".enlarge_img img")[0].naturalWidth;
                var enlarge_img_h=$(".enlarge_img img")[0].naturalHeight;

                if((enlarge_img_w+100<$(window).width())&&(enlarge_img_h+200<$(window).height())){
                    $(".enlarge_img").css({
                        width:enlarge_img_w+"px",
                        height:enlarge_img_h+"px"
                    });
                }else {
                    var $width=$(window).width()-100;
                    var $height=($(window).width()-100)*enlarge_img_h/enlarge_img_w;
                    if($height+200>=$(window).height()){
                        $width=($(window).height()-200)*enlarge_img_w/enlarge_img_h;
                        $height=$(window).height()-200;
                    }
                    $(".enlarge_img").css({
                        width:$width+"px",
                        height:$height+"px"
                    });
                }
                global_data.enlarge_position();
                $("#enlarge_wrap").show();
                $(".enlarge_title").css({
                    left:$("#enlarge_wrap").innerWidth()/2-$('.enlarge_title').innerWidth()/2
                });
            });
            $("body").on("click","#enlarge_bg",function () {
                global_data.enlarge_hidden();
            });
            $("#enlarge_close").on("click",function () {
                global_data.enlarge_hidden();
            });
            $(document).on("keyup",function (e) {
                if(e.which==27){
                    global_data.enlarge_hidden();
                }
            });
        },
        enlargeHover:function (string) {
            // 移动放大
            var enlargeW=400;
            var enlargeH=400;
            $('body').append('<div id="enlarge_hover" style="display: none;position: absolute;top: -'+enlargeH+'px;left:-'+enlargeW+'px;z-index:10000;width: '+enlargeW+'px;height: '+enlargeH+'px; padding: 10px;box-sizing:border-box;  background-color: rgba(0,0,0,.5);  box-shadow: 10px 10px 10px #333;"><div class="enlarge_hover_img" style="width: 100%;height: 100%;background-size: contain;background-repeat: no-repeat;background-position: center;"></div><div class="enlarge_hover_text" style="position: absolute;top: 10px;right: 10px;height: 30px;padding: 0 5px;line-height: 30px;box-sizing: border-box;color: #ffffff;background-color: rgba(0,0,0,.5)"></div></div>');
            $('body').on('mouseenter',string,function () {
                var that = $(this);
                var imgUrl=that.attr("src");
                //图片原始尺寸
                var enlarge_img_w=that[0].naturalWidth;
                var enlarge_img_h=that[0].naturalHeight;
                //如果图片地址不存在或请求不到则跳出
                if(!imgUrl || !enlarge_img_w || !enlarge_img_h){
                    $("#enlarge_hover").hide();
                    return false;
                }
                var minWH=global_data.minWH(300,300,enlarge_img_w,enlarge_img_h);
                if(!minWH){return false;}
                enlargeW=minWH.width+20;
                enlargeH=minWH.height+20;
                // 图片定位
                var imgPositionX = that.offset().left+$(this).innerWidth()+20;
                var imgPositionY = that.offset().top-(enlargeH/2);
                var windowH=$(window).height()-20;
                if(imgPositionY<=20){
                    imgPositionY=20;
                }else if(imgPositionY+enlargeH>=windowH){
                    imgPositionY=windowH-enlargeH;
                }
                //判断图层是否存在
                var enlargeHover=$("#enlarge_hover");
                if(enlargeHover.length && enlargeHover.length>0){
                    enlargeHover.css({
                        display:"block",
                        width:enlargeW+"px",
                        height:enlargeH+"px",
                        top:imgPositionY+"px",
                        left:imgPositionX+"px"
                    });
                    enlargeHover.find(".enlarge_hover_img").css({
                        "background-image":"url('"+that.attr("src")+"')"
                    });
                    enlargeHover.find(".enlarge_hover_text").html(enlarge_img_w+"*"+enlarge_img_h+"px")
                }
            });
            $('body').on('mouseleave',string,function () {
                $("#enlarge_hover").hide();
            });
        },
        enlargeText:function (string) {
            $('body').append('<div id="enlarge_text" style="opacity: 0.9;display: none;position: absolute;top: 0;left:-300px;max-width: 400px;min-width: 200px;font-size: 16px;line-height: 20px;border: 1px solid rgba(0,0,0,.2);box-shadow: 0 5px 10px rgba(0,0,0,.2);border-radius: 6px;background-color: #444444;"><div class="enlarge_text_wrap" style="width: 100%;height: 100%;min-height: 40px;box-sizing: border-box;padding: 10px;color: #eeeeee;"></div><div class="enlarge_text_arrow" style="position: absolute;width: 0;height: 0;"></div></div>')
            $('body').on('mouseenter',string,function () {
                var that=$(this);
                if(that.hasClass("enlargeTextCon")){
                    var enlargeTextCon=that.html();
                }else if(that.hasClass("enlargeTextTitle")){
                    var enlargeTextCon=that.attr("data-title");
                }else{
                    return false;
                }
                if(enlargeTextCon.length<=0){
                    $("#enlarge_text").hide();
                    return false;
                }
                $(".enlarge_text_wrap").html(enlargeTextCon);
                //元素宽高
                var enlargeEleW=that.innerWidth();
                var enlargeEletH=that.innerHeight();
                //元素距浏览器的距离
                var enlargeTextLeft=that.offset().left;
                var enlargeTextTop=that.offset().top;
                //容器的宽高
                var enlargeTextW=$("#enlarge_text").innerWidth();
                var enlargeTextH=$("#enlarge_text").innerHeight();
                //浏览器的宽高
                var windowW=$(window).width();
                var windowH=$(window).height();

                //判断用左框还是右框
                if(enlargeTextLeft>(windowW-enlargeEleW-enlargeTextW-13)){
                    //左框
                    if((enlargeTextTop+enlargeTextH)<(windowH-10)){
                        //左下框
                        $("#enlarge_text").css({
                            display:"block",
                            "top":enlargeTextTop,
                            "bottom":"auto",
                            "left":enlargeTextLeft-(enlargeTextW+13),
                            "right":"auto",
                        });
                        $(".enlarge_text_arrow").css({
                            "top":"6px",
                            "bottom":"auto",
                            "left":enlargeTextW+"px",
                            "right":"auto",
                            "border":"12px solid transparent",
                            "border-left-color":"#444444",
                            "border-right-width":0
                        })
                    }else{
                        //左上框
                        $("#enlarge_text").css({
                            display:"block",
                            "top":enlargeTextTop-enlargeTextH+enlargeEletH,
                            "bottom":"auto",
                            "left":enlargeTextLeft-(enlargeTextW+13),
                            "right":"auto"
                        });
                        $(".enlarge_text_arrow").css({
                            "top":"auto",
                            "bottom":"6px",
                            "left":enlargeTextW+"px",
                            "right":"auto",
                            "border":"12px solid transparent",
                            "border-left-color":"#444444",
                            "border-right-width":0
                        });
                    }
                }else{
                    //右框
                    if((enlargeTextTop+enlargeTextH)<(windowH-10)){
                        //右下框
                        $("#enlarge_text").css({
                            display:"block",
                            "top":enlargeTextTop,
                            "bottom":"auto",
                            "left":enlargeTextLeft+enlargeEleW+13,
                            "right":"auto"
                        });
                        $(".enlarge_text_arrow").css({
                            "top":"6px",
                            "bottom":"auto",
                            "left":"-12px",
                            "right":"auto",
                            "border":"12px solid transparent",
                            "border-right-color":"#444444",
                            "border-left-width":0
                        });
                    }else{
                        //右上框
                        $("#enlarge_text").css({
                            display:"block",
                            "top":enlargeTextTop-enlargeTextH+enlargeEletH,
                            "bottom":"auto",
                            "left":enlargeTextLeft+enlargeEleW+13,
                            "right":"auto"
                        });
                        $(".enlarge_text_arrow").css({
                            "top":"auto",
                            "bottom":"6px",
                            "left":"-12px",
                            "right":"auto",
                            "border":"12px solid transparent",
                            "border-right-color":"#444444",
                            "border-left-width":0
                        });
                    }
                }
            });
            $('body').on('mouseleave',string,function () {
                $("#enlarge_text").hide();
            });
        },
        use: []
    }
    $.extend({
        enlargePic:function (string) {
            /**
             * 功能：1.点击图片放大，2、鼠标移入图片展示方法简图，3、文字放大
             * 用法：a、功能1和功能2：调用$.enlargePic(图片对象)入口函数
             *      b、功能3：调用$.enlargePic()入口函数的前提下，并在使用的元素上加class属性：enlargeTextCon（展示元素的内容）或
             *      enlargeTextTitle（展示元素的data-title[展示此项要把展示的内容添加到此元素的data-title属性中]）
             */
            if(string&&string.length&&string.length>0){
                global_data.enlargeClick(string);
                global_data.enlargeHover(string);
            }
            global_data.enlargeText("[class*='enlargeText']");
        }
    });
})(jQuery);