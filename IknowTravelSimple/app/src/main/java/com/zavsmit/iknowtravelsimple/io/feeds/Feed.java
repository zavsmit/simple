package com.zavsmit.iknowtravelsimple.io.feeds;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed{

    @JsonProperty("_id")
    private String idFeed;

    @JsonProperty("type")
    private String type;

    @JsonProperty("article_type")
    private String articleType;

    @JsonProperty("state")
    private String state;

    @JsonProperty("title")
    private String title;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("published")
    private Date published;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("categories")
    private ArrayList<FeedCategory> categories;

    @JsonProperty("article_cover_1x")
    private ArticleCover articleCover;

    @JsonProperty("address_cover_1x")
    private ArticleCover addressCover;


    public String getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(String idFeed) {
        this.idFeed = idFeed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public ArrayList<FeedCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<FeedCategory> categories) {
        this.categories = categories;
    }

    public ArticleCover getArticleCover() {
        return articleCover;
    }

    public void setArticleCover(ArticleCover articleCover) {
        this.articleCover = articleCover;
    }

    public ArticleCover getAddressCover() {
        return addressCover;
    }

    public void setAddressCover(ArticleCover addressCover) {
        this.addressCover = addressCover;
    }
}
