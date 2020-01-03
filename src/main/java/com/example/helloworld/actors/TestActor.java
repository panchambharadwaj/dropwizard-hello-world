package com.example.helloworld.actors;

import com.example.helloworld.models.TestRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import io.appform.dropwizard.actors.actor.Actor;
import io.appform.dropwizard.actors.actor.ActorConfig;
import io.appform.dropwizard.actors.connectivity.RMQConnection;
import io.appform.dropwizard.actors.retry.RetryStrategyFactory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class TestActor extends Actor<ActionType, TestRequest> {

  @Builder
  public TestActor(ActorConfig config, RMQConnection connection, ObjectMapper mapper,
      RetryStrategyFactory retryStrategyFactory) {
    super(ActionType.STRAVA_WEBHOOKS, config, connection, mapper, retryStrategyFactory, TestRequest.class,
        ImmutableSet.of(JsonProcessingException.class));
  }

  @Override
  public boolean handle(final TestRequest testRequest) {
    try {
      log.info("Consuming {}", testRequest);
      Thread.sleep(5000);
      return true;
    } catch (Exception e) {
      log.error("Error consuming");
      return false;
    }
  }

}
