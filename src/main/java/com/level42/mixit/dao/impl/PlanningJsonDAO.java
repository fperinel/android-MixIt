package com.level42.mixit.dao.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.xml.sax.helpers.DefaultHandler;

import roboguice.inject.InjectResource;

import com.level42.mixit.R;
import com.level42.mixit.dao.IPlanningDAO;
import com.level42.mixit.exceptions.DataAccessException;
import com.level42.mixit.models.Planning;
import com.level42.mixit.models.Session;

/**
 * Interface de manipulation des Talks de MixIT
 */
public class PlanningJsonDAO extends DefaultHandler implements IPlanningDAO {

    /**
     * Source de données des sessions (salle/heure => id du talk)
     * Ces informations ne sont pas fournies par le WS (pour le moment)
     */
    @InjectResource(R.string.planning_json)
    private String planningJson;

    /**
     * Objet planning
     */
    private Planning planning;

    /*
     * (non-Javadoc)
     * @see com.level42.mixit.dao.IPlanningDAO#getPlanning()
     */
    public Planning getPlanning() throws DataAccessException {
	if (planning == null) {
	    try {
		ObjectMapper mapper = new ObjectMapper();
		List<Session> sessions = mapper.readValue(planningJson,
			new TypeReference<List<Session>>() {
			});
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
