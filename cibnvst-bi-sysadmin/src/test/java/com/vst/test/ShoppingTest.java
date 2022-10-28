package com.vst.test;


import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.common.tools.string.ToolsString;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ShoppingTest {

    @Test
    public void test1() {
        try {
            String JIAYOU_URL = "http://peisong.jiayougo.cn/action/com.harbortek.jiayou.scm.action.SupplierMgmtAction?method=getPartnerPrmList";
            String startDay = "2020-06-21";
            String endDay = "2020-06-21";
            String cookie = "JSESSIONID=D5497E65A53D66ADA058126B6C4CBE57";

            Map<String, String> header = new HashMap<String, String>();
            header.put("Host", "peisong.jiayougo.cn");
            header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
            header.put("Accept", "*/*");
            header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            header.put("Accept-Encoding", "gzip, deflate");
            header.put("X-Requested-With", "XMLHttpRequest");
            header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            header.put("Referer", "http://peisong.jiayougo.cn/lis/partner/cmp_info3.jsp");
            header.put("Content-Length", "159");
            header.put("Cookie", cookie);
            header.put("Connection", "keep-alive");

            String postBody = "startDate={startDay} 00:00:00&endDate={endDay} 23:59:59&start=0&limit=100&ORD_APPR_SURE=S&is_thq=N&time_type=order_date".replace("{startDay}", startDay).replace("{endDay}", endDay);

            String jsonUrl = ToolsHttp.httpPost(JIAYOU_URL, header, postBody);
            if(!ToolsString.isEmpty(jsonUrl)) {
                JSONObject urlJson = JSONObject.parseObject(jsonUrl);
                System.out.println(urlJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
