package com.example.helloworld;

import com.example.helloworld.actors.ActionType;
import com.example.helloworld.actors.TestActor;
import com.example.helloworld.resources.HelloWorldResource;
import io.appform.dropwizard.actors.RabbitmqActorBundle;
import io.appform.dropwizard.actors.config.RMQConfig;
import io.appform.dropwizard.actors.retry.RetryStrategyFactory;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

  private RabbitmqActorBundle<HelloWorldConfiguration> rabbitmqActorBundle;

  public static void main(String[] args) throws Exception {
    new HelloWorldApplication().run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(
        new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(false)
        )
    );
    bootstrap.addBundle(new SwaggerBundle<HelloWorldConfiguration>() {
      @Override
      protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(HelloWorldConfiguration configuration) {
        return configuration.swaggerBundleConfiguration;
      }
    });
    rabbitmqActorBundle = new RabbitmqActorBundle<HelloWorldConfiguration>() {
      @Override
      protected RMQConfig getConfig(HelloWorldConfiguration helloWorldConfiguration) {
        return helloWorldConfiguration.getRmqConfig();
      }
    };
    bootstrap.addBundle(rabbitmqActorBundle);
  }

  @Override
  public void run(HelloWorldConfiguration configuration,
      Environment environment) {
    environment.lifecycle().manage(new Managed() {
      TestActor testActor = null;

      @Override
      public void start() throws Exception {
        RetryStrategyFactory retryStrategyFactory = new RetryStrategyFactory();
        testActor = TestActor.builder()
            .config(configuration.getActors().get(ActionType.STRAVA_WEBHOOKS))
            .connection(rabbitmqActorBundle.getConnection())
            .mapper(environment.getObjectMapper())
            .retryStrategyFactory(retryStrategyFactory)
            .build();
        testActor.start();

        environment.jersey().register(
            HelloWorldResource.builder()
                .testActor(testActor)
                .build());
      }

      @Override
      public void stop() throws Exception {
        if (testActor != null) {
          testActor.stop();
        }
      }
    });
  }
}