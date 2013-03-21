package com.level42.mixtit.services.remote;

import java.util.List;

import com.google.inject.Inject;
import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.models.LightningTalk;
import com.level42.mixtit.services.ILightningTalkService;
import com.level42.mixtit.webservices.IWebServices;

/**
 * Service bouchonné pour l'appel des Webservices
 */
public class LightningTalkService implements ILightningTalkService {
	
	@Inject
	private IWebServices ws;
	
	public List<LightningTalk> getLightningTalks() throws CommunicationException {
		List<LightningTalk> talks = ws.getLightningTalks();
		return talks;
	}

	public LightningTalk getLightningTalk(Integer id) throws CommunicationException, NotFoundException {
		LightningTalk talk = ws.getLightningTalk(id);
		return talk;
	}

}
