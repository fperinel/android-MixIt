package com.level42.mixtit.services.remote;

import java.util.List;

import com.google.inject.Inject;
import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.ITalkService;
import com.level42.mixtit.webservices.IWebServices;

/**
 * Service bouchonné pour l'appel des Webservices
 */
public class TalkService implements ITalkService {
	
	@Inject
	private IWebServices ws;
	
	public List<Talk> getTalks() throws CommunicationException {
		List<Talk> talks = ws.getTalks();
		return talks;
	}

	public Talk getTalk(Integer id) throws CommunicationException, NotFoundException {
		Talk talk = ws.getTalk(id);
		return talk;
	}

}
