package com.example.helloworld.resources;

import com.example.helloworld.api.Saying;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;
import io.swagger.annotations.Api;

@Path("/hello-world")
@Api(value = "Hello World")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  private final String template;
  private final String defaultName;
  private final AtomicLong counter;

  public HelloWorldResource(String template, String defaultName) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
  }

  @GET
  @Timed
  @ApiResponses({@ApiResponse(code = 202, message = "Success."),
      @ApiResponse(code = 500, message = "Error"),
  })
  @ApiOperation(value = "Hello")
  public Saying sayHello(@QueryParam("name") Optional<String> name) {
    final String value = String.format(template, name.orElse(defaultName));
    return new Saying(counter.incrementAndGet(), value);
  }
}