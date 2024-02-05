package com.yedamFinal.aco.admin.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;
import com.yedamFinal.aco.admin.mapper.AdminMapper;
import com.yedamFinal.aco.admin.service.AdminService;
import com.yedamFinal.aco.common.PaginationDTO;


@Service
public class AdminServiceImpl implements AdminService {

	private Map<String,String> dropdownMember = new HashMap<String,String>();
	
	public AdminServiceImpl() {
		// 정렬기준
		dropdownMember.put("0", "notLeave");
		dropdownMember.put("1", "Leave");
	}
	
	
	@Autowired
	AdminMapper adminMapper;
	
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
	public boolean deleteNotice(int noticeBoardNo) {
		int result = adminMapper.deleteNotice(noticeBoardNo);
		return result == 1? true : false;
	}
	@Override
	public Map<String,Object> getAdMemberList(int pageNo,String leaveStatus){
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
	public List<AdminReportVO> getAdReportList() {
		return adminMapper.getAdReportList();
	}

	@Override
	public List<AdminQnaVO> getAdQnaList() {
		return adminMapper.getAdQnaList();
	}

	@Override
	public List<AdminSettleVO> getAdSettleList() {
		return adminMapper.getAdSettleList();
	}

	@Override
	public List<AdminEmoVO> getAdEmoList() {
		return adminMapper.getAdEmoList();
	}
	@Override
	public List<AdminEmoVO> getMainEmoList() {
		return adminMapper.getMainEmoList();
	}
	//이모티콘 등록
	@Override
	public int insertEmo(AdminEmoVO adminEmoVO) {
		int result = adminMapper.insertEmo(adminEmoVO);
		if(result == 1) {
			return adminEmoVO.getEmoNo();
		}else {
			return -1;
		}
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
	// 이모티콘 드롭박스
	@Override
	public List<AdminEmoVO> getSaleAdEmoList(String emoState) {
		return adminMapper.getSaleAdEmoList(emoState);
	}
}
