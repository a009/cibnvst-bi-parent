package com.vst.test;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.http.ToolsHttp;
import com.vst.defend.communal.util.JsonUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UmengTest {

    @Test
    public void test1() {
        String url = "https://mobile.umeng.com/ht/api/v3/app/event/analysis/trend?relatedId=53eb1622fd98c52bb000343e&dataSourceId=53eb1622fd98c52bb000343e";
        String cookie = "cna=Mb62Fbs9hw4CAbcOHjBWTe29; UM_distinctid=16c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446; uc_session_id=ffca2f1b-e0d7-4aa0-add7-44f4e7cfb276; isg=BIWFpqAjdkyXa1B3J6mTDfhel8G_QjnUv3aMS4frs7zoHq6QSpE1pUG4KMLoRVGM; Hm_lvt_289016bc8d714b0144dc729f1f2ddc0d=1597305760,1597974316,1598257819,1598579851; dplus_cross_id=1738e0daf41252-054394a0b973de8-4c302372-100200-1738e0daf42228; dplus_finger_print=810732550; umplus_uc_loginid=wenshen.hu%4091vst.com; um_lang=zh; CNZZDATA1259864772=216972944-1596415663-https%253A%252F%252Fworkbench.umeng.com%252F%7C1598579717; Hm_lpvt_289016bc8d714b0144dc729f1f2ddc0d=1598579878; umplus_uc_token=1lEd5wCR-EH-hNb1Hnpdhpg_ce7bdea909144b9894247758252231e9; cn_1258498910_dplus=1%5B%7B%7D%2C0%2C1598579877%2C0%2C1598579877%2C%22%24direct%22%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221563411689%22%2C%22%24direct%22%2C%22%24direct%22%5D; EGG_SESS=_GOCmDZcFN18IbC7Ny32_v8FY9mgHNIH5WPB6gDyynkbMiwIiK5GWXRa2iE8UuXNjYMdKKYVOObLEA4KP7ELT8BGd7jmCc2cVHWIsfb6L5jo1DzLp0JEZt0hRJnfJ-Z6xo_yE3qmvay6LHKhB9Ky0pSY-LjtfTGZh_hqHadeY3T1yx-dsMPH-oKp59xna9Y_TsTJ7s0rXDpVB5WhCBfm-b-vlRbpXETVrVu0my3-xKguHTrts2lkAlj1luDk7cafAQ2bF27RSjBYDv0acXVgmkyfz7mK5Zt_jnqY43OlJxGaJiAgzrSa2Gv1i0PNPEj8poee9SAvZJYBACuC_NY8TwUZm4xDyC1v_F7wjbzvCuYpqDDH3zj9cnjBqh4CoRMQMca-JvYmdv1XRtqUdGtcmxF7ZcQFQr1kQTJuhJL8YWdMBu-14n6-MS7usHNM06ayUYU2mg2p-ZNeQy_JqrmzAlPnN-7alIYxWs5i84u5BMrMeD_9OeGkTvGrXNwabL7NgJh9uLZLwmFsUNEYpRx3p8bHimZ7RIZ0pn9QLdcyvLTkQsGqJJs6AySZV_fmw6z0tR-ozDcZ8kYgUc0Dlr94A-PyvdIdSczYih3Si981S_G9BjXVcrH8p9vdxtEhS6P8StmXkTJz2iJsKqmxPefwk0zfPOBDspQIP5jOvuNS1HPZJIP4pUZjJxHlb9pOgReI9IutNGdSdU071_KWgb00POZrwZxQb7d24jpQeNap4NQ=; cn_1259864772_dplus=1%5B%7B%22UserID%22%3A%22wenshen.hu%4091vst.com%22%2C%22Uapp_appkey%22%3A%2253eb1622fd98c52bb000343e%22%2C%22Uapp_platform%22%3A%22android%22%7D%2C0%2C1598259423%2C0%2C1598259423%2Cnull%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221596415663%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; cn_1273967994_dplus=1%5B%7B%7D%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221596415663%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; XSRF-TOKEN=979d01a0-31e3-42bf-92f4-39fbaad55c9f; XSRF-TOKEN-HAITANG=2683e3b9-cdd9-4772-9e62-40ee6eedf3fc";

        JSONObject body = new JSONObject();
        body.put("relatedId", "53eb1622fd98c52bb000343e");
        body.put("fromDate", "2020-08-27");
        body.put("toDate", "2020-08-27");
        body.put("version", JsonUtils._EMPTY_JSONARRAY);
        body.put("channel", JsonUtils._EMPTY_JSONARRAY);
        body.put("timeUnit", "day");
        body.put("eventName", "live_xiaowei_play_count");
        // body.put("eventName", "live_xiaowei_category_play_count");
        body.put("view", "deviceSView");
        body.put("dataSourceId", "53eb1622fd98c52bb000343e");

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("X-XSRF-TOKEN", "979d01a0-31e3-42bf-92f4-39fbaad55c9f");
        // header.put("x-xsrf-token-haitang", "ee034dcb-728b-4b99-a425-495e583db2b2");
        header.put("Cookie", cookie);

        String str = ToolsHttp.httpPost(url, header, body.toJSONString());
        System.out.println(str);
    }

    @Test
    public void test2() {
        String url = "https://mobile.umeng.com/ht/api/v3/app/event/analysis/property/string/detail?relatedId=53eb1622fd98c52bb000343e&dataSourceId=53eb1622fd98c52bb000343e";
        String cookie = "cna=Mb62Fbs9hw4CAbcOHjBWTe29; UM_distinctid=16c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446; uc_session_id=ffca2f1b-e0d7-4aa0-add7-44f4e7cfb276; isg=BM3Negv3rphZsQivrzFbdYAm3-lHqgF8t170Mw9Q52RwBv241Pn8TO7QcBpg3Rk0; Hm_lvt_289016bc8d714b0144dc729f1f2ddc0d=1596419894,1597026056,1597116786,1597284828; dplus_cross_id=1738e0daf41252-054394a0b973de8-4c302372-100200-1738e0daf42228; dplus_finger_print=810732550; umplus_uc_loginid=wenshen.hu%4091vst.com; um_lang=zh; CNZZDATA1259864772=216972944-1596415663-https%253A%252F%252Fworkbench.umeng.com%252F%7C1597283250; Hm_lpvt_289016bc8d714b0144dc729f1f2ddc0d=1597284864; umplus_uc_token=16QzDtGZDQlwGTH4g3-A9xA_30b86daccbb341a197a2c53d6fe3c2ba; cn_1258498910_dplus=1%5B%7B%7D%2C0%2C1597284864%2C0%2C1597284864%2C%22%24direct%22%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221563411689%22%2C%22%24direct%22%2C%22%24direct%22%5D; EGG_SESS=_GOCmDZcFN18IbC7Ny32_v8FY9mgHNIH5WPB6gDyynkbMiwIiK5GWXRa2iE8UuXNjYMdKKYVOObLEA4KP7ELT8BGd7jmCc2cVHWIsfb6L5jo1DzLp0JEZt0hRJnfJ-Z6xo_yE3qmvay6LHKhB9Ky0pSY-LjtfTGZh_hqHadeY3T1yx-dsMPH-oKp59xna9Y_TsTJ7s0rXDpVB5WhCBfm-b-vlRbpXETVrVu0my3-xKguHTrts2lkAlj1luDk7cafAQ2bF27RSjBYDv0acXVgmkyfz7mK5Zt_jnqY43OlJxGaJiAgzrSa2Gv1i0PNPEj8poee9SAvZJYBACuC_NY8TwUZm4xDyC1v_F7wjbzvCuYpqDDH3zj9cnjBqh4CoRMQMca-JvYmdv1XRtqUdGtcmxF7ZcQFQr1kQTJuhJL8YWdMBu-14n6-MS7usHNM06ayUYU2mg2p-ZNeQy_JqrmzAlPnN-7alIYxWs5i84u5BMrMeD_9OeGkTvGrXNwabL7NgJh9uLZLwmFsUNEYpRx3p8bHimZ7RIZ0pn9QLdcyvLTkQsGqJJs6AySZV_fmw6z0tR-ozDcZ8kYgUc0Dlr94A-PyvdIdSczYih3Si981S_G9BjXVcrH8p9vdxtEhS6P8StmXkTJz2iJsKqmxPefwk0zfPOBDspQIP5jOvuNS1HP0cTMpG2tsBGgvvoaGmCdvMpxUifnqjxHk_URXl-mcFUS0ZMktE4Bh8emA5so-TSc=; cn_1259864772_dplus=1%5B%7B%22UserID%22%3A%22wenshen.hu%4091vst.com%22%2C%22Uapp_appkey%22%3A%2253eb1622fd98c52bb000343e%22%2C%22Uapp_platform%22%3A%22android%22%7D%2C0%2C1597126252%2C0%2C1597126252%2Cnull%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221596415663%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; cn_1273967994_dplus=1%5B%7B%7D%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221596415663%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; XSRF-TOKEN=47049289-f667-46a9-8901-3bb28a947c5a; XSRF-TOKEN-HAITANG=2bf494ea-a953-41be-842f-413c76780901";

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=utf-8");
        header.put("X-XSRF-TOKEN", "47049289-f667-46a9-8901-3bb28a947c5a");
        header.put("Cookie", cookie);

        JSONObject body = new JSONObject();
        body.put("relatedId", "53eb1622fd98c52bb000343e");
        body.put("fromDate", "2020-08-01");
        body.put("toDate", "2020-08-01");
        body.put("version", JsonUtils._EMPTY_JSONARRAY);
        body.put("channel", JsonUtils._EMPTY_JSONARRAY);
        body.put("timeUnit", "day");
//        body.put("eventName", "live_xiaowei_play_count");
//        body.put("propertyName", "live_xiaowei_play_count");
        body.put("eventName", "live_xiaowei_category_play_count");
        body.put("propertyName", "live_xiaowei_category_play_count");
        body.put("view", "propertyStringLaunchView");
        body.put("type", "propertyValue");
        body.put("dataSourceId", "53eb1622fd98c52bb000343e");

        String str = ToolsHttp.httpPost(url, header, body.toJSONString());
        System.out.println(str);
    }

    @Test
    public void test3() {
        String cookie1 = "cna=Mb62Fbs9hw4CAbcOHjBWTe29; UM_distinctid=16c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446; uc_session_id=ffca2f1b-e0d7-4aa0-add7-44f4e7cfb276; isg=BM3Negv3rphZsQivrzFbdYAm3-lHqgF8t170Mw9Q52RwBv241Pn8TO7QcBpg3Rk0; Hm_lvt_289016bc8d714b0144dc729f1f2ddc0d=1596419894,1597026056,1597116786,1597284828; dplus_cross_id=1738e0daf41252-054394a0b973de8-4c302372-100200-1738e0daf42228; dplus_finger_print=810732550; umplus_uc_loginid=wenshen.hu%4091vst.com; um_lang=zh; CNZZDATA1259864772=216972944-1596415663-https%253A%252F%252Fworkbench.umeng.com%252F%7C1597283250; Hm_lpvt_289016bc8d714b0144dc729f1f2ddc0d=1597284864; umplus_uc_token=16QzDtGZDQlwGTH4g3-A9xA_30b86daccbb341a197a2c53d6fe3c2ba; cn_1258498910_dplus=1%5B%7B%7D%2C0%2C1597284864%2C0%2C1597284864%2C%22%24direct%22%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221563411689%22%2C%22%24direct%22%2C%22%24direct%22%5D; EGG_SESS=_GOCmDZcFN18IbC7Ny32_v8FY9mgHNIH5WPB6gDyynkbMiwIiK5GWXRa2iE8UuXNjYMdKKYVOObLEA4KP7ELT8BGd7jmCc2cVHWIsfb6L5jo1DzLp0JEZt0hRJnfJ-Z6xo_yE3qmvay6LHKhB9Ky0pSY-LjtfTGZh_hqHadeY3T1yx-dsMPH-oKp59xna9Y_TsTJ7s0rXDpVB5WhCBfm-b-vlRbpXETVrVu0my3-xKguHTrts2lkAlj1luDk7cafAQ2bF27RSjBYDv0acXVgmkyfz7mK5Zt_jnqY43OlJxGaJiAgzrSa2Gv1i0PNPEj8poee9SAvZJYBACuC_NY8TwUZm4xDyC1v_F7wjbzvCuYpqDDH3zj9cnjBqh4CoRMQMca-JvYmdv1XRtqUdGtcmxF7ZcQFQr1kQTJuhJL8YWdMBu-14n6-MS7usHNM06ayUYU2mg2p-ZNeQy_JqrmzAlPnN-7alIYxWs5i84u5BMrMeD_9OeGkTvGrXNwabL7NgJh9uLZLwmFsUNEYpRx3p8bHimZ7RIZ0pn9QLdcyvLTkQsGqJJs6AySZV_fmw6z0tR-ozDcZ8kYgUc0Dlr94A-PyvdIdSczYih3Si981S_G9BjXVcrH8p9vdxtEhS6P8StmXkTJz2iJsKqmxPefwk0zfPOBDspQIP5jOvuNS1HP0cTMpG2tsBGgvvoaGmCdvMpxUifnqjxHk_URXl-mcFUS0ZMktE4Bh8emA5so-TSc=; cn_1259864772_dplus=1%5B%7B%22UserID%22%3A%22wenshen.hu%4091vst.com%22%2C%22Uapp_appkey%22%3A%2253eb1622fd98c52bb000343e%22%2C%22Uapp_platform%22%3A%22android%22%7D%2C0%2C1597126252%2C0%2C1597126252%2Cnull%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221596415663%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; cn_1273967994_dplus=1%5B%7B%7D%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%2216c02c5476bdd-0347090fa8ba74-116b634a-100200-16c02c5476c446%22%2C%221596415663%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; XSRF-TOKEN=47049289-f667-46a9-8901-3bb28a947c5a; XSRF-TOKEN-HAITANG=2bf494ea-a953-41be-842f-413c76780901";
        String cookie2 = "UM_distinctid=16ef459f79560a-02fdf93e102a36-2393f61-100200-16ef459f796739; cna=/HVuFt9WUBACAaN9Ocdg6u83; dplus_cross_id=173d6904f90d6-0ba7404b2e68bf-3323765-100200-173d6904f9161e; dplus_finger_print=2661843970; uc_session_id=a7d4b5fc-a1a0-4c27-98c3-aeb13b4a690c; umplus_uc_token=1Hvq4bq1NQ5l0yjYWFkO3cA_4e58729d7124443e8bbf635913bfdc24; umplus_uc_loginid=wenshen.hu%4091vst.com; um_lang=zh; EGG_SESS=_GOCmDZcFN18IbC7Ny32_v8FY9mgHNIH5WPB6gDyynkbMiwIiK5GWXRa2iE8UuXNjYMdKKYVOObLEA4KP7ELT8BGd7jmCc2cVHWIsfb6L5jo1DzLp0JEZt0hRJnfJ-Z6xo_yE3qmvay6LHKhB9Ky0pSY-LjtfTGZh_hqHadeY3T1yx-dsMPH-oKp59xna9Y_TsTJ7s0rXDpVB5WhCBfm-b-vlRbpXETVrVu0my3-xKguHTrts2lkAlj1luDk7cafAQ2bF27RSjBYDv0acXVgmkyfz7mK5Zt_jnqY43OlJxGaJiAgzrSa2Gv1i0PNPEj8poee9SAvZJYBACuC_NY8TwUZm4xDyC1v_F7wjbzvCuYpqDDH3zj9cnjBqh4CoRMQMca-JvYmdv1XRtqUdGtcmxF7ZcQFQr1kQTJuhJL8YWdMBu-14n6-MS7usHNM06ayUYU2mg2p-ZNeQy_JqrmzAlPnN-7alIYxWs5i84u5BMrMeD_9OeGkTvGrXNwabL7NgJh9uLZLwmFsUNEYpRx3p8bHimZ7RIZ0pn9QLdcyvLTkQsGqJJs6AySZV_fmw6z0tR-ozDcZ8kYgUc0Dlr94A-PyvdIdSczYih3Si981S_G9BjXVcrH8p9vdxtEhS6P8StmXkTJz2iJsKqmxPefwk0zfPOBDspQIP5jOvuNS1HPjgIWPGkQlToC10osdJ5XvL-BnZg1k3REM914Rb4Gfs4S1dFi1QwMiMjf37Bzyevk=; Hm_lvt_289016bc8d714b0144dc729f1f2ddc0d=1597032649,1597116121; cn_1258498910_dplus=1%5B%7B%7D%2C0%2C1597116126%2C0%2C1597116126%2C%22%24direct%22%2C%2216ef459f79560a-02fdf93e102a36-2393f61-100200-16ef459f796739%22%2C%221597027701%22%2C%22%24direct%22%2C%22%24direct%22%5D; Hm_lpvt_289016bc8d714b0144dc729f1f2ddc0d=1597116127; isg=BJGRwekcqpIRwsaEKx_fDxIPoJ0r_gVwXBpGw3Mmj9h3GrFsu03QQF54uO78Sp2o; ummo_ss=BAh7CEkiGXdhcmRlbi51c2VyLnVzZXIua2V5BjoGRVRbCEkiCVVzZXIGOwBGWwZvOhNCU09OOjpPYmplY3RJZAY6CkBkYXRhWxFpV2kiaQHCaQH%2FaVtpKWkQaQGsaQH0aQBpTmkBpkkiGUQ0aWU1MHkwVWYyaUZKRTZ4elJBBjsAVEkiFHVtcGx1c191Y190b2tlbgY7AEYiPTFIdnE0YnExTlE1bDB5allXRmtPM2NBXzRlNTg3MjlkNzEyNDQ0M2U4YmJmNjM1OTEzYmZkYzI0SSIPc2Vzc2lvbl9pZAY7AFRJIiU5NGJlYWU4ODAzZWQ3OTVlNTJmN2ZhYzAyODBmYmViOQY7AEY%3D--5be3672d5a6582faed483500aee79edf5f94bcee; cn_1259864772_dplus=1%5B%7B%22UserID%22%3A%22wenshen.hu%4091vst.com%22%2C%22Uapp_appkey%22%3A%2253eb1622fd98c52bb000343e%22%2C%22Uapp_platform%22%3A%22android%22%7D%2C0%2C1597049314%2C0%2C1597049314%2Cnull%2C%2216ef459f79560a-02fdf93e102a36-2393f61-100200-16ef459f796739%22%2C%221597032067%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; cn_1273967994_dplus=1%5B%7B%7D%2Cnull%2Cnull%2Cnull%2Cnull%2Cnull%2C%2216ef459f79560a-02fdf93e102a36-2393f61-100200-16ef459f796739%22%2C%221597032067%22%2C%22https%3A%2F%2Fworkbench.umeng.com%2F%22%2C%22workbench.umeng.com%22%5D; CNZZDATA1259864772=1169208154-1597032067-https%253A%252F%252Fworkbench.umeng.com%252F%7C1597115584; XSRF-TOKEN=84f631fb-9d01-4a55-b031-ec0bc18390ad; XSRF-TOKEN-HAITANG=19d4b47d-0ac9-46d2-ade7-0b8f69e530aa";
        String cookie3 = "UM_distinctid=171a5df03a7115-05c98d3c74489b-30637600-1aeaa0-171a5df03a8a32; dplus_cross_id=171a5df03a91c8-0a68866057fb75-30637600-1aeaa0-171a5df03aab2e; dplus_finger_print=1941106172; cna=JRw8FUFBhX0CAaN9Ijz8xxkZ; umplus_uc_loginid=jianfeng520; cn_1276392090_dplus=1%5B%7B%7D%2C0%2C1587712628%2C0%2C1587712628%2Cnull%2C%22171a5df03a7115-05c98d3c74489b-30637600-1aeaa0-171a5df03a8a32%22%2C%221587710773%22%2C%22https%3A%2F%2Fweb.umeng.com%2Fmain.php%3Fc%3Duser%26a%3Dindex%22%2C%22web.umeng.com%22%5D; um_lang=zh; CNZZDATA1259864772=1605190393-1587708690-https%253A%252F%252Fwww.umeng.com%252F%7C1597283250; XSRF-TOKEN=744fb78b-cb29-4787-9c05-51526de84b89; uc_session_id=10f81479-a13f-4daf-83e8-7e0f37a4a575; Hm_lvt_289016bc8d714b0144dc729f1f2ddc0d=1597287806; Hm_lpvt_289016bc8d714b0144dc729f1f2ddc0d=1597287806; umplus_uc_token=1ErIDXc1wCnoaG31VklzxsQ_abb7de4b7f3f47c1b94049dd5c486e3b; cn_1258498910_dplus=1%5B%7B%22userid%22%3A%22jianfeng520%22%7D%2C0%2C1597287810%2C0%2C1597287810%2C%22www.baidu.com%22%2C%22171a5df03a7115-05c98d3c74489b-30637600-1aeaa0-171a5df03a8a32%22%2C%221587625742%22%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DZ0f1amNNXiv-B7UhLwUQZli35dScRTJPFxoBjmnz9QK%26wd%3D%26eqid%3Dad371b440000895b000000065ea13f39%22%2C%22www.baidu.com%22%5D; EGG_SESS=_GOCmDZcFN18IbC7Ny32_jE4YaQ_aYwZxhPACRjThr85DYdG12pZpi1Ppxyo7yj9cwIL35tBWxDs1Of3yp_NiRZwUYBH_GPWynwcaQtjt7FxK0Z-DvG9eoeDtw62xiSvzOVeUhSBxfVrJd2XnZPjAUGacIiErDJtnprofvEi4kfRVfdEfu3D5tIuceBeH2v-nFFLHn_WxcdGXQ-Ew4Qcm8e8q-piGmi2uB5AqRNp-wcNEcPcwtBQ7I-QBUzam73hD5h3j-QM5H3KECa9o1iBsVQ3sPeE-lkUhJQKlUX4eNqm450sktyaQkiFb4pXzMihbIBYR4rbcm1O0MpGTBRQo42fjDRnb94jft36NXXR6c1Q07LGqF7aqytPxA3bX_m6doM5r3qi31VSmi41Fl7BxjbXVnKJ9dOjKtRO_6H2ul6aT3IvBotU0adoiXTZwA2NO3LAyWjS6pKmmiRsHYSOQ1XeM4UR3lh6FS1gI08gk46nGMB_I5A3CLOQX6aPXk5un-OpnRcRc3_h0tMZY9OrehswnjCNlR63Nu01kLpxUXj5EyIHGnSwLl4cauGoOg2M_Qs3RgEaZGgJKCY4500_km-juFS7bhceKlrOWhIxPnaZUb1v3htDK2VHU9V--77kgB6-PLEOHTQWJKPjOaBDxpCNqi4M04QAQQ4GqpJhj6lvO6vdEI7Ao5Eb2Lma-259cdDNeLGwD3bx0VUq21fT3V1Cfk5dMi3fABFSXvJamtIQAD0V2x9WM06pU5UqZkNU; XSRF-TOKEN-HAITANG=a57cdefd-3ea7-4aa9-a386-f221f1638fa7; cn_1259864772_dplus=1%5B%7B%22Uapp_appkey%22%3A%2253eb1622fd98c52bb000343e%22%2C%22Uapp_platform%22%3A%22android%22%2C%22UserID%22%3A%22jianfeng520%22%2C%22%E6%98%AF%E5%90%A6%E7%99%BB%E5%BD%95%22%3Atrue%7D%2C0%2C1597287822%2C0%2C1597287822%2C%22%24direct%22%2C%22171a5df03a7115-05c98d3c74489b-30637600-1aeaa0-171a5df03a8a32%22%2C%221587708690%22%2C%22https%3A%2F%2Fwww.umeng.com%2Fanalytics%22%2C%22www.umeng.com%22%5D; cn_1273967994_dplus=1%5B%7B%7D%2Cnull%2Cnull%2Cnull%2Cnull%2C%22%24direct%22%2C%22171a5df03a7115-05c98d3c74489b-30637600-1aeaa0-171a5df03a8a32%22%2C%221587708690%22%2C%22https%3A%2F%2Fwww.umeng.com%2Fanalytics%22%2C%22www.umeng.com%22%5D; isg=BEpKM78RIbFdv6yIO79zWuxTmzbsO86V2T_hvNSD9h0oh-pBvMsepZDxl_Nbd0Yt";
        // XSRF-TOKEN=84f631fb-9d01-4a55-b031-ec0bc18390ad;
        int start = cookie3.indexOf("XSRF-TOKEN=") + 11;
        int end = cookie3.indexOf(";", start);

        String xsrfToken = cookie3.substring(start, end);
        System.out.println(xsrfToken);
    }
}
