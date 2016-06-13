package org.pode.cosmos.exceptionHandling.mapper;

import org.pode.cosmos.domain.exceptions.ConstraintViolationEntry;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.*;

/**
 * Maps ConstraintViolationException to appropriate response. Tje response contains a list
 * with the detected violations. Each violation entry comprises the information about the
 * concerned fields name, the invalid value and the message of the violation.
 */
@Provider
@Produces("application/json")
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException>{

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
        List<ConstraintViolationEntry> violationList = new ArrayList<>();
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
            String name = "";
            Object invalidValue = violation.getInvalidValue();
            if(Objects.nonNull(invalidValue)){
                invalidValueString = invalidValue.toString();
            }
            if(Objects.nonNull(field)){
                name = field.getName();
            }
            ConstraintViolationEntry entry = new ConstraintViolationEntry(
                    name,
                    invalidValueString,
                    violation.getMessage()
                    );
            violationList.add(entry);
        }
        return violationList;
    }

}
