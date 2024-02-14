package com.yedamFinal.aco.freeboard.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yedamFinal.aco.freeboard.mapper.FreeBoardMapper;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.freeboard.service.serviceImpl.FreeBoardServiceImpl;


@Controller
@PropertySource("classpath:config.properties")
public class FreeBoardController {
	
	@Autowired
	private FreeBoardService freeBoardService;

	
	@GetMapping("/freeBoardList")
	public String getFreeBoardList(Model model) {
		model.addAttribute("getFreeBoardList", freeBoardService.getFreeBoardAll());
		return "freeBoard/freeBoardList";
	}
	
	@GetMapping("/freeBoardInfo/{fbno}")
	public String getFreeBoard(@PathVariable("fbno") int fbno, Model model) {
		
		model.addAttribute("freeBoardInfo",freeBoardService.getFreeBoard(fbno));
		
		return "freeBoard/freeBoardInfo";
		
	}
	
}
