package dao;

import java.util.List;

import model.Contact;

/**
 * Interface du DAO permettant de gerer les contacts
 * 
 * @author duboism
 * 
 */
public interface IContactDao {

    /**
     * Methode qui permet de rechercher un contact a partir de son nom
     * 
     * @param nom nom du contact recherche
     * @return contact correspondant, si aucun ne correspond retourne <code>null</code>
     */
    Contact rechercherContact(String nom);

    /**
     * Methode permettant d'inserer en base de donnees le contact
     * 
     * @param contact contact a ajouter en base
     */
    void creerContact(Contact contact);

    /**
     * Mise a jour du nom d'un contact
     * 
     * @param contact contact a mettre a jour
     * @param nom nouveau nom du contact
     * @return contact a jour avec le nouveau nom
     */
    Contact updateContact(Contact contact, String nom);

    List<Contact> recupererListe();
}
