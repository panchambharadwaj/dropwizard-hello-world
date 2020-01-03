package com.example.helloworld.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class PostgresUrlParser {

  public String parse(String url) throws URISyntaxException {
    URI herokuUri = new URI(url);
    String host = herokuUri.getHost();
    int port = herokuUri.getPort();
    String dbName = herokuUri.getPath().substring(1);
    String username = herokuUri.getUserInfo().split(":")[0];
    String password = herokuUri.getUserInfo().split(":")[1];
    return "jdbc:postgresql://" + host + ":" + port + "/" + dbName + "?sslmode=require&user=" + username + "&password"
        + "=" + password;
  }

}
