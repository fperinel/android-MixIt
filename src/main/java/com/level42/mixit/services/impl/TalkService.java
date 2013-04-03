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
import com.level42.mixit.services.IInterestService;
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
	
	@Inject
	protected IInterestService interestService;
	
	protected Map<Integer, Speaker> speakers;
	
	protected Map<Integer, Interest> interests;
	
	public List<Talk> getTalks() throws FunctionnalException, TechnicalException {
		try {
			return ws.getTalks();
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		}
	}

	public Talk getTalk(Integer id) throws FunctionnalException, TechnicalException {
		try {
			Talk talk = ws.getTalk(id);

			// Dans la nouvelle version des APIP, les objects sont retournés
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
		if (talk.getSpeakersId() != null) {
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
	}
	
	protected void hydrateTalkInterests(Talk talk) throws FunctionnalException, TechnicalException {
		if (talk.getInterestsId() != null) {
			List<Interest> interests = new ArrayList<Interest>();
			for (Integer id : talk.getInterestsId()) {
				Interest interest = this.getInterestById(id);		
				if (interest != null) {
					interests.add(interest);
				}
			}
			talk.setInterests(interests);
		}
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
	
	protected Interest getInterestById(Integer id) throws FunctionnalException, TechnicalException {
		if (interests == null) {
			interests = new HashMap<Integer, Interest>();
			// Ajoute les speakers
			List<Interest> allInterests = interestService.getInterests();		
			for (Interest interest : allInterests) {
				interests.put(interest.getId(), interest);
			}
		}
		return interests.get(id);
	}

}
