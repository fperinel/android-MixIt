package com.level42.mixit.models;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Classe représentant un Talk
 */
public class Talk implements Comparable<Talk> {

	private Integer id;
	
	private String title;
	
	private String summary;
	
	private String description;
	
	@JsonProperty("interests")
	private List<Integer> interestsId;

	@JsonIgnore
	private List<Interest> interests;
	
	@JsonProperty("speakers")
	private List<Integer> speakersId;

	@JsonIgnore
	private List<Speaker> speakers;
	
	private String format;
	
	private String level;

	@JsonIgnore
	private Session session;
	
	private String start;
	
	private String end;	
	
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
	 * @return the interests Id
	 */
	public List<Integer> getInterestsId() {
		return interestsId;
	}

	/**
	 * @param interests the interests Id to set
	 */
	public void setInterestsId(List<Integer> interestsId) {
		this.interestsId = interestsId;
	}

	/**
	 * @return the speakers Id
	 */
	public List<Integer> getSpeakersId() {
		return speakersId;
	}

	/**
	 * @param speakers the speakers Id to set
	 */
	public void setSpeakersId(List<Integer> speakersId) {
		this.speakersId = speakersId;
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

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	
	/**
	 * Retourne la date de la session du Talk
	 * @return Date de la session du Talk
	 */
	public Date getDateSession() {
		if (this.session != null) {
			return this.session.getDateFormat();
		} else {
			return null;
		}
	}
	
	/**
	 * Retourne la salle de la session du Talk
	 * @return Salle de la session du Talk
	 */
	public String getSalleSession() {
		if (this.session != null) {
			return this.session.getSalle();
		} else {
			return null;
		}
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

	public int compareTo(Talk another) {
		if (another.getDateSession() == null) {
			return 1;
		}
		if (this.getDateSession() == null) {
			return -1;
		}
		return getDateSession().compareTo(another.getDateSession());
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
}