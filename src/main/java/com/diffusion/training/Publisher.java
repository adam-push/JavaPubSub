package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.TopicUpdate;
import com.pushtechnology.diffusion.client.features.UpdateStream;
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

        UpdateStream<JSON> stream = session.feature(TopicUpdate.class).newUpdateStreamBuilder()
                .specification(spec)
                .build("my/json/topic", JSON.class);

        // Update our topic
        for(int i = 0; i < 100; i++) {
            stream.set(Diffusion.dataTypes().json().fromJsonString("{\"counter\": " + i +"}"));
            Thread.sleep(1000);
        }

        Thread.sleep(1000);
        session.close();
    }

}
