package com.yedamFinal.aco.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;


public interface AdminService {
	public List<AdminMainVO> getAdCntList();
	
	public Map<String,Object> getAdNoticeList(int pageNo);
	public boolean deleteNotice(int noticeBoardNo);
	
	public Map<String,Object> getAdMemberList(int pageNo, String leaveStatus);
	public Map<String,Object> getAdReportList(int pageNo, String reportStatus);
	public Map<String,Object> getAdQnaList(int pageNo, String answerStatus);
	public Map<String,Object> getAdSettleList(int pageNo, String processStatus );	
	public Map<String,Object> getAdEmoList(int pageNo, String emoStatus);
	
	
	//통계조회
	public Map<String, Object> getTagEmoList(String date);
	
	//통계 달력기간 조회
	public Map<String, Object> getTagEmoPeriodList(String sday, String eday);
	
	//이모티콘 상점 메인
	public Map<String, Object> getMainEmoList(int pageNo);
	//이모티콘 등록
	public Map<String, Object> insertEmo(AdminEmoVO adminEmoVO, MultipartFile[] files);
	//public int insertEmo(AdminEmoVO adminEmoVO, MultipartFile[] file);	
	//이모티콘 판매종료
	public boolean updateEmo(int emoNo);
	//이모티콘 판매재개
	public boolean updateEmo2(int emoNo);
	//이모티콘 상점 이모티콘상세
	public AdminEmoVO getEmoDetail(AdminEmoVO adminEmoVO);
	//이모티콘 구매 내역
	public List<AdminEmoVO> getEmoBuyList(int memberNo);
	//이모티콘 구매하기
	public int buyEmo(AdminEmoVO adminEmoVO);
	//이모티콘 구매목록 삭제
	public boolean deleteEmo(AdminEmoVO adminEmoVO);
	//	공지등록
	public int insertNotice(AdminMainVO adminMainVO);

	public List<AdminEmoVO> getMyEmoList(int memberNo);
}
