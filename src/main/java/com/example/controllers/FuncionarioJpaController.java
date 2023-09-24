/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.example.models.Roles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import com.example.controllers.exceptions.NonexistentEntityException;
import com.example.models.Funcionario;

/**
 *
 * @author kevin
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) {
        if (funcionario.getRoles() == null) {
            funcionario.setRoles(new ArrayList<Roles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Roles> attachedRoles = new ArrayList<Roles>();
            for (Roles rolesRolesToAttach : funcionario.getRoles()) {
                rolesRolesToAttach = em.getReference(rolesRolesToAttach.getClass(), rolesRolesToAttach.getId());
                attachedRoles.add(rolesRolesToAttach);
            }
            funcionario.setRoles(attachedRoles);
            em.persist(funcionario);
            for (Roles rolesRoles : funcionario.getRoles()) {
                Funcionario oldFuncionarioOfRolesRoles = rolesRoles.getFuncionario();
                rolesRoles.setFuncionario(funcionario);
                rolesRoles = em.merge(rolesRoles);
                if (oldFuncionarioOfRolesRoles != null) {
                    oldFuncionarioOfRolesRoles.getRoles().remove(rolesRoles);
                    oldFuncionarioOfRolesRoles = em.merge(oldFuncionarioOfRolesRoles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getId());
            List<Roles> rolesOld = persistentFuncionario.getRoles();
            List<Roles> rolesNew = funcionario.getRoles();
            List<Roles> attachedRolesNew = new ArrayList<Roles>();
            for (Roles rolesNewRolesToAttach : rolesNew) {
                rolesNewRolesToAttach = em.getReference(rolesNewRolesToAttach.getClass(), rolesNewRolesToAttach.getId());
                attachedRolesNew.add(rolesNewRolesToAttach);
            }
            rolesNew = attachedRolesNew;
            funcionario.setRoles(rolesNew);
            funcionario = em.merge(funcionario);
            for (Roles rolesOldRoles : rolesOld) {
                if (!rolesNew.contains(rolesOldRoles)) {
                    rolesOldRoles.setFuncionario(null);
                    rolesOldRoles = em.merge(rolesOldRoles);
                }
            }
            for (Roles rolesNewRoles : rolesNew) {
                if (!rolesOld.contains(rolesNewRoles)) {
                    Funcionario oldFuncionarioOfRolesNewRoles = rolesNewRoles.getFuncionario();
                    rolesNewRoles.setFuncionario(funcionario);
                    rolesNewRoles = em.merge(rolesNewRoles);
                    if (oldFuncionarioOfRolesNewRoles != null && !oldFuncionarioOfRolesNewRoles.equals(funcionario)) {
                        oldFuncionarioOfRolesNewRoles.getRoles().remove(rolesNewRoles);
                        oldFuncionarioOfRolesNewRoles = em.merge(oldFuncionarioOfRolesNewRoles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = funcionario.getId();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<Roles> roles = funcionario.getRoles();
            for (Roles rolesRoles : roles) {
                rolesRoles.setFuncionario(null);
                rolesRoles = em.merge(rolesRoles);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
