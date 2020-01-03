package com.example.helloworld;

import com.example.helloworld.actors.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import io.appform.dropwizard.actors.actor.ActorConfig;
import io.appform.dropwizard.actors.config.RMQConfig;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class HelloWorldConfiguration extends Configuration {

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration swaggerBundleConfiguration;

  @JsonProperty("rmq")
  @NotNull
  @Valid
  public RMQConfig rmqConfig;

  @NotNull
  @NotEmpty
  @Valid
  public Map<ActionType, ActorConfig> actors = Maps.newHashMap();

}