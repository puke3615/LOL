package com.puke.lol.controller;

import com.puke.lol.base.PageResult;
import com.puke.lol.base.Result;
import com.puke.lol.entity.Hero;
import com.puke.lol.entity.HeroExample;
import com.puke.lol.query.HeroQuery;
import com.puke.lol.service.HeroService;
import com.puke.lol.vo.HeroVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author zijiao
 * @version 18/4/21
 */
@RestController
@RequestMapping("/hero")
public class HeroController {

    @Resource
    private HeroService heroService;

    @RequestMapping("/getList")
    public PageResult<HeroVo> getList(HeroQuery query) {
        List<HeroVo> list = heroService.getList(query);
        long totalSize = heroService.countByExample(query);
        return PageResult.success(query, list, totalSize);
    }

    @RequestMapping("/getHeroById")
    public Result<HeroVo> getHeroById(Long heroId) {
        HeroVo heroVo = heroService.getHeroById(heroId);
        return Optional.ofNullable(heroVo)
                .map(Result::success)
                .orElse(Result.error("未查到该条数据~"));
    }

    @RequestMapping("/updateHeroById")
    public Result<Void> updateByExampleSelective(Hero hero) {
        if (hero.getId() == null) {
            return Result.error("未设置数据ID~");
        }
        HeroExample example = new HeroExample();
        example.createCriteria().andIdEqualTo(hero.getId());
        if (heroService.countByExample(example) == 0) {
            return Result.error("未查到该条数据~");
        }
        heroService.updateByExampleSelective(hero);
        return Result.success();
    }

    @RequestMapping("/deleteHeroById")
    public Result<Void> deleteHeroById(Long heroId) {
        if (heroId == null) {
            return Result.error("未设置数据ID~");
        }
        HeroExample example = new HeroExample();
        example.createCriteria().andIdEqualTo(heroId);
        if (heroService.countByExample(example) == 0) {
            return Result.error("未查到该条数据~");
        }
        heroService.deleteHeroById(heroId);
        return Result.success();
    }

}
