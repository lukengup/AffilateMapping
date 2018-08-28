/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.affiliatemapping;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mycompany.affiliatemapping.entities.AffiliateGroup;
import com.mycompany.affiliatemapping.entities.AffiliateGroupMap;
import com.mycompany.affiliatemapping.entities.Affiliates;
import com.mycompany.affiliatemapping.entities.controllers.AffiliateGroupJpaController;
import com.mycompany.affiliatemapping.entities.controllers.AffiliateGroupMapJpaController;
import com.mycompany.affiliatemapping.entities.controllers.AffiliatesJpaController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author patrickbanguka
 */
public class Mapper {

    static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("SNAPSHOTPU");
    static String LOGIN_URL = "https://live.seoforge.xyz/api/v1/login/";
    static String AFFILIATE_GROUP = "https://live.seoforge.xyz/api/v1/affiliates/groups/";
    
    public static void main(String[] args) throws UnirestException, IOException, Exception {

        //System.out.println(access_token);
        ///
        String csvFile = "/Users/patrickbanguka/Downloads/affiliate_superaffiliate_mapping.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
         String access_token = get_access_token();
        try {

            int i = 0;
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if (++i < 352) {
                    continue;
                }
               
               
                
                
                

                // use comma as separator
                String[] affiliate_data = line.split(cvsSplitBy);

                 System.out.println(affiliate_data[0]+" "+affiliate_data[1]+" "+affiliate_data[2]);
                AffiliateGroupJpaController affiliateGroupJpaController = new AffiliateGroupJpaController(EMF);
                AffiliatesJpaController affilatesController = new AffiliatesJpaController(EMF);
                AffiliateGroup affiliate_group = affiliateGroupJpaController.findAffiliateGroup(affiliate_data[2]);

                if (affiliate_group == null) {
                    affiliate_group = new AffiliateGroup();

                    Map details = new HashMap<String, HashMap<String, String>>();
                    // details.put("clients","");
                    // details.put("duping_rules", new HashMap<String, String>());
                    JSONArray details_obj = new JSONArray();

                    affiliate_group.setAffiliateGroupName(affiliate_data[2]);
                    affiliate_group.setDetails(details_obj.toString());
                    affiliateGroupJpaController.create(affiliate_group);
                    

                    Map m = new HashMap<Object, Object>();
                    m.put("affiliate_group_name", affiliate_data[2]);
                    m.put("details", new JSONArray());
                    //System.out.println( );

                    HttpResponse<JsonNode> response = Unirest.post(AFFILIATE_GROUP)
                            .header("accept", "application/json")
                            .header("content-type", "application/json")
                            .header("Authorization", "Bearer " + access_token)
                            .body(new JSONObject(m).toString())
                            .asJson();

                    if (response.getStatus() != 200) {
                        throw new Exception(response.getStatusText() + " " + response.getBody().toString());
                    }
                }

                //Mapping 
                affiliate_group = affiliateGroupJpaController.findAffiliateGroup(affiliate_data[2]);
                Affiliates affiliate = affilatesController.findAffiliates(affiliate_data[1]);
                if (affiliate == null) {
                    // Create affililiate
                    affiliate = new Affiliates();
                    affiliate.setAffiliateId(affiliate_group.getId());
                    affiliate.setNewAffiliateName(affiliate_data[1]);
                    affiliate.setOldAffiliateName(affiliate_data[1]);
                    
                   
                    affiliate.setOldSourceId(Integer.parseInt(affiliate_data[0]));

                    affilatesController.create(affiliate);

                }

                AffiliateGroupMapJpaController affiliateGroupMapJpaController = new AffiliateGroupMapJpaController(EMF);
                affiliate = affilatesController.findAffiliates(affiliate_data[1]);
                AffiliateGroupMap affiliateGroupMap = new AffiliateGroupMap();

                affiliateGroupMap.setAffiliateGroupId(affiliate_group.getId());
                affiliateGroupMap.setAffiliateId(affiliate.getId());
                affiliateGroupMapJpaController.create(affiliateGroupMap);

                int remote_afiliate_group_id = get_live_affiliate_group_id(affiliate_data[2], access_token);
                if (remote_afiliate_group_id != 0) {

                    Map details = new HashMap<String, HashMap<String, String>>();

                    JSONArray details_obj = new JSONArray();

                    Map m = new HashMap<Object, Object>();
                    m.put("affiliate_group_id", remote_afiliate_group_id);
                    m.put("affiliate_name", affiliate_data[1]);
                    m.put("details", new JSONArray());

                    HttpResponse<JsonNode> response = Unirest.post("https://live.seoforge.xyz/api/v1/affiliates")
                            .header("accept", "application/json")
                            .header("Authorization", "Bearer " + access_token)
                            .header("content-type", "application/json")
                            .body(new JSONObject(m).toString())
                            .asJson();
                    if (response.getStatus() != 200) {
                        //throw new Exception(response.getBody().toString());
                        continue;
                    }

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static int get_live_affiliate_group_id(String affiliate_group_name, String access_token) throws UnirestException {
        HttpResponse<String> response = Unirest.get(AFFILIATE_GROUP)
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + access_token)
                .asString();
        JSONObject object = new JSONObject(response.getBody());

        JSONArray array = object.getJSONArray("payload");
        for (int i = 0; i < array.length(); i++) {
            JSONObject res = array.getJSONObject(i);
            // System.out.println(res.toString());System.exit(0);
            if (res.getString("affiliate_group_name").equals(affiliate_group_name)) {
                return res.getInt("affiliate_group_id");
            }
        }
        return 0;
    }

    private static String get_access_token() throws UnirestException {
        HttpResponse<String> response = Unirest.post(LOGIN_URL)
                .header("accept", "application/json")
                .field("email", "kerren+3waydigital_api@entrostat.com")
                .field("password", "0dbfc6ba4b5d4d741afca5a727263f5d9ef527b387577e598c934383a34b19a5")
                .asString();

        return new JSONObject(response.getBody()).getJSONObject("payload").getString("access_token");
    }

}
