package com.yedamFinal.aco.admin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedamFinal.aco.admin.AdminEmoVO;
import com.yedamFinal.aco.admin.AdminMainVO;
import com.yedamFinal.aco.admin.AdminMemberVO;
import com.yedamFinal.aco.admin.AdminQnaVO;
import com.yedamFinal.aco.admin.AdminReportVO;
import com.yedamFinal.aco.admin.AdminSettleVO;
import com.yedamFinal.aco.admin.mapper.AdminMapper;
import com.yedamFinal.aco.admin.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	
	@Override
	public List<AdminMemberVO> getAdMemberList(){
		return adminMapper.getAdMemberList();
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

	@Override
	public List<AdminMainVO> getAdNoticeList() {
		return adminMapper.getAdNoticeList();
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
}
