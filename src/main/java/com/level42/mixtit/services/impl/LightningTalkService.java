package com.level42.mixtit.services.impl;

import java.util.List;

import com.google.inject.Inject;
import com.level42.mixtit.R;
import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.FunctionnalException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.exceptions.TechnicalException;
import com.level42.mixtit.models.LightningTalk;
import com.level42.mixtit.services.ILightningTalkService;
import com.level42.mixtit.webservices.IWebServices;

/**
 * Service bouchonné pour l'appel des Webservices
 */
public class LightningTalkService extends TalkService implements ILightningTalkService {
	
	@Inject
	private IWebServices ws;
	
	public List<LightningTalk> getLightningTalks() throws FunctionnalException, TechnicalException {
		try {
			return ws.getLightningTalks();
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		}
	}

	public LightningTalk getLightningTalk(Integer id) throws FunctionnalException, TechnicalException {
		try {	
			LightningTalk lTalk = ws.getLightningTalk(id);
			this.hydrateTalkInterests(lTalk);
			this.hydrateTalkSpeakers(lTalk);			
			return lTalk;
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_NotFoundException), e);
		}
	}

}
