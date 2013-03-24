package com.level42.mixit.services.impl;

import java.util.Hashtable;
import java.util.Map;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.dao.IPlanningDAO;
import com.level42.mixit.exceptions.DataAccessException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.Planning;
import com.level42.mixit.models.Session;
import com.level42.mixit.services.IPlanningService;

/**
 * Service de gestion du planning
 */
public class PlanningService extends AbstractService implements IPlanningService {

	@Inject
	private IPlanningDAO planningDAO;
	
	private Map<Integer, Session> sessions;
	
	public Session getPlanningSession(Integer sessionId) throws FunctionnalException, TechnicalException {
		try {
			Planning planning = planningDAO.getPlanning();
			if (planning != null) {
				this.indexPlanning(planning);
				return sessions.get(sessionId);
			} else {
				return null;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new TechnicalException(getText(R.string.exception_message_DataAccessException), e);
		}
	}
	
	/**
	 * Index les sessions par identifiant de talk
	 * @param planning
	 */
	protected void indexPlanning(Planning planning) {
		if (sessions == null) {
			sessions = new Hashtable<Integer, Session>();
			
			for (Session session : planning.getSessions()) {
				sessions.put(session.getId(), session);
			}
		}
	}
}
