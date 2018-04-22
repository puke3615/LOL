package com.puke.lol.service;

import com.puke.lol.entity.Hero;
import com.puke.lol.entity.HeroExample;
import com.puke.lol.mapper.HeroMapper;
import com.puke.lol.query.HeroQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author zijiao
 * @version 18/4/21
 */
@Service
public class HeroService {

    @Resource
    private HeroMapper heroMapper;

    public List<Hero> getList(HeroQuery query) {
        HeroExample example = new HeroExample();
        example.setPageQuery(query);
        HeroExample.Criteria criteria = example.createCriteria();
        addCondition(query.getId(), criteria::andIdEqualTo);
        addCondition(query.getName(), criteria::andNameLike);
        addCondition(query.getNickname(), criteria::andNicknameLike);
        addCondition(query.getType(), criteria::andTypeLike);
        return heroMapper.selectByExample(example);
    }

    private static <T> void addCondition(T param, Consumer<T> consumer) {
        Optional.ofNullable(param)
                .filter(p -> !(p instanceof Collection) || !((Collection) p).isEmpty())
                .ifPresent(consumer);
    }

}
