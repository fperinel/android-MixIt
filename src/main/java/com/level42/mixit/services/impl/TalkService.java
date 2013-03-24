package com.level42.mixit.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Interest;
import com.level42.mixit.models.Session;
import com.level42.mixit.models.Speaker;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.IEntityService;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.utils.Utils;
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de gestion des Talks
 */
public class TalkService extends AbstractService implements ITalkService {
	
	@Inject
	protected IWebServices ws;
	
	@Inject
	protected IPlanningService planningService;
	
	@Inject
	protected IEntityService entityService;
	
	protected Map<Integer, Speaker> speakers;
	
	public List<Talk> getTalks() throws FunctionnalException, TechnicalException {
		try {
			List<Talk> talks = ws.getTalks();
			return talks;
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		}
	}

	public Talk getTalk(Integer id) throws FunctionnalException, TechnicalException {
		try {
			Talk talk = ws.getTalk(id);

			this.hydrateTalkInterests(talk);
			this.hydrateTalkSession(talk);
			this.hydrateTalkSpeakers(talk);
			
			return talk;
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_NotFoundException), e);
		}
	}
	
	protected void hydrateTalkSession(Talk talk) throws FunctionnalException, TechnicalException {
		// Ajoute le planning
		Session session = planningService.getPlanningSession(talk.getId());
		talk.setSession(session);
	}
	
	protected void hydrateTalkSpeakers(Talk talk) throws FunctionnalException, TechnicalException {
		List<Speaker> speakers = new ArrayList<Speaker>();
		for (Integer id : talk.getSpeakersId()) {
			Speaker spk = this.getSpeakerById(id);		
			if (spk != null) {
				Bitmap image = Utils.loadBitmap(spk.getUrlimage());
				spk.setImage(image);
				speakers.add(spk);
			}
		}
		talk.setSpeakers(speakers);
	}
	
	protected void hydrateTalkInterests(Talk talk) throws FunctionnalException, TechnicalException {

		// Ajoute les centre d'intérêts
		List<Interest> interests = new ArrayList<Interest>();
		/*for (Integer id : talk.getInterestsId()) {
			Speaker speaker = entityService.getSpeaker(id);
			speakers.add(speaker);
		}*/
		talk.setInterests(interests);
	}
	
	protected Speaker getSpeakerById(Integer id) throws FunctionnalException, TechnicalException {
		if (speakers == null) {
			speakers = new HashMap<Integer, Speaker>();
			// Ajoute les speakers
			List<Speaker> allSpeakers = entityService.getSpeakers();		
			for (Speaker spk : allSpeakers) {
				speakers.put(spk.getId(), spk);
			}
		}
		return speakers.get(id);
	}

}
