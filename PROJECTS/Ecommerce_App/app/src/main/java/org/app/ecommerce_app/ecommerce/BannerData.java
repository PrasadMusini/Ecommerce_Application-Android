package org.app.ecommerce_app.ecommerce;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class BannerData {

    @SerializedName("ListResult")
    private ArrayList<BannerList> bannersList;
    private boolean IsSuccess;
    private int AffectedRecords;
    private String EndUserMessage;

    public BannerData(ArrayList<BannerList> bannersList, boolean isSuccess, int affectedRecords, String endUserMessage) {
        this.bannersList = bannersList;
        IsSuccess = isSuccess;
        AffectedRecords = affectedRecords;
        EndUserMessage = endUserMessage;
    }

    public ArrayList<BannerList> getBannersList() {
        return bannersList;
    }

    public void setBannersList(ArrayList<BannerList> bannersList) {
        this.bannersList = bannersList;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public int getAffectedRecords() {
        return AffectedRecords;
    }

    public void setAffectedRecords(int affectedRecords) {
        AffectedRecords = affectedRecords;
    }

    public String getEndUserMessage() {
        return EndUserMessage;
    }

    public void setEndUserMessage(String endUserMessage) {
        EndUserMessage = endUserMessage;
    }

    public class BannerList{

        private int Id;
        private String Name;
        private String FilePath;
        private boolean IsActive;

        public BannerList(int id, String name, String filePath, boolean isActive) {
            Id = id;
            Name = name;
            FilePath = filePath;
            IsActive = isActive;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String filePath) {
            FilePath = filePath;
        }

        public boolean isActive() {
            return IsActive;
        }

        public void setActive(boolean active) {
            IsActive = active;
        }
    }

}


