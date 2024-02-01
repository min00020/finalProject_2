package com.yedamFinal.aco.common;

import lombok.Data;

@Data
public class PaginationDTO {
	private int curPage;
	private int showContentCnt;
	private int total;
	
	private int lastPage;
	private int endPage;
	private int startPage;
	
	private boolean prev;
	private boolean next;
	
	
    public PaginationDTO(int total, int curPage, int showContentCnt) {
        this.curPage = curPage;
        this.showContentCnt = showContentCnt;
        this.total = total;

        this.lastPage = (int) (Math.ceil((total / showContentCnt)));
        this.endPage = (int) (Math.ceil((curPage / 10.0)) * 10);
        this.startPage = this.endPage - 9;
        this.endPage = this.endPage > this.lastPage ? this.lastPage : this.endPage;
        this.prev = this.curPage > 1;
		this.next = this.curPage < this.lastPage;	
    }
}
