package org.pode.cosmos.resources;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.bs.interfaces.TraitCrudServiceLocal;
import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.exceptions.NoSuchEntityForIdException;
import org.pode.cosmos.exceptions.handler.GenericExceptionMapper;
import org.pode.cosmos.exceptions.handler.NoSuchEntityForIdMapper;
import org.pode.cosmos.exceptions.handler.NotSupportedMediaTypeMapper;
import org.pode.cosmos.exceptions.model.ExceptionInfo;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Created by patrick on 24.02.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SocialContactResourceTest extends JerseyTest{

    @Mock
    private static SocialContactCrudServiceLocal service;

    @Mock
    private static TraitCrudServiceLocal traitService;

    @Override
    protected Application configure() {
        ResourceConfig res = new ResourceConfig();
        res.register(new TestBinder());
        res.register(SocialContactResource.class);
        res.register(NoSuchEntityForIdMapper.class);
        res.register(GenericExceptionMapper.class);
        res.register(NotSupportedMediaTypeMapper.class);
        return res;
    }

    @Test
    public void getAllContacts_noProblems_StatusCodeOK(){
        Response response = target("/contacts").request().get(Response.class);

        verifyStatusCode(response, Response.Status.OK);
    }

    @Test
    public void getAllContacts_noProblems_MediaTypeAppJson(){
        Response response = target("/contacts").request().get(Response.class);

        verifyMediatypeIsAppJson(response);
    }

    @Test
    public void getAllContacts_wrongMediaType_NotSupportedMediaType(){
        ExceptionInfo ei = new ExceptionInfo(
                NotSupportedException.class.getName(),
                "HTTP 415 Unsupported Media Type",
                "Supported media types: application/json"
        );
        Response response = target("/contacts")
                .request().header("Content-Type", MediaType.APPLICATION_XML)
                .get(Response.class);

        verifyExceptionInfo(response,ei);
    }

    @Test
    public void getAllContacts_noExistingEntity_EmptyList(){
        List<SocialContact> noEntries = new ArrayList<>();
        when(service.findAll()).thenReturn(noEntries);

        Response response = target("/contacts").request().get(Response.class);

        assertThat(response.readEntity(new GenericType<List<SocialContact>>(){}).size(), equalTo(0));
    }

    @Test
    public void getAllContacts_twoEntitiesInDb_ListWithTwoEntities(){
        List<SocialContact> twoEntries = new ArrayList<>();
        twoEntries.add(new SocialContact());
        twoEntries.add(new SocialContact());
        when(service.findAll()).thenReturn(twoEntries);

        Response response = target("/contacts").request().get(Response.class);

        assertThat(response.readEntity(new GenericType<List<SocialContact>>(){}).size(), equalTo(2));
    }

    @Test
    public void getContactById_idOne_StatusCodeOK(){
        Response response = target("/contacts/1").request().get(Response.class);
        verifyStatusCode(response, Response.Status.OK);
    }

    @Test
    public void getContactById_idOne_MediaTypeAppJson(){
        Response response = target("/contacts/1").request().get(Response.class);
        verifyMediatypeIsAppJson(response);
    }

    @Test
    public void getContactById_idOne_validSocialContactObject(){
        SocialContact contact = createTestContact();
        SocialContact matcher = createTestContact();
        when(service.findById(1L)).thenReturn(contact);

        Response response = target("/contacts/1").request().get(Response.class);

        verifySocialContactProperties(response, matcher);
    }

    @Test
    public void getContactById_NoEntityExistsForId_ExceptionInfo(){
        final ExceptionInfo ei = new ExceptionInfo(
                NoSuchEntityForIdException.class.getName(),
                "error",
                "The entity with the id #1 does not exist");
        final long ID = 1L;
        when(service.findById(1L)).thenThrow(new NoSuchEntityForIdException(ID, ei.getErrorMsg()));

        Response response = target("/contacts/1").request().get(Response.class);
        verifyExceptionInfo(response, ei);
    }

    @Test
    public void saveContact_validEntityAsBody_StatusCodeCreated(){
        SocialContact sc = createTestContact();

        when(service.save(any(SocialContact.class))).thenReturn(sc);

        Response response = target("/contacts").request().post(wrapIntoEntity(sc));
        verifyStatusCode(response, Response.Status.CREATED);
    }

    @Test
    public void saveContact_validEntityAsBody_MediaTypeAppJson() {
        SocialContact sc = createTestContact();

        when(service.save(any(SocialContact.class))).thenReturn(sc);

        Response response = target("/contacts").request().post(wrapIntoEntity(sc));
        verifyMediatypeIsAppJson(response);
    }

    @Test
    public void saveContact_validEntityAsBody_validSocialContactObject(){
        SocialContact sc = createTestContact();
        when(service.save(any(SocialContact.class))).thenReturn(sc);

        Response response = target("/contacts").request().post(wrapIntoEntity(sc));

        verifyHeaderValue(response, "location", "http://localhost:9998/contacts/1");
    }

    @Test
    public void updateContact_validEntityAsBody_StatusCodeOK(){
        SocialContact sc = createTestContact();
        when(service.update(any(SocialContact.class))).thenReturn(sc);

        Response response = target("/contacts/1").request().put(wrapIntoEntity(sc));

        verifyStatusCode(response, Response.Status.OK);
    }

    @Test
    public void updateContact_validEntityAsBody_MediaTypeAppJson(){
        SocialContact sc = createTestContact();
        when(service.update(any(SocialContact.class))).thenReturn(sc);

        Response response = target("/contacts/1").request().put(wrapIntoEntity(sc));

        verifyMediatypeIsAppJson(response);
    }

    @Test
    public void updateContact_validEntityAsBody_ValidSocialContact(){
        SocialContact sc = createTestContact();
        SocialContact matcher = createTestContact();
        when(service.update(any(SocialContact.class))).thenReturn(sc);

        Response response = target("/contacts/1").request().put(wrapIntoEntity(sc));

        verifySocialContactProperties(response, matcher);
    }

    @Test
    public void updateContact_NoEntityForId_NoSuchEntityForIdException(){
        ExceptionInfo ei = new ExceptionInfo(
                NoSuchEntityForIdException.class.getName(),
                "error",
                "The entity with the id #1 does not exist"
        );
        when(service.update(any(SocialContact.class))).thenThrow(
                new NoSuchEntityForIdException(1L, ei.getErrorMsg()));

        Response response = target("/contacts/1").request().put(wrapIntoEntity(createTestContact()));

        verifyExceptionInfo(response, ei);
    }

    @Test
    public void deleteContact_noProblems_StatusCodeOK(){
        Response response = target("/contacts/1").request().delete();

        verifyStatusCode(response, Response.Status.OK);
    }

    @Test
    public void deleteContact_noProblems_MediaTypeAppJson(){
        Response response = target("/contacts/1").request().delete();

        verifyMediatypeIsAppJson(response);
    }

    @Test
    public void deleteContact_noProblems_deletedSocialContact(){
        SocialContact sc = createTestContact();
        SocialContact matcher = createTestContact();
        when(service.delete(1L)).thenReturn(sc);

        Response response = target("/contacts/1").request().delete();

        verifySocialContactProperties(response, matcher);
    }

    @Test
    public void deleteContact_noSuchEntity_NoSuchEntityForIdException(){
        ExceptionInfo ei = new ExceptionInfo(
                NoSuchEntityForIdException.class.getName(),
                "error",
                "The entity with the id #1 does not exist"
        );
        when(service.delete(1L)).thenThrow(new NoSuchEntityForIdException(1L, ei.getErrorMsg()));

        Response response = target("/contacts/1").request().delete();

        verifyExceptionInfo(response, ei);
    }

    //Helper
    private static SocialContact createTestContact(){
        LocalDate d = LocalDate.of(2015, 12, 12);

        SocialContact sc = new SocialContact(
                "John",
                "Doe",
                d);
        sc.setId(1L);
        return sc;
    }

    private static SocialContact createTestContact(final Long id, String first, String last, LocalDate d){
        SocialContact sc = new SocialContact(first, last, d);
        sc.setId(id);
        return sc;
    }

    private static void verifyMediatypeIsAppJson(Response response){
        assertThat(response.getMediaType(), equalTo(MediaType.APPLICATION_JSON_TYPE));
    }

    private static void verifyStatusCode(Response response, Response.Status code){
        assertThat(response.getStatusInfo(), equalTo(code));
    }

    private static void verifySocialContactProperties(Response response, SocialContact matcher){
        SocialContact actual = response.readEntity(new GenericType<SocialContact>(){});
        assertThat(actual.getFirstName(), equalTo(matcher.getFirstName()));
        assertThat(actual.getLastName(), equalTo(matcher.getLastName()));
        assertThat(actual.getId(), equalTo(matcher.getId()));
    }

    private static void verifyExceptionInfo(Response response,
                                            ExceptionInfo matcher){
        ExceptionInfo errInfo = response.readEntity(new GenericType<ExceptionInfo>(){});
        assertThat(errInfo.getExceptionClass(), equalTo(matcher.getExceptionClass()));
        assertThat(errInfo.getErrorMsg(), equalTo(matcher.getErrorMsg()));
        assertThat(errInfo.getErrorInfo(), equalTo(matcher.getErrorInfo()));
    }

    private static Entity<SocialContact> wrapIntoEntity(SocialContact sc){
        return Entity.entity(sc, MediaType.APPLICATION_JSON_TYPE);
    }

    private static void verifyHeaderValue(Response response, String header, String expectedValue){
        MultivaluedMap<String,Object> headers = response.getHeaders();
        String location = (String)headers.getFirst(header);
        assertThat(location, equalTo(expectedValue));
    }

    public static class TestBinder extends AbstractBinder{

        @Override
        protected void configure() {
            bindFactory(ServiceProvider.class).to(SocialContactCrudServiceLocal.class);
            bindFactory(TraitServiceProvider.class).to(TraitCrudServiceLocal.class);
        }
    }

    //Provide social contact service
    public static class ServiceProvider implements Factory<SocialContactCrudServiceLocal>{
        @Override
        public SocialContactCrudServiceLocal provide() {
            return service;
        }

        @Override
        public void dispose(SocialContactCrudServiceLocal instance) {
        }
    }

    //provide trait service
    public static class TraitServiceProvider implements Factory<TraitCrudServiceLocal>{
        @Override
        public TraitCrudServiceLocal provide() {
            return traitService;
        }

        @Override
        public void dispose(TraitCrudServiceLocal instance) {
        }
    }
}