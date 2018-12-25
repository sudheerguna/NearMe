package com.product.nearme.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertisement {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("interestName")
    @Expose
    private String interestName;
    @SerializedName("descriptionAdv")
    @Expose
    private String descriptionAdv;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("locationPinId")
    @Expose
    private String locationPinId;
    @SerializedName("loc_lat")
    @Expose
    private String locLat;
    @SerializedName("loc_long")
    @Expose
    private String locLong;
    @SerializedName("postingCity")
    @Expose
    private String postingCity;
    @SerializedName("postStatus")
    @Expose
    private String postStatus;
    @SerializedName("broadcastId")
    @Expose
    private String broadcastId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("isbroadcasting")
    @Expose
    private String isbroadcasting;
    @SerializedName("seenByCount")
    @Expose
    private String seenByCount;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("isevent")
    @Expose
    private Boolean isevent;
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("features")
    @Expose
    private Features features;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getDescriptionAdv() {
        return descriptionAdv;
    }

    public void setDescriptionAdv(String descriptionAdv) {
        this.descriptionAdv = descriptionAdv;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationPinId() {
        return locationPinId;
    }

    public void setLocationPinId(String locationPinId) {
        this.locationPinId = locationPinId;
    }

    public String getLocLat() {
        return locLat;
    }

    public void setLocLat(String locLat) {
        this.locLat = locLat;
    }

    public String getLocLong() {
        return locLong;
    }

    public void setLocLong(String locLong) {
        this.locLong = locLong;
    }

    public String getPostingCity() {
        return postingCity;
    }

    public void setPostingCity(String postingCity) {
        this.postingCity = postingCity;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(String broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIsbroadcasting() {
        return isbroadcasting;
    }

    public void setIsbroadcasting(String isbroadcasting) {
        this.isbroadcasting = isbroadcasting;
    }

    public String getSeenByCount() {
        return seenByCount;
    }

    public void setSeenByCount(String seenByCount) {
        this.seenByCount = seenByCount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsevent() {
        return isevent;
    }

    public void setIsevent(Boolean isevent) {
        this.isevent = isevent;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

}
