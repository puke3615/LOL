package com.puke.lol.base;

import lombok.Data;

import java.util.Optional;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
public class Example {

    private int start = 0;
    private int limit = Constant.LIMIT_MAX;

    public Example(PageQuery query) {
        Optional.ofNullable(query)
                .ifPresent(q -> {
                    setLimit(q.getLimit());
                    setStart(q.getStart());
                });
    }

}
