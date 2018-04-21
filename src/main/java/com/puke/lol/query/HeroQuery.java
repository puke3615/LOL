package com.puke.lol.query;

import com.puke.lol.base.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeroQuery extends PageQuery {

    /* 英雄ID */
    private Long id;

    /* 英雄名称 */
    private String name;

    /* 英雄昵称 */
    private String nickname;

    /* 英雄类型 */
    private String type;

}
