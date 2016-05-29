package org.pode.cosmos.exceptions.handler;

import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.exceptions.model.ConstraintViolationEntry;
import org.pode.cosmos.exceptions.model.ExceptionInfo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.*;

/**
 * Created by patrick on 28.05.16.
 */
@Provider
public class ValidationConstraintMapper implements ExceptionMapper<ConstraintViolationException>{

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        List<ConstraintViolationEntry> violationEntryList = consolidateConstraintViolations(violations);
        GenericEntity<List<ConstraintViolationEntry>> entity =
                new GenericEntity<List<ConstraintViolationEntry>>(violationEntryList){};

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(entity)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    private List<ConstraintViolationEntry> consolidateConstraintViolations(Set<ConstraintViolation<?>> violations){
        List<ConstraintViolationEntry> violationList = new ArrayList();
        for (ConstraintViolation<?> violation : violations){
            Iterator<Path.Node> iterator = violation.getPropertyPath().iterator();
            Path.Node field = null;
            while(true){
                if(iterator.hasNext()){
                    field = iterator.next();
                }else {
                    break;
                }
            }
            String invalidValueString = "";
            Object invalidValue = violation.getInvalidValue();
            if(Objects.nonNull(invalidValue)){
                invalidValueString = invalidValue.toString();
            }
            ConstraintViolationEntry entry = new ConstraintViolationEntry(
                    field.getName(),
                    invalidValueString,
                    violation.getMessage()
                    );
            violationList.add(entry);
        }
        return violationList;
    }

}
