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

/**
 * Contains the data about a channel that will be displayed on the launcher.
 */
public class Subscription {

    private long channelId;
    private String name;
    private String description;
    private int channelLogo;

    /**
     * Constructor for Gson to use.
     */
    public Subscription() {

    }

    private Subscription(String name, String description, int channelLogo) {
        this.name = name;
        this.description = description;
        this.channelLogo = channelLogo;
    }

    public static Subscription createSubscription(String name, String description, int channelLogo) {
        return new Subscription(name, description, channelLogo);
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(int channelLogo) {
        this.channelLogo = channelLogo;
    }
}
