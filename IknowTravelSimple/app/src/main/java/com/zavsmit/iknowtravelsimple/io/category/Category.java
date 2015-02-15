package com.zavsmit.iknowtravelsimple.io.category;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Sega on 14.02.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    @JsonProperty("_id")
    private String idCategory;

    @JsonProperty("name")
    private String name;

    @JsonProperty("locale")
    private String locale;

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
