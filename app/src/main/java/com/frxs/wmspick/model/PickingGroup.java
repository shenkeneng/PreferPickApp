package com.frxs.wmspick.model;


import java.io.Serializable;

/**
 * Created by Chentie on 2018/9/20.
 */

public class PickingGroup implements Serializable{

    /**
     * PGroupID : 0
     * PGroupName : string
     * StationID : 0
     * StationName : string
     * StationSort : 0
     */

    private int PGroupID;
    private String PGroupName;
    private int StationID;
    private String StationName;
    private int StationSort;
    private String PickType;// 当前拣货类型   SUM_PRODUCT|SUM_SHOP|SUM_LINE

    public int getPGroupID() {
        return PGroupID;
    }

    public void setPGroupID(int PGroupID) {
        this.PGroupID = PGroupID;
    }

    public String getPGroupName() {
        return PGroupName;
    }

    public void setPGroupName(String PGroupName) {
        this.PGroupName = PGroupName;
    }

    public int getStationID() {
        return StationID;
    }

    public void setStationID(int StationID) {
        this.StationID = StationID;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public int getStationSort() {
        return StationSort;
    }

    public void setStationSort(int StationSort) {
        this.StationSort = StationSort;
    }

    public String getPickType() {
        return PickType;
    }

    public void setPickType(String pickType) {
        PickType = pickType;
    }
}
