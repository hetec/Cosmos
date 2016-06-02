package org.pode.cosmos.appconfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import java.util.Locale;

/**
 * Created by patrick on 02.06.16.
 */
@ApplicationScoped
public class LocaleProducer {

    @Produces @DefaultLocale
    public Locale defaultLocale(){
        return new Locale("de", "DE");
    }

}
