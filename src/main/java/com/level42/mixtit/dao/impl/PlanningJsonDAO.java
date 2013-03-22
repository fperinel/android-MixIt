package com.level42.mixtit.dao.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.xml.sax.helpers.DefaultHandler;

import roboguice.inject.InjectResource;

import com.level42.mixtit.R;
import com.level42.mixtit.dao.IPlanningDAO;
import com.level42.mixtit.exceptions.DataAccessException;
import com.level42.mixtit.models.Planning;
import com.level42.mixtit.models.Session;

/**
 * Interface de manipulation des Talks de MixIT
 */
public class PlanningJsonDAO extends DefaultHandler implements IPlanningDAO {

	@InjectResource(R.string.planning_json)
	private String planningJson;
	
	private Planning planning;

	public Planning getPlanning() throws DataAccessException {
		if (planning == null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				List<Session> sessions = mapper.readValue(planningJson, new TypeReference<List<Session>>() {});
				planning = new Planning();
				planning.setSessions(sessions);
			} catch (JsonParseException e) {
				e.printStackTrace();
				throw new DataAccessException(e);
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw new DataAccessException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new DataAccessException(e);
			}
		}
		return planning;
	}
}
