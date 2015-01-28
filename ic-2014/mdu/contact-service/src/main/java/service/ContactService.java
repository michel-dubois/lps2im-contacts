package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Contact;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import dao.ContactDaoImpl;
import dao.IContactDao;
import exception.ContactException;
import exception.ContactInexistantException;

/**
 * Service de gestion des contacts
 * 
 * @author duboism
 * 
 */
public class ContactService {

    private static final int TAILLE_MAX_NOM = 40;
    private static final int TAILLE_MIN_NOM = 3;

    private IContactDao contactDao = new ContactDaoImpl();

    /**
     * Méthode permettant d'ajouter un contact
     * 
     * @param nom nom du contact a créer
     * @throws ContactException si le nom passé est déjà présent en base de données
     */
    public void creerContact(String nom) throws ContactException {
        int taille = StringUtils.trimToEmpty(nom).length();
        if (taille < TAILLE_MIN_NOM || taille > TAILLE_MAX_NOM) {
            throw new IllegalArgumentException("Le nom doit être compris entre 3 et 40 caractères");
        }

        if (contactDao.rechercherContact(nom) != null) {
            throw new ContactException("Un contact avec le nom " + nom + " existe déjà en base de données");
        }
        Contact contact = new Contact();
        contact.setNom(nom);
        contactDao.creerContact(contact);
    }

    /**
     * Méthode qui permet de modifier un contact
     * 
     * @param ancienNom ancien nom du contact
     * @param nouveauNom nouveau nom
     * @return contact modifié
     * @throws ContactInexistantException exception si le contact n'existe plus en base
     * @throws ContactException exception si le nouveau nom est déjà utilisé
     */
    public Contact modifierNomContact(String ancienNom, String nouveauNom) throws ContactInexistantException,
            ContactException {

        Contact contactBase = contactDao.rechercherContact(ancienNom);
        if (contactBase == null) {
            throw new ContactInexistantException("Un contact avec le nom " + ancienNom
                    + " n'existe pas en base de données");
        }

        if (contactDao.rechercherContact(nouveauNom) != null) {
            throw new ContactException("Un contact avec le nom " + nouveauNom + " existe déjà en base de données");
        }

        return contactDao.updateContact(contactBase, nouveauNom);
    }

    public void setContactDao(IContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public Collection<String> recupererListeNomContact() {

        List<Contact> listeContact = contactDao.recupererListe();
        if (CollectionUtils.isNotEmpty(listeContact)) {
            // Transformation de mon objet contact pour récupérer le nom
            return Collections2.transform(listeContact, new Function<Contact, String>() {

                public String apply(Contact contact) {
                    return contact.getNom();
                }

            });
        } else {
            return new ArrayList<String>();
        }

    }
}
