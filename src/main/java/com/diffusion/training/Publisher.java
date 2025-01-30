package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.TopicUpdate;
import com.pushtechnology.diffusion.client.features.control.topics.TopicControl;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;
import com.pushtechnology.diffusion.client.topics.details.TopicType;
import com.pushtechnology.diffusion.datatype.json.JSON;

public class  Publisher {

    public static void main(String[] args) throws Exception {
        final Session session = Diffusion.sessions()
                .principal("admin")
                .password("password")
                .open("ws://localhost:8090");

        System.out.println("SessionID = " + session.getSessionId());

        TopicSpecification spec = Diffusion.newTopicSpecification(TopicType.JSON);

        // Add topic with "String" type
        session.feature(TopicControl.class)
                .addTopic("my/json/topic", spec)
                .whenComplete((result, err) -> {
                    if(err != null) {
                        System.err.println(err.getMessage());
                    }
                    else {
                        System.out.println("Topic added successfully: " + result);
                    }
                });

        // Update our topic
        for(int i = 0; i < 100; i++) {
            session.feature(TopicUpdate.class)
                    .set("my/json/topic", JSON.class, Diffusion.dataTypes().json().fromJsonString("{\"counter\": " + i +"}"));
            Thread.sleep(1000);
        }

        Thread.sleep(1000);
        session.close();
    }

}
