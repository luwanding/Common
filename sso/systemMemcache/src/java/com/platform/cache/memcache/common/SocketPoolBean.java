package com.platform.cache.memcache.common;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * MEMCACHE的配置信息BEAN
 * </pre>
 *
 * @author pengchenq
 * @version $ MemcacheUtil.java, v 0.1 2011-8-24 下午02:58:10 pengchenq Exp $
 * @since   JDK1.6
 */
class SocketPoolBean {
    private String name;
    
    private boolean failover = true;

    private int initConn = 50;

    private int minConn = 50;

    private int maxConn = 250;

    private int maintSleep = 30 * 1000;

    private boolean nagle = false;

    private int socketTo = 3 * 1000;

    private boolean aliveCheck = true;

    private int maxIdle = 3 * 1000;

    private List<String> servers = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFailover() {
        return failover;
    }

    public void setFailover(boolean failover) {
        this.failover = failover;
    }

    public int getInitConn() {
        return initConn;
    }

    public void setInitConn(int initConn) {
        this.initConn = initConn;
    }

    public int getMinConn() {
        return minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public int getMaintSleep() {
        return maintSleep;
    }

    public void setMaintSleep(int maintSleep) {
        this.maintSleep = maintSleep;
    }

    public boolean isNagle() {
        return nagle;
    }

    public void setNagle(boolean nagle) {
        this.nagle = nagle;
    }

    public int getSocketTo() {
        return socketTo;
    }

    public void setSocketTo(int socketTo) {
        this.socketTo = socketTo;
    }

    public boolean isAliveCheck() {
        return aliveCheck;
    }

    public void setAliveCheck(boolean aliveCheck) {
        this.aliveCheck = aliveCheck;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }
    
    public void add(String server){
        if(servers == null){
            servers = new ArrayList<String>();
        }
        servers.add(server);
    }
}
