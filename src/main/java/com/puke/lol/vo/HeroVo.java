package com.puke.lol.vo;

import lombok.Data;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
public class HeroVo {

    /* ID */
    private Long id;

    /* 名称 */
    private String name;

    /* 昵称 */
    private String nickname;

    /* 头像 */
    private String avatar;

    /* 类型 */
    private String type;

    /* 背景图片 */
    private String background;

    /* 背景故事 */
    private String story;

    /* 技能 */
    private String skill;

}
