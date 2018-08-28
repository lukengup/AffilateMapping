/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.affiliatemapping.entities.controllers;

import com.mycompany.affiliatemapping.entities.AffiliateGroupMap;
import com.mycompany.affiliatemapping.entities.controllers.exceptions.NonexistentEntityException;
import com.mycompany.affiliatemapping.entities.controllers.exceptions.PreexistingEntityException;
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
public class AffiliateGroupMapJpaController implements Serializable {

    public AffiliateGroupMapJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AffiliateGroupMap affiliateGroupMap) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(affiliateGroupMap);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAffiliateGroupMap(affiliateGroupMap.getId()) != null) {
                throw new PreexistingEntityException("AffiliateGroupMap " + affiliateGroupMap + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AffiliateGroupMap affiliateGroupMap) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            affiliateGroupMap = em.merge(affiliateGroupMap);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = affiliateGroupMap.getId();
                if (findAffiliateGroupMap(id) == null) {
                    throw new NonexistentEntityException("The affiliateGroupMap with id " + id + " no longer exists.");
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
            AffiliateGroupMap affiliateGroupMap;
            try {
                affiliateGroupMap = em.getReference(AffiliateGroupMap.class, id);
                affiliateGroupMap.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The affiliateGroupMap with id " + id + " no longer exists.", enfe);
            }
            em.remove(affiliateGroupMap);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AffiliateGroupMap> findAffiliateGroupMapEntities() {
        return findAffiliateGroupMapEntities(true, -1, -1);
    }

    public List<AffiliateGroupMap> findAffiliateGroupMapEntities(int maxResults, int firstResult) {
        return findAffiliateGroupMapEntities(false, maxResults, firstResult);
    }

    private List<AffiliateGroupMap> findAffiliateGroupMapEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AffiliateGroupMap.class));
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

    public AffiliateGroupMap findAffiliateGroupMap(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AffiliateGroupMap.class, id);
        } finally {
            em.close();
        }
    }

    public int getAffiliateGroupMapCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AffiliateGroupMap> rt = cq.from(AffiliateGroupMap.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
