package com.example.helpers;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WiremockHelper {
    private WireMockServer wireMockServer = null;

    public WiremockHelper(int port) {
        wireMockServer = new WireMockServer(wireMockConfig().port(port));
    }

    public void dumpInteractions() {
        System.err.println(" >>>> WIREMOCK <<<< ");
        wireMockServer.getAllServeEvents().forEach(event -> {
            System.err.println(event.getRequest().getAbsoluteUrl());
        });
        System.err.println(" >>>> WIREMOCK <<<< ");
    }

    public void start() {
        wireMockServer.start();
    }

    public void stop() {
        wireMockServer.stop();
    }
}