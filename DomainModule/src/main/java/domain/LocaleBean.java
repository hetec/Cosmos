package domain;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Locale;

/**
 * Created by patrick on 03.02.16.
 */
@Stateless
public class LocaleBean implements LocaleBeanLocal{

    @Override
    public String getLocaleString() {
        return Locale.getDefault().toString();
    }
}
