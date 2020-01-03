package com.example.helloworld.clients;

import com.example.helloworld.configs.XmemcachedConfig;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemCachierClient {

  private XmemcachedConfig xmemcachedConfig;

  public MemCachierClient(XmemcachedConfig xmemcachedConfig) {
    this.xmemcachedConfig = xmemcachedConfig;
  }

  public MemcachedClient getClient() throws IOException {
    List<InetSocketAddress> servers =
        AddrUtil.getAddresses(xmemcachedConfig.getMemCachierServers().replace(",", " "));
    AuthInfo authInfo = AuthInfo.plain(xmemcachedConfig.getMemCachierUsername(),
        xmemcachedConfig.getMemCachierPassword());

    MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);

    for(InetSocketAddress server : servers) {
      builder.addAuthInfo(server, authInfo);
    }

    builder.setCommandFactory(new BinaryCommandFactory());
    builder.setConnectTimeout(xmemcachedConfig.getConnectTimeout());
    builder.setEnableHealSession(xmemcachedConfig.isEnableHealSession());
    builder.setHealSessionInterval(xmemcachedConfig.getHealSessionInterval());

    return builder.build();
  }

}
