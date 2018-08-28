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
import javax.persistence.Lob;
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
@Table(name = "affiliate_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AffiliateGroup.findAll", query = "SELECT a FROM AffiliateGroup a")
    , @NamedQuery(name = "AffiliateGroup.findById", query = "SELECT a FROM AffiliateGroup a WHERE a.id = :id")
    , @NamedQuery(name = "AffiliateGroup.findByNuseoAffiliateGroupId", query = "SELECT a FROM AffiliateGroup a WHERE a.nuseoAffiliateGroupId = :nuseoAffiliateGroupId")
    , @NamedQuery(name = "AffiliateGroup.findByAffiliateGroupName", query = "SELECT a FROM AffiliateGroup a WHERE a.affiliateGroupName = :affiliateGroupName")
    , @NamedQuery(name = "AffiliateGroup.findByCreatedAt", query = "SELECT a FROM AffiliateGroup a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "AffiliateGroup.findByUpdatedAt", query = "SELECT a FROM AffiliateGroup a WHERE a.updatedAt = :updatedAt")})
public class AffiliateGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nuseo_affiliate_group_id")
    private Integer nuseoAffiliateGroupId;
    @Basic(optional = false)
    @Column(name = "affiliate_group_name")
    private String affiliateGroupName;
    @Basic(optional = false)
    @Lob
    @Column(name = "details")
    private String details;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public AffiliateGroup() {
    }

    public AffiliateGroup(Integer id) {
        this.id = id;
    }

    public AffiliateGroup(Integer id, String affiliateGroupName, String details) {
        this.id = id;
        this.affiliateGroupName = affiliateGroupName;
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNuseoAffiliateGroupId() {
        return nuseoAffiliateGroupId;
    }

    public void setNuseoAffiliateGroupId(Integer nuseoAffiliateGroupId) {
        this.nuseoAffiliateGroupId = nuseoAffiliateGroupId;
    }

    public String getAffiliateGroupName() {
        return affiliateGroupName;
    }

    public void setAffiliateGroupName(String affiliateGroupName) {
        this.affiliateGroupName = affiliateGroupName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AffiliateGroup)) {
            return false;
        }
        AffiliateGroup other = (AffiliateGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.affiliatemapping.entities.AffiliateGroup[ id=" + id + " ]";
    }

 
    
}
