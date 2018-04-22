package com.puke.lol.mapper;

import com.puke.lol.entity.Skin;
import com.puke.lol.entity.SkinExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkinMapper {
    long countByExample(SkinExample example);

    int deleteByExample(SkinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Skin record);

    int insertSelective(Skin record);

    List<Skin> selectByExample(SkinExample example);

    Skin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Skin record, @Param("example") SkinExample example);

    int updateByExample(@Param("record") Skin record, @Param("example") SkinExample example);

    int updateByPrimaryKeySelective(Skin record);

    int updateByPrimaryKey(Skin record);
}