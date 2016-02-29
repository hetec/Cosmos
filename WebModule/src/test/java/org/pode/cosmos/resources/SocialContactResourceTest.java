package org.pode.cosmos.resources;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.generators.ObjectFactory;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.domain.SocialContact;
import org.pode.cosmos.domain.exceptions.NoSuchEntityForIdException;
import org.pode.cosmos.exceptions.handler.NoSuchEntityForIdMapper;
import org.pode.cosmos.exceptions.model.ExceptionInfo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by patrick on 24.02.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialContactResourceTest extends JerseyTest{

    @Mock
    private static SocialContactCrudServiceLocal service;

    @Override
    protected Application configure() {
        ResourceConfig res = new ResourceConfig();
        res.register(new TestBinder());
        res.register(SocialContactResource.class);
        res.register(NoSuchEntityForIdMapper.class);
        return res;
    }

    @Test
    public void getAllContacts_noProblems_StatusCodeOK(){
        Response response = target("/contacts").request().get(Response.class);
        assertThat(response.getStatusInfo(), equalTo(Response.Status.OK));
    }

    @Test
    public void getAllContacts_noProblems_MediaTypeAppJson(){
        Response response = target("/contacts").request().get(Response.class);
        assertThat(response.getMediaType(), equalTo(MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    public void getAllContacts_noExistingEntity_EmptyList(){
        List<SocialContact> noEntries = new ArrayList<>();
        when(service.findAll()).thenReturn(noEntries);

        Response response = target("/contacts").request().get(Response.class);

        verify(service, times(1)).findAll();
        assertThat(response.readEntity(new GenericType<List<SocialContact>>(){}).size(), equalTo(0));
    }

    @Test
    public void getAllContacts_twoEntitiesInDb_ListWithTwoEntities(){
        List<SocialContact> twoEntries = new ArrayList<>();
        twoEntries.add(new SocialContact());
        twoEntries.add(new SocialContact());
        when(service.findAll()).thenReturn(twoEntries);

        Response response = target("/contacts").request().get(Response.class);

        verify(service, times(1)).findAll();
        assertThat(response.readEntity(new GenericType<List<SocialContact>>(){}).size(), equalTo(2));
    }

    @Test
    public void getContactById_idOne_StatusCodeOK(){
        Response response = target("/contacts/1").request().get(Response.class);
        assertThat(response.getStatusInfo(), equalTo(Response.Status.OK));
    }

    @Test
    public void getContactById_idOne_MediaTypeAppJson(){
        Response response = target("/contacts/1").request().get(Response.class);
        assertThat(response.getMediaType(), equalTo(MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    public void getContactById_idOne_validSocialContactObject(){
        final String firstName = "John";
        final String lastName = "Doe";
        final Long id = 1L;

        SocialContact contact = new SocialContact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setId(id);
        when(service.findById(1L)).thenReturn(contact);

        Response response = target("/contacts/1").request().get(Response.class);

        SocialContact foundContact = response.readEntity(new GenericType<SocialContact>(){});
        assertThat(foundContact.getFirstName(), equalTo(firstName));
        assertThat(foundContact.getLastName(), equalTo(lastName));
        assertThat(foundContact.getId(), equalTo(id));
    }

    @Test
    public void getContactById_NoEntityExistsForId_ExceptionInfo(){
        final String ERR_MSG = "error";
        final long ID = 1L;
        final String ERR_INFO = "The entity with the id #1 does not exist";
        when(service.findById(1L)).thenThrow(new NoSuchEntityForIdException(ID, ERR_MSG));

        Response response = target("/contacts/1").request().get(Response.class);

        ExceptionInfo errInfo = response.readEntity(new GenericType<ExceptionInfo>(){});
        assertThat(errInfo.getExceptionClass(), equalTo(NoSuchEntityForIdException.class.getName()));
        assertThat(errInfo.getErrorMsg(), equalTo(ERR_MSG));
        assertThat(errInfo.getErrorInfo(), equalTo(ERR_INFO));
    }

    @Test
    public void saveContact_validEntityAsBody_StatusCodeCreated(){
        SocialContact sc = new SocialContact();
        sc.setFirstName("John");
        sc.setLastName("Doe");
        sc.setId(1L);

        when(service.save(sc)).thenReturn(sc);

        Entity<SocialContact> contactEntity = Entity.entity(sc, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("/contacts").request().post(contactEntity);
        assertThat(response.getStatusInfo(), equalTo(Response.Status.CREATED));
    }

    @Test
    public void saveContact_validEntityAsBody_MediaTypeAppJson(){
        SocialContact sc = new SocialContact();
        sc.setFirstName("John");
        sc.setLastName("Doe");
        sc.setId(1L);

        when(service.save(sc)).thenReturn(sc);

        Entity<SocialContact> contactEntity = Entity.entity(sc, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("/contacts").request().post(contactEntity);
        assertThat(response.getMediaType(), equalTo(MediaType.APPLICATION_JSON_TYPE));
    }

    @Test
    public void saveContact_validEntityAsBody_validSocialContactObject(){
        SocialContact sc = new SocialContact();
        sc.setFirstName("John");
        sc.setLastName("Doe");
        sc.setId(1L);

        when(service.save(sc)).thenReturn(sc);

        Entity<SocialContact> contactEntity = Entity.entity(sc, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("/contacts").request().post(contactEntity);
        MultivaluedMap<String,Object> headers = response.getHeaders();
        String location = (String)headers.getFirst("location");
        assertThat(location, equalTo("http://localhost:9998/contacts/1"));
    }

    @Test
    public void saveContact_noObjectAsBody_BadRequest(){
        Entity<SocialContact> contactEntity = Entity.entity(null, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("/contacts").request().post(contactEntity);

        assertThat(response.getStatus(), equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    public static class TestBinder extends AbstractBinder{

        @Override
        protected void configure() {
            bindFactory(ServiceProvider.class).to(SocialContactCrudServiceLocal.class);
        }
    }

    public static class ServiceProvider implements Factory<SocialContactCrudServiceLocal>{
        @Override
        public SocialContactCrudServiceLocal provide() {
            return service;
        }

        @Override
        public void dispose(SocialContactCrudServiceLocal instance) {
        }
    }
}