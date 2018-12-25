package com.product.nearme.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertisements {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("advertisements")
    @Expose
    private List<Advertisement> advertisements = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

}
