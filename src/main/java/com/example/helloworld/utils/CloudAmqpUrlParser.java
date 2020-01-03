package com.example.helloworld.utils;

import com.example.helloworld.configs.CloudAmqpConfig;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import org.clojars.pancham.dropwizard.actors.config.Broker;
import org.clojars.pancham.dropwizard.actors.config.RMQConfig;

public class CloudAmqpUrlParser {

  public RMQConfig parse(CloudAmqpConfig cloudAmqpConfig) throws URISyntaxException {
    URI cloudAmqpUrl = new URI(cloudAmqpConfig.getUrl());
    return RMQConfig.builder()
        .brokers(Collections.singletonList(Broker.builder()
            .host(cloudAmqpUrl.getHost())
            .port(cloudAmqpUrl.getPort())
            .build()))
        .userName(cloudAmqpUrl.getUserInfo().split(":")[0])
        .password(cloudAmqpUrl.getUserInfo().split(":")[1])
        .threadPoolSize(cloudAmqpConfig.getThreadPoolSize())
        .virtualHost(cloudAmqpUrl.getPath().substring(1))
        .build();
  }
}
