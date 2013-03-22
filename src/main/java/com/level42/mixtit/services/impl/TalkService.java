package com.level42.mixtit.services.impl;

import java.util.List;

import com.google.inject.Inject;
import com.level42.mixtit.R;
import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.FunctionnalException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.exceptions.TechnicalException;
import com.level42.mixtit.models.Session;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.IPlanningService;
import com.level42.mixtit.services.ITalkService;
import com.level42.mixtit.webservices.IWebServices;

/**
 * Service de gestion des Talks
 */
public class TalkService extends AbstractService implements ITalkService {
	
	@Inject
	private IWebServices ws;
	
	@Inject
	private IPlanningService planningService;
	
	public List<Talk> getTalks() throws FunctionnalException, TechnicalException {
		try {
			List<Talk> talks = ws.getTalks();
			
			for (Talk talk : talks) {
				Session session = planningService.getPlanningSession(talk.getId());
				talk.setSession(session);
			}
			
			return talks;			
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		}
	}

	public Talk getTalk(Integer id) throws FunctionnalException, TechnicalException {
		try {
			Talk talk = ws.getTalk(id);
			
			Session session = planningService.getPlanningSession(talk.getId());
			talk.setSession(session);
			
			return talk;
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_NotFoundException), e);
		}
	}

}
