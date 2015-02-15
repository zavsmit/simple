package com.zavsmit.iknowtravelsimple.db;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Sega on 15.02.2015.
 */
public class DBFeeds extends SugarRecord<DBFeeds> {

    String title;
    String idFeeds;
    String idtype;
    String image;
    ArrayList<String> categories;

    public DBFeeds() {
    }

    public DBFeeds(String title, String idFeeds, String idtype, String image, ArrayList<String> categories) {
        this.title = title;
        this.idFeeds = idFeeds;
        this.idtype = idtype;
        this.image = image;
        this.categories = categories;
    }
}
