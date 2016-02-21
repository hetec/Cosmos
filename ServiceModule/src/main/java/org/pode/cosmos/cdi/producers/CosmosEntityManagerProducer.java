package org.pode.cosmos.cdi.producers;

import org.pode.cosmos.cdi.qualifiers.CosmosCtx;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by patrick on 21.02.16.
 */
@Stateless
public class CosmosEntityManagerProducer {

    @PersistenceContext(unitName = "cosmos")
    private EntityManager entityManager;

    @Produces @CosmosCtx
    public EntityManager getEm(){
        return entityManager;
    }

}
