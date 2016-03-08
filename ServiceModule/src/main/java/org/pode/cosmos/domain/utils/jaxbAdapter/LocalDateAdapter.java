package org.pode.cosmos.domain.utils.jaxbAdapter;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by patrick on 08.03.16.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String dateInput) throws Exception {
        LocalDate date;
         try {
             date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_DATE);
         } catch (DateTimeParseException noIsoDate){
             try {
                 date = LocalDate.parse(dateInput, DateTimeFormatter.BASIC_ISO_DATE);
             } catch (DateTimeParseException noBasicIsoDate){
                 try {
                     date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_DATE_TIME);
                 } catch (DateTimeParseException noIsoDateTime){
                     try {
                         DateTimeFormatter slashFromatter = DateTimeFormatter.ofPattern("yyyy'/'MM'/'dd");
                         date = LocalDate.parse(dateInput, slashFromatter);
                     } catch (DateTimeParseException noSlashSeparatedDate){
                         throw new IllegalStateException();
                     }
                 }
             }
         }

        return date;
    }
    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return DateTimeFormatter.ISO_DATE.format(localDate);
    }
}
