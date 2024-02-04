package com.yedamFinal.aco.bookmark.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yedamFinal.aco.bookmark.MybookmarkVO;
import com.yedamFinal.aco.bookmark.mapper.BookmarkMapper;
import com.yedamFinal.aco.bookmark.service.BookmarkService;

public class BookmarkServiceImpl implements BookmarkService{
	   @Autowired
       private BookmarkMapper bookmarkMapper;

	@Override
	public void addBookmark(MybookmarkVO mybookVO) {
		MybookmarkVO bookmark = new MybookmarkVO();
			bookmark.setMemberNo(mybookVO.getMemberNo());
			bookmark.setQuestionBoardNo(mybookVO.getQuestionBoardNo());
			bookmark.setRegistDate(mybookVO.getRegistDate());
			bookmark.setTitle(mybookVO.getTitle());
			bookmarkMapper.insertBookmark(bookmark);
	}

	@Override
	public void deleteBookmark(MybookmarkVO mybookVO) {
		MybookmarkVO bookmark = new MybookmarkVO();
         bookmark.setMemberNo(mybookVO.getMemberNo());
         bookmark.setQuestionBoardNo(mybookVO.getQuestionBoardNo());
		 bookmarkMapper.deleteBookmark(bookmark);
	}

	@Override
	public boolean isBookmark(MybookmarkVO mybookVO) {
		 MybookmarkVO bookmark = new MybookmarkVO();
         bookmark.setMemberNo(mybookVO.getMemberNo());
         bookmark.setQuestionBoardNo(mybookVO.getQuestionBoardNo());
		 return bookmarkMapper.doesBookmarkExist(bookmark);
	}

      
}
