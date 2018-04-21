package com.puke.lol.base;

import lombok.Data;

import java.util.Collections;
import java.util.List;

import static com.puke.lol.base.Constant.MSG_SUCCESS;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
public class PageResult<T> {

    private boolean success;
    private String msg;
    private List<T> list;
    private int page = 1;
    private int pageSize = 10;

    public PageResult() {
    }

    public PageResult(List<T> list, int page, int pageSize) {
        this.list = list;
        this.page = page;
        this.pageSize = pageSize;
        this.success = true;
        this.msg = MSG_SUCCESS;
    }

    public static <T> PageResult<T> success(PageQuery query, List<T> list) {
        list = list == null ? Collections.emptyList() : list;
        return new PageResult<>(list, query.getPage(), query.getPageSize());
    }

    public static <T> PageResult<T> success(List<T> list) {
        list = list == null ? Collections.emptyList() : list;
        return new PageResult<>(list, 1, list.size());
    }

    public static <T> PageResult<T> error() {
        return error(null);
    }

    public static <T> PageResult<T> error(String msg) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }

}
