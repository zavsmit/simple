package com.zavsmit.iknowtravelsimple.io.feeds;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelFeeds extends ArrayList<Feed> {
}
