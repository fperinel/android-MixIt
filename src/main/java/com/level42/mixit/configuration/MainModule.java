package com.level42.mixit.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.level42.mixit.dao.IPlanningDAO;
import com.level42.mixit.dao.impl.PlanningJsonDAO;
import com.level42.mixit.services.IEntityService;
import com.level42.mixit.services.IInterestService;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.services.impl.EntityService;
import com.level42.mixit.services.impl.InterestService;
import com.level42.mixit.services.impl.LightningTalkService;
import com.level42.mixit.services.impl.PlanningService;
import com.level42.mixit.services.impl.TalkService;
import com.level42.mixit.webservices.IWebServices;
import com.level42.mixit.webservices.impl.WebServices;

/**
 * Classe de gestion des injections
 */
public class MainModule extends AbstractModule {

    /*
     * (non-Javadoc)
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        bind(ITalkService.class).to(TalkService.class).in(Singleton.class);
        bind(ILightningTalkService.class).to(LightningTalkService.class).in(
                Singleton.class);
        bind(IPlanningService.class).to(PlanningService.class).in(
                Singleton.class);
        bind(IEntityService.class).to(EntityService.class).in(Singleton.class);
        bind(IInterestService.class).to(InterestService.class).in(
                Singleton.class);

        bind(IWebServices.class).to(WebServices.class).in(Singleton.class);

        bind(IPlanningDAO.class).to(PlanningJsonDAO.class).in(Singleton.class);
    }

}
