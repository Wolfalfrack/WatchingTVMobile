package com.watchingTVmobile.watchingTV.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerieCreditsResponse {

    @SerializedName("cast")
    private List<SerieCastingDescrip> casts;
    @SerializedName("crew")
    private List<SerieCrewDescrip> crews;
    @SerializedName("id")
    private Integer id;

    public SerieCreditsResponse(List<SerieCastingDescrip> casts, List<SerieCrewDescrip> crews, Integer id) {
        this.casts = casts;
        this.crews = crews;
        this.id = id;
    }

    public List<SerieCastingDescrip> getCasts() {
        return casts;
    }

    public void setCasts(List<SerieCastingDescrip> casts) {
        this.casts = casts;
    }

    public List<SerieCrewDescrip> getCrews() {
        return crews;
    }

    public void setCrews(List<SerieCrewDescrip> crews) {
        this.crews = crews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
