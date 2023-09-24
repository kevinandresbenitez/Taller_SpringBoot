/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.example.controllers.exceptions.NonexistentEntityException;
import com.example.models.Enfermero;

/**
 *
 * @author kevin
 */
public class EnfermeroJpaController implements Serializable {

    public EnfermeroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enfermero enfermero) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(enfermero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enfermero enfermero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            enfermero = em.merge(enfermero);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = enfermero.getId();
                if (findEnfermero(id) == null) {
                    throw new NonexistentEntityException("The enfermero with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermero enfermero;
            try {
                enfermero = em.getReference(Enfermero.class, id);
                enfermero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enfermero with id " + id + " no longer exists.", enfe);
            }
            em.remove(enfermero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enfermero> findEnfermeroEntities() {
        return findEnfermeroEntities(true, -1, -1);
    }

    public List<Enfermero> findEnfermeroEntities(int maxResults, int firstResult) {
        return findEnfermeroEntities(false, maxResults, firstResult);
    }

    private List<Enfermero> findEnfermeroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enfermero.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Enfermero findEnfermero(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enfermero.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnfermeroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enfermero> rt = cq.from(Enfermero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
