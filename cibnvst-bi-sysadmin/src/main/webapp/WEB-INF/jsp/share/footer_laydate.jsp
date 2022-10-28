<%@ page language="java" pageEncoding="UTF-8"%>
 <!--日历-->
	<script type="text/javascript">
	  //方法1.使用样式控制日期格式
	  //onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
	  
	  //方法2.日期使用默认格式和范围（YYYY-MM-DD）
	  !function(){
	    laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库（不管是方法123此项必要）
	    //laydate({elem: '#startDate'});//绑定元素
	    //laydate({elem: '#endDate'});//绑定元素
	    laydate({elem: '#startDate2'});//绑定元素
	    laydate({elem: '#endDate2'});//绑定元素
	  }();
	  
	  //方法3.日期格式和范围限制
	  var start = {
	    elem: '#startDate',
	    //format: 'YYYY-MM-DD',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    //min: laydate.now(), //当前系统时间
	    //max: '2099-12-31', //最大日期
	    max: '2099-12-31 00:00:00', //最大日期
	    istime: true, //是否显示时分秒
	    istoday: false, //是否显示'今天'
	    choose: function(datas){
	      end.min = datas; //开始日选好后，重置结束日的最小日期
	      end.start = datas //将结束日的初始值设定为开始日
	    }
	  };
	  var end = {
	    elem: '#endDate',
	    //format: 'YYYY-MM-DD',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    min: laydate.now(), //当前系统时间
	    //max: '2099-12-31', //最大日期
	    max: '2099-12-31 00:00:00',
	    istime: true, //是否显示时分秒
	    istoday: false, //是否显示'今天'
	    choose: function(datas){
	      start.max = datas; //结束日选好后，充值开始日的最大日期
	    }
	  };
	  //绑定元素
	  laydate(start);
	  laydate(end);
	  
	  //给时间赋值
	  //$("#startDate").val(getNDateTime("YYYY-MM-DD",6));
  	  //$("#endDate").val(getNDateTime("YYYY-MM-DD",0));
	  //$("#startDate").val(getNDateTime("YYYY-MM-DD hh:mm:ss",6));
  	  //$("#endDate").val(getNDateTime("YYYY-MM-DD hh:mm:ss",0));
	</script>