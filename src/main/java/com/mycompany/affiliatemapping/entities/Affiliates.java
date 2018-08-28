/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.affiliatemapping.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author patrickbanguka
 */
@Entity
@Table(name = "affiliates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Affiliates.findAll", query = "SELECT a FROM Affiliates a")
    , @NamedQuery(name = "Affiliates.findByAffiliateId", query = "SELECT a FROM Affiliates a WHERE a.affiliateId = :affiliateId")
    , @NamedQuery(name = "Affiliates.findByOldAffiliateName", query = "SELECT a FROM Affiliates a WHERE a.oldAffiliateName = :oldAffiliateName")
    , @NamedQuery(name = "Affiliates.findByNewAffiliateName", query = "SELECT a FROM Affiliates a WHERE a.newAffiliateName = :newAffiliateName")
    , @NamedQuery(name = "Affiliates.findByOldSourceId", query = "SELECT a FROM Affiliates a WHERE a.oldSourceId = :oldSourceId")
    , @NamedQuery(name = "Affiliates.findByCreatedAt", query = "SELECT a FROM Affiliates a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "Affiliates.findByUpdatedAt", query = "SELECT a FROM Affiliates a WHERE a.updatedAt = :updatedAt")
    , @NamedQuery(name = "Affiliates.findById", query = "SELECT a FROM Affiliates a WHERE a.id = :id")})
public class Affiliates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "affiliate_id")
    private int affiliateId;
    @Basic(optional = false)
    @Column(name = "old_affiliate_name")
    private String oldAffiliateName;
    @Basic(optional = false)
    @Column(name = "new_affiliate_name")
    private String newAffiliateName;
    @Basic(optional = false)
    @Column(name = "old_source_id")
    private int oldSourceId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Affiliates() {
    }

    public Affiliates(Integer id) {
        this.id = id;
    }

    public Affiliates(Integer id, int affiliateId, String oldAffiliateName, String newAffiliateName, int oldSourceId) {
        this.id = id;
        this.affiliateId = affiliateId;
        this.oldAffiliateName = oldAffiliateName;
        this.newAffiliateName = newAffiliateName;
        this.oldSourceId = oldSourceId;
    }

    public int getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getOldAffiliateName() {
        return oldAffiliateName;
    }

    public void setOldAffiliateName(String oldAffiliateName) {
        this.oldAffiliateName = oldAffiliateName;
    }

    public String getNewAffiliateName() {
        return newAffiliateName;
    }

    public void setNewAffiliateName(String newAffiliateName) {
        this.newAffiliateName = newAffiliateName;
    }

    public int getOldSourceId() {
        return oldSourceId;
    }

    public void setOldSourceId(int oldSourceId) {
        this.oldSourceId = oldSourceId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Affiliates)) {
            return false;
        }
        Affiliates other = (Affiliates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.affiliatemapping.entities.Affiliates[ id=" + id + " ]";
    }
    
}
