package com.example.helloworld.configs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudAmqpConfig {

  private String url;

  private int port;

  private int threadPoolSize;

}
