package com.yedamFinal.aco.freeboard.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.freeboard.mapper.FreeBoardMapper;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.freeboard.service.serviceImpl.FreeBoardServiceImpl;


@Controller
@PropertySource("classpath:config.properties")
public class FreeBoardController {
	
	@Autowired
	private FreeBoardService freeBoardService;

	
//	@GetMapping("/freeBoard/freeBoardList")
//	public String getFreeBoardList(Model model) {
//		model.addAttribute("getFreeBoardList", freeBoardService.getFreeBoardAll());
//		return "board/freeBoardList";
//	}
	
}
