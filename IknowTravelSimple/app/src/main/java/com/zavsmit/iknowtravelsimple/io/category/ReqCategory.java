package com.zavsmit.iknowtravelsimple.io.category;

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

/**
 * Created by Sega on 14.02.2015.
 */
public class ReqCategory extends SpringAndroidSpiceRequest<ModelCategory> {

    private Context context;

    public ReqCategory(Context context) {
        super(ModelCategory.class);
        this.context = context;
    }

    @Override
    public ModelCategory loadDataFromNetwork() throws Exception {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(context.getString(R.string.xApi), context.getString(R.string.four));
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        createMessageConverters(restTemplate);

        String parameters = "?" +
                "&nofields=created,updated,morpher,__v" +
                "&nopopulates=all";

        ResponseEntity<ModelCategory> list = getRestTemplate().exchange(Constants.START_REQ_URL + Constants.CATEGORY + parameters,
                HttpMethod.GET, requestEntity, ModelCategory.class);
        return list.getBody();
    }

    private void createMessageConverters(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }
}