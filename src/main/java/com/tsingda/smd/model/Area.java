package com.tsingda.smd.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "area")
public class Area implements Serializable {
    
    /** 
     * serialVersionUID
     */ 
    private static final long serialVersionUID = 5832865156230045586L;

    private String areaCode;

    private String topAreaCode;

    private String areaName;

    private String areaLevel;

    private String isLast;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getTopAreaCode() {
        return topAreaCode;
    }

    public void setTopAreaCode(String topAreaCode) {
        this.topAreaCode = topAreaCode == null ? null : topAreaCode.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel == null ? null : areaLevel.trim();
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast == null ? null : isLast.trim();
    }
}