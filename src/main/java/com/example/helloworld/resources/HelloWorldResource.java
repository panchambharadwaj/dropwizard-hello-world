package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.actors.TestActor;
import com.example.helloworld.daos.BotDao;
import com.example.helloworld.models.TestRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.rubyeye.xmemcached.MemcachedClient;
import org.jdbi.v3.core.Jdbi;

@Path("/")
@Api(value = "Hello World")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class HelloWorldResource {

  private final TestActor testActor;

  private BotDao botDao;

  private MemcachedClient memcachedClient;

  @Builder
  public HelloWorldResource(Jdbi jdbi, TestActor testActor, MemcachedClient memcachedClient) {
    this.testActor = testActor;
    this.botDao = jdbi.onDemand(BotDao.class);
    this.memcachedClient = memcachedClient;
  }

  @GET
  @Path("/v1/sayHello")
  @Timed
  @ApiResponses({@ApiResponse(code = 202, message = "Success."),
      @ApiResponse(code = 500, message = "Error"),
  })
  @ApiOperation(value = "Say Hello")
  public Response sayHello(@QueryParam("name") String name) throws Exception {
    int athleteId = botDao.getAthleteIdByName("Pancham Bharadwaj");
    log.info("Athlete ID: {}", athleteId);
    log.info("Putting into cache..");
    boolean result = memcachedClient.set("Pancham Bharadwaj", 0, athleteId);
    log.info("Result: {}", result);
    testActor.publish(TestRequest.builder()
        .message(name)
        .build());
    athleteId = memcachedClient.get("Pancham Bharadwaj");
    log.info("Fetched from cache: {}", athleteId);

    return Response.status(Status.OK).build();
  }
}