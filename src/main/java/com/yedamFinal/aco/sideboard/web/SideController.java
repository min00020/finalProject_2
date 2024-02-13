package com.yedamFinal.aco.sideboard.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.UserDetailVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.sideboard.SideVO;
import com.yedamFinal.aco.sideboard.serviceImpl.SideServiceImpl;
@Controller
public class SideController {
	@Autowired
	SideServiceImpl sideService;
	@Autowired
	MemberServiceImpl memberService;
	
	@GetMapping("/sideProjectList/{status}")
	public String getsideProjectForm(@PathVariable("status") String status,@RequestParam("pageNo") int pageNo,  Model model) {
		var ret = sideService.getRecruitingList(pageNo, status);
		model.addAttribute("recList", ret.get("sideList"));
		model.addAttribute("pageDTO", ret.get("pageDTO"));
		return "sideboard/sideProjectList";
	}
	
	@GetMapping("/sideInfo/{bno}")
    public String getSideProjectDetails(@PathVariable("bno") int bno,
                                        Model model) {
		MemberVO memberVO = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			memberVO = userDetails.getMemberVO();
		}
        SideVO vo = sideService.getSideInfo(bno);
        model.addAttribute("sideInfo", vo);
        String isCheckMember = null;
        if(memberVO != null && memberVO.getMemberNo() == vo.getMemberNo()) {
        	model.addAttribute("isCheckMember", "1");
        	}
        return "sideboard/sideInfo";
    }
	
	
	 @PostMapping("/updateStatus")
	 @ResponseBody
	 public Map<String, Object> updateBoardStatus(@RequestParam(value="bno") int bno,@RequestParam String status, SideVO sideVO, Model model) {
		 MemberVO memberVO = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
				UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
				memberVO = userDetails.getMemberVO();
			}
	        SideVO vo = sideService.getSideInfo(bno);
	        model.addAttribute("sideInfo", vo);
	        String isCheckMember = null;
	        if(memberVO != null && memberVO.getMemberNo() == vo.getMemberNo()) {
	        	model.addAttribute("isCheckMember", "1");
	        	}
	        Map<String, Object> map = sideService.updateBoardStatus(bno, status, vo);
	        
	        SideVO vo2 = sideService.getSideInfo(bno);
	        map.put("sideInfo", vo2);
	        return map;
	 }

	 @GetMapping("/insertSideProject")
	 public String insertProject(Integer bno, Model model) {
		 MemberVO memberVO = null;
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 if (authentication != null && authentication.getPrincipal() instanceof UserDetailVO) {
			 UserDetailVO userDetails = (UserDetailVO) authentication.getPrincipal();
			 memberVO = userDetails.getMemberVO();
		 }
		 
		 MemberVO findVO = memberService.getMemberInfo(memberVO);
		 if(findVO.getGitToken() == null) {
			 return "redirect:/gitLinkPage?id=" + findVO.getId();
		 }
		 
		 //수정폼
		 if(bno != null) {
			 model.addAttribute("bno", bno);
			 SideVO vo = sideService.getSideInfo(bno);
		     model.addAttribute("sideInfo", vo);
		      
			 
		 }
		 var tagList = memberService.getTagList();
		 model.addAttribute("tagList", tagList);
		 return "sideboard/sideInsert";
	 }
	 
	 @PostMapping("/insertAjax")
	 @ResponseBody
	 public Map<String ,Object> insertProject(SideVO sideVO){
	        return sideService.insertProject(sideVO);
	 }
	 
	 @PostMapping("/updateAjax")
	 @ResponseBody
	 public Map<String ,Object> updateProject(@PathVariable(value="bno") int bno, Model model, SideVO vo) {
			return sideService.modifyProject(vo, bno);
		 
	 }
	 
	 @GetMapping("/deleteAjax")
	 public String deleteProject(@RequestParam int bno) {
		 sideService.deleteProject(bno);
		 return "redirect:sideProjectList/Q001/?pageNo=1";
	 }
	 
}
