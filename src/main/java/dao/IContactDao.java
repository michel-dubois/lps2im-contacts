package dao;

import model.Contact;

/**
 * Interface du DAO permettant de gérer les contacts
 * @author duboism
 *
 */
public interface IContactDao {

	/**
	 * Méthode qui permet de rechercher un contact a partir de son nom 
	 * @param nom nom du contact recherché 
	 * @return contact correspondant, si aucun ne correspond retourne <code>null</code>
	 */
	Contact rechercherContact(String nom);
	
	/**
	 * Méthode permettant d'insérer en base de données le contact
	 * @param contact contact a ajouter en base 
	 */
	void creerContact(Contact contact);
	
	/**
	 * Mise à jour du nom d'un contact 
	 * @param contact contact a mettre à jour 
	 * @param nom nouveau nom du contact 
	 * @return contact a jour avec le nouveau nom
	 */
	Contact updateContact(Contact contact, String nom); 
}
