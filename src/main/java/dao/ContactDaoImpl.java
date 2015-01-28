package dao;

import java.util.ArrayList;
import java.util.List;

import model.Contact;

/**
 * Implementation du dao de contact
 * 
 * @author duboism
 * 
 */
public class ContactDaoImpl implements IContactDao {

    /** Liste des contacts (base de données en mémoire) */
    private List<Contact> contacts = new ArrayList<Contact>();

    @Override
    public Contact rechercherContact(String nom) {
        for (Contact contact : contacts) {
            if (contact.getNom().equalsIgnoreCase(nom)) {
                return contact;
            }
        }
        return null;

    }

    @Override
    public void creerContact(Contact contact) {
        contacts.add(contact);
    }

    @Override
    public Contact updateContact(Contact contact, String nom) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Contact> recupererListe() {
        return contacts;

    }

}
