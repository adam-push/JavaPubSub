package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.TopicUpdate;
import com.pushtechnology.diffusion.client.features.control.topics.TopicControl;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicType;

public class  Publisher {

    public static void main(String[] args) throws Exception {
        final Session session = Diffusion.sessions()
                .principal("admin")
                .password("password")
                .open("ws://localhost:8090");

        System.out.println("SessionID = " + session.getSessionId());

        // Add topic with "String" type
        session.feature(TopicControl.class)
                .addTopic("my/first/topic", TopicType.STRING)
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
                    .set("my/first/topic", String.class, "Hello, world : " + i);
            Thread.sleep(1000);
        }

        Thread.sleep(1000);
        session.close();
    }

}
