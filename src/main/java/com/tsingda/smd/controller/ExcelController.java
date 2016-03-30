package com.tsingda.smd.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsingda.smd.dao.mapper.AreaMapper;
import com.tsingda.smd.model.Area;

@Controller
@RequestMapping(value = "/excel")
public class ExcelController {

    @Resource(name = "areaMapper")
    private AreaMapper areaMapper;

    @RequestMapping(value = "/arealist", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=GBK")
    public @ResponseBody byte[] areaList() throws UnsupportedEncodingException {
        List<Area> list = areaMapper.selectAll();
        StringBuffer sb = new StringBuffer();
        for (Area area : list) {
            sb.append(area.getAreaCode()).append("\t").append(area.getAreaName()).append("\r\n");
        }
        return sb.toString().getBytes("GBK");
    }

    @RequestMapping(value = "/area/{p}")
    public @ResponseBody List<Area> area(@PathVariable String p) throws UnsupportedEncodingException {
        Assert.hasText(p, "父区域code不能为空");
        if (p.contains("-")) {
            p = p.split("-")[0];
        }
        List<Area> list = areaMapper.selectByParentCode(p);

        return list;
    }

    @RequestMapping(value = "/area/xml/{code}")
    public @ResponseBody Area areaXml(@PathVariable String code) throws UnsupportedEncodingException {
        return areaMapper.selectByPrimaryKey(code);
    }

}
