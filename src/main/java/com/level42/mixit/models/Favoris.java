package com.level42.mixit.models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Classe représentant un favoris.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Favoris {

    /**
     * Identifiant du favoris.
     */
    private Integer id;

    /**
     * Titre du talk
     */
    private String title;
    
    /**
     * Résumé du talk
     */
    private String summary;
    
    /**
     * Description
     */
    private String description;
    
    /**
     * Centres d'intérêts
     */
    private List<Interest> interests;
    
    /**
     * Image
     */
    @JsonProperty("urlimage")
    private String urlImage;
    
    /**
     * Speakers
     */
    private List<Speaker> speakers;
    
    /**
     * Format
     */
    private String format;
    
    /**
     * Niveau
     */
    private String level;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the interests
     */
    public List<Interest> getInterests() {
        return interests;
    }

    /**
     * @param interests the interests to set
     */
    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    /**
     * @return the urlImage
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * @param urlImage the urlImage to set
     */
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    /**
     * @return the speakers
     */
    public List<Speaker> getSpeakers() {
        return speakers;
    }

    /**
     * @param speakers the speakers to set
     */
    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }
}
