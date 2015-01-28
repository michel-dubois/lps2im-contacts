package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import model.Contact;

import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.IContactDao;
import exception.ContactException;
import exception.ContactInexistantException;

/**
 * Classe de test du service de contact
 * 
 * @author duboism
 * 
 */
public class ContactServiceTest {

    /** Service a tester */
    private ContactService service = new ContactService();
    private IMocksControl control = EasyMock.createControl();
    private IContactDao contactDaoMock = control.createMock(IContactDao.class);

    @Before
    public void beforeTest() {
        service.setContactDao(contactDaoMock);
    }

    /** Vérifie que l'on lève une exception si le nom passé est null */
    @Test(expected = IllegalArgumentException.class)
    public void testNomNull() throws ContactException {
        service.creerContact(null);
    }

    /** Vérifie que l'on lève une exception si le nom passé est vide */
    @Test(expected = IllegalArgumentException.class)
    public void testNomVide() throws ContactException {
        service.creerContact(" ");
    }

    /** Vérifie que l'on lève une exception si le nom passé est trop court (<3 car) */
    @Test(expected = IllegalArgumentException.class)
    public void testNomTropCourt() throws ContactException {
        service.creerContact("to");
    }

    /** Vérifie que l'on lève une exception si le nom passé est trop long (>40 car) */
    @Test(expected = IllegalArgumentException.class)
    public void testNomTropLong() throws ContactException {
        service.creerContact(UUID.randomUUID() + " " + UUID.randomUUID());
    }

    /** Vérifie que l'on lève une exception si le nom passé existe déjà en base de données */
    @Test(expected = ContactException.class)
    public void testCasDoublon() throws ContactException {
        control.reset();

        // Appels attendus
        Contact contactBase = new Contact();
        String nomContactAAjouter = "Doublon";
        contactBase.setNom(nomContactAAjouter);

        EasyMock.expect(contactDaoMock.rechercherContact(nomContactAAjouter)).andReturn(contactBase);

        control.replay();
        service.creerContact(nomContactAAjouter);
    }

    /** Vérifie le cas passant */
    @Test
    public void testCasNormal() throws ContactException {
        // Remise à 0 des mocks
        control.reset();
        String nomContactAAjouter = "Arnaud";
        // Appels attendus
        EasyMock.expect(contactDaoMock.rechercherContact(nomContactAAjouter)).andReturn(null);

        Capture<Contact> captureContact = EasyMock.newCapture(CaptureType.FIRST);
        contactDaoMock.creerContact(EasyMock.capture(captureContact));

        // Fin de la phase d'enregistrement
        control.replay();

        // Appel du service
        service.creerContact(nomContactAAjouter);

        control.verify();
        // Vérification que l'ensemble des appels ont bien été effectués
        Contact contactCapture = captureContact.getValue();
        org.junit.Assert.assertEquals(nomContactAAjouter, contactCapture.getNom());
    }

    /**
     * Tentative de modification d'un contact qui n'existe pas (ou plus)
     * 
     * @throws ContactInexistantException
     * @throws ContactException
     */
    @Test(expected = ContactInexistantException.class)
    public void testModifierContactInexistant() throws ContactInexistantException, ContactException {

        control.reset();
        String ancienNom = "ancienNom";
        // Appels attendus
        EasyMock.expect(contactDaoMock.rechercherContact(ancienNom)).andReturn(null);

        control.replay();

        service.modifierNomContact(ancienNom, "nouveauNom");
    }

    /**
     * Tentative de modification d'un contact qui existe avec le nom d'un contact déjà existant
     * 
     * @throws ContactInexistantException
     * @throws ContactException
     */
    @Test(expected = ContactException.class)
    public void testModifierContactExistantMaisNouveauNomDejaPris() throws ContactInexistantException, ContactException {

        control.reset();
        String ancienNom = "ancienNom";
        // Appels attendus
        Contact contactEnBase = new Contact();
        contactEnBase.setNom(ancienNom);
        EasyMock.expect(contactDaoMock.rechercherContact(ancienNom)).andReturn(contactEnBase);
        String nouveauNom = "nouveauNom";
        EasyMock.expect(contactDaoMock.rechercherContact(nouveauNom)).andReturn(new Contact());

        control.replay();

        service.modifierNomContact(ancienNom, nouveauNom);
    }

    /**
     * Tentative de modification d'un contact qui existe avec le nom d'un contact déjà existant
     * 
     * @throws ContactInexistantException
     * @throws ContactException
     */
    @Test
    public void testModifierContactExistantAvecNouveauOk() throws ContactInexistantException, ContactException {

        control.reset();
        String ancienNom = "ancienNom";
        // Appels attendus
        Contact contactEnBase = new Contact();
        contactEnBase.setNom(ancienNom);
        EasyMock.expect(contactDaoMock.rechercherContact(ancienNom)).andReturn(contactEnBase);
        String nouveauNom = "nouveauNom";
        EasyMock.expect(contactDaoMock.rechercherContact(nouveauNom)).andReturn(null);

        Contact contactMisAJour = new Contact();
        contactMisAJour.setNom(nouveauNom);
        EasyMock.expect(contactDaoMock.updateContact(contactEnBase, nouveauNom)).andReturn(contactMisAJour);
        control.replay();

        Contact resultat = service.modifierNomContact(ancienNom, nouveauNom);
        org.junit.Assert.assertEquals(nouveauNom, resultat.getNom());

    }

    @Test
    public void testRecupererNomContactListeNull() {
        control.reset();

        EasyMock.expect(contactDaoMock.recupererListe()).andReturn(null);

        control.replay();
        Collection<String> result = service.recupererListeNomContact();

        org.junit.Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testRecupererNomContactListeVide() {
        control.reset();

        EasyMock.expect(contactDaoMock.recupererListe()).andReturn(new ArrayList<Contact>());

        control.replay();
        Collection<String> result = service.recupererListeNomContact();

        org.junit.Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testRecupererNomContactListeRemplie() {
        control.reset();
        Contact contact = new Contact();
        contact.setNom("Test");

        EasyMock.expect(contactDaoMock.recupererListe()).andReturn(Collections.singletonList(contact));

        control.replay();
        Collection<String> result = service.recupererListeNomContact();

        org.junit.Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains("Test"));
    }

}
