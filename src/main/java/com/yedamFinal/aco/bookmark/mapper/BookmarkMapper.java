package com.yedamFinal.aco.bookmark.mapper;

import com.yedamFinal.aco.bookmark.MybookmarkVO;

public interface BookmarkMapper {
	public void insertBookmark(MybookmarkVO mybookVO);
	public void deleteBookmark(MybookmarkVO mybookVO);
	public boolean doesBookmarkExist(MybookmarkVO mybookVO);
}
