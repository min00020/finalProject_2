package com.yedamFinal.aco.admin.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.admin.AdminEmoUseImgVO;
import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.mapper.AdminMapper;
import com.yedamFinal.aco.admin.service.AdminService;
import com.yedamFinal.aco.common.PaginationDTO;
import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;


@Service
public class AdminServiceImpl implements AdminService {

	private Map<String,String> dropdownMember = new HashMap<String,String>();
	private Map<String,String> dropdownReport = new HashMap<String,String>();
	private Map<String,String> dropdownQna = new HashMap<String,String>();
	private Map<String,String> dropdownSettle = new HashMap<String,String>();
	private Map<String,String> dropdownEmo = new HashMap<String,String>();
	
	private Map<String,String> selectDate = new HashMap<String,String>();
	
	public AdminServiceImpl() {
		// 정렬기준
		dropdownMember.put("0", "notLeave");
		dropdownMember.put("1", "Leave");
		dropdownReport.put("0", "wait");
		dropdownReport.put("1", "impose");
		dropdownReport.put("2", "back");
		dropdownQna.put("0", "wait");
		dropdownQna.put("1", "answer");
		dropdownQna.put("2", "resolve");
		dropdownSettle.put("0", "wait");
		dropdownSettle.put("1", "complete");
		dropdownEmo.put("0", "sale");
		dropdownEmo.put("1", "end");
		
		selectDate.put("0", "today");
		selectDate.put("1", "7day");
		selectDate.put("2", "1month");
		selectDate.put("3", "3month");
		selectDate.put("4", "6month");
		
	}
	
	
	@Autowired
	AdminMapper adminMapper;
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Override
	public  Map<String,Object> getAdNoticeList(int pageNo) {
		Map<String,Object> mp = new HashMap<String, Object>();
		var AdNoticeList = adminMapper.getAdNoticeList(pageNo);
		PaginationDTO dto = null;
		if(AdNoticeList.size() > 0) {
			dto = new PaginationDTO(adminMapper.selectAdNoticeCount(), pageNo , 5);
		}
		
		mp.put("noticeList", AdNoticeList);
		mp.put("pageDTO", dto);
		return mp;
	}
	@Override
	public Map<String, Object> getMainEmoList(@RequestParam int pageNo) {
		Map<String, Object> mp = new HashMap<String, Object>();
		var MainEmoList = adminMapper.getMainEmoList(pageNo);
		PaginationDTO dto = null;
		if(MainEmoList.size() > 0) {
			dto = new PaginationDTO(adminMapper.MainEmoListCount(), pageNo, 9);
		}
		mp.put("mainEmoList", MainEmoList);
		mp.put("pageDTO", dto);
		return mp;
	}
	@Override
	public boolean deleteNotice(int noticeBoardNo) {
		int result = adminMapper.deleteNotice(noticeBoardNo);
		return result == 1? true : false;
	}
	@Override
	public Map<String,Object> getAdMemberList(int pageNo, String leaveStatus){
		String data = dropdownMember.get(leaveStatus);
		
		Map<String,Object> mp = new HashMap<String, Object>();
		if(data == null) {
			var AdMemberList = adminMapper.getAdMemberList(pageNo);
			PaginationDTO dto = null;
			if(AdMemberList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdMemberCount(), pageNo , 10);
			}
			
			mp.put("memberList", AdMemberList);
			mp.put("pageDTO", dto);
		}
		else {
			var AdMemberList = adminMapper.getAdDropMemberList(pageNo, data);
			PaginationDTO dto = null;
			if(AdMemberList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdLeaveMemberCount(data), pageNo , 10);
			}
			
			mp.put("memberList", AdMemberList);
			mp.put("pageDTO", dto);
		}
		
		
		return mp;
	}

	@Override
	public  Map<String,Object> getAdReportList(int pageNo, String reportStatus) {
		String data = dropdownReport.get(reportStatus);
		
		Map<String,Object> mp = new HashMap<String, Object>();
		if(data == null) {
			var AdReportList = adminMapper.getAdReportList(pageNo);
			PaginationDTO dto = null;
			if(AdReportList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdReportCount(), pageNo , 10);
			}
			
			mp.put("reportList", AdReportList);
			mp.put("pageDTO", dto);
		}
		else {
			var AdReportList = adminMapper.getAdDropReportList(pageNo, data);
			PaginationDTO dto = null;
			if(AdReportList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdStateReportCount(data), pageNo , 10);
			}
			
			mp.put("reportList", AdReportList);
			mp.put("pageDTO", dto);
		}
		
		
		return mp;
	}

	@Override
	public Map<String,Object> getAdQnaList(int pageNo, String answerStatus) {
		String data = dropdownQna.get(answerStatus);
		
		Map<String,Object> mp = new HashMap<String, Object>();
		if(data == null) {
			var AdQnaList = adminMapper.getAdQnaList(pageNo);
			PaginationDTO dto = null;
			if(AdQnaList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdQnaCount(), pageNo , 10);
			}
			
			mp.put("qnaList", AdQnaList);
			mp.put("pageDTO", dto);
		}
		else {
			var AdQnaList = adminMapper.getAdDropQnaList(pageNo, data);
			PaginationDTO dto = null;
			if(AdQnaList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdStateQnaCount(data), pageNo , 10);
			}
			
			mp.put("qnaList", AdQnaList);
			mp.put("pageDTO", dto);
		}
		
		
		return mp;
	}

	@Override
	public Map<String,Object> getAdSettleList(int pageNo, String processStatus) {
		String data = dropdownSettle.get(processStatus);
		
		Map<String,Object> mp = new HashMap<String, Object>();
		if(data == null) {
			var AdSettleList = adminMapper.getAdSettleList(pageNo);
			PaginationDTO dto = null;
			if(AdSettleList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdSettleCount(), pageNo , 10);
			}
			
			mp.put("settleList", AdSettleList);
			mp.put("pageDTO", dto);
		}
		else {
			var AdSettleList = adminMapper.getAdDropSettleList(pageNo, data);
			PaginationDTO dto = null;
			if(AdSettleList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdStateSettleCount(data), pageNo , 10);
			}
			
			mp.put("settleList", AdSettleList);
			mp.put("pageDTO", dto);
		}
		
		
		return mp;
	}

	@Override
	public Map<String,Object> getAdEmoList(int pageNo, String emoStatus) {
		String data = dropdownEmo.get(emoStatus);
		
		Map<String,Object> mp = new HashMap<String, Object>();
		if(data == null) {
			var AdEmoList = adminMapper.getAdEmoList(pageNo);
			PaginationDTO dto = null;
			if(AdEmoList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdEmoCount(), pageNo , 10);
			}
			mp.put("emoList", AdEmoList);
			mp.put("pageDTO", dto);
		}
		else {
			var AdEmoList = adminMapper.getAdDropEmoList(pageNo, data);
			PaginationDTO dto = null;
			if(AdEmoList.size() > 0) {
				dto = new PaginationDTO(adminMapper.selectAdStateEmoCount(data), pageNo , 10);
			}
			
			mp.put("emoList", AdEmoList);
			mp.put("pageDTO", dto);
		}
		return mp;
	}
	
	// 통계조회
	@Override
	public Map<String, Object> getTagEmoList(String date) {
		String data = selectDate.get(date);
		if(data == null) {
			date = "null".toString();
		}
		Map<String, Object> mp = new HashMap<String, Object>();		
		mp.put("tagList",adminMapper.getTagListByCount(date));
		mp.put("emoSaleList", adminMapper.getEmoSaleList(date));
		
		return mp;
	}
	//통계 달력기간 조회
	@Override
	public Map<String, Object> getTagEmoPeriodList(String sday, String eday) {
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("tagList", adminMapper.getTagListByCountByPeriod(sday, eday));
		mp.put("emoSaleList", adminMapper.getEmoSaleListByPeriod(sday, eday));
		return mp;
	}
	
	
	
	//이모티콘 등록
	@Override
	public Map<String, Object> insertEmo(AdminEmoVO adminEmoVO, MultipartFile[] files) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", "200");
		
		if(files != null) {
			List<AdminEmoUseImgVO> useImgList = new ArrayList<>();
			fileService.emoticonUpload(files, adminEmoVO, useImgList);
			return ret;
		}
		 
		return ret;
	}
	//이모티콘 판매종료
	@Override
	public boolean updateEmo(int emoNo) {
		int result = adminMapper.updateEmo(emoNo);
		return result == 1? true : false;
	}
	//이모티콘 판매재개
	@Override
	public boolean updateEmo2(int emoNo) {
		int result = adminMapper.updateEmo2(emoNo);
		return result == 1? true : false;
	}
	@Override
	public List<AdminMainVO> getAdCntList() {
		return adminMapper.getAdCntList();
	}

	//공지등록
	@Override
	public int insertNotice(AdminMainVO adminMainVO) {
		return adminMapper.insertNotice(adminMainVO);
	}
	//이모티콘 상점 이모티콘상세
	@Override
	public AdminEmoVO getEmoDetail(AdminEmoVO vo) {
		return adminMapper.getEmoDetail(vo);
	}
	//이모티콘 구매내역
	@Override
	public List<AdminEmoVO> getEmoBuyList(int memberNo) {
		return adminMapper.getEmoBuyList(memberNo);
	}
	@Override
	public int buyEmo(AdminEmoVO adminEmoVO) {
		int result = adminMapper.buyEmo(adminEmoVO);
		if(result == 1) {
			return adminMapper.insertMyemoticon(adminEmoVO);
		}
			return 0;
	}
	@Override
	public List<AdminEmoVO> getMyEmoList(int memberNo) {
		return adminMapper.getMyEmoList(memberNo);
	}
	@Override
	public int deleteEmo(AdminEmoVO adminEmoVO) {
		return  adminMapper.deleteEmo(adminEmoVO);
	}
}
