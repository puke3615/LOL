package com.puke.lol;

import com.puke.lol.mapper.HeroMapper;
import com.puke.lol.mapper.SkillMapper;
import com.puke.lol.query.HeroQuery;
import com.puke.lol.service.HeroService;
import com.puke.lol.vo.HeroVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LolApplicationTests {

    @Resource
    private HeroService heroService;
    @Resource
    private HeroMapper heroMapper;
    @Resource
    private SkillMapper skillMapper;

    @Test
    public void testFindAllHeroes() {
        HeroQuery query = new HeroQuery();
        List<HeroVo> list = null;
        try {
            list = heroService.getList(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list);
    }

}
