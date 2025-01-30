package com.diffusion.training;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.session.Session;

public class  Publisher {

    public static void main(String[] args) throws Exception {
        final Session session = Diffusion.sessions()
                .principal("admin")
                .password("password")
                .open("ws://localhost:8090");

        System.out.println("SessionID = " + session.getSessionId());

        session.close();
    }

}
