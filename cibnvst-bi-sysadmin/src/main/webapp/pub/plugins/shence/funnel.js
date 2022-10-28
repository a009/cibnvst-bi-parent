$(function () {
    var g_data={
        all:[
            {
                "bucket_id": 6,
                "virtual": false,
                "visible": true,
                "pinyin": "$ qi dong app",
                "name": "$AppStart",
                "cname": "$启动app",
                "id": 7,
                "tag": []
            }, {
                "bucket_id": 8,
                "virtual": false,
                "visible": true,
                "pinyin": "$ liu lan ye mian (app)",
                "name": "$AppViewScreen",
                "cname": "$浏览页面(app)",
                "id": 29,
                "tag": []
            }, {
                "bucket_id": 9,
                "virtual": false,
                "visible": true,
                "pinyin": "$ tui chu app",
                "name": "$AppEnd",
                "cname": "$退出app",
                "id": 30,
                "tag": []
            }, {
                "bucket_id": 2,
                "virtual": false,
                "visible": true,
                "pinyin": "App  dian ji",
                "name": "$AppClick",
                "cname": "App 点击",
                "id": 37,
                "tag": [2, 3]
            }, {
                "bucket_id": 14,
                "virtual": false,
                "visible": true,
                "pinyin": "Web  yuan su dian ji",
                "name": "$WebClick",
                "cname": "Web 元素点击",
                "id": 36,
                "tag": []
            }, {
                "bucket_id": 13,
                "virtual": false,
                "visible": true,
                "pinyin": "Web  liu lan ye mian",
                "name": "$pageview",
                "cname": "Web 浏览页面",
                "id": 35,
                "tag": [2, 4]
            }, {
                "bucket_id": 3,
                "virtual": false,
                "visible": true,
                "pinyin": "shang chuan duan shi pin",
                "name": "uploadVideo",
                "cname": "上传短视频",
                "id": 24,
                "tag": []
            }, {
                "bucket_id": 1,
                "virtual": false,
                "visible": true,
                "pinyin": "ge ren xiang qing ye",
                "name": "personalDetailsPage",
                "cname": "个人详情页",
                "id": 32,
                "tag": []
            }, {
                "bucket_id": 9,
                "virtual": false,
                "visible": true,
                "pinyin": "zhu bo shi ming ren zheng",
                "name": "realNameAuthentication",
                "cname": "主播实名认证",
                "id": 10,
                "tag": []
            }, {
                "bucket_id": 5,
                "virtual": false,
                "visible": true,
                "pinyin": "ju bao shi pin",
                "name": "reportVideo",
                "cname": "举报视频",
                "id": 26,
                "tag": []
            }, {
                "bucket_id": 7,
                "virtual": false,
                "visible": true,
                "pinyin": "chong zhi",
                "name": "recharge",
                "cname": "充值",
                "id": 18,
                "tag": []
            }, {
                "bucket_id": 9,
                "virtual": false,
                "visible": true,
                "pinyin": "guan zhu",
                "name": "attention",
                "cname": "关注",
                "id": 20,
                "tag": []
            }, {
                "bucket_id": 0,
                "virtual": false,
                "visible": true,
                "pinyin": "fen xiang zhi bo",
                "name": "share",
                "cname": "分享直播",
                "id": 21,
                "tag": []
            }, {
                "bucket_id": 0,
                "virtual": false,
                "visible": true,
                "pinyin": "fen xiang duan shi pin",
                "name": "shareVideo",
                "cname": "分享短视频",
                "id": 1,
                "tag": []
            }, {
                "bucket_id": 3,
                "virtual": false,
                "visible": true,
                "pinyin": "qu xiao guan zhu",
                "name": "cancelAttention",
                "cname": "取消关注",
                "id": 4,
                "tag": []
            }, {
                "bucket_id": 1,
                "virtual": false,
                "visible": true,
                "pinyin": "kai shi lu zhi duan shi pin",
                "name": "startRecording",
                "cname": "开始录制短视频",
                "id": 22,
                "tag": []
            }, {
                "bucket_id": 0,
                "virtual": false,
                "visible": true,
                "pinyin": "kai shi zhi bo",
                "name": "startLive",
                "cname": "开始直播",
                "id": 11,
                "tag": []
            }, {
                "bucket_id": 5,
                "virtual": false,
                "visible": true,
                "pinyin": "sou suo",
                "name": "search",
                "cname": "搜索",
                "id": 6,
                "tag": []
            }, {
                "bucket_id": 7,
                "virtual": false,
                "visible": true,
                "pinyin": "zhu ce",
                "name": "signUp",
                "cname": "注册",
                "id": 28,
                "tag": []
            }, {
                "virtual": true,
                "visible": true,
                "pinyin": "huo yue te zheng shi jian -ghm",
                "name": "LoyaltyUser",
                "cname": "活跃特征事件-ghm",
                "id": 38,
                "tag": []
            }, {
                "bucket_id": 2,
                "virtual": false,
                "visible": true,
                "pinyin": "ji huo APP",
                "name": "AppInstall",
                "cname": "激活APP",
                "id": 13,
                "tag": []
            }, {
                "bucket_id": 3,
                "virtual": false,
                "visible": true,
                "pinyin": "dian ji banner",
                "name": "clickBanner",
                "cname": "点击banner",
                "id": 14,
                "tag": []
            }, {
                "bucket_id": 8,
                "virtual": false,
                "visible": true,
                "pinyin": "dian zan",
                "name": "praise",
                "cname": "点赞",
                "id": 9,
                "tag": []
            }, {
                "bucket_id": 4,
                "virtual": false,
                "visible": true,
                "pinyin": "liu yan",
                "name": "message",
                "cname": "留言",
                "id": 5,
                "tag": []
            }, {
                "bucket_id": 0,
                "virtual": false,
                "visible": true,
                "pinyin": "deng lu",
                "name": "login",
                "cname": "登录",
                "id": 31,
                "tag": []
            }, {
                "virtual": true,
                "visible": true,
                "pinyin": "zhi bo hu dong",
                "name": "interactive",
                "cname": "直播互动",
                "id": 33,
                "tag": []
            }, {
                "bucket_id": 8,
                "virtual": false,
                "visible": true,
                "pinyin": "duan shi pin qu xiao dian zan",
                "name": "cancelPraiseVideo",
                "cname": "短视频取消点赞",
                "id": 19,
                "tag": []
            }, {
                "bucket_id": 1,
                "virtual": false,
                "visible": true,
                "pinyin": "duan shi pin bo fang",
                "name": "playVideo",
                "cname": "短视频播放",
                "id": 12,
                "tag": []
            }, {
                "bucket_id": 4,
                "virtual": false,
                "visible": true,
                "pinyin": "duan shi pin dian zan",
                "name": "praiseVideo",
                "cname": "短视频点赞",
                "id": 15,
                "tag": []
            }, {
                "bucket_id": 5,
                "virtual": false,
                "visible": true,
                "pinyin": "si xin zhu bo",
                "name": "privateChat",
                "cname": "私信主播",
                "id": 16,
                "tag": []
            }, {
                "bucket_id": 2,
                "virtual": false,
                "visible": true,
                "pinyin": "jie shu lu zhi duan shi pin",
                "name": "endRecording",
                "cname": "结束录制短视频",
                "id": 23,
                "tag": []
            }, {
                "bucket_id": 6,
                "virtual": false,
                "visible": true,
                "pinyin": "jie shu zhi bo",
                "name": "endLive",
                "cname": "结束直播",
                "id": 17,
                "tag": []
            }, {
                "bucket_id": 2,
                "virtual": false,
                "visible": true,
                "pinyin": "huo qu yan zheng ma",
                "name": "getCode",
                "cname": "获取验证码",
                "id": 3,
                "tag": []
            }, {
                "virtual": true,
                "visible": true,
                "pinyin": "shi pin bo fang",
                "name": "startVideo",
                "cname": "视频播放",
                "id": 34,
                "tag": []
            }, {
                "bucket_id": 6,
                "virtual": false,
                "visible": true,
                "pinyin": "ping lun duan shi pin",
                "name": "commentVideo",
                "cname": "评论短视频",
                "id": 27,
                "tag": []
            }, {
                "bucket_id": 7,
                "virtual": false,
                "visible": true,
                "pinyin": "jin ru zhi bo jian",
                "name": "goToRoom",
                "cname": "进入直播间",
                "id": 8,
                "tag": []
            }, {
                "bucket_id": 4,
                "virtual": false,
                "visible": true,
                "pinyin": "tui chu zhi bo jian",
                "name": "leaveRoom",
                "cname": "退出直播间",
                "id": 25,
                "tag": []
            }, {
                "bucket_id": 1,
                "virtual": false,
                "visible": true,
                "pinyin": "song li wu",
                "name": "sendGift",
                "cname": "送礼物",
                "id": 2,
                "tag": []
            }
        ],
        //可以显示漏斗的列表
        funnel:[
            {
                "access": true,
                "user_id": 25327,
                "step_name": ["$启动app", "$启动app"],
                "name": "12",
                "id": 95,
                "max_convert_time": 20160
            }, {
                "access": true,
                "user_id": 2989,
                "step_name": ["$启动app", "$浏览页面(app)", "注册", "上传短视频"],
                "name": "3D照片",
                "id": 22,
                "max_convert_time": 10080
            }, {
                "access": true,
                "user_id": 2989,
                "step_name": ["$浏览页面(app)", "$浏览页面(app)", "上传短视频", "$浏览页面(app)"],
                "name": "dh",
                "id": 33,
                "max_convert_time": 10080
            }, {
                "access": true,
                "user_id": 2989,
                "step_name": ["$启动app", "短视频播放", "点赞", "关注", "分享短视频"],
                "name": "短视频播放",
                "id": 5,
                "max_convert_time": 10080
            }, {
                "access": true,
                "user_id": 2989,
                "step_name": ["$浏览页面(app)", "App 点击", "$浏览页面(app)", "视频播放"],
                "name": "流程漏斗",
                "id": 30,
                "max_convert_time": 60
            }, {
                "access": true,
                "user_id": 1,
                "step_name": ["$启动app", "登录", "进入直播间", "直播互动", "送礼物"],
                "name": "直播",
                "id": 1,
                "max_convert_time": 10080
            }, {
                "access": true,
                "user_id": 1,
                "step_name": ["$启动app", "进入直播间", "直播互动", "送礼物", "充值"],
                "name": "6月支付",
                "id": 2,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 19611,
                "step_name": ["$启动app", "$浏览页面(app)", "$退出app"],
                "name": "测试漏斗",
                "id": 3,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 5075,
                "step_name": ["点击banner", "$启动app", "充值", "举报视频", "充值"],
                "name": "从站外到站内",
                "id": 4,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 19993,
                "step_name": ["$启动app", "登录", "上传短视频"],
                "name": "上传",
                "id": 6,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 19993,
                "step_name": ["$启动app", "注册", "登录"],
                "name": "注册/登录",
                "id": 7,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 8282,
                "step_name": ["$启动app", "注册", "开始直播"],
                "name": "只播",
                "id": 11,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 8282,
                "step_name": ["$启动app", "登录", "充值", "$退出app"],
                "name": "充值",
                "id": 12,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20075,
                "step_name": ["$启动app", "$浏览页面(app)", "注册", "$浏览页面(app)"],
                "name": "注册转化",
                "id": 13,
                "max_convert_time": 60
            }, {
                "access": false,
                "user_id": 8282,
                "step_name": ["$启动app", "注册", "登录"],
                "name": "注册",
                "id": 14,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 18624,
                "step_name": ["App 点击", "充值"],
                "name": "test12345",
                "id": 15,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20550,
                "step_name": ["$启动app", "$浏览页面(app)", "$退出app"],
                "name": "启动到退出",
                "id": 16,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 4165,
                "step_name": ["登录", "$启动app"],
                "name": "ppp",
                "id": 17,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20438,
                "step_name": ["App 点击", "Web 浏览页面"],
                "name": "test615",
                "id": 18,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20750,
                "step_name": ["$启动app", "开始直播", "分享直播", "充值", "直播互动"],
                "name": "东方小倩直播间",
                "id": 21,
                "max_convert_time": 43200
            }, {
                "access": false,
                "user_id": 19749,
                "step_name": ["$启动app", "注册", "开始直播"],
                "name": "转化漏斗",
                "id": 23,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20513,
                "step_name": ["$启动app", "$浏览页面(app)", "$退出app", "$启动app"],
                "name": "999",
                "id": 24,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 21053,
                "step_name": ["$启动app", "注册", "充值"],
                "name": "注册转化率",
                "id": 25,
                "max_convert_time": 259200
            }, {
                "access": false,
                "user_id": 21171,
                "step_name": ["App 点击", "注册", "短视频播放"],
                "name": "用户流失模型",
                "id": 26,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 21471,
                "step_name": ["$启动app", "上传短视频", "点赞", "分享短视频"],
                "name": "短视频上传",
                "id": 27,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 21607,
                "step_name": ["$浏览页面(app)", "App 点击", "充值"],
                "name": "testliyan",
                "id": 28,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22279,
                "step_name": ["App 点击", "$浏览页面(app)"],
                "name": "test1",
                "id": 31,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22331,
                "step_name": ["注册", "登录", "进入直播间"],
                "name": "注册观看直播转化率",
                "id": 32,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22778,
                "step_name": ["$浏览页面(app)", "举报视频", "直播互动", "$浏览页面(app)", "$浏览页面(app)", "$浏览页面(app)", "$浏览页面(app)", "$浏览页面(app)", "$浏览页面(app)"],
                "name": "123",
                "id": 34,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 10374,
                "step_name": ["$浏览页面(app)", "上传短视频", "分享直播"],
                "name": "快爽",
                "id": 35,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 6978,
                "step_name": ["个人详情页", "个人详情页", "分享短视频"],
                "name": "test",
                "id": 36,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["$浏览页面(app)", "123"],
                "name": "333",
                "id": 37,
                "max_convert_time": 306720
            }, {
                "access": false,
                "user_id": 22994,
                "step_name": ["$浏览页面(app)", "上传短视频", "个人详情页"],
                "name": "4454",
                "id": 38,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22994,
                "step_name": ["App 点击", "$浏览页面(app)"],
                "name": "5",
                "id": 39,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["$浏览页面(app)", "App 点击", "Web 元素点击", "上传短视频"],
                "name": "111",
                "id": 40,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 21053,
                "step_name": ["App 点击", "$浏览页面(app)", "App 点击", "关注"],
                "name": "test关注",
                "id": 41,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 18963,
                "step_name": ["Web 元素点击", "$浏览页面(app)"],
                "name": "撒",
                "id": 42,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 18963,
                "step_name": ["上传短视频", "$浏览页面(app)", "举报视频", "个人详情页"],
                "name": "逛淘宝",
                "id": 43,
                "max_convert_time": 20160
            }, {
                "access": false,
                "user_id": 1312,
                "step_name": ["进入直播间", "送礼物", "退出直播间", "$退出app"],
                "name": "test1111",
                "id": 44,
                "max_convert_time": 1440
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["$浏览页面(app)", "Web 元素点击", "上传短视频"],
                "name": "1",
                "id": 45,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["$浏览页面(app)", "$浏览页面(app)"],
                "name": "233",
                "id": 46,
                "max_convert_time": 43200
            }, {
                "access": false,
                "user_id": 1312,
                "step_name": ["直播互动", "$浏览页面(app)", "$浏览页面(app)"],
                "name": "121",
                "id": 47,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 23723,
                "step_name": ["打开千寻", "浏览直播页面", "进入直播页面", "退出直播页面"],
                "name": "国广直播",
                "id": 48,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 23723,
                "step_name": ["打开千寻", "浏览直播页面", "进入直播页面", "退出直播页面"],
                "name": "国广直播",
                "id": 49,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 1312,
                "step_name": ["$浏览页面(app)", "送礼物", "充值"],
                "name": "中国充值转化",
                "id": 50,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 23890,
                "step_name": ["$浏览页面(app)", "App 点击"],
                "name": "漏斗测试",
                "id": 52,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 18422,
                "step_name": ["$启动app", "开始直播", "分享直播", "注册", "登录", "充值", "送礼物"],
                "name": "直播APP流程",
                "id": 53,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24075,
                "step_name": ["$浏览页面(app)", "App 点击", "开始直播"],
                "name": "我是漏斗",
                "id": 54,
                "max_convert_time": 60
            }, {
                "access": false,
                "user_id": 23336,
                "step_name": ["Web 浏览页面", "Web 元素点击"],
                "name": "web使用",
                "id": 55,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 9202,
                "step_name": ["App 点击", "搜索", "进入直播间", "分享直播"],
                "name": "分享直播",
                "id": 56,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 6279,
                "step_name": ["$启动app", "进入直播间", "退出直播间", "分享直播"],
                "name": "直播转化",
                "id": 57,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["$浏览页面(app)", "App 点击", "上传短视频"],
                "name": "2123",
                "id": 58,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["App 点击", "充值"],
                "name": "23214",
                "id": 59,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 20747,
                "step_name": ["$浏览页面(app)", "App 点击", "Web 元素点击", "Web 浏览页面", "上传短视频", "个人详情页", "主播实名认证", "举报视频", "充值", "关注", "分享直播", "分享短视频"],
                "name": "长漏斗",
                "id": 60,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24575,
                "step_name": ["短视频播放", "短视频点赞", "私信主播"],
                "name": "播放-点赞-私信",
                "id": 61,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 18455,
                "step_name": ["$浏览页面(app)", "$浏览页面(app)", "上传短视频"],
                "name": "123123123",
                "id": 62,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 1312,
                "step_name": ["$浏览页面(app)", "$浏览页面(app)", "$浏览页面(app)"],
                "name": "11aaa",
                "id": 63,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24229,
                "step_name": ["$浏览页面(app)", "主播实名认证"],
                "name": "8",
                "id": 64,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24436,
                "step_name": ["激活APP", "$启动app", "充值"],
                "name": "转化",
                "id": 65,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22978,
                "step_name": ["$浏览页面(app)", "$浏览页面(app)"],
                "name": "2312312312",
                "id": 66,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24558,
                "step_name": ["网页/客户端浏览页面", "观看视频", "分享视频"],
                "name": "视频分享转化率",
                "id": 67,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24558,
                "step_name": ["网页/客户端浏览页面", "注册为会员", "充值为VIP"],
                "name": "用户-会员-VIP转化率",
                "id": 68,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24558,
                "step_name": ["网页/客户端浏览页面", "点击进入商城", "浏览商品", "提交订单", "支付订单", "复购（再次成功下单）"],
                "name": "音悦商城下单转换率",
                "id": 69,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 23759,
                "step_name": ["$启动app", "$浏览页面(app)"],
                "name": "我的漏斗",
                "id": 70,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 23759,
                "step_name": ["$启动app", "$启动app"],
                "name": "1231231",
                "id": 71,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 23759,
                "step_name": ["$启动app", "$启动app"],
                "name": "111111111",
                "id": 72,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24913,
                "step_name": ["$启动app", "进入直播间", "送礼物"],
                "name": "送礼物",
                "id": 73,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22355,
                "step_name": ["$启动app", "$启动app"],
                "name": "Mytest",
                "id": 74,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 24203,
                "step_name": ["$启动app", "$浏览页面(app)", "$退出app"],
                "name": "测试",
                "id": 75,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22923,
                "step_name": ["$启动app", "注册", "关注", "开始录制短视频"],
                "name": "录制漏斗",
                "id": 76,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22994,
                "step_name": ["$启动app", "$启动app"],
                "name": "abcdefg",
                "id": 77,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 6736,
                "step_name": ["$启动app", "个人详情页", "上传短视频", "主播实名认证", "开始直播"],
                "name": "完善资料直播",
                "id": 78,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25246,
                "step_name": ["$启动app", "$启动app", "$启动app", "App 点击"],
                "name": "cash",
                "id": 79,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 22978,
                "step_name": ["$浏览页面(app)", "App 点击"],
                "name": "testfilter",
                "id": 80,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25383,
                "step_name": ["$启动app", "$启动app", "$启动app"],
                "name": "123456789",
                "id": 81,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 6279,
                "step_name": ["激活APP", "App 点击", "开始直播"],
                "name": "中投测试",
                "id": 82,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25476,
                "step_name": ["$启动app", "$浏览页面(app)", "主播实名认证", "分享直播", "直播互动"],
                "name": "测试数据",
                "id": 83,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25275,
                "step_name": ["注册", "登录", "开始直播", "充值"],
                "name": "注册-登录-直播-充值",
                "id": 84,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25476,
                "step_name": ["$启动app", "上传短视频", "举报视频", "开始录制短视频", "直播互动"],
                "name": "测试留存分析",
                "id": 85,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25874,
                "step_name": ["$启动app", "$退出app"],
                "name": "123456",
                "id": 86,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 25874,
                "step_name": ["$启动app", "$浏览页面(app)", "App 点击", "$退出app"],
                "name": "100120",
                "id": 87,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "神策漏斗",
                "id": 88,
                "max_convert_time": 14400
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "生产2",
                "id": 89,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "生产3",
                "id": 90,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "生产4",
                "id": 91,
                "max_convert_time": 144000
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app", "$启动app"],
                "name": "测试5",
                "id": 92,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "测试6",
                "id": 93,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "请问",
                "id": 94,
                "max_convert_time": 5
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "额外全额",
                "id": 96,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "士大夫但是不",
                "id": 97,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "我去饿v",
                "id": 98,
                "max_convert_time": 10080
            }, {
                "access": false,
                "user_id": 26240,
                "step_name": ["$启动app", "$启动app"],
                "name": "啊上次",
                "id": 99,
                "max_convert_time": 10080
            }],
        //筛选属性
        properties:{
            "session": [],
            "step_name": ["$启动app", "进入直播间", "直播互动", "送礼物", "充值"],
            "event": [{
                "db_column_name": "p__ip",
                "is_dimension": true,
                "has_dict": false,
                "cname": "IP",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "IP",
                "event_id": -1,
                "name": "$ip",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 94,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__country",
                "is_dimension": true,
                "has_dict": false,
                "cname": "国家",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guo jia",
                "event_id": -1,
                "name": "$country",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 82,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__screen_width",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕宽度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu kuan du",
                "event_id": -1,
                "name": "$screen_width",
                "data_type": "number",
                "event_name": "$Anything",
                "id": 89,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__screen_height",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕高度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu gao du",
                "event_id": -1,
                "name": "$screen_height",
                "data_type": "number",
                "event_name": "$Anything",
                "id": 88,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "event_id": -1,
                "name": "$utm_medium",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 91,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "event_id": -1,
                "name": "$utm_source",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 92,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__app_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "应用版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ying yong ban ben",
                "event_id": -1,
                "name": "$app_version",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 76,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__os",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong",
                "event_id": -1,
                "name": "$os",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 83,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__os_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong ban ben",
                "event_id": -1,
                "name": "$os_version",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 85,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__wifi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否 WIFI",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou  WIFI",
                "event_id": -1,
                "name": "$wifi",
                "data_type": "bool",
                "event_name": "$Anything",
                "id": 93,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__browser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi",
                "event_id": -1,
                "name": "$browser",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 77,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__browser_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi ban ben",
                "event_id": -1,
                "name": "$browser_version",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 78,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "event_id": -1,
                "name": "$utm_matching_type",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 90,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "user_id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "event_id": -1,
                "name": "$user_id",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 1000001,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "event_id": -1,
                "name": "$province",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 80,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "event_id": -1,
                "name": "$city",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 81,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__network_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "网络类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wang luo lei xing",
                "event_id": -1,
                "name": "$network_type",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 87,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__manufacturer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "设备制造商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei zhi zao shang",
                "event_id": -1,
                "name": "$manufacturer",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 86,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__model",
                "is_dimension": true,
                "has_dict": true,
                "cname": "设备型号",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei xing hao",
                "event_id": -1,
                "name": "$model",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 84,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__carrier",
                "is_dimension": true,
                "has_dict": false,
                "cname": "运营商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yun ying shang",
                "event_id": -1,
                "name": "$carrier",
                "data_type": "string",
                "event_name": "$Anything",
                "id": 79,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__ip",
                "is_dimension": true,
                "has_dict": false,
                "cname": "IP",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "IP",
                "event_id": 7,
                "name": "$ip",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 94,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__country",
                "is_dimension": true,
                "has_dict": false,
                "cname": "国家",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guo jia",
                "event_id": 7,
                "name": "$country",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 82,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__screen_width",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕宽度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu kuan du",
                "event_id": 7,
                "name": "$screen_width",
                "data_type": "number",
                "event_name": "$AppStart",
                "id": 89,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__screen_height",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕高度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu gao du",
                "event_id": 7,
                "name": "$screen_height",
                "data_type": "number",
                "event_name": "$AppStart",
                "id": 88,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "event_id": 7,
                "name": "$utm_medium",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 91,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "event_id": 7,
                "name": "$utm_source",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 92,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__app_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "应用版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ying yong ban ben",
                "event_id": 7,
                "name": "$app_version",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 76,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__os",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong",
                "event_id": 7,
                "name": "$os",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 83,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__os_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong ban ben",
                "event_id": 7,
                "name": "$os_version",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 85,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__wifi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否 WIFI",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou  WIFI",
                "event_id": 7,
                "name": "$wifi",
                "data_type": "bool",
                "event_name": "$AppStart",
                "id": 93,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__browser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi",
                "event_id": 7,
                "name": "$browser",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 77,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__browser_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi ban ben",
                "event_id": 7,
                "name": "$browser_version",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 78,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "event_id": 7,
                "name": "$utm_matching_type",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 90,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "user_id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "event_id": 7,
                "name": "$user_id",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 1000001,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "event_id": 7,
                "name": "$province",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 80,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "event_id": 7,
                "name": "$city",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 81,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__network_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "网络类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wang luo lei xing",
                "event_id": 7,
                "name": "$network_type",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 87,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__manufacturer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "设备制造商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei zhi zao shang",
                "event_id": 7,
                "name": "$manufacturer",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 86,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__model",
                "is_dimension": true,
                "has_dict": true,
                "cname": "设备型号",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei xing hao",
                "event_id": 7,
                "name": "$model",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 84,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__carrier",
                "is_dimension": true,
                "has_dict": false,
                "cname": "运营商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yun ying shang",
                "event_id": 7,
                "name": "$carrier",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 79,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p_resume_from_background",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否从后台唤醒",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou cong hou tai huan xing",
                "event_id": 7,
                "name": "resume_from_background",
                "data_type": "string",
                "event_name": "$AppStart",
                "id": 45,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p_is_first_time",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否首日启动",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou shou ri qi dong",
                "event_id": 7,
                "name": "is_first_time",
                "data_type": "bool",
                "event_name": "$AppStart",
                "id": 31,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p_is_first_day",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否首次启动",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou shou ci qi dong",
                "event_id": 7,
                "name": "is_first_day",
                "data_type": "bool",
                "event_name": "$AppStart",
                "id": 30,
                "has_db_column_name": true,
                "funnel_step": 0
            }, {
                "db_column_name": "p__ip",
                "is_dimension": true,
                "has_dict": false,
                "cname": "IP",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "IP",
                "event_id": 8,
                "name": "$ip",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 94,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__country",
                "is_dimension": true,
                "has_dict": false,
                "cname": "国家",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guo jia",
                "event_id": 8,
                "name": "$country",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 82,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__screen_width",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕宽度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu kuan du",
                "event_id": 8,
                "name": "$screen_width",
                "data_type": "number",
                "event_name": "goToRoom",
                "id": 89,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__screen_height",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕高度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu gao du",
                "event_id": 8,
                "name": "$screen_height",
                "data_type": "number",
                "event_name": "goToRoom",
                "id": 88,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "event_id": 8,
                "name": "$utm_medium",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 91,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "event_id": 8,
                "name": "$utm_source",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 92,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__app_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "应用版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ying yong ban ben",
                "event_id": 8,
                "name": "$app_version",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 76,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__os",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong",
                "event_id": 8,
                "name": "$os",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 83,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__os_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong ban ben",
                "event_id": 8,
                "name": "$os_version",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 85,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__wifi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否 WIFI",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou  WIFI",
                "event_id": 8,
                "name": "$wifi",
                "data_type": "bool",
                "event_name": "goToRoom",
                "id": 93,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__browser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi",
                "event_id": 8,
                "name": "$browser",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 77,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__browser_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi ban ben",
                "event_id": 8,
                "name": "$browser_version",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 78,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "event_id": 8,
                "name": "$utm_matching_type",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 90,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "user_id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "event_id": 8,
                "name": "$user_id",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 1000001,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "event_id": 8,
                "name": "$province",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 80,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "event_id": 8,
                "name": "$city",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 81,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__network_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "网络类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wang luo lei xing",
                "event_id": 8,
                "name": "$network_type",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 87,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__manufacturer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "设备制造商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei zhi zao shang",
                "event_id": 8,
                "name": "$manufacturer",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 86,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__model",
                "is_dimension": true,
                "has_dict": true,
                "cname": "设备型号",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei xing hao",
                "event_id": 8,
                "name": "$model",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 84,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__carrier",
                "is_dimension": true,
                "has_dict": false,
                "cname": "运营商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yun ying shang",
                "event_id": 8,
                "name": "$carrier",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 79,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p_anchorid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo ID",
                "event_id": 8,
                "name": "anchorID",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 1,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p_nickname",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播昵称",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo ni cheng",
                "event_id": 8,
                "name": "nickName",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 37,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p_anchorlevel",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播等级",
                "cardinality": 0,
                "unit": "",
                "is_measure": true,
                "pinyin": "zhu bo deng ji",
                "event_id": 8,
                "name": "anchorLevel",
                "data_type": "number",
                "event_name": "goToRoom",
                "id": 3,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p_anchorlabel",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播标签（二级分类）",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo biao qian （ er ji fen lei ）",
                "event_id": 8,
                "name": "anchorLabel",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 2,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p_anchortype",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播类型（一级分类）",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo lei xing （ yi ji fen lei ）",
                "event_id": 8,
                "name": "anchorType",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 5,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p_roomid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播间ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo jian ID",
                "event_id": 8,
                "name": "roomID",
                "data_type": "string",
                "event_name": "goToRoom",
                "id": 46,
                "has_db_column_name": true,
                "funnel_step": 1
            }, {
                "db_column_name": "p__ip",
                "is_dimension": true,
                "has_dict": false,
                "cname": "IP",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "IP",
                "event_id": 33,
                "name": "$ip",
                "data_type": "string",
                "event_name": "interactive",
                "id": 94,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__country",
                "is_dimension": true,
                "has_dict": false,
                "cname": "国家",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guo jia",
                "event_id": 33,
                "name": "$country",
                "data_type": "string",
                "event_name": "interactive",
                "id": 82,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__screen_width",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕宽度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu kuan du",
                "event_id": 33,
                "name": "$screen_width",
                "data_type": "number",
                "event_name": "interactive",
                "id": 89,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__screen_height",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕高度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu gao du",
                "event_id": 33,
                "name": "$screen_height",
                "data_type": "number",
                "event_name": "interactive",
                "id": 88,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "event_id": 33,
                "name": "$utm_medium",
                "data_type": "string",
                "event_name": "interactive",
                "id": 91,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "event_id": 33,
                "name": "$utm_source",
                "data_type": "string",
                "event_name": "interactive",
                "id": 92,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__app_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "应用版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ying yong ban ben",
                "event_id": 33,
                "name": "$app_version",
                "data_type": "string",
                "event_name": "interactive",
                "id": 76,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__os",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong",
                "event_id": 33,
                "name": "$os",
                "data_type": "string",
                "event_name": "interactive",
                "id": 83,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__os_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong ban ben",
                "event_id": 33,
                "name": "$os_version",
                "data_type": "string",
                "event_name": "interactive",
                "id": 85,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__wifi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否 WIFI",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou  WIFI",
                "event_id": 33,
                "name": "$wifi",
                "data_type": "bool",
                "event_name": "interactive",
                "id": 93,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__browser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi",
                "event_id": 33,
                "name": "$browser",
                "data_type": "string",
                "event_name": "interactive",
                "id": 77,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__browser_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi ban ben",
                "event_id": 33,
                "name": "$browser_version",
                "data_type": "string",
                "event_name": "interactive",
                "id": 78,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "event_id": 33,
                "name": "$utm_matching_type",
                "data_type": "string",
                "event_name": "interactive",
                "id": 90,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "user_id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "event_id": 33,
                "name": "$user_id",
                "data_type": "string",
                "event_name": "interactive",
                "id": 1000001,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "event_id": 33,
                "name": "$province",
                "data_type": "string",
                "event_name": "interactive",
                "id": 80,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "event_id": 33,
                "name": "$city",
                "data_type": "string",
                "event_name": "interactive",
                "id": 81,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__network_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "网络类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wang luo lei xing",
                "event_id": 33,
                "name": "$network_type",
                "data_type": "string",
                "event_name": "interactive",
                "id": 87,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__manufacturer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "设备制造商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei zhi zao shang",
                "event_id": 33,
                "name": "$manufacturer",
                "data_type": "string",
                "event_name": "interactive",
                "id": 86,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__model",
                "is_dimension": true,
                "has_dict": true,
                "cname": "设备型号",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei xing hao",
                "event_id": 33,
                "name": "$model",
                "data_type": "string",
                "event_name": "interactive",
                "id": 84,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__carrier",
                "is_dimension": true,
                "has_dict": false,
                "cname": "运营商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yun ying shang",
                "event_id": 33,
                "name": "$carrier",
                "data_type": "string",
                "event_name": "interactive",
                "id": 79,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_anchorid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo ID",
                "event_id": 33,
                "name": "anchorID",
                "data_type": "string",
                "event_name": "interactive",
                "id": 1,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_nickname",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播昵称",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo ni cheng",
                "event_id": 33,
                "name": "nickName",
                "data_type": "string",
                "event_name": "interactive",
                "id": 37,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_anchorlevel",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播等级",
                "cardinality": 0,
                "unit": "",
                "is_measure": true,
                "pinyin": "zhu bo deng ji",
                "event_id": 33,
                "name": "anchorLevel",
                "data_type": "number",
                "event_name": "interactive",
                "id": 3,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_barragecost",
                "is_dimension": true,
                "has_dict": false,
                "cname": "弹幕花费",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "tan mu hua fei",
                "event_id": 33,
                "name": "barrageCost",
                "data_type": "number",
                "event_name": "interactive",
                "id": 12,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_isbarrage",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否弹幕",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou tan mu",
                "event_id": 33,
                "name": "isBarrage",
                "data_type": "bool",
                "event_name": "interactive",
                "id": 22,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_anchorlabel",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播标签（二级分类）",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo biao qian （ er ji fen lei ）",
                "event_id": 33,
                "name": "anchorLabel",
                "data_type": "string",
                "event_name": "interactive",
                "id": 2,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_anchortype",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播类型（一级分类）",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo lei xing （ yi ji fen lei ）",
                "event_id": 33,
                "name": "anchorType",
                "data_type": "string",
                "event_name": "interactive",
                "id": 5,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p_roomid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播间ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo jian ID",
                "event_id": 33,
                "name": "roomID",
                "data_type": "string",
                "event_name": "interactive",
                "id": 46,
                "has_db_column_name": true,
                "funnel_step": 2
            }, {
                "db_column_name": "p__ip",
                "is_dimension": true,
                "has_dict": false,
                "cname": "IP",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "IP",
                "event_id": 2,
                "name": "$ip",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 94,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__country",
                "is_dimension": true,
                "has_dict": false,
                "cname": "国家",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guo jia",
                "event_id": 2,
                "name": "$country",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 82,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__screen_width",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕宽度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu kuan du",
                "event_id": 2,
                "name": "$screen_width",
                "data_type": "number",
                "event_name": "sendGift",
                "id": 89,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__screen_height",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕高度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu gao du",
                "event_id": 2,
                "name": "$screen_height",
                "data_type": "number",
                "event_name": "sendGift",
                "id": 88,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "event_id": 2,
                "name": "$utm_medium",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 91,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "event_id": 2,
                "name": "$utm_source",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 92,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__app_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "应用版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ying yong ban ben",
                "event_id": 2,
                "name": "$app_version",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 76,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__os",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong",
                "event_id": 2,
                "name": "$os",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 83,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__os_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong ban ben",
                "event_id": 2,
                "name": "$os_version",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 85,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__wifi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否 WIFI",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou  WIFI",
                "event_id": 2,
                "name": "$wifi",
                "data_type": "bool",
                "event_name": "sendGift",
                "id": 93,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__browser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi",
                "event_id": 2,
                "name": "$browser",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 77,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__browser_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi ban ben",
                "event_id": 2,
                "name": "$browser_version",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 78,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "event_id": 2,
                "name": "$utm_matching_type",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 90,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "user_id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "event_id": 2,
                "name": "$user_id",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 1000001,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "event_id": 2,
                "name": "$province",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 80,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "event_id": 2,
                "name": "$city",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 81,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__network_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "网络类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wang luo lei xing",
                "event_id": 2,
                "name": "$network_type",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 87,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__manufacturer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "设备制造商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei zhi zao shang",
                "event_id": 2,
                "name": "$manufacturer",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 86,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__model",
                "is_dimension": true,
                "has_dict": true,
                "cname": "设备型号",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei xing hao",
                "event_id": 2,
                "name": "$model",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 84,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__carrier",
                "is_dimension": true,
                "has_dict": false,
                "cname": "运营商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yun ying shang",
                "event_id": 2,
                "name": "$carrier",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 79,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_anchorid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo ID",
                "event_id": 2,
                "name": "anchorID",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 1,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_nickname",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播昵称",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo ni cheng",
                "event_id": 2,
                "name": "nickName",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 37,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_anchorlevel",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播等级",
                "cardinality": 0,
                "unit": "",
                "is_measure": true,
                "pinyin": "zhu bo deng ji",
                "event_id": 2,
                "name": "anchorLevel",
                "data_type": "number",
                "event_name": "sendGift",
                "id": 3,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_experiencepergift",
                "is_dimension": true,
                "has_dict": false,
                "cname": "单礼物所获经验值",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "dan li wu suo huo jing yan zhi",
                "event_id": 2,
                "name": "experiencePerGift",
                "data_type": "number",
                "event_name": "sendGift",
                "id": 17,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_costpergift",
                "is_dimension": true,
                "has_dict": false,
                "cname": "单礼物花费金币",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "dan li wu hua fei jin bi",
                "event_id": 2,
                "name": "costPerGift",
                "data_type": "number",
                "event_name": "sendGift",
                "id": 15,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_anchorlabel",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播标签（二级分类）",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo biao qian （ er ji fen lei ）",
                "event_id": 2,
                "name": "anchorLabel",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 2,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_anchortype",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播类型（一级分类）",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo lei xing （ yi ji fen lei ）",
                "event_id": 2,
                "name": "anchorType",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 5,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_roomid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "直播间ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhi bo jian ID",
                "event_id": 2,
                "name": "roomID",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 46,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_numberpergift",
                "is_dimension": true,
                "has_dict": false,
                "cname": "礼物单次送出数量",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "li wu dan ci song chu shu liang",
                "event_id": 2,
                "name": "numberPerGift",
                "data_type": "number",
                "event_name": "sendGift",
                "id": 39,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_giftname",
                "is_dimension": true,
                "has_dict": false,
                "cname": "礼物名称",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "li wu ming cheng",
                "event_id": 2,
                "name": "giftName",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 19,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p_gifttype",
                "is_dimension": true,
                "has_dict": false,
                "cname": "礼物类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "li wu lei xing",
                "event_id": 2,
                "name": "giftType",
                "data_type": "string",
                "event_name": "sendGift",
                "id": 20,
                "has_db_column_name": true,
                "funnel_step": 3
            }, {
                "db_column_name": "p__ip",
                "is_dimension": true,
                "has_dict": false,
                "cname": "IP",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "IP",
                "event_id": 18,
                "name": "$ip",
                "data_type": "string",
                "event_name": "recharge",
                "id": 94,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__country",
                "is_dimension": true,
                "has_dict": false,
                "cname": "国家",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guo jia",
                "event_id": 18,
                "name": "$country",
                "data_type": "string",
                "event_name": "recharge",
                "id": 82,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__screen_width",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕宽度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu kuan du",
                "event_id": 18,
                "name": "$screen_width",
                "data_type": "number",
                "event_name": "recharge",
                "id": 89,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__screen_height",
                "is_dimension": true,
                "has_dict": false,
                "cname": "屏幕高度",
                "cardinality": 0,
                "unit": "像素",
                "is_measure": true,
                "pinyin": "ping mu gao du",
                "event_id": 18,
                "name": "$screen_height",
                "data_type": "number",
                "event_name": "recharge",
                "id": 88,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "event_id": 18,
                "name": "$utm_medium",
                "data_type": "string",
                "event_name": "recharge",
                "id": 91,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "event_id": 18,
                "name": "$utm_source",
                "data_type": "string",
                "event_name": "recharge",
                "id": 92,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__app_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "应用版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ying yong ban ben",
                "event_id": 18,
                "name": "$app_version",
                "data_type": "string",
                "event_name": "recharge",
                "id": 76,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__os",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong",
                "event_id": 18,
                "name": "$os",
                "data_type": "string",
                "event_name": "recharge",
                "id": 83,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__os_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "操作系统版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cao zuo xi tong ban ben",
                "event_id": 18,
                "name": "$os_version",
                "data_type": "string",
                "event_name": "recharge",
                "id": 85,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__wifi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否 WIFI",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou  WIFI",
                "event_id": 18,
                "name": "$wifi",
                "data_type": "bool",
                "event_name": "recharge",
                "id": 93,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__browser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi",
                "event_id": 18,
                "name": "$browser",
                "data_type": "string",
                "event_name": "recharge",
                "id": 77,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__browser_version",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器版本",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi ban ben",
                "event_id": 18,
                "name": "$browser_version",
                "data_type": "string",
                "event_name": "recharge",
                "id": 78,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "event_id": 18,
                "name": "$utm_matching_type",
                "data_type": "string",
                "event_name": "recharge",
                "id": 90,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "user_id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "event_id": 18,
                "name": "$user_id",
                "data_type": "string",
                "event_name": "recharge",
                "id": 1000001,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "event_id": 18,
                "name": "$province",
                "data_type": "string",
                "event_name": "recharge",
                "id": 80,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "event_id": 18,
                "name": "$city",
                "data_type": "string",
                "event_name": "recharge",
                "id": 81,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__network_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "网络类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wang luo lei xing",
                "event_id": 18,
                "name": "$network_type",
                "data_type": "string",
                "event_name": "recharge",
                "id": 87,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__manufacturer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "设备制造商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei zhi zao shang",
                "event_id": 18,
                "name": "$manufacturer",
                "data_type": "string",
                "event_name": "recharge",
                "id": 86,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__model",
                "is_dimension": true,
                "has_dict": true,
                "cname": "设备型号",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "she bei xing hao",
                "event_id": 18,
                "name": "$model",
                "data_type": "string",
                "event_name": "recharge",
                "id": 84,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p__carrier",
                "is_dimension": true,
                "has_dict": false,
                "cname": "运营商",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yun ying shang",
                "event_id": 18,
                "name": "$carrier",
                "data_type": "string",
                "event_name": "recharge",
                "id": 79,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p_rechargetype",
                "is_dimension": true,
                "has_dict": false,
                "cname": "充值方式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "chong zhi fang shi",
                "event_id": 18,
                "name": "rechargeType",
                "data_type": "string",
                "event_name": "recharge",
                "id": 43,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p_rechargeamount",
                "is_dimension": true,
                "has_dict": false,
                "cname": "充值金额",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "chong zhi jin e",
                "event_id": 18,
                "name": "rechargeAmount",
                "data_type": "number",
                "event_name": "recharge",
                "id": 42,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p_issuccess",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否成功",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou cheng gong",
                "event_id": 18,
                "name": "isSuccess",
                "data_type": "bool",
                "event_name": "recharge",
                "id": 29,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p_isfirsttime",
                "is_dimension": true,
                "has_dict": false,
                "cname": "是否首次",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shi fou shou ci",
                "event_id": 18,
                "name": "isFirstTime",
                "data_type": "bool",
                "event_name": "recharge",
                "id": 24,
                "has_db_column_name": true,
                "funnel_step": 4
            }, {
                "db_column_name": "p_number",
                "is_dimension": true,
                "has_dict": false,
                "cname": "金币数量",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "jin bi shu liang",
                "event_id": 18,
                "name": "number",
                "data_type": "number",
                "event_name": "recharge",
                "id": 38,
                "has_db_column_name": true,
                "funnel_step": 4
            }],
            "user": [{
                "db_column_name": "p_guildid",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播所属机构ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo suo shu ji gou ID",
                "name": "guildID",
                "data_type": "string",
                "is_segmenter": false,
                "id": 63,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_guildname",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播所属机构名称",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo suo shu ji gou ming cheng",
                "name": "guildName",
                "data_type": "string",
                "is_segmenter": false,
                "id": 64,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_yearofbirth",
                "is_dimension": true,
                "has_dict": false,
                "cname": "出生年份",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "chu sheng nian fen",
                "name": "yearOfBirth",
                "data_type": "number",
                "is_segmenter": false,
                "id": 73,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__name",
                "is_dimension": true,
                "has_dict": false,
                "cname": "姓名",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "xing ming",
                "name": "$name",
                "data_type": "string",
                "is_segmenter": false,
                "id": 97,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__utm_medium",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列媒介",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie mei jie",
                "name": "$utm_medium",
                "data_type": "string",
                "is_segmenter": false,
                "id": 100,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__utm_source",
                "is_dimension": true,
                "has_dict": false,
                "cname": "广告系列来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "guang gao xi lie lai yuan",
                "name": "$utm_source",
                "data_type": "string",
                "is_segmenter": false,
                "id": 101,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_sex",
                "is_dimension": true,
                "has_dict": false,
                "cname": "性别",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "xing bie",
                "name": "sex",
                "data_type": "string",
                "is_segmenter": false,
                "id": 71,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_recentrechargeday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "最近充值时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zui jin chong zhi shi jian",
                "name": "recentRechargeDay",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 69,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_recentcostday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "最近花费时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zui jin hua fei shi jian",
                "name": "recentCostDay",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 68,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_recentvisitday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "最近访问时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zui jin fang wen shi jian",
                "name": "recentVisitDay",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 70,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__signup_time",
                "is_dimension": true,
                "has_dict": false,
                "cname": "注册时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu ce shi jian",
                "name": "$signup_time",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 98,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__first_browser_language",
                "is_dimension": true,
                "has_dict": false,
                "cname": "浏览器语言",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu lan qi yu yan",
                "name": "$first_browser_language",
                "data_type": "string",
                "is_segmenter": false,
                "id": 104,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__utm_matching_type",
                "is_dimension": true,
                "has_dict": false,
                "cname": "渠道追踪匹配模式",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "qu dao zhui zong pi pei mo shi",
                "name": "$utm_matching_type",
                "data_type": "string",
                "is_segmenter": false,
                "id": 99,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_birthday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "生日",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng ri",
                "name": "birthday",
                "data_type": "string",
                "is_segmenter": false,
                "id": 57,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "id",
                "is_dimension": false,
                "has_dict": true,
                "cname": "用户 ID",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu  ID",
                "name": "$id",
                "data_type": "string",
                "is_segmenter": false,
                "id": 1000002,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_nickname",
                "is_dimension": true,
                "has_dict": false,
                "cname": "用户昵称",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu ni cheng",
                "name": "nickname",
                "data_type": "string",
                "is_segmenter": false,
                "id": 66,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_level",
                "is_dimension": true,
                "has_dict": false,
                "cname": "用户等级",
                "cardinality": 0,
                "is_measure": true,
                "pinyin": "yong hu deng ji",
                "name": "level",
                "data_type": "number",
                "is_segmenter": false,
                "id": 65,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_usertype",
                "is_dimension": true,
                "has_dict": false,
                "cname": "用户类型",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yong hu lei xing",
                "name": "userType",
                "data_type": "string",
                "is_segmenter": false,
                "id": 72,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__province",
                "is_dimension": true,
                "has_dict": false,
                "cname": "省份",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "sheng fen",
                "name": "$province",
                "data_type": "string",
                "is_segmenter": false,
                "id": 95,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__city",
                "is_dimension": true,
                "has_dict": false,
                "cname": "城市",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "cheng shi",
                "name": "$city",
                "data_type": "string",
                "is_segmenter": false,
                "id": 96,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_email",
                "is_dimension": true,
                "has_dict": false,
                "cname": "邮箱",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "you xiang",
                "name": "email",
                "data_type": "string",
                "is_segmenter": false,
                "id": 58,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_firstrechargeday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次充值时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci chong zhi shi jian",
                "name": "firstRechargeDay",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 61,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__first_referrer",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次前向地址",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci qian xiang di zhi",
                "name": "$first_referrer",
                "data_type": "string",
                "is_segmenter": false,
                "id": 103,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__first_referrer_host",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次前向域名",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci qian xiang yu ming",
                "name": "$first_referrer_host",
                "data_type": "string",
                "is_segmenter": false,
                "id": 105,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_firstchannelsource",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次渠道来源",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci qu dao lai yuan",
                "name": "firstChannelSource",
                "data_type": "string",
                "is_segmenter": false,
                "id": 59,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_firstcostday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次花费时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci hua fei shi jian",
                "name": "firstCostDay",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 60,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_firstvisitday",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次访问时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci fang wen shi jian",
                "name": "firstVisitDay",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 62,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p__first_visit_time",
                "is_dimension": true,
                "has_dict": false,
                "cname": "首次访问时间",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "shou ci fang wen shi jian",
                "name": "$first_visit_time",
                "data_type": "datetime",
                "is_segmenter": false,
                "id": 102,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_Lostfrompaymentprocessinjune",
                "is_dimension": true,
                "has_dict": false,
                "cname": "6月充值流失人群",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "6 yue chong zhi liu shi ren qun",
                "name": "Lostfrompaymentprocessinjune",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000011,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_fenqun",
                "is_dimension": true,
                "has_dict": false,
                "cname": "fenqun",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "fenqun",
                "name": "fenqun",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000006,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_zhubo",
                "is_dimension": true,
                "has_dict": false,
                "cname": "主播",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "zhu bo",
                "name": "zhubo",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000015,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_BestUser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "优质用户",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "you zhi yong hu",
                "name": "BestUser",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000003,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_lostfromlastpay",
                "is_dimension": true,
                "has_dict": false,
                "cname": "充值流失人群",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "chong zhi liu shi ren qun",
                "name": "lostfromlastpay",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000010,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_wei",
                "is_dimension": true,
                "has_dict": false,
                "cname": "微分享",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "wei fen xiang",
                "name": "wei",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000013,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_campus",
                "is_dimension": true,
                "has_dict": false,
                "cname": "校园群体",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "xiao yuan qun ti",
                "name": "campus",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000004,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_liushi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "流失永固",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu shi yong gu",
                "name": "liushi",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000008,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_LOSEUEUE",
                "is_dimension": true,
                "has_dict": false,
                "cname": "流失用户1",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu shi yong hu 1",
                "name": "LOSEUEUE",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000009,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_lostUser",
                "is_dimension": true,
                "has_dict": false,
                "cname": "流失用户分组",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "liu shi yong hu fen zu",
                "name": "lostUser",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000012,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_ceshi",
                "is_dimension": true,
                "has_dict": false,
                "cname": "测试",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "ce shi",
                "name": "ceshi",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000005,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_yuce1",
                "is_dimension": true,
                "has_dict": true,
                "cname": "预测1",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "yu ce 1",
                "name": "yuce1",
                "data_type": "number",
                "is_segmenter": true,
                "id": 1000014,
                "has_db_column_name": true,
                "funnel_step": -1
            }, {
                "db_column_name": "p_seg_gaochongzhipinlvyonghu",
                "is_dimension": true,
                "has_dict": false,
                "cname": "高频花费用户",
                "cardinality": 0,
                "is_measure": false,
                "pinyin": "gao pin hua fei yong hu",
                "name": "gaochongzhipinlvyonghu",
                "data_type": "bool",
                "is_segmenter": true,
                "id": 1000007,
                "has_db_column_name": true,
                "funnel_step": -1
            }]
        },
        //6月支付的漏斗数据
        report:{
            "date_list": ["$ALL", "2017-08-01", "2017-08-02", "2017-08-03", "2017-08-04", "2017-08-05", "2017-08-06", "2017-08-07", "2017-08-08", "2017-08-09", "2017-08-10", "2017-08-11", "2017-08-12", "2017-08-13", "2017-08-14", "2017-08-15", "2017-08-16", "2017-08-17", "2017-08-18", "2017-08-19", "2017-08-20", "2017-08-21", "2017-08-22", "2017-08-23", "2017-08-24", "2017-08-25", "2017-08-26", "2017-08-27", "2017-08-28", "2017-08-29", "2017-08-30", "2017-08-31"],
            "funnel_detail": [
                {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 24967,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 17377,
                            "conversion_rate": 69.6,
                            "wastage_user": 7590,
                            "median_converted_time": 230.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 17377,
                        "conversion_rate": 69.6,
                        "rows": [{
                            "converted_user": 16991,
                            "conversion_rate": 97.78,
                            "wastage_user": 386,
                            "median_converted_time": 284.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 16991,
                        "conversion_rate": 97.78,
                        "rows": [{
                            "converted_user": 10951,
                            "conversion_rate": 64.45,
                            "wastage_user": 6040,
                            "median_converted_time": 555.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 10951,
                        "conversion_rate": 64.45,
                        "rows": [{
                            "converted_user": 3214,
                            "conversion_rate": 29.35,
                            "wastage_user": 7737,
                            "median_converted_time": 2716.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 3214, "conversion_rate": 29.35, "rows": []}],
                    "completion_rate": 12.87,
                    "overview": [[{
                        "converted_user": 24967,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 17377,
                        "conversion_rate": 69.6,
                        "completion_rate": 69.6
                    }, {
                        "converted_user": 16991,
                        "conversion_rate": 97.78,
                        "completion_rate": 68.05
                    }, {
                        "converted_user": 10951,
                        "conversion_rate": 64.45,
                        "completion_rate": 43.86
                    }, {"converted_user": 3214, "conversion_rate": 29.35, "completion_rate": 12.87}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 2239,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 909,
                            "conversion_rate": 40.6,
                            "wastage_user": 1330,
                            "median_converted_time": 169.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 909,
                        "conversion_rate": 40.6,
                        "rows": [{
                            "converted_user": 863,
                            "conversion_rate": 94.94,
                            "wastage_user": 46,
                            "median_converted_time": 164.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 863,
                        "conversion_rate": 94.94,
                        "rows": [{
                            "converted_user": 141,
                            "conversion_rate": 16.34,
                            "wastage_user": 722,
                            "median_converted_time": 506.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 141,
                        "conversion_rate": 16.34,
                        "rows": [{
                            "converted_user": 24,
                            "conversion_rate": 17.02,
                            "wastage_user": 117,
                            "median_converted_time": 2476.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 24, "conversion_rate": 17.02, "rows": []}],
                    "completion_rate": 1.07,
                    "overview": [[{
                        "converted_user": 2239,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 909, "conversion_rate": 40.6, "completion_rate": 40.6}, {
                        "converted_user": 863,
                        "conversion_rate": 94.94,
                        "completion_rate": 38.54
                    }, {"converted_user": 141, "conversion_rate": 16.34, "completion_rate": 6.3}, {
                        "converted_user": 24,
                        "conversion_rate": 17.02,
                        "completion_rate": 1.07
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1392,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 491,
                            "conversion_rate": 35.27,
                            "wastage_user": 901,
                            "median_converted_time": 152.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 491,
                        "conversion_rate": 35.27,
                        "rows": [{
                            "converted_user": 468,
                            "conversion_rate": 95.32,
                            "wastage_user": 23,
                            "median_converted_time": 155.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 468,
                        "conversion_rate": 95.32,
                        "rows": [{
                            "converted_user": 17,
                            "conversion_rate": 3.63,
                            "wastage_user": 451,
                            "median_converted_time": 581.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 17,
                        "conversion_rate": 3.63,
                        "rows": [{
                            "converted_user": 1,
                            "conversion_rate": 5.88,
                            "wastage_user": 16,
                            "median_converted_time": 2828.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 1, "conversion_rate": 5.88, "rows": []}],
                    "completion_rate": 0.07,
                    "overview": [[{
                        "converted_user": 1392,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 491, "conversion_rate": 35.27, "completion_rate": 35.27}, {
                        "converted_user": 468,
                        "conversion_rate": 95.32,
                        "completion_rate": 33.62
                    }, {"converted_user": 17, "conversion_rate": 3.63, "completion_rate": 1.22}, {
                        "converted_user": 1,
                        "conversion_rate": 5.88,
                        "completion_rate": 0.07
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1491,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 896,
                            "conversion_rate": 60.09,
                            "wastage_user": 595,
                            "median_converted_time": 296.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 896,
                        "conversion_rate": 60.09,
                        "rows": [{
                            "converted_user": 884,
                            "conversion_rate": 98.66,
                            "wastage_user": 12,
                            "median_converted_time": 269.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 884,
                        "conversion_rate": 98.66,
                        "rows": [{
                            "converted_user": 544,
                            "conversion_rate": 61.54,
                            "wastage_user": 340,
                            "median_converted_time": 550.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 544,
                        "conversion_rate": 61.54,
                        "rows": [{
                            "converted_user": 120,
                            "conversion_rate": 22.06,
                            "wastage_user": 424,
                            "median_converted_time": 3031.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 120, "conversion_rate": 22.06, "rows": []}],
                    "completion_rate": 8.05,
                    "overview": [[{
                        "converted_user": 1491,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 896, "conversion_rate": 60.09, "completion_rate": 60.09}, {
                        "converted_user": 884,
                        "conversion_rate": 98.66,
                        "completion_rate": 59.29
                    }, {"converted_user": 544, "conversion_rate": 61.54, "completion_rate": 36.49}, {
                        "converted_user": 120,
                        "conversion_rate": 22.06,
                        "completion_rate": 8.05
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1518,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 920,
                            "conversion_rate": 60.61,
                            "wastage_user": 598,
                            "median_converted_time": 290.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 920,
                        "conversion_rate": 60.61,
                        "rows": [{
                            "converted_user": 908,
                            "conversion_rate": 98.7,
                            "wastage_user": 12,
                            "median_converted_time": 286.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 908,
                        "conversion_rate": 98.7,
                        "rows": [{
                            "converted_user": 573,
                            "conversion_rate": 63.11,
                            "wastage_user": 335,
                            "median_converted_time": 528.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 573,
                        "conversion_rate": 63.11,
                        "rows": [{
                            "converted_user": 93,
                            "conversion_rate": 16.23,
                            "wastage_user": 480,
                            "median_converted_time": 2809.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 93, "conversion_rate": 16.23, "rows": []}],
                    "completion_rate": 6.13,
                    "overview": [[{
                        "converted_user": 1518,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 920, "conversion_rate": 60.61, "completion_rate": 60.61}, {
                        "converted_user": 908,
                        "conversion_rate": 98.7,
                        "completion_rate": 59.82
                    }, {"converted_user": 573, "conversion_rate": 63.11, "completion_rate": 37.75}, {
                        "converted_user": 93,
                        "conversion_rate": 16.23,
                        "completion_rate": 6.13
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1534,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 945,
                            "conversion_rate": 61.6,
                            "wastage_user": 589,
                            "median_converted_time": 286.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 945,
                        "conversion_rate": 61.6,
                        "rows": [{
                            "converted_user": 933,
                            "conversion_rate": 98.73,
                            "wastage_user": 12,
                            "median_converted_time": 285.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 933,
                        "conversion_rate": 98.73,
                        "rows": [{
                            "converted_user": 576,
                            "conversion_rate": 61.74,
                            "wastage_user": 357,
                            "median_converted_time": 548.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 576,
                        "conversion_rate": 61.74,
                        "rows": [{
                            "converted_user": 116,
                            "conversion_rate": 20.14,
                            "wastage_user": 460,
                            "median_converted_time": 2931.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 116, "conversion_rate": 20.14, "rows": []}],
                    "completion_rate": 7.56,
                    "overview": [[{
                        "converted_user": 1534,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 945, "conversion_rate": 61.6, "completion_rate": 61.6}, {
                        "converted_user": 933,
                        "conversion_rate": 98.73,
                        "completion_rate": 60.82
                    }, {"converted_user": 576, "conversion_rate": 61.74, "completion_rate": 37.55}, {
                        "converted_user": 116,
                        "conversion_rate": 20.14,
                        "completion_rate": 7.56
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1630,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1009,
                            "conversion_rate": 61.9,
                            "wastage_user": 621,
                            "median_converted_time": 277.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1009,
                        "conversion_rate": 61.9,
                        "rows": [{
                            "converted_user": 993,
                            "conversion_rate": 98.41,
                            "wastage_user": 16,
                            "median_converted_time": 288.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 993,
                        "conversion_rate": 98.41,
                        "rows": [{
                            "converted_user": 629,
                            "conversion_rate": 63.34,
                            "wastage_user": 364,
                            "median_converted_time": 563.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 629,
                        "conversion_rate": 63.34,
                        "rows": [{
                            "converted_user": 146,
                            "conversion_rate": 23.21,
                            "wastage_user": 483,
                            "median_converted_time": 2958.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 146, "conversion_rate": 23.21, "rows": []}],
                    "completion_rate": 8.96,
                    "overview": [[{
                        "converted_user": 1630,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 1009, "conversion_rate": 61.9, "completion_rate": 61.9}, {
                        "converted_user": 993,
                        "conversion_rate": 98.41,
                        "completion_rate": 60.92
                    }, {"converted_user": 629, "conversion_rate": 63.34, "completion_rate": 38.59}, {
                        "converted_user": 146,
                        "conversion_rate": 23.21,
                        "completion_rate": 8.96
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1552,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 938,
                            "conversion_rate": 60.44,
                            "wastage_user": 614,
                            "median_converted_time": 271.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 938,
                        "conversion_rate": 60.44,
                        "rows": [{
                            "converted_user": 918,
                            "conversion_rate": 97.87,
                            "wastage_user": 20,
                            "median_converted_time": 267.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 918,
                        "conversion_rate": 97.87,
                        "rows": [{
                            "converted_user": 572,
                            "conversion_rate": 62.31,
                            "wastage_user": 346,
                            "median_converted_time": 611.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 572,
                        "conversion_rate": 62.31,
                        "rows": [{
                            "converted_user": 125,
                            "conversion_rate": 21.85,
                            "wastage_user": 447,
                            "median_converted_time": 2749.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 125, "conversion_rate": 21.85, "rows": []}],
                    "completion_rate": 8.05,
                    "overview": [[{
                        "converted_user": 1552,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 938, "conversion_rate": 60.44, "completion_rate": 60.44}, {
                        "converted_user": 918,
                        "conversion_rate": 97.87,
                        "completion_rate": 59.15
                    }, {"converted_user": 572, "conversion_rate": 62.31, "completion_rate": 36.86}, {
                        "converted_user": 125,
                        "conversion_rate": 21.85,
                        "completion_rate": 8.05
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1666,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 991,
                            "conversion_rate": 59.48,
                            "wastage_user": 675,
                            "median_converted_time": 280.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 991,
                        "conversion_rate": 59.48,
                        "rows": [{
                            "converted_user": 973,
                            "conversion_rate": 98.18,
                            "wastage_user": 18,
                            "median_converted_time": 278.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 973,
                        "conversion_rate": 98.18,
                        "rows": [{
                            "converted_user": 592,
                            "conversion_rate": 60.84,
                            "wastage_user": 381,
                            "median_converted_time": 524.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 592,
                        "conversion_rate": 60.84,
                        "rows": [{
                            "converted_user": 117,
                            "conversion_rate": 19.76,
                            "wastage_user": 475,
                            "median_converted_time": 2738.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 117, "conversion_rate": 19.76, "rows": []}],
                    "completion_rate": 7.02,
                    "overview": [[{
                        "converted_user": 1666,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 991, "conversion_rate": 59.48, "completion_rate": 59.48}, {
                        "converted_user": 973,
                        "conversion_rate": 98.18,
                        "completion_rate": 58.4
                    }, {"converted_user": 592, "conversion_rate": 60.84, "completion_rate": 35.53}, {
                        "converted_user": 117,
                        "conversion_rate": 19.76,
                        "completion_rate": 7.02
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1670,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1050,
                            "conversion_rate": 62.87,
                            "wastage_user": 620,
                            "median_converted_time": 285.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1050,
                        "conversion_rate": 62.87,
                        "rows": [{
                            "converted_user": 1033,
                            "conversion_rate": 98.38,
                            "wastage_user": 17,
                            "median_converted_time": 281.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1033,
                        "conversion_rate": 98.38,
                        "rows": [{
                            "converted_user": 660,
                            "conversion_rate": 63.89,
                            "wastage_user": 373,
                            "median_converted_time": 558.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 660,
                        "conversion_rate": 63.89,
                        "rows": [{
                            "converted_user": 157,
                            "conversion_rate": 23.79,
                            "wastage_user": 503,
                            "median_converted_time": 2773.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 157, "conversion_rate": 23.79, "rows": []}],
                    "completion_rate": 9.4,
                    "overview": [[{
                        "converted_user": 1670,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1050,
                        "conversion_rate": 62.87,
                        "completion_rate": 62.87
                    }, {"converted_user": 1033, "conversion_rate": 98.38, "completion_rate": 61.86}, {
                        "converted_user": 660,
                        "conversion_rate": 63.89,
                        "completion_rate": 39.52
                    }, {"converted_user": 157, "conversion_rate": 23.79, "completion_rate": 9.4}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1739,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1086,
                            "conversion_rate": 62.45,
                            "wastage_user": 653,
                            "median_converted_time": 278.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1086,
                        "conversion_rate": 62.45,
                        "rows": [{
                            "converted_user": 1070,
                            "conversion_rate": 98.53,
                            "wastage_user": 16,
                            "median_converted_time": 277.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1070,
                        "conversion_rate": 98.53,
                        "rows": [{
                            "converted_user": 686,
                            "conversion_rate": 64.11,
                            "wastage_user": 384,
                            "median_converted_time": 565.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 686,
                        "conversion_rate": 64.11,
                        "rows": [{
                            "converted_user": 148,
                            "conversion_rate": 21.57,
                            "wastage_user": 538,
                            "median_converted_time": 2694.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 148, "conversion_rate": 21.57, "rows": []}],
                    "completion_rate": 8.51,
                    "overview": [[{
                        "converted_user": 1739,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1086,
                        "conversion_rate": 62.45,
                        "completion_rate": 62.45
                    }, {"converted_user": 1070, "conversion_rate": 98.53, "completion_rate": 61.53}, {
                        "converted_user": 686,
                        "conversion_rate": 64.11,
                        "completion_rate": 39.45
                    }, {"converted_user": 148, "conversion_rate": 21.57, "completion_rate": 8.51}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1779,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1086,
                            "conversion_rate": 61.05,
                            "wastage_user": 693,
                            "median_converted_time": 3022.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1086,
                        "conversion_rate": 61.05,
                        "rows": [{
                            "converted_user": 1072,
                            "conversion_rate": 98.71,
                            "wastage_user": 14,
                            "median_converted_time": 277.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1072,
                        "conversion_rate": 98.71,
                        "rows": [{
                            "converted_user": 650,
                            "conversion_rate": 60.63,
                            "wastage_user": 422,
                            "median_converted_time": 537.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 650,
                        "conversion_rate": 60.63,
                        "rows": [{
                            "converted_user": 144,
                            "conversion_rate": 22.15,
                            "wastage_user": 506,
                            "median_converted_time": 2804.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 144, "conversion_rate": 22.15, "rows": []}],
                    "completion_rate": 8.09,
                    "overview": [[{
                        "converted_user": 1779,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1086,
                        "conversion_rate": 61.05,
                        "completion_rate": 61.05
                    }, {"converted_user": 1072, "conversion_rate": 98.71, "completion_rate": 60.26}, {
                        "converted_user": 650,
                        "conversion_rate": 60.63,
                        "completion_rate": 36.54
                    }, {"converted_user": 144, "conversion_rate": 22.15, "completion_rate": 8.09}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1738,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1084,
                            "conversion_rate": 62.37,
                            "wastage_user": 654,
                            "median_converted_time": 276.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1084,
                        "conversion_rate": 62.37,
                        "rows": [{
                            "converted_user": 1065,
                            "conversion_rate": 98.25,
                            "wastage_user": 19,
                            "median_converted_time": 286.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1065,
                        "conversion_rate": 98.25,
                        "rows": [{
                            "converted_user": 665,
                            "conversion_rate": 62.44,
                            "wastage_user": 400,
                            "median_converted_time": 558.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 665,
                        "conversion_rate": 62.44,
                        "rows": [{
                            "converted_user": 137,
                            "conversion_rate": 20.6,
                            "wastage_user": 528,
                            "median_converted_time": 2705.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 137, "conversion_rate": 20.6, "rows": []}],
                    "completion_rate": 7.88,
                    "overview": [[{
                        "converted_user": 1738,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1084,
                        "conversion_rate": 62.37,
                        "completion_rate": 62.37
                    }, {"converted_user": 1065, "conversion_rate": 98.25, "completion_rate": 61.28}, {
                        "converted_user": 665,
                        "conversion_rate": 62.44,
                        "completion_rate": 38.26
                    }, {"converted_user": 137, "conversion_rate": 20.6, "completion_rate": 7.88}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1768,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1062,
                            "conversion_rate": 60.07,
                            "wastage_user": 706,
                            "median_converted_time": 271.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1062,
                        "conversion_rate": 60.07,
                        "rows": [{
                            "converted_user": 1042,
                            "conversion_rate": 98.12,
                            "wastage_user": 20,
                            "median_converted_time": 272.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1042,
                        "conversion_rate": 98.12,
                        "rows": [{
                            "converted_user": 631,
                            "conversion_rate": 60.56,
                            "wastage_user": 411,
                            "median_converted_time": 560.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 631,
                        "conversion_rate": 60.56,
                        "rows": [{
                            "converted_user": 138,
                            "conversion_rate": 21.87,
                            "wastage_user": 493,
                            "median_converted_time": 2912.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 138, "conversion_rate": 21.87, "rows": []}],
                    "completion_rate": 7.81,
                    "overview": [[{
                        "converted_user": 1768,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1062,
                        "conversion_rate": 60.07,
                        "completion_rate": 60.07
                    }, {"converted_user": 1042, "conversion_rate": 98.12, "completion_rate": 58.94}, {
                        "converted_user": 631,
                        "conversion_rate": 60.56,
                        "completion_rate": 35.69
                    }, {"converted_user": 138, "conversion_rate": 21.87, "completion_rate": 7.81}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1725,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1050,
                            "conversion_rate": 60.87,
                            "wastage_user": 675,
                            "median_converted_time": 273.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1050,
                        "conversion_rate": 60.87,
                        "rows": [{
                            "converted_user": 1033,
                            "conversion_rate": 98.38,
                            "wastage_user": 17,
                            "median_converted_time": 278.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1033,
                        "conversion_rate": 98.38,
                        "rows": [{
                            "converted_user": 642,
                            "conversion_rate": 62.15,
                            "wastage_user": 391,
                            "median_converted_time": 585.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 642,
                        "conversion_rate": 62.15,
                        "rows": [{
                            "converted_user": 139,
                            "conversion_rate": 21.65,
                            "wastage_user": 503,
                            "median_converted_time": 2685.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 139, "conversion_rate": 21.65, "rows": []}],
                    "completion_rate": 8.06,
                    "overview": [[{
                        "converted_user": 1725,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1050,
                        "conversion_rate": 60.87,
                        "completion_rate": 60.87
                    }, {"converted_user": 1033, "conversion_rate": 98.38, "completion_rate": 59.88}, {
                        "converted_user": 642,
                        "conversion_rate": 62.15,
                        "completion_rate": 37.22
                    }, {"converted_user": 139, "conversion_rate": 21.65, "completion_rate": 8.06}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1696,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1030,
                            "conversion_rate": 60.73,
                            "wastage_user": 666,
                            "median_converted_time": 275.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1030,
                        "conversion_rate": 60.73,
                        "rows": [{
                            "converted_user": 1012,
                            "conversion_rate": 98.25,
                            "wastage_user": 18,
                            "median_converted_time": 278.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1012,
                        "conversion_rate": 98.25,
                        "rows": [{
                            "converted_user": 629,
                            "conversion_rate": 62.15,
                            "wastage_user": 383,
                            "median_converted_time": 555.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 629,
                        "conversion_rate": 62.15,
                        "rows": [{
                            "converted_user": 149,
                            "conversion_rate": 23.69,
                            "wastage_user": 480,
                            "median_converted_time": 6008.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 149, "conversion_rate": 23.69, "rows": []}],
                    "completion_rate": 8.79,
                    "overview": [[{
                        "converted_user": 1696,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1030,
                        "conversion_rate": 60.73,
                        "completion_rate": 60.73
                    }, {"converted_user": 1012, "conversion_rate": 98.25, "completion_rate": 59.67}, {
                        "converted_user": 629,
                        "conversion_rate": 62.15,
                        "completion_rate": 37.09
                    }, {"converted_user": 149, "conversion_rate": 23.69, "completion_rate": 8.79}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1776,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1095,
                            "conversion_rate": 61.66,
                            "wastage_user": 681,
                            "median_converted_time": 283.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1095,
                        "conversion_rate": 61.66,
                        "rows": [{
                            "converted_user": 1073,
                            "conversion_rate": 97.99,
                            "wastage_user": 22,
                            "median_converted_time": 278.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1073,
                        "conversion_rate": 97.99,
                        "rows": [{
                            "converted_user": 646,
                            "conversion_rate": 60.21,
                            "wastage_user": 427,
                            "median_converted_time": 560.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 646,
                        "conversion_rate": 60.21,
                        "rows": [{
                            "converted_user": 153,
                            "conversion_rate": 23.68,
                            "wastage_user": 493,
                            "median_converted_time": 2682.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 153, "conversion_rate": 23.68, "rows": []}],
                    "completion_rate": 8.61,
                    "overview": [[{
                        "converted_user": 1776,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1095,
                        "conversion_rate": 61.66,
                        "completion_rate": 61.66
                    }, {"converted_user": 1073, "conversion_rate": 97.99, "completion_rate": 60.42}, {
                        "converted_user": 646,
                        "conversion_rate": 60.21,
                        "completion_rate": 36.37
                    }, {"converted_user": 153, "conversion_rate": 23.68, "completion_rate": 8.61}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1900,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1148,
                            "conversion_rate": 60.42,
                            "wastage_user": 752,
                            "median_converted_time": 286.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1148,
                        "conversion_rate": 60.42,
                        "rows": [{
                            "converted_user": 1111,
                            "conversion_rate": 96.78,
                            "wastage_user": 37,
                            "median_converted_time": 299.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1111,
                        "conversion_rate": 96.78,
                        "rows": [{
                            "converted_user": 685,
                            "conversion_rate": 61.66,
                            "wastage_user": 426,
                            "median_converted_time": 521.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 685,
                        "conversion_rate": 61.66,
                        "rows": [{
                            "converted_user": 143,
                            "conversion_rate": 20.88,
                            "wastage_user": 542,
                            "median_converted_time": 2689.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 143, "conversion_rate": 20.88, "rows": []}],
                    "completion_rate": 7.53,
                    "overview": [[{
                        "converted_user": 1900,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1148,
                        "conversion_rate": 60.42,
                        "completion_rate": 60.42
                    }, {"converted_user": 1111, "conversion_rate": 96.78, "completion_rate": 58.47}, {
                        "converted_user": 685,
                        "conversion_rate": 61.66,
                        "completion_rate": 36.05
                    }, {"converted_user": 143, "conversion_rate": 20.88, "completion_rate": 7.53}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1868,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1093,
                            "conversion_rate": 58.51,
                            "wastage_user": 775,
                            "median_converted_time": 285.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1093,
                        "conversion_rate": 58.51,
                        "rows": [{
                            "converted_user": 1063,
                            "conversion_rate": 97.26,
                            "wastage_user": 30,
                            "median_converted_time": 281.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1063,
                        "conversion_rate": 97.26,
                        "rows": [{
                            "converted_user": 636,
                            "conversion_rate": 59.83,
                            "wastage_user": 427,
                            "median_converted_time": 562.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 636,
                        "conversion_rate": 59.83,
                        "rows": [{
                            "converted_user": 178,
                            "conversion_rate": 27.99,
                            "wastage_user": 458,
                            "median_converted_time": 2888.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 178, "conversion_rate": 27.99, "rows": []}],
                    "completion_rate": 9.53,
                    "overview": [[{
                        "converted_user": 1868,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1093,
                        "conversion_rate": 58.51,
                        "completion_rate": 58.51
                    }, {"converted_user": 1063, "conversion_rate": 97.26, "completion_rate": 56.91}, {
                        "converted_user": 636,
                        "conversion_rate": 59.83,
                        "completion_rate": 34.05
                    }, {"converted_user": 178, "conversion_rate": 27.99, "completion_rate": 9.53}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1854,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1071,
                            "conversion_rate": 57.77,
                            "wastage_user": 783,
                            "median_converted_time": 277.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1071,
                        "conversion_rate": 57.77,
                        "rows": [{
                            "converted_user": 1035,
                            "conversion_rate": 96.64,
                            "wastage_user": 36,
                            "median_converted_time": 282.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1035,
                        "conversion_rate": 96.64,
                        "rows": [{
                            "converted_user": 581,
                            "conversion_rate": 56.14,
                            "wastage_user": 454,
                            "median_converted_time": 551.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 581,
                        "conversion_rate": 56.14,
                        "rows": [{
                            "converted_user": 148,
                            "conversion_rate": 25.47,
                            "wastage_user": 433,
                            "median_converted_time": 2698.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 148, "conversion_rate": 25.47, "rows": []}],
                    "completion_rate": 7.98,
                    "overview": [[{
                        "converted_user": 1854,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1071,
                        "conversion_rate": 57.77,
                        "completion_rate": 57.77
                    }, {"converted_user": 1035, "conversion_rate": 96.64, "completion_rate": 55.83}, {
                        "converted_user": 581,
                        "conversion_rate": 56.14,
                        "completion_rate": 31.34
                    }, {"converted_user": 148, "conversion_rate": 25.47, "completion_rate": 7.98}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1827,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1112,
                            "conversion_rate": 60.86,
                            "wastage_user": 715,
                            "median_converted_time": 275.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1112,
                        "conversion_rate": 60.86,
                        "rows": [{
                            "converted_user": 1067,
                            "conversion_rate": 95.95,
                            "wastage_user": 45,
                            "median_converted_time": 272.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1067,
                        "conversion_rate": 95.95,
                        "rows": [{
                            "converted_user": 588,
                            "conversion_rate": 55.11,
                            "wastage_user": 479,
                            "median_converted_time": 544.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 588,
                        "conversion_rate": 55.11,
                        "rows": [{
                            "converted_user": 156,
                            "conversion_rate": 26.53,
                            "wastage_user": 432,
                            "median_converted_time": 2592.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 156, "conversion_rate": 26.53, "rows": []}],
                    "completion_rate": 8.54,
                    "overview": [[{
                        "converted_user": 1827,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1112,
                        "conversion_rate": 60.86,
                        "completion_rate": 60.86
                    }, {"converted_user": 1067, "conversion_rate": 95.95, "completion_rate": 58.4}, {
                        "converted_user": 588,
                        "conversion_rate": 55.11,
                        "completion_rate": 32.18
                    }, {"converted_user": 156, "conversion_rate": 26.53, "completion_rate": 8.54}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1778,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1064,
                            "conversion_rate": 59.84,
                            "wastage_user": 714,
                            "median_converted_time": 273.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1064,
                        "conversion_rate": 59.84,
                        "rows": [{
                            "converted_user": 1024,
                            "conversion_rate": 96.24,
                            "wastage_user": 40,
                            "median_converted_time": 265.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1024,
                        "conversion_rate": 96.24,
                        "rows": [{
                            "converted_user": 538,
                            "conversion_rate": 52.54,
                            "wastage_user": 486,
                            "median_converted_time": 579.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 538,
                        "conversion_rate": 52.54,
                        "rows": [{
                            "converted_user": 140,
                            "conversion_rate": 26.02,
                            "wastage_user": 398,
                            "median_converted_time": 2641.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 140, "conversion_rate": 26.02, "rows": []}],
                    "completion_rate": 7.87,
                    "overview": [[{
                        "converted_user": 1778,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1064,
                        "conversion_rate": 59.84,
                        "completion_rate": 59.84
                    }, {"converted_user": 1024, "conversion_rate": 96.24, "completion_rate": 57.59}, {
                        "converted_user": 538,
                        "conversion_rate": 52.54,
                        "completion_rate": 30.26
                    }, {"converted_user": 140, "conversion_rate": 26.02, "completion_rate": 7.87}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1688,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1047,
                            "conversion_rate": 62.03,
                            "wastage_user": 641,
                            "median_converted_time": 267.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1047,
                        "conversion_rate": 62.03,
                        "rows": [{
                            "converted_user": 1006,
                            "conversion_rate": 96.08,
                            "wastage_user": 41,
                            "median_converted_time": 274.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1006,
                        "conversion_rate": 96.08,
                        "rows": [{
                            "converted_user": 555,
                            "conversion_rate": 55.17,
                            "wastage_user": 451,
                            "median_converted_time": 588.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 555,
                        "conversion_rate": 55.17,
                        "rows": [{
                            "converted_user": 136,
                            "conversion_rate": 24.5,
                            "wastage_user": 419,
                            "median_converted_time": 2758.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 136, "conversion_rate": 24.5, "rows": []}],
                    "completion_rate": 8.06,
                    "overview": [[{
                        "converted_user": 1688,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1047,
                        "conversion_rate": 62.03,
                        "completion_rate": 62.03
                    }, {"converted_user": 1006, "conversion_rate": 96.08, "completion_rate": 59.6}, {
                        "converted_user": 555,
                        "conversion_rate": 55.17,
                        "completion_rate": 32.88
                    }, {"converted_user": 136, "conversion_rate": 24.5, "completion_rate": 8.06}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1733,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1040,
                            "conversion_rate": 60.01,
                            "wastage_user": 693,
                            "median_converted_time": 246.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1040,
                        "conversion_rate": 60.01,
                        "rows": [{
                            "converted_user": 998,
                            "conversion_rate": 95.96,
                            "wastage_user": 42,
                            "median_converted_time": 260.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 998,
                        "conversion_rate": 95.96,
                        "rows": [{
                            "converted_user": 523,
                            "conversion_rate": 52.4,
                            "wastage_user": 475,
                            "median_converted_time": 614.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 523,
                        "conversion_rate": 52.4,
                        "rows": [{
                            "converted_user": 146,
                            "conversion_rate": 27.92,
                            "wastage_user": 377,
                            "median_converted_time": 2699.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 146, "conversion_rate": 27.92, "rows": []}],
                    "completion_rate": 8.42,
                    "overview": [[{
                        "converted_user": 1733,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {"converted_user": 1040, "conversion_rate": 60.01, "completion_rate": 60.01}, {
                        "converted_user": 998,
                        "conversion_rate": 95.96,
                        "completion_rate": 57.59
                    }, {"converted_user": 523, "conversion_rate": 52.4, "completion_rate": 30.18}, {
                        "converted_user": 146,
                        "conversion_rate": 27.92,
                        "completion_rate": 8.42
                    }]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1823,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1106,
                            "conversion_rate": 60.67,
                            "wastage_user": 717,
                            "median_converted_time": 270.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1106,
                        "conversion_rate": 60.67,
                        "rows": [{
                            "converted_user": 1062,
                            "conversion_rate": 96.02,
                            "wastage_user": 44,
                            "median_converted_time": 268.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1062,
                        "conversion_rate": 96.02,
                        "rows": [{
                            "converted_user": 571,
                            "conversion_rate": 53.77,
                            "wastage_user": 491,
                            "median_converted_time": 572.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 571,
                        "conversion_rate": 53.77,
                        "rows": [{
                            "converted_user": 143,
                            "conversion_rate": 25.04,
                            "wastage_user": 428,
                            "median_converted_time": 2694.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 143, "conversion_rate": 25.04, "rows": []}],
                    "completion_rate": 7.84,
                    "overview": [[{
                        "converted_user": 1823,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1106,
                        "conversion_rate": 60.67,
                        "completion_rate": 60.67
                    }, {"converted_user": 1062, "conversion_rate": 96.02, "completion_rate": 58.26}, {
                        "converted_user": 571,
                        "conversion_rate": 53.77,
                        "completion_rate": 31.32
                    }, {"converted_user": 143, "conversion_rate": 25.04, "completion_rate": 7.84}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1787,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1107,
                            "conversion_rate": 61.95,
                            "wastage_user": 680,
                            "median_converted_time": 253.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1107,
                        "conversion_rate": 61.95,
                        "rows": [{
                            "converted_user": 1065,
                            "conversion_rate": 96.21,
                            "wastage_user": 42,
                            "median_converted_time": 256.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1065,
                        "conversion_rate": 96.21,
                        "rows": [{
                            "converted_user": 575,
                            "conversion_rate": 53.99,
                            "wastage_user": 490,
                            "median_converted_time": 619.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 575,
                        "conversion_rate": 53.99,
                        "rows": [{
                            "converted_user": 157,
                            "conversion_rate": 27.3,
                            "wastage_user": 418,
                            "median_converted_time": 2659.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 157, "conversion_rate": 27.3, "rows": []}],
                    "completion_rate": 8.79,
                    "overview": [[{
                        "converted_user": 1787,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1107,
                        "conversion_rate": 61.95,
                        "completion_rate": 61.95
                    }, {"converted_user": 1065, "conversion_rate": 96.21, "completion_rate": 59.6}, {
                        "converted_user": 575,
                        "conversion_rate": 53.99,
                        "completion_rate": 32.18
                    }, {"converted_user": 157, "conversion_rate": 27.3, "completion_rate": 8.79}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1747,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1110,
                            "conversion_rate": 63.54,
                            "wastage_user": 637,
                            "median_converted_time": 260.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1110,
                        "conversion_rate": 63.54,
                        "rows": [{
                            "converted_user": 1061,
                            "conversion_rate": 95.59,
                            "wastage_user": 49,
                            "median_converted_time": 263.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1061,
                        "conversion_rate": 95.59,
                        "rows": [{
                            "converted_user": 560,
                            "conversion_rate": 52.78,
                            "wastage_user": 501,
                            "median_converted_time": 603.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 560,
                        "conversion_rate": 52.78,
                        "rows": [{
                            "converted_user": 142,
                            "conversion_rate": 25.36,
                            "wastage_user": 418,
                            "median_converted_time": 2580.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 142, "conversion_rate": 25.36, "rows": []}],
                    "completion_rate": 8.13,
                    "overview": [[{
                        "converted_user": 1747,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1110,
                        "conversion_rate": 63.54,
                        "completion_rate": 63.54
                    }, {"converted_user": 1061, "conversion_rate": 95.59, "completion_rate": 60.73}, {
                        "converted_user": 560,
                        "conversion_rate": 52.78,
                        "completion_rate": 32.05
                    }, {"converted_user": 142, "conversion_rate": 25.36, "completion_rate": 8.13}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1802,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1120,
                            "conversion_rate": 62.15,
                            "wastage_user": 682,
                            "median_converted_time": 246.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1120,
                        "conversion_rate": 62.15,
                        "rows": [{
                            "converted_user": 1077,
                            "conversion_rate": 96.16,
                            "wastage_user": 43,
                            "median_converted_time": 263.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1077,
                        "conversion_rate": 96.16,
                        "rows": [{
                            "converted_user": 593,
                            "conversion_rate": 55.06,
                            "wastage_user": 484,
                            "median_converted_time": 604.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 593,
                        "conversion_rate": 55.06,
                        "rows": [{
                            "converted_user": 162,
                            "conversion_rate": 27.32,
                            "wastage_user": 431,
                            "median_converted_time": 2652.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 162, "conversion_rate": 27.32, "rows": []}],
                    "completion_rate": 8.99,
                    "overview": [[{
                        "converted_user": 1802,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1120,
                        "conversion_rate": 62.15,
                        "completion_rate": 62.15
                    }, {"converted_user": 1077, "conversion_rate": 96.16, "completion_rate": 59.77}, {
                        "converted_user": 593,
                        "conversion_rate": 55.06,
                        "completion_rate": 32.91
                    }, {"converted_user": 162, "conversion_rate": 27.32, "completion_rate": 8.99}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1810,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1105,
                            "conversion_rate": 61.05,
                            "wastage_user": 705,
                            "median_converted_time": 260.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1105,
                        "conversion_rate": 61.05,
                        "rows": [{
                            "converted_user": 1059,
                            "conversion_rate": 95.84,
                            "wastage_user": 46,
                            "median_converted_time": 262.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1059,
                        "conversion_rate": 95.84,
                        "rows": [{
                            "converted_user": 564,
                            "conversion_rate": 53.26,
                            "wastage_user": 495,
                            "median_converted_time": 607.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 564,
                        "conversion_rate": 53.26,
                        "rows": [{
                            "converted_user": 139,
                            "conversion_rate": 24.65,
                            "wastage_user": 425,
                            "median_converted_time": 2667.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 139, "conversion_rate": 24.65, "rows": []}],
                    "completion_rate": 7.68,
                    "overview": [[{
                        "converted_user": 1810,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1105,
                        "conversion_rate": 61.05,
                        "completion_rate": 61.05
                    }, {"converted_user": 1059, "conversion_rate": 95.84, "completion_rate": 58.51}, {
                        "converted_user": 564,
                        "conversion_rate": 53.26,
                        "completion_rate": 31.16
                    }, {"converted_user": 139, "conversion_rate": 24.65, "completion_rate": 7.68}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1803,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1094,
                            "conversion_rate": 60.68,
                            "wastage_user": 709,
                            "median_converted_time": 261.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1094,
                        "conversion_rate": 60.68,
                        "rows": [{
                            "converted_user": 1049,
                            "conversion_rate": 95.89,
                            "wastage_user": 45,
                            "median_converted_time": 270.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1049,
                        "conversion_rate": 95.89,
                        "rows": [{
                            "converted_user": 561,
                            "conversion_rate": 53.48,
                            "wastage_user": 488,
                            "median_converted_time": 584.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 561,
                        "conversion_rate": 53.48,
                        "rows": [{
                            "converted_user": 149,
                            "conversion_rate": 26.56,
                            "wastage_user": 412,
                            "median_converted_time": 2713.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 149, "conversion_rate": 26.56, "rows": []}],
                    "completion_rate": 8.26,
                    "overview": [[{
                        "converted_user": 1803,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1094,
                        "conversion_rate": 60.68,
                        "completion_rate": 60.68
                    }, {"converted_user": 1049, "conversion_rate": 95.89, "completion_rate": 58.18}, {
                        "converted_user": 561,
                        "conversion_rate": 53.48,
                        "completion_rate": 31.11
                    }, {"converted_user": 149, "conversion_rate": 26.56, "completion_rate": 8.26}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1893,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1160,
                            "conversion_rate": 61.28,
                            "wastage_user": 733,
                            "median_converted_time": 253.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1160,
                        "conversion_rate": 61.28,
                        "rows": [{
                            "converted_user": 1121,
                            "conversion_rate": 96.64,
                            "wastage_user": 39,
                            "median_converted_time": 254.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1121,
                        "conversion_rate": 96.64,
                        "rows": [{
                            "converted_user": 608,
                            "conversion_rate": 54.24,
                            "wastage_user": 513,
                            "median_converted_time": 570.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 608,
                        "conversion_rate": 54.24,
                        "rows": [{
                            "converted_user": 151,
                            "conversion_rate": 24.84,
                            "wastage_user": 457,
                            "median_converted_time": 2508.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 151, "conversion_rate": 24.84, "rows": []}],
                    "completion_rate": 7.98,
                    "overview": [[{
                        "converted_user": 1893,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1160,
                        "conversion_rate": 61.28,
                        "completion_rate": 61.28
                    }, {"converted_user": 1121, "conversion_rate": 96.64, "completion_rate": 59.22}, {
                        "converted_user": 608,
                        "conversion_rate": 54.24,
                        "completion_rate": 32.12
                    }, {"converted_user": 151, "conversion_rate": 24.84, "completion_rate": 7.98}]]
                }, {
                    "steps": [{
                        "event_name": "$AppStart",
                        "converted_user": 1896,
                        "conversion_rate": 100.0,
                        "rows": [{
                            "converted_user": 1155,
                            "conversion_rate": 60.92,
                            "wastage_user": 741,
                            "median_converted_time": 260.0
                        }]
                    }, {
                        "event_name": "goToRoom",
                        "converted_user": 1155,
                        "conversion_rate": 60.92,
                        "rows": [{
                            "converted_user": 1116,
                            "conversion_rate": 96.62,
                            "wastage_user": 39,
                            "median_converted_time": 264.0
                        }]
                    }, {
                        "event_name": "interactive",
                        "converted_user": 1116,
                        "conversion_rate": 96.62,
                        "rows": [{
                            "converted_user": 605,
                            "conversion_rate": 54.21,
                            "wastage_user": 511,
                            "median_converted_time": 577.0
                        }]
                    }, {
                        "event_name": "sendGift",
                        "converted_user": 605,
                        "conversion_rate": 54.21,
                        "rows": [{
                            "converted_user": 151,
                            "conversion_rate": 24.96,
                            "wastage_user": 454,
                            "median_converted_time": 2664.0
                        }]
                    }, {"event_name": "recharge", "converted_user": 151, "conversion_rate": 24.96, "rows": []}],
                    "completion_rate": 7.96,
                    "overview": [[{
                        "converted_user": 1896,
                        "conversion_rate": 100.0,
                        "completion_rate": 100.0
                    }, {
                        "converted_user": 1155,
                        "conversion_rate": 60.92,
                        "completion_rate": 60.92
                    }, {"converted_user": 1116, "conversion_rate": 96.62, "completion_rate": 58.86}, {
                        "converted_user": 605,
                        "conversion_rate": 54.21,
                        "completion_rate": 31.91
                    }, {"converted_user": 151, "conversion_rate": 24.96, "completion_rate": 7.96}]]
                }],
            "by_values": ["$ALL"],
            "event_names": ["$AppStart", "goToRoom", "interactive", "sendGift", "recharge"],
            "report_update_time": "2017-09-13 13:48:52.990",
            "data_update_time": "2017-09-07 23:58:50.000",
            "data_sufficient_update_time": "2017-09-07 23:58:50.000",
            "truncated": false
        }
    }
    var g_fun = {
        //初始化日期控件
        init_daterangepicker: function (ele) {
            //定义locale汉化插件
            var locale = {
                "separator": " 至 ",
                "applyLabel": "确定",
                "cancelLabel": "取消",
                "fromLabel": "起始时间",
                "toLabel": "结束时间'",
                'startOfWeek': "monday",
                "customRangeLabel": "自定义",
                "weekLabel": "W",
                "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                "firstDay": 1
            };
            //日期控件初始化
            ele.daterangepicker(
                {
                    'locale': locale,
                    "separator": " 至 ",
                    //汉化按钮部分
                    ranges: {
                        '今日': [moment(), moment()],
                        '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        '本周': [moment().startOf('week').add(1, 'days'), moment()],
                        '上周': [moment().subtract(1, 'week').startOf('week').add(1, 'days'), moment().subtract(1, 'week').endOf('week').add(1, 'days')],
                        '本月': [moment().startOf('month'), moment()],
                        '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                        '本年': [moment().startOf('year'), moment()],
                        '去年': [moment().subtract(1, 'year').startOf('year'), moment().subtract(1, 'year').endOf('year')],
                        '过去7天': [moment().subtract(6, 'days'), moment()],
                        '过去30天': [moment().subtract(29, 'days'), moment()],
                        '上线至今': [moment().subtract(29, 'days'), moment()]
                    },
                    startDate: moment().subtract(1, 'hours'),
                    endDate: moment(),
                    maxDate: moment(),
                    alwaysShowCalendars: true
                },
                function (start, end) {
                    if (!ele.html()) {
                        ele.val(start.format('YYYY-M-DD') + '至' + end.format('YYYY-M-DD'));
                        $('.dashboard-widget-timerange').html(start.format('YYYY-M-DD') + ' 至 ' + end.format('YYYY-M-DD'));
                    } else {
                        ele.html(start.format('YYYY-M-DD') + '至' + end.format('YYYY-M-DD'));
                    }
                },
                ele.val(moment().subtract(2, 'hours').format('YYYY-M-DD') + '至' + moment().format('YYYY-M-DD'))
            ).bind('datepicker-apply', function (event, obj) {
                console.log(obj);
            });
            //初始化显示当前时间
            if (!ele.html()) {
                ele.val(moment().subtract(2, 'hours').format('YYYY-M-DD') + '至' + moment().format('YYYY-M-DD'));
            } else {
                ele.html(moment().subtract(2, 'hours').format('YYYY-M-DD') + '至' + moment().format('YYYY-M-DD'));
            }
            $('.daterangepicker .ranges ul li:nth-child(1)').addClass('active');
        },
        render:function () {
            //渲染页面主要结构
            $(".content-roll").html(template("tpl-funnel-index", {}));
            $(".sa-report .segmentation-chart").show();

            //显示漏斗选择项
            if (g_data.funnel) {
                $(".sa-report .funnel-ops").show();//显示
                $("#select-funnel").html(template("tpl-select-funnel", g_data.funnel));
                $('#select-funnel select').multiselect();
            }

            //显示过滤选择项
            if (g_data.properties) {
                var eventMain = {}
                $(g_data.properties.event).each(function () {
                    if (!(this.event_name in eventMain)) {
                        eventMain[this.event_name] = [];
                    }
                    eventMain[this.event_name].push(this);
                })
                g_data.properties.newEvent = eventMain
                $("#funnel-group").html(template("tpl-filter-by-item", g_data.properties));
                $('#funnel-group select').multiselect({
                    maxHeight: 490
                });
            }
            //显示设置
            $(".btn-toolbar .btn-group .chart-config").html(template("tpl-toolbar-config", {}));

            //渲染漏斗报表结构
            $(".sa-report .report-chart").show();
            g_fun.init_daterangepicker($("#inputDate"));//初始化时间
            $("#funnel_datepick_convert_time_content").html("7天");



            //动态添加标题
            $("#reportName").html("转化漏斗");

            var echartFilterBtnActive=$("#btn-toolbar .btn-group .btn-default.active").attr("data-method");
            g_data.report["set"]={
                echartFilterBtnActive:echartFilterBtnActive
            }
            //渲染趋势图结构
            var reportNew={};
            $.extend(reportNew,g_data.report);
            reportNew.overview="true";
            $("#funnelContainer").html(template("tpl-funnel-arrow", g_data.report));
            $("#singleContainer").html(template("tpl-funnel-arrow", reportNew));
            console.log(g_data.report);
            console.log(reportNew);

            //漏斗箭头点击事件
            var funnelSvgActive=$("#trendFunnels #funnelContainer .funnel-content svg.active").attr("data-step");
            g_data.report["set"]={
                funnelSvgActive:funnelSvgActive
            };

            var reportData = [];
            $(g_data.report.date_list).each(function (index, value) {
                if (index != 0) {
                    reportData.push(value)
                }
            });
            var reportRate = [];
            $(g_data.report.funnel_detail).each(function (index, value) {
                if (index != 0) {
                    reportRate.push(this.completion_rate)
                }
            });

            //指定图标的配置和数据
            var option = {
                color: ["#559FF0", "#AACF44", "#FF9945", "#3AD1C5", "#FACF2A", "#FC6772", "#788CF0", "#2DCA93", "#EF717A", "#98AAD4", "#A5C63A", "#79302A", "#34485E", "#BDC3C7", "#A5C63A", "#84C1E9", "#81E0A9", "#F0B17A", "#AB9EDC", "#F0938A", "#FFE166", "#E5DABF", "#75D6C3", "#F5A9AF", "#C1CCE5", "#C9DC88", "#AE827F", "#85919E", "#D7DBDD", "#C9DC88"],
                tooltip: {
                    trigger: 'axis',
                    backgroundColor: "rgba(255,255,255,0.9)",
                    borderWidth: 1,
                    borderColor: "#3398DB",
                    textStyle: {
                        color: "#2e384a",
                        fontSize: 12,
                        fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                        fontWeight: 300
                    },
                    formatter: function (params) {
                        return params[0].name.slice(5) + '<br/>总体: ' + params[0].value + '%<br/>'
                    },
                    axisPointer: {
                        type: 'line',
                    }
                },
                grid: {show: false, top: 25, right: 4, left: 4, containLabel: true},
                xAxis: {
                    type: "category",
                    splitLine: {show: false},
                    axisLine: {lineStyle: {color: "#ccc"}},
                    axisTick: {show: true, lineStyle: {color: "#ccc"}, alignWithLabel: true},
                    axisLabel: {
                        textStyle: {
                            color: "#7F7F7F",
                            fontSize: 12,
                            fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                            fontWeight: 300
                        },
                        formatter: function (params) {
                            return params.slice(5)
                        }
                    },
                    data: reportData,
                },
                yAxis: {
                    type: "value",
                    splitLine: {show: true, lineStyle: {type: "dot", width: "0.8"}},
                    axisLine: {show: false},
                    axisTick: {show: false},
                    axisLabel: {
                        textStyle: {
                            color: "#7F7F7F",
                            fontSize: 12,
                            fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                            fontWeight: "300"
                        },
                        formatter: '{value} %'
                    }
                },
                legend: {
                    data: ['总体'],
                    top: 'bottom',
                    borderColor: '#85919E',
                    itemWidth: 20,
                    itemHeight: 10,
                    selectedMode: true,
                    textStyle: {
                        color: "black",
                        fontWeight: "bold",
                        fontSize: 12,
                        fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                    },
                },
                series: [{
                    name: '总体',
                    type: 'line',
                    symbol: 'circle',
                    symbolSize: 6,
                    data: reportRate
                }]
            };
            //初始化echarts实例
            var myChart = echarts.init(document.getElementById('chartContainer'));
            //使用制定的配置项和数据显示图表
            myChart.setOption(option);

            //表格
            $("#table-container").html(template("tpl-funnel-table", g_data.report));
        },
        event:function () {
            //漏斗箭头点击事件
            var $funnelSvg=$("#trendFunnels #funnelContainer .funnel-content svg");
            $funnelSvg.click(function () {
                $funnelSvg.removeClass("active");
                $(this).addClass("active")
            });
            //“趋势”“对比”点击事件
            var $echartFilterBtn=$("#btn-toolbar .btn-group .btn-default");
            $echartFilterBtn.click(function () {
                $echartFilterBtn.removeClass("active");
                $(this).addClass("active");
                var echartFilter=$(this).attr("data-method");
                if(echartFilter=="trends"){
                    $("#trendFunnels").show();
                    $("#singleContainer").hide();
                }else if(echartFilter=="overview"){
                    $("#trendFunnels").hide();
                    $("#singleContainer").show();
                }
            })
        },
        init:function () {
            this.render();
            this.event();
        }
    }
    g_fun.init()
    console.log(g_data.all);
    console.log(g_data.funnel);
    console.log(g_data.properties);
    console.log('report:');
    console.log(g_data.report);

})