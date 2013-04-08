package com.level42.mixit.models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.graphics.Bitmap;

/**
 * Classe représentant un membre.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

    /**
     * Identifiant du membre.
     */
    private Integer id;

    /**
     * Prénom.
     */
    private String firstname;

    /**
     * Nom.
     */
    private String lastname;

    /**
     * login.
     */
    private String login;

    /**
     * Entreprise.
     */
    private String company;

    /**
     * Description courte.
     */
    private String shortdesc;

    /**
     * Description longue.
     */
    private String longdesc;

    /**
     * URL de l'avatar du membre.
     */
    private String urlimage;

    /**
     * Objet image de l'avatar du membre.
     */
    @JsonIgnore
    private Bitmap image;

    /**
     * Nombre de consultations.
     */
    private String nbConsults;

    /**
     * Liste des liens.
     */
    private List<Integer> links;

    /**
     * Liste des membre liés.
     */
    private List<Integer> linkers;

    /**
     * Liste des liens partagés.
     */
    private List<Link> sharedLinks;

    /**
     * Logo (entreprise).
     */
    private String logo;

    /**
     * Niveau sponsor.
     */
    private String level;

    /**
     * Type de membre.
     */
    private String type;

    /**
     * Retourne l'identifiant unique du membre.
     * 
     * @return Identifiant
     */
    public Integer getId() {
        return id;
    }

    /**
     * Renseigne l'identifiant unique du membre.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le prénom.
     * 
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Renseigne le prénom.
     * 
     * @param firstname
     *            the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Retourne le nom.
     * 
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Renseigne le nom.
     * 
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Retourne le login.
     * 
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Renseigne le login.
     * 
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Retourne l'entreprise.
     * 
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Rensiegne l'entreprise.
     * 
     * @param company
     *            the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Retourne la description courte.
     * 
     * @return the shortdesc
     */
    public String getShortdesc() {
        return shortdesc;
    }

    /**
     * Renseigne la description courte.
     * 
     * @param shortdesc
     *            the shortdesc to set
     */
    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    /**
     * Retourne la description longue.
     * 
     * @return the longdesc
     */
    public String getLongdesc() {
        return longdesc;
    }

    /**
     * Renseigne la description longue.
     * 
     * @param longdesc
     *            the longdesc to set
     */
    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    /**
     * Retourne de l'url de l'avatar.
     * 
     * @return the urlimage
     */
    public String getUrlimage() {
        return urlimage;
    }

    /**
     * Renseigne l'url de l'avatar.
     * 
     * @param urlimage
     *            the urlimage to set
     */
    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    /**
     * Retourne le nombre de consultation.
     * 
     * @return the nbConsults
     */
    public String getNbConsults() {
        return nbConsults;
    }

    /**
     * Renseigne le nombre de consultation.
     * 
     * @param nbConsults
     *            the nbConsults to set
     */
    public void setNbConsults(String nbConsults) {
        this.nbConsults = nbConsults;
    }

    /**
     * Retourne les liens.
     * 
     * @return the links
     */
    public List<Integer> getLinks() {
        return links;
    }

    /**
     * Rensiegne les liens.
     * 
     * @param links
     *            the links to set
     */
    public void setLinks(List<Integer> links) {
        this.links = links;
    }

    /**
     * Retourne les membres liés.
     * 
     * @return the linkers
     */
    public List<Integer> getLinkers() {
        return linkers;
    }

    /**
     * rensiegne les membres liés.
     * 
     * @param linkers
     *            the linkers to set
     */
    public void setLinkers(List<Integer> linkers) {
        this.linkers = linkers;
    }

    /**
     * Retourne les liens partagés.
     * 
     * @return the sharedLinks
     */
    public List<Link> getSharedLinks() {
        return sharedLinks;
    }

    /**
     * Renseigne les liens partagés.
     * 
     * @param sharedLinks
     *            the sharedLinks to set
     */
    public void setSharedLinks(List<Link> sharedLinks) {
        this.sharedLinks = sharedLinks;
    }

    /**
     * Retourne le logo de l'entreprise.
     * 
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Renseigne le logo de l'entreprise.
     * 
     * @param logo
     *            the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Retourne le niveau du sponsor.
     * 
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Renseigne le niveau du sponsor.
     * 
     * @param level
     *            the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Retourne le type de membre.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Renseigne le type de membre.
     * 
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retourne l'objet image de l'avatar.
     * 
     * @return the image
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Renseigne l'objet image de l'avatar.
     * 
     * @param image
     *            the image to set
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }
}
