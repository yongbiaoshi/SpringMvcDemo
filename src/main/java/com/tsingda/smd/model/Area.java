package com.tsingda.smd.model;

import java.io.Serializable;

public class Area implements Serializable {
    
    /** 
     * serialVersionUID
     */ 
    private static final long serialVersionUID = 1533181555305352855L;

    private String areaCode;

    private String topAreaCode;

    private String areaName;

    private String areaLevel;

    private String isLast;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTopAreaCode() {
        return topAreaCode;
    }

    public void setTopAreaCode(String topAreaCode) {
        this.topAreaCode = topAreaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

}