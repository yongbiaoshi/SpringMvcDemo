package com.tsingda.smd.dao.mapper;

import java.util.List;

import com.tsingda.smd.model.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(String areaCode);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(String areaCode);

    List<Area> selectAll();
    
    List<Area> selectByParentCode(String parentCode);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
}