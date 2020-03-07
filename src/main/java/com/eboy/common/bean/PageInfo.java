package com.eboy.common.bean;

import java.io.Serializable;

/**
 * @ClassName PageInfo
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-03-07 19:06
 * @Version 1.0
 **/
public class PageInfo implements Serializable {

    private Integer page;

    private Integer limit;

    private Long totalCount;

    public PageInfo() {
    }

    public PageInfo(Integer page, Integer limit, Long totalCount) {
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

}
