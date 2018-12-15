package com.watchingTVmobile.watchingTV.network;


import com.watchingTVmobile.watchingTV.network.films.Film;
import com.watchingTVmobile.watchingTV.network.films.FilmCreditResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsSortisResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsPopulairesResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsVenirResponse;
import com.watchingTVmobile.watchingTV.network.series.SerieDiffAuj;
import com.watchingTVmobile.watchingTV.network.series.SerieDiffActuResponse;
import com.watchingTVmobile.watchingTV.network.series.SeriePopulaireResponse;
import com.watchingTVmobile.watchingTV.network.series.Serie;
import com.watchingTVmobile.watchingTV.network.series.SerieCreditsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //Films

    @GET("movie/now_playing")
    Call<FilmsSortisResponse> getNowShowingMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("language") String langue);

    @GET("movie/popular")
    Call<FilmsPopulairesResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("language") String langue);

    @GET("movie/upcoming")
    Call<FilmsVenirResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("language") String langue);

    @GET("movie/{id}")
    Call<Film> getMovieDetails(@Path("id") Integer movieId, @Query("api_key") String apiKey, @Query("language") String langue);

    @GET("movie/{id}/credits")
    Call<FilmCreditResponse> getMovieCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey, @Query("language") String langue);

    @GET("genre/movie/list")
    Call<com.watchingTVmobile.watchingTV.network.films.GenresList> getMovieGenresList(@Query("api_key") String apiKey, @Query("language") String langue);

    //Series

    @GET("tv/airing_today")
    Call<SerieDiffAuj> getAiringTodayTVShows(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("language") String langue);

    @GET("tv/on_the_air")
    Call<SerieDiffActuResponse> getOnTheAirTVShows(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("language") String langue);

    @GET("tv/popular")
    Call<SeriePopulaireResponse> getPopularTVShows(@Query("api_key") String apiKey, @Query("page") Integer page, @Query("language") String langue);

    @GET("tv/{id}")
    Call<Serie> getTVShowDetails(@Path("id") Integer tvShowId, @Query("api_key") String apiKey, @Query("language") String langue);

    @GET("tv/{id}/credits")
    Call<SerieCreditsResponse> getTVShowCredits(@Path("id") Integer movieId, @Query("api_key") String apiKey, @Query("language") String langue);

    @GET("genre/tv/list")
    Call<com.watchingTVmobile.watchingTV.network.series.GenresList> getTVShowGenresList(@Query("api_key") String apiKey, @Query("language") String langue);


}
