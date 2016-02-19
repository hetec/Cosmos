package service;

import domain.LocaleBeanLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;

/**
 * Created by patrick on 03.02.16.
 */
@Stateless
public class DateBean implements DateBeanLocal{

    @EJB
    LocaleBeanLocal localeBean;

    @Override
    public String getCurrentDateString(){
        return new Date().toString();
    }
}
