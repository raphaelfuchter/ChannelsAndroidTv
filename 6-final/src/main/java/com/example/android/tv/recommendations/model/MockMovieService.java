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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Mocks gathering movies from an external source.
 */
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

        return Arrays.asList(seriadosSubscription);
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

        try {
            String resultado = findJSONFromUrl("https://api.themoviedb.org/3/trending/all/week?api_key=3eac9721452d5839bfc882cc266d5f8a&language=pt-BR&include_image_language=pt");

            JSONObject items = new JSONObject(resultado);
            Iterator x = items.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()) {
                String key = (String) x.next();
                if (key.equals("results")) {
                    jsonArray.put(items.get(key));
                }
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray a = jsonArray.getJSONArray(i);
                for (int j = 0; j < a.length(); j++) {
                    JSONObject result = a.getJSONObject(j);

                    list.add(buildMovieInfo("category",
                            result.getString("original_title"),
                            result.getString("overview"),
                            "https://image.tmdb.org/t/p/w500" + result.getString("poster_path"),
                            "https://image.tmdb.org/t/p/w500" + result.getString("poster_path")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private static String findJSONFromUrl(String url) {
        String result = "";
        try {
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setReadTimeout(15000); //milliseconds
            conn.setConnectTimeout(1500); // milliseconds
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                result = sb.toString();
            } else {
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }

        return result;
    }

}

