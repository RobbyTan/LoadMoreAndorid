package com.example.uph.pertemuan7.api.Services;

import com.example.uph.pertemuan7.api.Model.Project;
import com.example.uph.pertemuan7.api.Model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by denly93 on 7/24/17.
 */

public interface TestingService {
    @GET("users/{user}/repos")
    Call<List<Project>> listRepos(@Path("user") String user);

    @GET("search/repositories")
    Call<Repository> repositories(@Query("q") String q, @Query("sort") String sort,
            @Query("order") String order, @Query("page") Integer page,
            @Query("per_page") Integer per_page);
}
