/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.android.tv.recommendations.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.tv.recommendations.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Mocks gathering movies from an external source. */
public final class MockMovieService {

    private static List<Movie> list;
    private static long count = 0;

    /**
     * Creates a list of subscriptions that every users should have.
     *
     * @param context used for accessing shared preferences.
     * @return a list of default subscriptions.
     */
    public static List<Subscription> createUniversalSubscriptions(Context context) {

        String seriados = context.getString(R.string.seriados);
        Subscription seriadosSubscription =
                Subscription.createSubscription(
                        seriados,
                        context.getString(R.string.seriados_descricao),
                        R.drawable.ic_video_library_blue_80dp);

        String filmes = context.getString(R.string.filmes);
        Subscription filmesSubscription =
                Subscription.createSubscription(
                        filmes,
                        context.getString(R.string.filmes_descricao),
                        R.drawable.ic_movie_blue_80dp);

        return Arrays.asList(seriadosSubscription, filmesSubscription);
    }

    /**
     * Creates and caches a list of movies.
     *
     * @return a list of movies.
     */
    public static List<Movie> getList() {
        if (list == null || list.isEmpty()) {
            list = createMovieList();
        }
        return list;
    }

    /**
     * Shuffles the list of movies to make the returned list appear to be a different list from
     * {@link #getList()}.
     *
     * @return a list of movies in random order.
     */
    public static List<Movie> getFreshList() {
        List<Movie> shuffledMovies = new ArrayList<>(getList());
        Collections.shuffle(shuffledMovies);
        return shuffledMovies;
    }

    private static List<Movie> createMovieList() {
        List<Movie> list = new ArrayList<>();

        /*
        seriados:
        https://api.themoviedb.org/3/trending/tv/week?api_key=3eac9721452d5839bfc882cc266d5f8a&language=pt-BR&include_image_language=pt

        filmes:
        https://api.themoviedb.org/3/trending/movie/week?api_key=3eac9721452d5839bfc882cc266d5f8a&language=pt-BR&include_image_language=pt
        */

        list.add(buildMovieInfo("category",
                "Ford v Ferrari",
                "Durante a década de 1960, a Ford resolve entrar no ramo das corridas automobilísticas de forma que a empresa ganhe o prestígio e o glamour da concorrente Ferrari, campeoníssima em várias corridas. Para tanto, contrata o ex-piloto Carroll Shelby (Matt Damon) para chefiar a empreitada. Por mais que tenha carta branca para montar sua equipe, incluindo o piloto e engenheiro Ken Miles (Christian Bale), Shelby enfrenta problemas com a diretoria da Ford, especialmente pela mentalidade mais voltada para os negócios e a imagem da empresa do que propriamente em relação ao aspecto esportivo.",
                "https://image.tmdb.org/t/p/w200/lKhF0QX724VS2QqBzSZ4KJif3Ny.jpg",
                "https://image.tmdb.org/t/p/w200/lKhF0QX724VS2QqBzSZ4KJif3Ny.jpg"));

        return list;
    }

    private static Movie buildMovieInfo(
            String category,
            String title,
            String description,
            String cardImageUrl,
            String backgroundImageUrl) {
        Movie movie = new Movie();
        movie.setId(count);
        incCount();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setCategory(category);
        movie.setCardImageUrl(cardImageUrl);
        movie.setBackgroundImageUrl(backgroundImageUrl);
        return movie;
    }

    private static void incCount() {
        count++;
    }

}
