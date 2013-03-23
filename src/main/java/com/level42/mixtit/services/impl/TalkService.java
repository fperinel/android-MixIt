package com.level42.mixtit.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.google.inject.Inject;
import com.level42.mixtit.R;
import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.FunctionnalException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.exceptions.TechnicalException;
import com.level42.mixtit.models.Interest;
import com.level42.mixtit.models.Session;
import com.level42.mixtit.models.Speaker;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.IEntityService;
import com.level42.mixtit.services.IPlanningService;
import com.level42.mixtit.services.ITalkService;
import com.level42.mixtit.utils.Utils;
import com.level42.mixtit.webservices.IWebServices;

/**
 * Service de gestion des Talks
 */
public class TalkService extends AbstractService implements ITalkService {
	
	@Inject
	private IWebServices ws;
	
	@Inject
	private IPlanningService planningService;
	
	@Inject
	private IEntityService entityService;
	
	private Map<Integer, Speaker> speakers;
	
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
