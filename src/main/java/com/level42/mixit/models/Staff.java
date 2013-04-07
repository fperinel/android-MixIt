package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un membre du staff (hérite d'un membre)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Staff extends Member {

}
