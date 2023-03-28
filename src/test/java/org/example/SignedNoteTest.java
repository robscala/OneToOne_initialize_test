package org.example;

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
        entityManagerFactory = Persistence.createEntityManagerFactory( "refresh_test" );
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    @Test
    public void signed_note_test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Object originalCount = entityManager.createNativeQuery("select count(*) from contact_info").getSingleResult();
        SignedNote signedNote = entityManager.find(SignedNote.class, 365138);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        Object finalCount = entityManager.createNativeQuery("select count(*) from contact_info").getSingleResult();
        assertEquals("Same number of contact_info records", originalCount, finalCount);
        entityManager.close();
    }
}