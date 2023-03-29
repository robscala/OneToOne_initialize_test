package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SignedNoteTest
{
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );

        // Populate records in the database:
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Creating database records");
        Person person = new Person("Henry");
        SignedNote signedNote = new SignedNote("a note", person);
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.persist(signedNote);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void signed_note_test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Object originalCount = entityManager.createNativeQuery("select count(*) from contact_info").getSingleResult();
        SignedNote signedNote = entityManager.find(SignedNote.class, 1L);
        entityManager.getTransaction().begin();
//        Person newPerson = new Person();
//        newPerson.setFirstName("me");
//        entityManager.persist(newPerson);
        entityManager.getTransaction().commit();
        Object finalCount = entityManager.createNativeQuery("select count(*) from contact_info").getSingleResult();
        System.out.println("Original count: " + originalCount + "  Final count: " + finalCount);
        assertEquals("Same number of contact_info records", originalCount, finalCount);
        entityManager.close();
    }
}