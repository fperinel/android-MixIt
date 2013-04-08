package com.level42.mixit.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Classe représentant un sponsor (hérite d'un membre).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sponsor extends Member {

}
