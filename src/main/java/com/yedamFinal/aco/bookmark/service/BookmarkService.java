package com.yedamFinal.aco.bookmark.service;

import com.yedamFinal.aco.bookmark.MybookmarkVO;

public interface BookmarkService {
	public void addBookmark(MybookmarkVO mybookVO);
	public void deleteBookmark(MybookmarkVO mybookVO);
	public boolean isBookmark(MybookmarkVO mybookVO);
}
