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
@Table(name = "affilates_affiliate_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AffiliateGroupMap.findAll", query = "SELECT a FROM AffiliateGroupMap a")
    , @NamedQuery(name = "AffiliateGroupMap.findById", query = "SELECT a FROM AffiliateGroupMap a WHERE a.id = :id")
    , @NamedQuery(name = "AffiliateGroupMap.findByAffiliateId", query = "SELECT a FROM AffiliateGroupMap a WHERE a.affiliateId = :affiliateId")
    , @NamedQuery(name = "AffiliateGroupMap.findByAffiliateGroupId", query = "SELECT a FROM AffiliateGroupMap a WHERE a.affiliateGroupId = :affiliateGroupId")})
public class AffiliateGroupMap implements Serializable {

    @Column(name = "affiliate_group_id")
    private Integer affiliateGroupId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "affiliate_id")
    private Integer affiliateId;

    public AffiliateGroupMap() {
    }

    public AffiliateGroupMap(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Integer affiliateId) {
        this.affiliateId = affiliateId;
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
        if (!(object instanceof AffiliateGroupMap)) {
            return false;
        }
        AffiliateGroupMap other = (AffiliateGroupMap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.affiliatemapping.entities.AffiliateGroupMap[ id=" + id + " ]";
    }

    public Integer getAffiliateGroupId() {
        return affiliateGroupId;
    }

    public void setAffiliateGroupId(Integer affiliateGroupId) {
        this.affiliateGroupId = affiliateGroupId;
    }
    
}
