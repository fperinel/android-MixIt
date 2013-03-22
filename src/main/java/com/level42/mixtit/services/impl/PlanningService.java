package com.level42.mixtit.services.impl;

import com.google.inject.Inject;
import com.level42.mixtit.dao.IPlanningDAO;
import com.level42.mixtit.models.Planning;
import com.level42.mixtit.models.Session;
import com.level42.mixtit.services.IPlanningService;

/**
 * Service de gestion du planning
 */
public class PlanningService implements IPlanningService {

	@Inject
	private IPlanningDAO planningDAO;
	
	public Session getPlanningSession(Integer sessionId) {
		
		Planning planning = planningDAO.getPlanning();
		
		for (Session session : planning.getSessions()) {
			if (session.getSessionId() == sessionId) {
				return session;
			}
		}
		return null;
	}
}
