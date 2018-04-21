package com.puke.lol.base;

import lombok.Data;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
public class PageQuery {

    private int page = 1;
    private int pageSize = 10;

    public int getStart() {
        checkParameter();
        return (page - 1) * pageSize;
    }

    public int getLimit() {
        checkParameter();
        return pageSize;
    }

    private void checkParameter() {
        page = max(page, 1);
        pageSize = max(pageSize, 1);
    }


}
