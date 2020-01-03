package com.example.helloworld.configs;

import com.example.helloworld.actors.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.clojars.pancham.dropwizard.actors.actor.ActorConfig;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class HelloWorldConfiguration extends Configuration {

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration swaggerBundleConfiguration;

  @JsonProperty("rmq")
  public CloudAmqpConfig cloudAmqpConfig;

  @NotNull
  @NotEmpty
  @Valid
  public Map<ActionType, ActorConfig> actors = Maps.newHashMap();

  @JsonProperty("memCachier")
  @NotNull
  @Valid
  public XmemcachedConfig xmemcachedConfig;

  private DataSourceFactory database = new DataSourceFactory();

}