package com.puke.lol.service;

import com.puke.lol.entity.Hero;
import com.puke.lol.entity.HeroExample;
import com.puke.lol.entity.SkinExample;
import com.puke.lol.mapper.HeroMapper;
import com.puke.lol.mapper.SkillMapper;
import com.puke.lol.mapper.SkinMapper;
import com.puke.lol.query.HeroQuery;
import com.puke.lol.vo.HeroVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Service
public class HeroService {

    @Resource
    private HeroMapper heroMapper;
    @Resource
    private SkinMapper skinMapper;
    @Resource
    private SkillMapper skillMapper;

    public List<HeroVo> getList(HeroQuery query) {
        HeroExample example = query2Example(query);
        return heroMapper.selectByExample(example).stream()
                .map(this::hero2Vo)
                .collect(Collectors.toList());
    }

    public HeroVo getHeroById(Long heroId) {
        return Optional.ofNullable(heroMapper.selectByPrimaryKey(heroId))
                .map(this::hero2Vo)
                .orElse(null);
    }

    public void addHero(Hero hero) {
        heroMapper.insertSelective(hero);
    }

    public void updateByExampleSelective(Hero hero) {
        HeroExample example = getHeroExampleById(hero.getId());
        heroMapper.updateByExampleSelective(hero, example);
    }

    private static HeroExample getHeroExampleById(Long heroId) {
        HeroExample example = new HeroExample();
        example.createCriteria().andIdEqualTo(heroId);
        return example;
    }

    public void deleteHeroById(Long heroId) {
        heroMapper.deleteByExample(getHeroExampleById(heroId));
    }

    public long countByExample(HeroQuery query) {
        return heroMapper.countByExample(query2Example(query));
    }

    public long countByExample(HeroExample example) {
        return heroMapper.countByExample(example);
    }

    private static HeroExample query2Example(HeroQuery query) {
        HeroExample example = new HeroExample();
        example.setPageQuery(query);
        HeroExample.Criteria criteria = example.createCriteria();
        addCondition(query.getId(), criteria::andIdEqualTo);
        addCondition(query.getName(), criteria::andNameLike);
        addCondition(query.getNickname(), criteria::andNicknameLike);
        addCondition(query.getType(), criteria::andTypeLike);
        return example;
    }

    private static <T> void addCondition(T param, Consumer<T> consumer) {
        Optional.ofNullable(param)
                .filter(p -> !(p instanceof Collection) || !((Collection) p).isEmpty())
                .ifPresent(consumer);
    }

    private HeroVo hero2Vo(Hero hero) {
        if (hero == null) {
            return null;
        }
        HeroVo vo = HeroVo.toVo(hero);
        // Skin
        SkinExample skinExample = new SkinExample();
        skinExample.createCriteria().andHeroIdEqualTo(hero.getId());
        vo.setSkins(skinMapper.selectByExample(skinExample));
        // Skill
        Optional.ofNullable(hero.getSkill())
                .filter(Strings::isNotEmpty)
                .map(s -> Arrays.asList(s.split(",")).stream()
                        .map(Long::valueOf)
                        .map(skillMapper::selectByPrimaryKey)
                        .collect(Collectors.toList())
                )
                .ifPresent(vo::setSkill);
        return vo;
    }

}
