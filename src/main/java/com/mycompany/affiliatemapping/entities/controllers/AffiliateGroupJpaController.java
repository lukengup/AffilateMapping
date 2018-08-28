/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.affiliatemapping.entities.controllers;

import com.mycompany.affiliatemapping.entities.AffiliateGroup;
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
public class AffiliateGroupJpaController implements Serializable {

    public AffiliateGroupJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AffiliateGroup affiliateGroup) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(affiliateGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AffiliateGroup affiliateGroup) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            affiliateGroup = em.merge(affiliateGroup);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = affiliateGroup.getId();
                if (findAffiliateGroup(id) == null) {
                    throw new NonexistentEntityException("The affiliateGroup with id " + id + " no longer exists.");
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
            AffiliateGroup affiliateGroup;
            try {
                affiliateGroup = em.getReference(AffiliateGroup.class, id);
                affiliateGroup.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The affiliateGroup with id " + id + " no longer exists.", enfe);
            }
            em.remove(affiliateGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AffiliateGroup> findAffiliateGroupEntities() {
        return findAffiliateGroupEntities(true, -1, -1);
    }

    public List<AffiliateGroup> findAffiliateGroupEntities(int maxResults, int firstResult) {
        return findAffiliateGroupEntities(false, maxResults, firstResult);
    }

    private List<AffiliateGroup> findAffiliateGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AffiliateGroup.class));
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

    public AffiliateGroup findAffiliateGroup(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AffiliateGroup.class, id);
        } finally {
            em.close();
        }
    }

    public AffiliateGroup findAffiliateGroup(String group_name) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("AffiliateGroup.findByAffiliateGroupName");
            query.setParameter("affiliateGroupName", group_name);

            return (AffiliateGroup) query.getSingleResult();

        } catch (javax.persistence.NoResultException e) {
                return null;
        } finally {
            em.close();
        }
    }

    public int getAffiliateGroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AffiliateGroup> rt = cq.from(AffiliateGroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
