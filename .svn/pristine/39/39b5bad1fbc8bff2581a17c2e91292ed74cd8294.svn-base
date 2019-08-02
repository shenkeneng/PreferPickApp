package com.frxs.wmspick.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chentie on 2018/9/21.
 */

public class CompletePickOrder {

    private String PickID;
    private String BatPickID;//线路 | 商品（共有）
    private int PickEmpID;
    private String PickEmpName;
    private int StationID;
    private int StationSort;
    private String StationName;
    private int UserID;
    private String UserName;
    private int WID;
    private int OpAreaID;
    private List<CompletePickingOrderItemList> CompletePickingOrderItemList;

    public static class CompletePickingOrderItemList{
        String PickItemID;
        String BatPickItemID;//线路 | 商品（共有）
        double PickQty;
        private String SKU;
        double BuyQty;

        public String getPickItemID() {
            return PickItemID;
        }

        public void setPickItemID(String pickItemID) {
            PickItemID = pickItemID;
        }

        public double getPickQty() {
            return PickQty;
        }

        public void setPickQty(double pickQty) {
            PickQty = pickQty;
        }


        public String getSKU() {
            return SKU;
        }

        public void setSKU(String SKU) {
            this.SKU = SKU;
        }

        public double getBuyQty() {
            return BuyQty;
        }

        public void setBuyQty(double buyQty) {
            BuyQty = buyQty;
        }

        public String getBatPickItemID() {
            return BatPickItemID;
        }

        public void setBatPickItemID(String batPickItemID) {
            BatPickItemID = batPickItemID;
        }
    }

    public String getPickID() {
        return PickID;
    }

    public void setPickID(String pickID) {
        PickID = pickID;
    }

    public List<CompletePickOrder.CompletePickingOrderItemList> getCompletePickingOrderItemList() {
        return CompletePickingOrderItemList;
    }

    public void setCompletePickingOrderItemList(List<CompletePickOrder.CompletePickingOrderItemList> completePickingOrderItemList) {
        CompletePickingOrderItemList = completePickingOrderItemList;
    }

    public int getPickEmpID() {
        return PickEmpID;
    }

    public void setPickEmpID(int pickEmpID) {
        PickEmpID = pickEmpID;
    }

    public String getPickEmpName() {
        return PickEmpName;
    }

    public void setPickEmpName(String pickEmpName) {
        PickEmpName = pickEmpName;
    }

    public int getStationID() {
        return StationID;
    }

    public void setStationID(int stationID) {
        StationID = stationID;
    }

    public int getStationSort() {
        return StationSort;
    }

    public void setStationSort(int stationSort) {
        StationSort = stationSort;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getWID() {
        return WID;
    }

    public void setWID(int WID) {
        this.WID = WID;
    }

    public int getOpAreaID() {
        return OpAreaID;
    }

    public void setOpAreaID(int opAreaID) {
        OpAreaID = opAreaID;
    }

    public String getBatPickID() {
        return BatPickID;
    }

    public void setBatPickID(String batPickID) {
        BatPickID = batPickID;
    }
}
