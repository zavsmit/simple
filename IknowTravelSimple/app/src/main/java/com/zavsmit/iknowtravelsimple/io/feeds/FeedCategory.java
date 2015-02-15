package com.zavsmit.iknowtravelsimple.io.feeds;

import com.orm.SugarRecord;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Sega on 14.02.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedCategory {

    @JsonProperty("_id")
    private String idCategory;

    @JsonProperty("locale")
    private String locale;

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
