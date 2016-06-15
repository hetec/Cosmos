package org.pode.cosmos.domain.exceptions.jaxbAdapters;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by patrick on 01.06.16.
 */
public class ResponseStatusAdapter extends XmlAdapter<String, Response.Status>{

    @Override
    public Response.Status unmarshal(String status) throws Exception {
        return Response.Status.valueOf(status);
    }

    @Override
    public String marshal(Response.Status status) throws Exception {
        return status.name();
    }
}
