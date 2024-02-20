package com.yedamFinal.aco.sideboard.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedamFinal.aco.common.GitIssueDTO;
import com.yedamFinal.aco.common.service.SessionUtil;
import com.yedamFinal.aco.common.serviceImpl.GitHubServiceImpl;
import com.yedamFinal.aco.member.MemberVO;
import com.yedamFinal.aco.member.serviceImpl.MemberServiceImpl;
import com.yedamFinal.aco.sideboard.SideVO;
import com.yedamFinal.aco.sideboard.serviceImpl.SideServiceImpl;

import lombok.extern.log4j.Log4j2;

/**
 * 사이드프로젝트
 * @author 태경
 * 수정날짜   수정자   수정내용
 * -----------------------
 *          태경     사이드프로젝트
 *
 */
@Controller
public class SideController {
	
	@Autowired
	SideServiceImpl sideService;
	@Autowired
	MemberServiceImpl memberService;
	@Autowired
	GitHubServiceImpl gitService;
	
	/**
	 * 사이드 프로젝트 게시글 리스트
	 * @param status
	 * @param pageNo
	 * @param model
	 * @param memberNo
	 * @return sideboard/sideProjectList
	 */
	@GetMapping("/sideProjectList/{status}")
	public String getsideProjectForm(@PathVariable("status") String status, 
			                         @RequestParam("pageNo") int pageNo,
			                         Model model) {
		var ret = sideService.getRecruitingList(pageNo, status);
		
		model.addAttribute("recList", ret.get("sideList"));
		model.addAttribute("pageDTO", ret.get("pageDTO"));
		
		return "sideboard/sideProjectList";
	}
	
	
	/**
	 * 사이드 프로젝트 단건조회
	 * @param bno
	 * @param model
	 * @return sideboard/sideInfo
	 */
	@GetMapping("/sideInfo/{bno}")
    public String getSideProjectDetails(@PathVariable("bno") int bno,
                                        Model model) {
		MemberVO memberVO = SessionUtil.getSession();
		
		//사이드프로젝트 게시글 조회
        sideService.getSideInfoAndReplyList(bno,model);
        SideVO vo = (SideVO)model.getAttribute("sideInfo");
        
        // 사이드프로젝트 게시글 본인 확인여부
        if(memberVO != null && memberVO.getMemberNo() == vo.getMemberNo()) {
        	model.addAttribute("isCheckMember", "1");
        }
        
        // 사이드프로젝트 게시글 협업중 상태일때 정보(깃허브 레포지토리, 이슈리스트, 커밋리스트)
        if(memberVO != null && vo.getPublishingStatus().equals("Q002")) {
        	Map<String, Object> list = gitService.getGitHubRepositoryInfo(memberVO.getGitToken(),vo.getGitAddress());
        	model.addAttribute("gitrepo",list);
        	model.addAttribute("issueDTO", list.get("issueList"));
        	model.addAttribute("commitDTO", list.get("commitList"));
        }
        
        
        return "sideboard/sideInfo";
    }
	
	/**
	 * 모집중에서 협업중으로 상태 변경
	 * @param sideVO : bno,status
	 * @param model
	 * @return map
	 */
	 @PostMapping("/updateStatus")
	 @ResponseBody
	 public Map<String, Object> updateBoardStatus(SideVO sideVO) { 
		Map<String, Object> map = sideService.updateBoardStatus(sideVO);	
		return map;
	 }
	 
	 /**
	  * 사이드프로젝트 게시글 작성페이지 이동
	  * @param bno
	  * @param model
	  * @return sideboard/sideInsert
	  */
	 @GetMapping("/insertSideProject")
	 public String insertProject(Integer bno, Model model) {
		 
		 MemberVO memberVO = SessionUtil.getSession();
		 MemberVO findVO = memberService.getMemberInfo(memberVO);
		 
		 //깃 토큰 없는 경우 깃 연동페이지로 이동
		 if(findVO.getGitToken() == null) {
			 return "redirect:/gitLinkPage?id=" + findVO.getId();
		 }
		 
		 //수정폼
		 if(bno != null) {
			 model.addAttribute("bno", bno);
			 SideVO vo = sideService.getSideInfo(bno);
		     model.addAttribute("sideInfo", vo);	 
		 }
		 
		 //사용자가 입력한 태그리스트
		 var tagList = memberService.getTagList();
		 model.addAttribute("tagList", tagList);
		 
		 return "sideboard/sideInsert";
	 }
	 
	 /**
	  * 글 등록 처리
	  * @param sideVO
	  * @return map : result, vo
	  */
	 @PostMapping("/insertAjax")
	 @ResponseBody
	 public Map<String ,Object> insertProject(SideVO sideVO){
	        return sideService.insertProject(sideVO);
	 }
	 
	 /**
	  * 글 수정 처리
	  * @param bno
	  * @param model
	  * @param vo
	  * @return 
	  */
	 @PostMapping("/updateAjax/{bno}")
	 @ResponseBody
	 public Map<String ,Object> updateProject(@PathVariable(value="bno") int bno, Model model, SideVO vo) {
			return sideService.modifyProject(vo, bno);
		 
	 }
	 /**
	  * 글 삭제
	  * @param bno
	  * @return redirect:sideProjectList/Q001/?pageNo=1
	  */
	 @GetMapping("/deleteAjax")
	 public String deleteProject(@RequestParam int bno) {
		 sideService.deleteProject(bno);
		 return "redirect:sideProjectList/Q001/?pageNo=1";
	 }
	 /**
	  * 깃 이슈 작성
	  * @param title
	  * @param body
	  * @param bno
	  * @return success : 작성된 상태
	  */
	 @PostMapping("/insertGitIssueAjax")
	 @ResponseBody
	 public Map<String, Object> insertGitIssue(@RequestParam String title,@RequestParam String body,@RequestParam int bno){
		 MemberVO vo = SessionUtil.getSession();
		 
		 SideVO sideVO = sideService.getSideInfo(bno);
		 
		 Map<String, Object> success = gitService.insertGitIssue(vo.getGitToken(), sideVO.getGitAddress(),title, body);
		 return success;
	 }
	
		
}
