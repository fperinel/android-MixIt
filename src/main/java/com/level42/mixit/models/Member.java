package com.level42.mixit.models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import android.graphics.Bitmap;

/**
 * Classe représentant un membre
 */
public class Member {

	private Integer id;
	
	private String firstname;

	private String lastname;

	private String login;

	private String company;

	private String shortdesc;

	private String longdesc;

	private String urlimage;

	@JsonIgnore
	private Bitmap image;

	private String nbConsults;

	private List<Integer> links;

	private List<Integer> linkers;

	private List<Link> sharedLinks;

	private String logo;

	private String level;

	private String type;

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
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the shortdesc
	 */
	public String getShortdesc() {
		return shortdesc;
	}

	/**
	 * @param shortdesc the shortdesc to set
	 */
	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	/**
	 * @return the longdesc
	 */
	public String getLongdesc() {
		return longdesc;
	}

	/**
	 * @param longdesc the longdesc to set
	 */
	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}

	/**
	 * @return the urlimage
	 */
	public String getUrlimage() {
		return urlimage;
	}

	/**
	 * @param urlimage the urlimage to set
	 */
	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}

	/**
	 * @return the nbConsults
	 */
	public String getNbConsults() {
		return nbConsults;
	}

	/**
	 * @param nbConsults the nbConsults to set
	 */
	public void setNbConsults(String nbConsults) {
		this.nbConsults = nbConsults;
	}

	/**
	 * @return the links
	 */
	public List<Integer> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<Integer> links) {
		this.links = links;
	}

	/**
	 * @return the linkers
	 */
	public List<Integer> getLinkers() {
		return linkers;
	}

	/**
	 * @param linkers the linkers to set
	 */
	public void setLinkers(List<Integer> linkers) {
		this.linkers = linkers;
	}

	/**
	 * @return the sharedLinks
	 */
	public List<Link> getSharedLinks() {
		return sharedLinks;
	}

	/**
	 * @param sharedLinks the sharedLinks to set
	 */
	public void setSharedLinks(List<Link> sharedLinks) {
		this.sharedLinks = sharedLinks;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the image
	 */
	public Bitmap getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}
}
