package com.zavsmit.iknowtravelsimple.io.feeds;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.zavsmit.iknowtravelsimple.R;
import com.zavsmit.iknowtravelsimple.utils.Constants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReqFeeds extends SpringAndroidSpiceRequest<ModelFeeds> {

    private Context context;
    private int skip;
    private String idCategory;
    private String limit = String.valueOf(Constants.LIMIT);

    public ReqFeeds(Context context, int skip, String idCategory) {
        super(ModelFeeds.class);
        this.context = context;
        this.skip = skip;
        this.idCategory = idCategory;
    }

    public ReqFeeds(Context context) {
        super(ModelFeeds.class);
        this.context = context;
        skip = 0;
        limit = "all";
        idCategory = "";
    }

    @Override
    public ModelFeeds loadDataFromNetwork() throws Exception {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(context.getString(R.string.xApi), context.getString(R.string.four));
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        createMessageConverters(restTemplate);

        String parameters = "?" +
                "region=" + Constants.REGION +
                "&limit=" + limit +
                "&skip=" + skip +
                "&category=" + idCategory +
                "&nofields=views,article_cover_2x,article_cover_4x,article_ios_cover,article_blocks,body,warnings,audit,article_addresses,tags,city,copyrights,updated,created,saves,__v" +
                "&nopopulates=all";

        ResponseEntity<ModelFeeds> list = getRestTemplate().exchange(Constants.START_REQ_URL + Constants.CONTENTS + parameters,
                HttpMethod.GET, requestEntity, ModelFeeds.class);
        return list.getBody();
    }

    private void createMessageConverters(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }
}