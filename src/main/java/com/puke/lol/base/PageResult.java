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
    private List<T> data;
    private int page = 1;
    private int pageSize = 10;
    private long totalSize;
    private boolean isEnd;

    public PageResult() {
    }

    public PageResult(List<T> data, int page, int pageSize, long totalSize) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.success = true;
        this.msg = MSG_SUCCESS;
        this.totalSize = totalSize;
        this.isEnd = (page - 1) * pageSize + data.size() >= totalSize;
    }

    public static <T> PageResult<T> success(PageQuery query, List<T> data, Long totalSize) {
        data = data == null ? Collections.emptyList() : data;
        totalSize = totalSize == null ? data.size() : totalSize;
        return new PageResult<>(data, query.getPage(), query.getPageSize(), totalSize);
    }

    public static <T> PageResult<T> success(List<T> data) {
        data = data == null ? Collections.emptyList() : data;
        return new PageResult<>(data, 1, data.size(), 0L);
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
