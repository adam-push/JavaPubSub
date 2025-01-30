package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;

public class Subscriber {

    public static void main(String[] args) throws Exception {
        final Session session = Diffusion.sessions()
                .open("ws://localhost:8080");

        System.out.println("SessionID = " + session.getSessionId());

        // Set up stream for dispatching topic updates
        Topics topics = session.feature(Topics.class);
        topics.addStream("?my//", String.class, new Topics.ValueStream.Default<String>() {
            @Override
            public void onValue(String topicPath, TopicSpecification specification, String oldValue, String newValue) {
                System.out.println("Received update on topic " + topicPath + " : " + newValue);
            }
        });

        // Subscribe to topics
        topics.subscribe("?my//");

        Thread.sleep(10_000);
        session.close();
    }

}
