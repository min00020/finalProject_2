package com.yedamFinal.aco.freeboard.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yedamFinal.aco.common.serviceImpl.FileServiceImpl;
import com.yedamFinal.aco.freeboard.FreeBoardVO;
import com.yedamFinal.aco.freeboard.mapper.FreeBoardMapper;
import com.yedamFinal.aco.freeboard.service.FreeBoardService;


@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	@Autowired
	private FileServiceImpl fileService;
	
	
	@Autowired
	private FreeBoardMapper freeBoardMapper;

	@Override
	public List<FreeBoardVO> getFreeBoardAll() {

		return freeBoardMapper.getFreeBoardAll();
	}

	@Override
	public FreeBoardVO getFreeBoard(int fbno) {
	
		return freeBoardMapper.getFreeBoard(fbno);
	}

	@Override
	@Transactional
	public Map<String, Object> insertFreeBoard(FreeBoardVO freeBoardVO, MultipartFile[] files) {
		
		Map<String, Object> ret = new HashMap<String,Object>();
		ret.put("result", "200");
		
		//게시글 등록
		if(freeBoardMapper.insertFreeBoard(freeBoardVO) <= 0) {
			ret.put("result", "500");
			return ret;
		}
		
		// 첨부파일 등록
		if(files != null && files.length > 0) {
			int boardNo = freeBoardVO.getPk();
			int memberNo = freeBoardVO.getMemberNo();
			if(!fileService.uploadAttachFiles(files, memberNo, "N004", boardNo)) {
				ret.put("result", "500");
				return ret;
			}
		}
		
		return ret;
	}



	
}
