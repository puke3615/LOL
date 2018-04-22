package com.puke.lol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puke.lol.entity.Hero;
import com.puke.lol.entity.Skill;
import com.puke.lol.entity.Skin;
import com.puke.lol.mapper.HeroMapper;
import com.puke.lol.mapper.SkillMapper;
import com.puke.lol.mapper.SkinMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zijiao
 * @version 18/4/22
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataLoader {

    @Resource
    private SkinMapper skinMapper;
    @Resource
    private SkillMapper skillMapper;
    @Resource
    private HeroMapper heroMapper;

    public static void main(String[] args) {
        List<Data> dataList = loadJsonData();
        System.out.println(dataList.get(0).getSkills().size());
        new DataLoader().insertHero(dataList.get(0), Arrays.asList(1L));
    }

    private static List<Data> loadJsonData() {
        try {
            byte[] read = Files.readAllBytes(Paths.get("src/test/resource/detail.json"));
            return JSONArray.parseArray(new String(read), Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Test
    public void insertData() {
        List<Data> datas = loadJsonData();
        datas.forEach(this::insertItem);
        System.out.println("Completed.");
    }

    private void insertItem(Data data) {
        List<SkillItem> skills = data.getSkills();
        // Skill
        List<Long> skillIds = insertSkill(skills);
        // Hero
        Long heroId = insertHero(data, skillIds);
        // Skin
        insertSkin(data.getSkins(), heroId);
    }

    private void insertSkin(List<SkinItem> skins, Long heroId) {
        skins.stream()
                .map(item -> {
                    Skin skin = new Skin();
                    BeanUtils.copyProperties(item, skin);
                    skin.setHeroId(heroId);
                    return skin;
                })
                .forEach(skinMapper::insert);
    }

    private Long insertHero(Data data, List<Long> skillIds) {
        Hero hero = new Hero();
        BeanUtils.copyProperties(data, hero);
        BeanUtils.copyProperties(data.getInfo(), hero);
        hero.setSkill(Strings.join(skillIds, ','));
        hero.setType(Strings.join(data.getType(), ','));
        hero.setEnemyTips(JSON.toJSONString(data.getEnemytips()));
        hero.setAllyTips(JSON.toJSONString(data.getAllytips()));
        System.out.println(hero);
        heroMapper.insert(hero);
        return hero.getId();
    }

    private List<Long> insertSkill(List<SkillItem> skills) {
        List<Long> skillIds = new ArrayList<>();
        for (int i = 0; i < skills.size(); i++) {
            SkillItem item = skills.get(i);
            Skill skill = new Skill();
            BeanUtils.copyProperties(item, skill);
            skill.setType(i);
            skillMapper.insert(skill);
            skillIds.add(skill.getId());
        }
        return skillIds;
    }

    @lombok.Data
    public static class Data {

        private static final Map<String, Integer> TAG_2_INFO = new HashMap<String, Integer>() {{
            put("Fighter", 1);
            put("Mage", 2);
            put("Assassin", 3);
            put("Tank", 4);
            put("Marksman", 5);
            put("Support", 6);
        }};

        private String name;
        private String nickname;
        private String englishName;
        private String avatar;
        private String story;
        private Info info;
        private List<String> tags;
        private List<SkillItem> skills;
        private List<SkinItem> skins;
        private List<String> enemytips;
        private List<String> allytips;

        public List<Integer> getType() {
            if (tags == null) {
                return Collections.emptyList();
            }
            return tags.stream().map(TAG_2_INFO::get).collect(Collectors.toList());
        }
    }

    @lombok.Data
    private static class Info {
        private int attack;
        private int magic;
        private int defense;
        private int difficulty;
    }

    @lombok.Data
    private static class SkillItem {
        private String name;
        private String icon;
        private String tip;
        private String description;

    }

    @lombok.Data
    private static class SkinItem {
        private String name;
        private String icon;
    }

}
