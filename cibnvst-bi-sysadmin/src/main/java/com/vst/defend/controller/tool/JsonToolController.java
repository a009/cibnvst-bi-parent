package com.vst.defend.controller.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * JSON工具
 * @author lhp
 * @date 2017-12-12 下午04:24:00
 * @version
 */
@Controller
@RequestMapping("/jsonTool")
public class JsonToolController {

	/**
	 * 跳转到json页面
	 * @return
	 */
	@RequestMapping("/json")
	public String toJson(){
		return "json";
	}
}
