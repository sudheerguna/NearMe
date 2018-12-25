package com.product.nearme.models.adv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("stroke-opacity")
    @Expose
    private Integer strokeOpacity;
    @SerializedName("stroke-width")
    @Expose
    private Integer strokeWidth;
    @SerializedName("fill")
    @Expose
    private String fill;
    @SerializedName("stroke")
    @Expose
    private String stroke;
    @SerializedName("fill-opacity")
    @Expose
    private Double fillOpacity;

    public Integer getStrokeOpacity() {
        return strokeOpacity;
    }

    public void setStrokeOpacity(Integer strokeOpacity) {
        this.strokeOpacity = strokeOpacity;
    }

    public Integer getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(Integer strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public Double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(Double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

}
