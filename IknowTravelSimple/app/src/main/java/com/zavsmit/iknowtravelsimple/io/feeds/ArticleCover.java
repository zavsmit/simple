package com.zavsmit.iknowtravelsimple.io.feeds;

import com.orm.SugarRecord;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Sega on 14.02.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleCover{

    @JsonProperty("copyrights")
    private String copyrights;

    @JsonProperty("_id")
    private String idArticle;

    @JsonProperty("filename")
    private String filename;

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
