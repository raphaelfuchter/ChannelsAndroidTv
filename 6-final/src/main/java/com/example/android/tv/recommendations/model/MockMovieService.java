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

import android.content.Context;
import com.example.android.tv.recommendations.R;

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

        String toWatch = context.getString(R.string.to_watch);
        Subscription toWatchSubscription =
                Subscription.createSubscription(
                        toWatch,
                        context.getString(R.string.to_watch_description),
                        R.drawable.ic_video_library_blue_80dp);

        String trending = context.getString(R.string.trending);
        Subscription trendingSubscription =
                Subscription.createSubscription(
                        trending,
                        context.getString(R.string.trending_description),
                        R.drawable.ic_movie_blue_80dp);

        return Arrays.asList(toWatchSubscription, trendingSubscription);
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

        list.add(buildMovieInfo("category",
                "All American (2018)",
                "S02E11 - The Crossroads",
                "https://dg31sz3gwrwan.cloudfront.net/screen/7477831/1_iphone.jpg",
                "https://dg31sz3gwrwan.cloudfront.net/screen/7477831/1_iphone.jpg"));

        list.add(buildMovieInfo("category",
                "South Park",
                "S23E10 - Christmas Snow",
                "https://dg31sz3gwrwan.cloudfront.net/screen/7461511/1_iphone.jpg",
                "https://dg31sz3gwrwan.cloudfront.net/screen/7461511/1_iphone.jpg"));

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
