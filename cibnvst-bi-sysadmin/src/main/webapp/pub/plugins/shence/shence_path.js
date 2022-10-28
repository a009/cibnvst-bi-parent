

var data =[
  {"name": "总共"},
  {"name": "环境"},
  {"name": "土地利用"},
  {"name": "可可油"},
  {"name": "可可块"},
  {"name": "榛子"},
  {"name": "蔗糖"},
  {"name": "蔬菜"},
  {"name": "气候变化"},
  {"name": "有害物质"},
  {"name": "饮用水"},
  {"name": "资源开发"},
  {"name": "冷冻"},
  {"name": "包装"},
  {"name": "人权"},
  {"name": "童工"},
  {"name": "椰子油"},
  {"name": "劳役"},
  {"name": "健康安全"},
  {"name": "获得水"},
  {"name": "结社自由"},
  {"name": "土地使用权"},
  {"name": "歧视"},
  {"name": "工时"}
  ]
var links = [
  {"source": "总共", "target": "环境", "value": 0.342284047256003},
  {"source": "环境", "target": "土地利用", "value": 0.32322870366987},
  {"source": "土地利用", "target": "可可油", "value": 0.177682517071359},
  {"source": "土地利用", "target": "可可块", "value": 0.137241325342711},
  {"source": "土地利用", "target": "榛子", "value": 0.00433076373512774},
  {"source": "土地利用", "target": "蔗糖", "value": 0.00296956039863467},
  {"source": "土地利用", "target": "蔬菜", "value": 0.00100453712203756},
  {"source": "环境", "target": "气候变化", "value": 0.0112886157414413},
  {"source": "气候变化", "target": "可可油 (Organic)", "value": 0.00676852971933996},
  {"source": "气候变化", "target": "可可块 (Organic)", "value": 0.00394686874786743},
  {"source": "气候变化", "target": "蔗糖 (Organic)", "value": 0.000315972058711838},
  {"source": "气候变化", "target": "榛子 (Organic)", "value": 0.000218969462265292},
  {"source": "气候变化", "target": "蔬菜 (Organic)", "value": 3.82757532567656e-05},
  {"source": "环境", "target": "有害物质", "value": 0.00604275542495656},
  {"source": "有害物质", "target": "可可块 (Organic)", "value": 0.0055125989240741},
  {"source": "有害物质", "target": "可可油 (Organic)", "value": 0.000330017607892127},
  {"source": "有害物质", "target": "蔗糖 (Organic)", "value": 0.000200138892990337},
  {"source": "有害物质", "target": "榛子 (Organic)", "value": 0},
  {"source": "有害物质", "target": "蔬菜 (Organic)", "value": 0},
  {"source": "环境", "target": "饮用水", "value": 0.00148345269044703},
  {"source": "饮用水", "target": "可可油", "value": 0.00135309891304186},
  {"source": "饮用水", "target": "可可块", "value": 0.000105714137908639},
  {"source": "饮用水", "target": "榛子", "value": 1.33452642581887e-05},
  {"source": "饮用水", "target": "蔗糖", "value": 8.78074837009238e-06},
  {"source": "饮用水", "target": "蔬菜", "value": 2.5136268682477e-06},
  {"source": "环境", "target": "资源开发", "value": 0.000240519729288764},
  {"source": "资源开发", "target": "蔗糖", "value": 0.000226237279345084},
  {"source": "资源开发", "target": "蔬菜", "value": 1.42824499436793e-05},
  {"source": "资源开发", "target": "榛子", "value": 0},
  {"source": "资源开发", "target": "可可块", "value": 0},
  {"source": "资源开发", "target": "可可油", "value": 0},
  {"source": "环境", "target": "冷冻", "value": 0},
  {"source": "环境", "target": "包装", "value": 0},
  {"source": "总共", "target": "人权", "value": 0.307574096993239},
  {"source": "人权", "target": "童工", "value": 0.0410641202645833},
  {"source": "童工", "target": "榛子", "value": 0.0105339381639722},
  {"source": "童工", "target": "可可块", "value": 0.0105},
  {"source": "童工", "target": "可可油", "value": 0.0087294420777},
  {"source": "童工", "target": "椰子油", "value": 0.00474399974233333},
  {"source": "童工", "target": "蔗糖", "value": 0.00388226450884445},
  {"source": "童工", "target": "蔬菜", "value": 0.00267447577173333},
  {"source": "人权", "target": "劳役", "value": 0.0365458590642445},
  {"source": "劳役", "target": "榛子", "value": 0.0114913076376389},
  {"source": "劳役", "target": "可可油", "value": 0.0081134807347},
  {"source": "劳役", "target": "可可块", "value": 0.00765230236575},
  {"source": "劳役", "target": "蔗糖", "value": 0.004},
  {"source": "劳役", "target": "蔬菜", "value": 0.00296668823626667},
  {"source": "劳役", "target": "椰子油", "value": 0.00232208008988889},
  {"source": "人权", "target": "健康安全", "value": 0.0345435327843611},
  {"source": "健康安全", "target": "榛子", "value": 0.0121419536385},
  {"source": "健康安全", "target": "可可块", "value": 0.00766772850678333},
  {"source": "健康安全", "target": "可可油", "value": 0.0056245892061},
  {"source": "健康安全", "target": "椰子油", "value": 0.00361616847688889},
  {"source": "健康安全", "target": "蔗糖", "value": 0.00277374682533333},
  {"source": "健康安全", "target": "蔬菜", "value": 0.00271934613075556},
  {"source": "人权", "target": "获得水", "value": 0.0340206659360667},
  {"source": "获得水", "target": "可可块", "value": 0.0105},
  {"source": "获得水", "target": "可可油", "value": 0.0089274160792},
  {"source": "获得水", "target": "榛子", "value": 0.0054148022845},
  {"source": "获得水", "target": "蔗糖", "value": 0.00333938149786667},
  {"source": "获得水", "target": "蔬菜", "value": 0.00314663377488889},
  {"source": "获得水", "target": "椰子油", "value": 0.00269243229961111},
  {"source": "人权", "target": "结社自由", "value": 0.0320571523941667},
  {"source": "结社自由", "target": "榛子", "value": 0.0132312483463611},
  {"source": "结社自由", "target": "可可油", "value": 0.0077695200707},
  {"source": "结社自由", "target": "可可块", "value": 0.00510606573995},
  {"source": "结社自由", "target": "蔬菜", "value": 0.00354321156324444},
  {"source": "结社自由", "target": "蔗糖", "value": 0.00240710667391111},
  {"source": "结社自由", "target": "椰子油", "value": 0},
  {"source": "人权", "target": "土地使用权", "value": 0.0315022209894056},
  {"source": "土地使用权", "target": "榛子", "value": 0.00964970063322223},
  {"source": "土地使用权", "target": "可可块", "value": 0.00938530207965},
  {"source": "土地使用权", "target": "可可油", "value": 0.0060110791848},
  {"source": "土地使用权", "target": "蔗糖", "value": 0.00380818314608889},
  {"source": "土地使用权", "target": "蔬菜", "value": 0.00264795594564445},
  {"source": "土地使用权", "target": "椰子油", "value": 0},
  {"source": "人权", "target": "歧视", "value": 0.0211217763359833},
  {"source": "歧视", "target": "可可块", "value": 0.00609671700306667},
  {"source": "歧视", "target": "可可油", "value": 0.0047738806325},
  {"source": "歧视", "target": "椰子油", "value": 0.00368119084494444},
  {"source": "歧视", "target": "蔬菜", "value": 0.00286009813604444},
  {"source": "歧视", "target": "蔗糖", "value": 0.00283706180951111},
  {"source": "歧视", "target": "榛子", "value": 0.000872827909916666},
  {"source": "人权", "target": "工时", "value": 0.02082642898975},
  {"source": "工时", "target": "榛子", "value": 0.0107216773848333}
  ];
 


var myChart = echarts.init(document.getElementById('main'));  //声明一个ehcarts对象
var option = {
  title: {
    // text: 'Sankey Diagram'
  },
  tooltip: {
    trigger: 'item',
    triggerOn: 'mousemove'
  },
 
  series: [{
    type: 'sankey',
    layout: 'none',
    top:'10%',
    data: data,
    links: links,
    nodeWidth:160,
    label:{
      normal:{
        show:true,
        position:'inside',
      }
    },
    itemStyle: {         //节点的样式
      normal: {
        color:'rgba(0, 167, 208, 0.5)',
        borderWidth: 1,
        borderColor: '#00a7d0',

      }
    },
    tooltip:{
       formatter: '{b}<br /><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#c23531"></span>{c0}%',
       padding:5
    },
    lineStyle: {     //line的样式
      normal: {
        //color: 'source',
        curveness: 0.5
      }
    }
  }]
}
myChart.setOption(option);


//时间
$("#inputDate").daterangepicker({
    separator: " 至 ",
    ranges : {
      '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
      '今日': [moment().startOf('day'), moment()],
      '上周': [moment().subtract(1, 'week').startOf('week').add(1, 'days'), moment().subtract(1, 'week').endOf('week').add(1, 'days')],
      '本周': [moment().startOf('week').add(1, 'days'), moment()],
      '上月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')],
      '本月': [moment().startOf('month'), moment()],
      '去年': [moment().subtract('year', 1).startOf('year'), moment().subtract('year', 1).endOf('year')],
      '本年': [moment().startOf('year'), moment()],
      '最近7日': [moment().subtract('days', 6), moment()],
      '最近30日': [moment().subtract('days', 29), moment()],
      '最近60日': [moment().subtract('days', 59), moment()]
  },
  locale : {
    customRangeLabel : '自定义',
    firstDay : 0,
    startOfWeek:"一",
  },
  startDate: moment().subtract(1, 'hours'),
  endDate: moment(),
  maxDate: moment(),
  alwaysShowCalendars: true
},
  function(start, end) {//格式化日期显示框
  $('#inputDate').val(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
}
);
// 初始化 时间范围
$("#inputDate").val(moment().subtract( 2,'days').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'));

