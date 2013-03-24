package com.level42.mixit.services.impl;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.exceptions.CommunicationException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.NotFoundException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Interest;
import com.level42.mixit.services.IInterestService;
import com.level42.mixit.webservices.IWebServices;

/**
 * Service de gestion des Talks
 */
public class InterestService extends AbstractService implements IInterestService {
	
	@Inject
	protected IWebServices ws;
	
	protected Map<Integer, Interest> interests;

	public List<Interest> getInterests() throws FunctionnalException,
			TechnicalException {
		try {
			List<Interest> interests = ws.getInterests();
			return interests;
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		}
	}

	public Interest getInterest(Integer id) throws FunctionnalException,
			TechnicalException {
		try {
			Interest interest = ws.getInterest(id);			
			return interest;
		} catch (CommunicationException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_CommunicationException), e);
		} catch (NotFoundException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_NotFoundException), e);
		}
	}
}
