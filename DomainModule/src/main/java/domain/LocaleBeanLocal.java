package domain;

import javax.ejb.Local;

/**
 * Created by patrick on 03.02.16.
 */
@Local
public interface LocaleBeanLocal {

    public String getLocaleString();

}
