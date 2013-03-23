package com.level42.mixtit.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.level42.mixtit.dao.IPlanningDAO;
import com.level42.mixtit.dao.impl.PlanningJsonDAO;
import com.level42.mixtit.services.IEntityService;
import com.level42.mixtit.services.ILightningTalkService;
import com.level42.mixtit.services.IPlanningService;
import com.level42.mixtit.services.ITalkService;
import com.level42.mixtit.services.impl.EntityService;
import com.level42.mixtit.services.impl.LightningTalkService;
import com.level42.mixtit.services.impl.PlanningService;
import com.level42.mixtit.services.impl.TalkService;
import com.level42.mixtit.webservices.IWebServices;
import com.level42.mixtit.webservices.impl.WebServices;

/**
 * Classe de gestion des injections
 * - Mode bouchonné
 */
public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ITalkService.class).to(TalkService.class).in(Singleton.class);
		bind(ILightningTalkService.class).to(LightningTalkService.class).in(Singleton.class);
		bind(IPlanningService.class).to(PlanningService.class).in(Singleton.class);
		bind(IEntityService.class).to(EntityService.class).in(Singleton.class);
		
		bind(IWebServices.class).to(WebServices.class).in(Singleton.class);
		
		bind(IPlanningDAO.class).to(PlanningJsonDAO.class).in(Singleton.class);
	}

}
