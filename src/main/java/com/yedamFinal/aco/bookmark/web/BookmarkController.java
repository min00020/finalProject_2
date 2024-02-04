package com.yedamFinal.aco.bookmark.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.bookmark.serviceImpl.BookmarkServiceImpl;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;

@Controller
@RequestMapping("/myPage2")
public class BookmarkController {

	
	@Autowired 
	private MemberServiceImpl memberService;
	 
	@Autowired
	private BookmarkServiceImpl bookmarkService;

	@GetMapping("myPage2")
	@ResponseBody
	public boolean isBookmark(@RequestParam int memberNo, @RequestParam int questionBoardNo) {
		MybookmarkVO bookmark = new MybookmarkVO();
		bookmark.setMemberNo(memberNo);
		bookmark.setQuestionBoardNo(questionBoardNo);
		return bookmarkService.isBookmark(bookmark);
	}

	@PostMapping("myPage2")
    @ResponseBody
    @Transactional
	
    public String addBookmark(@RequestBody MybookmarkVO mybookVO) {
        bookmarkService.addBookmark(mybookVO);
        
        return "Bookmark added successfully";
    }

	@DeleteMapping("myPage2")
    @ResponseBody
    @Transactional
    public String deleteBookmark(@RequestParam int memberNo, @RequestParam int questionBoardNo) {
        
     
		MybookmarkVO bookmark = new MybookmarkVO();
		bookmark.setMemberNo(memberNo);
		bookmark.setQuestionBoardNo(questionBoardNo);

        bookmarkService.deleteBookmark(bookmark);
        return "Bookmark deleted successfully";
    }
}