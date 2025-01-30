package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;
import com.pushtechnology.diffusion.datatype.json.JSON;

public class Subscriber {

    public static void main(String[] args) throws Exception {
        final Session session = Diffusion.sessions()
                .open("ws://localhost:8080");

        System.out.println("SessionID = " + session.getSessionId());

        // Set up stream for dispatching topic updates
        Topics topics = session.feature(Topics.class);
        topics.addStream("?my//", JSON.class, new Topics.ValueStream.Default<JSON>() {
            @Override
            public void onValue(String topicPath, TopicSpecification specification, JSON oldValue, JSON newValue) {
                System.out.println("Received update on topic " + topicPath + " : " + newValue.toJsonString());
            }
        });

        // Subscribe to topics
        topics.subscribe("?my//");

        Thread.sleep(10_000);
        session.close();
    }

}
