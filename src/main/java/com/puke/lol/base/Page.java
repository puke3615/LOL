package com.puke.lol.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
@AllArgsConstructor
public class Page<T> {

    private List<T> list;
    private int page = 1;
    private int pageSize = 10;

    public static <T> Page<T> create(PageQuery query, List<T> list) {
        list = list == null ? Collections.emptyList() : list;
        return new Page<>(list, query.getPage(), query.getPageSize());
    }

    public static <T> Page<T> create(List<T> list) {
        list = list == null ? Collections.emptyList() : list;
        return new Page<>(list, 1, list.size());
    }

}
