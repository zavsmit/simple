package com.zavsmit.iknowtravelsimple.utils;

import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by Sega on 14.02.2015.
 */
public class Constants {

    public static final String GET_FEEDS_CACHE = "getFeeds";
    public static final long GET_FEEDS_TIME = DurationInMillis.ONE_HOUR;

    public static final String GET_CATEGORY_CACHE = "getCategory";
    public static final long GET_CATEGORY_TIME = DurationInMillis.ONE_DAY;

    public static final String START_REQ_URL = "http://api.iknow.travel/";
    public static final String CONTENTS = "contents" ;
    public static final String CATEGORY = "categories" ;
    public static final String PHOTO = "photo/crop/" ;
    public static final String REGION = "Russia_Moscow" ;

    public static final int LIMIT = 8;
}
