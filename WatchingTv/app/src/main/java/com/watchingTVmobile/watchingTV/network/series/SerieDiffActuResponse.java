package com.watchingTVmobile.watchingTV.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerieDiffActuResponse {

    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<SerieDescrip> results;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;

    public SerieDiffActuResponse(Integer page, List<SerieDescrip> results, Integer totalResults, Integer totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<SerieDescrip> getResults() {
        return results;
    }

    public void setResults(List<SerieDescrip> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
