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
	/**
	 * 공지사항 리스트페이지
	 * @param model
	 * @param pageNo
	 * @return
	 */
	@GetMapping("/noticeBoard")
	public String getNoticeBoard(Model model, int pageNo, String search) {
		if(search == null) {
			var ret = noticeBoardService.getAdNoticeList(pageNo);
			model.addAttribute("noticeList",ret.get("noticeList"));
			model.addAttribute("pageDTO",ret.get("pageDTO"));
		}else {
			var ret = noticeBoardService.searchNoticeBoard(pageNo, search);
			model.addAttribute("search", search);
			model.addAttribute("noticeList",ret.get("searchNoticeBoardList"));
			model.addAttribute("pageDTO",ret.get("pageDTO"));
		}
		
		return "notice/noticeList";
	}
	/**
	 * 공지사항 상세페이지
	 * @param noticeBoardVO
	 * @param model
	 * @return
	 */
	@GetMapping("/noticeInfo")
	public String getNoticeInfo(NoticeBoardVO noticeBoardVO, Model model) {
		NoticeBoardVO noticeInfo = noticeBoardService.getNoticeInfo(noticeBoardVO, model);
		model.addAttribute("noticeInfo", noticeInfo);
		return "notice/noticeInfo";
	}
}
