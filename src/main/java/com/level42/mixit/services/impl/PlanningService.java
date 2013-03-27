package com.level42.mixit.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.level42.mixit.R;
import com.level42.mixit.dao.IPlanningDAO;
import com.level42.mixit.exceptions.DataAccessException;
import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.models.Planning;
import com.level42.mixit.models.Session;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.services.ITalkService;

/**
 * Service de gestion du planning
 */
public class PlanningService extends AbstractService implements IPlanningService {

	@Inject
	private IPlanningDAO planningDAO;
	
	@Inject
	private ITalkService talkService;
	
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

	public List<GroupedTalks> getTalksForPlanning() throws FunctionnalException,
			TechnicalException {
		// Récupère les sessions
		List<Talk> talks = talkService.getTalks();
		
		// Ajout des sessions
		List<Talk> plannedTalks = new ArrayList<Talk>();
		for (Talk talk : talks) {
			Session session = this.getPlanningSession(talk.getId());
			talk.setSession(session);
			if(talk.getDateSession() != null) {
				plannedTalks.add(talk);
			}
		}		
		
		// Tri
		Collections.sort(plannedTalks);
		
		List<GroupedTalks> groupedTalks = new ArrayList<GroupedTalks>();
		
		Date lastDate = null;
		GroupedTalks gTalks = null;
		Integer id = 0;
		List<Talk> lTalks = new ArrayList<Talk>();
		for(Talk talk : plannedTalks) {
			if (lastDate == null || talk.getDateSession().compareTo(lastDate) != 0) {
				
				if (gTalks != null && lTalks != null) {
					gTalks.setTalks(lTalks);
					groupedTalks.add(gTalks);
					lTalks = new ArrayList<Talk>();
				}
				
				gTalks = new GroupedTalks();
				gTalks.setId(id);
				gTalks.setDate(talk.getDateSession());
				
				lastDate = talk.getDateSession();
				id++;
			}
			lTalks.add(talk);
		}
		
		return groupedTalks;
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
