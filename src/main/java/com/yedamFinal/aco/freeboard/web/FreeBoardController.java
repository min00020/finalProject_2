package com.yedamFinal.aco.freeboard.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.freeboard.mapper.FreeBoardMapper;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;

/**
* @author 박경석
* @since 2024.02.15
* @version 1.0
* @see
* 
* <pre>
* << 개정이력(Modification Information) >>
*  
*  *   수정일     수정자          수정내용
*  -------    --------    ---------------------------
*  2024.02.15   박경석          최초 생성
*  </pre>
* 
* 
**/
@Controller
@PropertySource("classpath:config.properties")
public class FreeBoardController {
	
	@Autowired
	private FreeBoardService freeBoardService;
	
	@Autowired
	private FreeBoardMapper freeBoardMapper;

	/**
	 * 자유게시판 전체조회
	 * @param model
	 * @return freeBoard/freeBoardList
	 */
	
	@GetMapping("/freeBoardList")
	public String getFreeBoardList(String search, @RequestParam(value = "pg", required = false, defaultValue = "1") int pg, Model model) {
		
		
		List<FreeBoardVO> ret = null;
		 // 검색창 입력의 경우.
        if(search == null)
        	ret = freeBoardService.getFreeBoardAll(model, pg);
        else {
        	ret = freeBoardService.getSearchFreeBoard(model,search, pg);
        	model.addAttribute("search",search); // 해당 search키워드로 페이징해야함.
        }
        
        if(ret == null) {
        	return "redirect:/loginForm";
        }
		
		model.addAttribute("getFreeBoardList", ret);
		return "freeBoard/freeBoardList";
	}
	
	
	/**
	 * 자유게시판 단건조회
	 * @param fboardNo
	 * @param model
	 * @return freeBoard/freeBoardInfo
	 */
	@GetMapping("/freeBoardInfo/{fboardNo}")
	public String getFreeBoard(@PathVariable("fboardNo") int fboardNo, Model model) {
		
		model.addAttribute("freeBoardInfo",freeBoardService.getFreeBoard(fboardNo,model));
		
		return "freeBoard/freeBoardInfo";
		
	}
	
	/**
	 * 자유게시판 작성 폼
	 * @param model
	 * @return freeBoard/freeBoardForm
	 */

	@GetMapping("/freeBoardForm")
	public String getFreeBoardForm(Model model) {
		return "freeBoard/freeBoardForm";
	}
	
	/**
	 * 자유게시판 등록
	 * @param freeBoardVO
	 * @param attachFile
	 * @param req
	 * @return ret
	 */
	
	@PostMapping("/freeBoardForm")
	@ResponseBody
	public Map<String,Object> insertFreeBoard(FreeBoardVO freeBoardVO, MultipartFile[] attachFile, HttpServletRequest req ){
		MemberVO vo = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
        	UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
        	vo = userDetails.getMemberVO();
        	freeBoardVO.setMemberNo(vo.getMemberNo());
        }
        var ret = freeBoardService.insertFreeBoard(freeBoardVO, attachFile);
    
        
		return ret;
	}
	
	//수정 - 별도 페이지
	@GetMapping("/freeBoardUpdate/{fboardNo}")
	public String freeBoardUpdateForm(@PathVariable("fboardNo") int fboardNo, Model model) {
		FreeBoardVO findVO = freeBoardService.getFreeBoard(fboardNo, model);
		model.addAttribute("freeBoardInfo", findVO);
		return "freeBoard/freeBoardUpdateForm";
	}
	
	//수정 - 처리
	@PutMapping("/freeBoardUpdate/{fboardNo}")
	@ResponseBody
	public Map<String,Object> updateFreeBoard( String title,String content,@PathVariable("fboardNo") int fboardNo){
	
		return freeBoardService.modifyFreeBoard(title, content, fboardNo);
		
	}
	
	
	//삭제
	@GetMapping("/deleteFreeBoard")
	public String deleteFreeBoard(@RequestParam int fboardNo) {
		freeBoardService.deleteFreeBoard(fboardNo);
		return "redirect:freeBoardList";
	}
	
//	//검색
//	@GetMapping("/getSearchFreeBoard")
//	public String searchFreeBoard(@RequestParam("search") String search,@RequestParam("pg") int pg, Model model) {
//		model.addAttribute("searchFreeBoard", freeBoardService.getSearchFreeBoard(search, pg));
//		
//		var freeBoardSearchList = freeBoardService.getSearchFreeBoard(search,pg);
//		PaginationDTO dto = null;
//		if(freeBoardSearchList.size() > 0) {
//			dto = new PaginationDTO(freeBoardMapper.searchFreeBoardCnt(), pg, 10);
//		}
//		model.addAttribute("pageDTO", dto);
//		return "freeBoard/freeBoardList";
//		
//	}
	

	
}
