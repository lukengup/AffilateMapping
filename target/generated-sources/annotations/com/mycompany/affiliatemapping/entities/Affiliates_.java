package com.mycompany.affiliatemapping.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-08-28T01:06:50")
@StaticMetamodel(Affiliates.class)
public class Affiliates_ { 

    public static volatile SingularAttribute<Affiliates, Date> createdAt;
    public static volatile SingularAttribute<Affiliates, String> newAffiliateName;
    public static volatile SingularAttribute<Affiliates, String> oldAffiliateName;
    public static volatile SingularAttribute<Affiliates, Integer> id;
    public static volatile SingularAttribute<Affiliates, Integer> oldSourceId;
    public static volatile SingularAttribute<Affiliates, Integer> affiliateId;
    public static volatile SingularAttribute<Affiliates, Date> updatedAt;

}