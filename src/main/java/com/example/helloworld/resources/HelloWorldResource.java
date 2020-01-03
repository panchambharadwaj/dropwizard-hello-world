package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.actors.TestActor;
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

@Path("/")
@Api(value = "Hello World")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  private final TestActor testActor;

  @Builder
  public HelloWorldResource(TestActor testActor) {
    this.testActor = testActor;
  }

  @GET
  @Timed
  @ApiResponses({@ApiResponse(code = 202, message = "Success."),
      @ApiResponse(code = 500, message = "Error"),
  })
  @ApiOperation(value = "Hello")
  public Response sayHello(@QueryParam("name") String name) throws Exception {
    testActor.publish(TestRequest.builder()
        .message(name)
        .build());
    return Response.status(Status.OK).build();
  }
}