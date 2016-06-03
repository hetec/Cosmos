package org.pode.cosmos.bs.services;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.bs.interfaces.TraitCrudServiceLocal;
import org.pode.cosmos.bs.serviceUtils.ErrorType;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.cdi.qualifiers.DefaultLocale;
import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.entities.Traits;
import org.pode.cosmos.domain.exceptions.ApiException;
import org.pode.cosmos.domain.exceptions.NoSuchEntityForIdException;
import org.pode.cosmos.domain.exceptions.errors.ApiSocialContactError;
import org.pode.cosmos.domain.exceptions.errors.ApiTraitError;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created by patrick on 16.03.16.
 */
@Stateless
public class TraitCrudService implements TraitCrudServiceLocal{

    private EntityManager em;
    private SocialContactCrudServiceLocal contactService;
    private Locale locale;


    public TraitCrudService() {}

    @Inject
    public TraitCrudService(
            @CosmosCtx EntityManager em,
            SocialContactCrudServiceLocal contactService,
            @DefaultLocale Locale locale){
        this.em = em;
        this.contactService = contactService;
        this.locale = locale;
    }

    @Override
    public Traits findById(Long traitId, Long contactId) {
        Traits foundTrait = em.find(Traits.class, traitId);
        if(!Objects.nonNull(foundTrait)){
            throw new ApiException(ApiTraitError.T10001, locale);
        }
        final Long traitContact = foundTrait.getContact().getId();
        if(!traitContact.equals(contactId)){
            throw new ApiException(ApiTraitError.T10002, locale);
        }
        return foundTrait;
    }

    @Override
    public Set<Traits> findAll(Long contactId) {
        return findAndCheckSocialContact(contactId).getTraits();
    }

    @Override
    public Traits save(Traits trait, Long contactId) {
        SocialContact sc = findAndCheckSocialContact(contactId);
        sc.addTrait(trait);
        contactService.save(sc);
        return trait;
    }

    @Override
    public Traits update(Traits trait, Long contactId) {
        //get trait for id to get uuid
        Traits persistedTrait = this.findById(trait.getId(), contactId);
        //set uuid of the persisted trait
        trait.setUuid(persistedTrait.getUuid());
        SocialContact sc = findAndCheckSocialContact(contactId);
        //remove old trait and add new one
        sc.removeTrait(trait);
        sc.addTrait(trait);
        //update contact and save the trait via cascading rules
        contactService.update(sc);
        return trait;
    }

    @Override
    public Traits delete(Long traitId, Long contactId) {
        SocialContact sc = findAndCheckSocialContact(contactId);
        //get the trait with the given id from the contact's traits set
        Optional<Traits> trait = sc.getTraits().stream()
                .filter(t -> traitId.equals(t.getId()))
                .findFirst();
        //Check if the trait is not null
        if(!Objects.nonNull(trait.get())){
            throw new ApiException(ApiTraitError.T10001, locale);
        }
        //Remove the trait form the list and through cascading from the db
        sc.removeTrait(trait.get());
        contactService.update(sc);
        return trait.get();
    }

    private SocialContact findAndCheckSocialContact(Long contactId){
        SocialContact sc = em.find(SocialContact.class, contactId);
        if(!Objects.nonNull(sc)){
            throw new ApiException(ApiSocialContactError.SC10001, locale);
        }
        return sc;
    }
}
