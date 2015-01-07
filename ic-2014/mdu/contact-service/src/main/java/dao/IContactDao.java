package dao;

import model.Contact;

/**
 * Interface du DAO permettant de g�rer les contacts
 * @author duboism
 *
 */
public interface IContactDao {

	/**
	 * M�thode qui permet de rechercher un contact a partir de son nom 
	 * @param nom nom du contact recherch� 
	 * @return contact correspondant, si aucun ne correspond retourne <code>null</code>
	 */
	Contact rechercherContact(String nom);
	
	/**
	 * M�thode permettant d'ins�rer en base de donn�es le contact
	 * @param contact contact a ajouter en base 
	 */
	void creerContact(Contact contact);
	
	/**
	 * Mise � jour du nom d'un contact 
	 * @param contact contact a mettre � jour 
	 * @param nom nouveau nom du contact 
	 * @return contact a jour avec le nouveau nom
	 */
	Contact updateContact(Contact contact, String nom); 
}
