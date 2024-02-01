package com.yedamFinal.aco.emoticon.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmoticonController {
	
	@GetMapping("/emoMain")
	public String getEmoMain() {
		return "emoticon/emoMain";
	}
	
}
