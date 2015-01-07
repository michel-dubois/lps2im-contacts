package service;

import org.apache.commons.lang3.StringUtils;

import model.Contact;
import dao.ContactDaoImpl;
import dao.IContactDao;
import exception.ContactInexistantException;
import exceptions.ContactException;

public class ContactService {

	private IContactDao contactDao = new ContactDaoImpl();
	
	/**
	 * Méthode permettant d'ajouter un contact 
	 * @param nom
	 * @throws ContactException si le nom passé est déjà présent en base de données
	 */
	public void creerContact(String nom) throws ContactException{
		int taille=StringUtils.trimToEmpty(nom).length();
		
//		if(nom == null || nom.trim().length() < 3 || nom.trim().length()>40){
		if  (taille < 3 || taille>40){
		   throw new IllegalArgumentException("Le nom doit être compris entre 3 et 40 caractères");
		}
		
		if(contactDao.rechercherContact(nom) != null){
			throw new ContactException("Un contact avec le nom "+nom+" existe déjà en base de données");
		}
		
		Contact contact = new Contact();
		contact.setNom(nom);
		contactDao.creerContact(contact);
	}

	public Contact modifierNomContact(String ancienNom, String nouveauNom) throws ContactInexistantException, ContactException{
		
		Contact contactBase = contactDao.rechercherContact(ancienNom);
		if(contactBase == null){
			throw new ContactInexistantException();
		}
		
		if(contactDao.rechercherContact(nouveauNom) != null){
			throw new ContactException("Un contact avec le nom "+nouveauNom+" existe déjà en base de données");
		}
		
		return contactDao.updateContact(contactBase, nouveauNom);
	}
	
	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	
	
	
}
