package com.test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class Response {
    @SerializedName("num_results")
    private int total;
    private String status;
    private List<Result> results;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public List<Result> getProducts() {
        return results;
    }
}
