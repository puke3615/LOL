package com.puke.lol.vo;

import com.alibaba.fastjson.JSONArray;
import com.puke.lol.entity.Hero;
import com.puke.lol.entity.Skill;
import com.puke.lol.entity.Skin;
import lombok.Data;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Data
public class HeroVo {

    private Long id;

    private String name;

    private String nickname;

    private String englishName;

    private String avatar;

    private String background;

    private String story;

    private Integer attack;

    private Integer magic;

    private Integer defense;

    private Integer difficulty;

    private List<Integer> type;

    private List<Skill> skill;

    private List<Skin> skins;

    private List<String> enemyTips;

    private List<String> allyTips;


    public static HeroVo toVo(Hero hero) {
        if (hero == null) {
            return null;
        }
        HeroVo vo = new HeroVo();
        BeanUtils.copyProperties(hero, vo);
        ofNullable(hero.getType())
                .filter(s -> s.contains(","))
                .map(s -> Arrays.asList(s.split(",")).stream()
                        .map(Integer::valueOf)
                        .collect(Collectors.toList())
                )
                .ifPresent(vo::setType);
        BiConsumer<String, Consumer<List<String>>> setListFunc = (str, consumer) -> Optional.ofNullable(str)
                .filter(Strings::isNotEmpty)
                .map(s -> JSONArray.parseArray(s, String.class))
                .ifPresent(consumer);
        setListFunc.accept(hero.getEnemyTips(), vo::setEnemyTips);
        setListFunc.accept(hero.getAllyTips(), vo::setAllyTips);
        return vo;
    }

}
