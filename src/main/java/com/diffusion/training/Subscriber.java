package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.session.Session;

public class Subscriber {

    public static void main(String[] args) throws Exception {
        final Session session = Diffusion.sessions()
                .open("ws://localhost:8080");

        System.out.println("SessionID = " + session.getSessionId());

        Thread.sleep(1000);
        session.close();
    }

}
