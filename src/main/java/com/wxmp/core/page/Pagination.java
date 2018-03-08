package com.wxmp.core.page;


public class Pagination<E> extends AbstractPage<E> {
	
	protected int start;
	protected int totalItemsCount;
	protected int totalPageCount;
	
	public Pagination(){}
	
	public int getTotalItemsCount() {
		return totalItemsCount;
	}

	public void setTotalItemsCount(int totalItemsCount) {
		this.totalItemsCount = totalItemsCount;
		this.totalPageCount = (getTotalItemsCount() - 1) / getPageSize() + getFirstPageNum();
	}
	
	@Override
    public boolean isLastPage() {
        return getLastPageNum() <= getPageNum();
    }

    @Override
    public int getLastPageNum() {
        return this.totalPageCount;
    }

    @Override
    public String toString() {
        return String.format("Page[%d] of [%d] in total [%d] :%s", this.getPageNum(), this.getLastPageNum(), this.getTotalItemsCount(), items.toString());
    }

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public int getStart() {
		this.start = (this.pageNum - 1) * this.pageSize;
		return start;
	}

}


