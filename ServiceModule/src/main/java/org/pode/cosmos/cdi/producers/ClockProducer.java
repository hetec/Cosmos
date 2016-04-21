package org.pode.cosmos.cdi.producers;

import org.pode.cosmos.cdi.qualifiers.SystemZonedClock;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.time.Clock;
import java.time.ZoneId;

/**
 * Created by patrick on 20.04.16.
 */
@RequestScoped
public class ClockProducer {

    @Produces
    @SystemZonedClock
    public Clock getSystemUtcClock(){
        return Clock.system(ZoneId.of("CET"));
    }

}
