package com.zavsmit.iknowtravelsimple.io.category;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Sega on 14.02.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelCategory extends ArrayList<Category> {
}
