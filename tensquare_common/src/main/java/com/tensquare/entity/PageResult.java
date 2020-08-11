package com.tensquare.entity;

import java.util.List;

/*
分页结果 实体
 */
public class PageResult<T> {

      private List<T> rows;//每页显示条数
      private Long total;//总记录数

    public PageResult(List<T> rows, Long total) {
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
