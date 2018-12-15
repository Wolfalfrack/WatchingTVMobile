package com.watchingTVmobile.watchingTV.network.films;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmsSortisResponse {

    @SerializedName("results")
    private List<FilmDescrip> results;
    @SerializedName("page")
    private Integer page;
    @SerializedName("total_results")
    private Integer totalResults;
    //dates missing
    @SerializedName("total_pages")
    private Integer totalPages;

    public FilmsSortisResponse(List<FilmDescrip> results, Integer page, Integer totalResults, Integer totalPages) {
        this.results = results;
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public List<FilmDescrip> getResults() {
        return results;
    }

    public void setResults(List<FilmDescrip> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
