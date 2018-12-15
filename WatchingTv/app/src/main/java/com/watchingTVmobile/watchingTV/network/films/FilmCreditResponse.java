package com.watchingTVmobile.watchingTV.network.films;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FilmCreditResponse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("cast")
    private List<FilmCastingDescrip> casts;
    @SerializedName("crew")
    private List<FilmsCrewDescrip> crews;

    public FilmCreditResponse(Integer id, List<FilmCastingDescrip> casts, List<FilmsCrewDescrip> crews) {
        this.id = id;
        this.casts = casts;
        this.crews = crews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<FilmCastingDescrip> getCasts() {
        return casts;
    }

    public void setCasts(List<FilmCastingDescrip> casts) {
        this.casts = casts;
    }

    public List<FilmsCrewDescrip> getCrews() {
        return crews;
    }

    public void setCrews(List<FilmsCrewDescrip> crews) {
        this.crews = crews;
    }

}
