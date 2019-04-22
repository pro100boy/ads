//package com.loopme.ads;
//
//import com.loopme.ads.dao.campaign.CampaignInsert;
//import com.loopme.ads.dao.campaign.CampaignSelect;
//import com.loopme.ads.domain.Campaign;
//import com.loopme.ads.dto.CampaignDto;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//import static org.junit.Assert.assertNull;
//
//public class AdsApplicationTests extends TestConfig {
//
//    @Autowired
//    private CampaignSelect select;
//
//    @Test
//    public void should_get_by_id() {
//        Campaign one = select.getOne(3);
//        System.out.println(toJson(one));
//    }
//
//    @Test
//    public void should_get_null() {
//        Campaign one = select.getOne(30);
//        assertNull(one);
//    }
//
//    @Test
//    public void should_get_with_params() {
//        List<CampaignDto> campaigns = select.get(1, null, null, null, 0, 10);
//        //assertEquals(3, campaigns.size());
//
//        System.out.println(toJson(campaigns));
//    }
//
////    @Test
////    public void should_get_empty() {
////        List<Campaign> campaigns = select.get(10, 3);
////        assertTrue(campaigns.isEmpty());
////    }
//    @Autowired
//    CampaignInsert insert;
//    @Test
//    public void insert(){
//        Number new_platform = insert.insertMessage("new name", 0);
//        System.out.println(new_platform);
//    }
//}
