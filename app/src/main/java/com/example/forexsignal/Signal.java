
package com.example.forexsignal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Signal {

    @Expose
    private String createdAt;
    @Expose
    private Long paid;
    @Expose
    private String prediction;
    @Expose
    private String signalName;
    @SerializedName("trade_id")
    private String tradeId;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

}
