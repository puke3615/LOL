package com.puke.lol.controller;

import com.puke.lol.base.Page;
import com.puke.lol.base.Result;
import com.puke.lol.entity.Hero;
import com.puke.lol.query.HeroQuery;
import com.puke.lol.service.HeroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public Result<Page<Hero>> getList(HeroQuery query) {
        return Result.success(heroService.getList(query));
    }

    @RequestMapping("/findAll")
    public Page<Hero> findAll() {
        return Page.create(heroService.findAll());
    }
}
