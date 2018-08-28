/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.affiliatemapping.entities.controllers;

import com.mycompany.affiliatemapping.entities.Affiliates;
import com.mycompany.affiliatemapping.entities.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author patrickbanguka
 */
public class AffiliatesJpaController implements Serializable {

    public AffiliatesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Affiliates affiliates) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(affiliates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Affiliates affiliates) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            affiliates = em.merge(affiliates);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = affiliates.getId();
                if (findAffiliates(id) == null) {
                    throw new NonexistentEntityException("The affiliates with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Affiliates affiliates;
            try {
                affiliates = em.getReference(Affiliates.class, id);
                affiliates.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The affiliates with id " + id + " no longer exists.", enfe);
            }
            em.remove(affiliates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Affiliates> findAffiliatesEntities() {
        return findAffiliatesEntities(true, -1, -1);
    }

    public List<Affiliates> findAffiliatesEntities(int maxResults, int firstResult) {
        return findAffiliatesEntities(false, maxResults, firstResult);
    }

    private List<Affiliates> findAffiliatesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Affiliates.class));
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

    public Affiliates findAffiliates(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Affiliates.class, id);
        } finally {
            em.close();
        }
    }
    
     public Affiliates findAffiliates(String name) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Affiliates.findByOldAffiliateName");
            query.setParameter("oldAffiliateName", name);

            return (Affiliates) query.getSingleResult();

        } catch (javax.persistence.NoResultException e) {
                return null;
        } finally {
            em.close();
        }
    }

    public int getAffiliatesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Affiliates> rt = cq.from(Affiliates.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
