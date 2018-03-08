package com.wxmp.core.page;

import java.util.*;

public abstract class AbstractPage<E> implements Page<E> {

    public static final int DEFAULT_FIRST_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    protected int pageSize = DEFAULT_PAGE_SIZE;
    protected int pageNum = DEFAULT_FIRST_PAGE_NUM;
    
    protected List<E> items;
    protected boolean lastPage;
    protected boolean firstPage;
    

    @Override
    public int getFirstPageNum() {
        return DEFAULT_FIRST_PAGE_NUM;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if (pageNum < DEFAULT_FIRST_PAGE_NUM) pageNum = DEFAULT_FIRST_PAGE_NUM;
        this.pageNum = pageNum;
    }

    @Override
    public List<E> getItems() {
        return items;
    }

    public void setItems(Collection<E> items) {
        if (items == null) items = Collections.emptyList();
        this.items = new ArrayList<E>(items);
        this.lastPage = this.items.size() < this.pageSize;
        this.firstPage = (getPageNum() <= getFirstPageNum());
    }

    @Override
    public boolean isFirstPage() {
    	firstPage = (getPageNum() <= getFirstPageNum());
    	return firstPage;
    }

    @Override
    public boolean isLastPage() {
        return lastPage;
    }

    public int getPrevPageNum() {
        return isFirstPage() ? getFirstPageNum() : getPageNum() - 1;
    }

    public int getNextPageNum() {
        return isLastPage() ? getPageNum() : getPageNum() + 1;
    }

    public int getPageStartIndex() {
        return (getPageNum() - getFirstPageNum()) * getPageSize();
    }

    public int getPageEndIndex() {
        return getPageStartIndex() + getItems().size();
    }

    public int getPrevPageEndIndex() {
        return isFirstPage() ? 0 : getPageStartIndex() - 1;
    }

    public int getNextPageStartIndex() {
        return isLastPage() ? getPageEndIndex() : getPageEndIndex() + 1;
    }

    @Override
    public Iterator<E> iterator() {
        return this.items.iterator();
    }

    @Override
    public String toString() {
        return "Page[" + this.getPageNum() + "]:" + items.toString();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
