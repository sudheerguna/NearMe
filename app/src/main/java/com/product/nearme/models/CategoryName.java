package com.product.nearme.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryName {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("mainCategoryid")
    @Expose
    private String mainCategoryid;

    public CategoryName(String id, String categoryid, String category_name, String mainCategoryid) {
        this.id = id;
        this.categoryid = categoryid;
        this.categoryName = category_name;
        this.mainCategoryid = mainCategoryid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMainCategoryid() {
        return mainCategoryid;
    }

    public void setMainCategoryid(String mainCategoryid) {
        this.mainCategoryid = mainCategoryid;
    }

}
