package com.example.helloworld.configs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XmemcachedConfig {

  private String memCachierServers;

  private String memCachierUsername;

  private String memCachierPassword;

  private long connectTimeout;

  private boolean enableHealSession;

  private long healSessionInterval;

}
