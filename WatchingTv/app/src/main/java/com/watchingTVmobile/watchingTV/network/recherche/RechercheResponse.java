package com.watchingTVmobile.watchingTV.network.recherche;

import java.util.List;

public class RechercheResponse {

    private Integer page;
    private List<RechercheResultat> results;
    private Integer totalPages;

    public RechercheResponse() {

    }

    public RechercheResponse(Integer page, List<RechercheResultat> results, Integer totalPages) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<RechercheResultat> getResults() {
        return results;
    }

    public void setResults(List<RechercheResultat> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
