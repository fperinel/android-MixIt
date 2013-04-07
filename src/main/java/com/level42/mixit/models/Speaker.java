package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un speaker (hérite d'un membre)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Speaker extends Member {

}
