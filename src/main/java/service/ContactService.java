package service;

import java.util.ArrayList;
import java.util.Calendar;

import model.Contact;

import org.apache.commons.lang3.StringUtils;

import dao.ContactDaoImpl;
import dao.IContactDao;
import exception.ContactInexistantException;
import exceptions.ContactException;

/**
 * Service de creation de contacts
 * 
 * @author duboism
 * 
 */
public class ContactService {

    private static final int MAX_SIZE = 40;
    private static final int MIN_SIZE = 3;
    private IContactDao contactDao = new ContactDaoImpl();

    /**
     * Méthode permettant d'ajouter un contact
     * 
     * @param nom
     * @throws ContactException si le nom passé est déjà présent en base de données
     */
    public void creerContact(String nom) throws ContactException {
        int taille = StringUtils.trimToEmpty(nom).length();

        // if(nom == null || nom.trim().length() < 3 || nom.trim().length()>40){
        if (taille < MIN_SIZE || taille > MAX_SIZE) {
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
     * 
     * @param ancienNom
     * @param nouveauNom
     * @return
     * @throws ContactInexistantException
     * @throws ContactException
     */
    public Contact modifierNomContact(String ancienNom, String nouveauNom) throws ContactInexistantException,
            ContactException {

        Contact contactBase = contactDao.rechercherContact(ancienNom);
        if (contactBase == null) {
            throw new ContactInexistantException("Le contact n'a pas été trouvé");
        }

        if (contactDao.rechercherContact(nouveauNom) != null) {
            throw new ContactException("Un contact avec le nom " + nouveauNom + " existe déjà en base de données");
        }

        return contactDao.updateContact(contactBase, nouveauNom);
    }

    public void setContactDao(IContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void methodebug() {
        Contact contact = null;
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 20) {
            contact = new Contact();
        }
        contact.setNom("nom");
        contact.getNom().replace("o", "u");
        if (contact.getNom().equals(new ArrayList<String>())) {
            System.out.println("Coucou");
        }
        while (true) {
            System.out.println("Coucou");
        }
    }
}
