package com.puke.lol.mapper;

import com.puke.lol.entity.Hero;
import com.puke.lol.entity.HeroExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HeroMapper {
    long countByExample(HeroExample example);

    int deleteByExample(HeroExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Hero record);

    int insertSelective(Hero record);

    List<Hero> selectByExample(HeroExample example);

    Hero selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Hero record, @Param("example") HeroExample example);

    int updateByExample(@Param("record") Hero record, @Param("example") HeroExample example);

    int updateByPrimaryKeySelective(Hero record);

    int updateByPrimaryKey(Hero record);

    @Select("select * from hero")
    List<Hero> findAll();

}