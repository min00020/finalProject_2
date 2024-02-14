package com.yedamFinal.aco.noticeboard.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yedamFinal.aco.noticeboard.NoticeBoardVO;
import com.yedamFinal.aco.noticeboard.service.NoticeBoardService;

@Controller
public class NoticeBoardController {
	@Autowired
	NoticeBoardService noticeBoardService;
	
	@GetMapping("/noticeBoard")
	public String getNoticeBoard(Model model, int pageNo) {
		var ret = noticeBoardService.getAdNoticeList(pageNo);
		model.addAttribute("noticeList",ret.get("noticeList"));
		model.addAttribute("pageDTO",ret.get("pageDTO"));
		return "notice/noticeList";
	}
	
	@GetMapping("/noticeInfo")
	public String getNoticeInfo(NoticeBoardVO noticeBoardVO, Model model) {
		NoticeBoardVO noticeInfo = noticeBoardService.getNoticeInfo(noticeBoardVO);
		model.addAttribute("noticeInfo", noticeInfo);
		return "notice/noticeInfo";
	}
}
