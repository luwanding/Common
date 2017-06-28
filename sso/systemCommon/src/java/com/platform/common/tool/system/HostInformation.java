package com.platform.common.tool.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;


public class HostInformation {

    private static HostInformation m_instance = null;
    
    private final Logger logger = Logger.getLogger(HostInformation.class);

    private InetAddress netAddress;

    public synchronized static HostInformation getInstance() {

        if (m_instance == null) {

            m_instance = new HostInformation();

        }

        return m_instance;
    }

    private HostInformation() {

        try {
            netAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
           logger.error(e);
        }
    }
    /**
     * 获取机器名
     * @return
     */
    public String getHostName() {

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
        	logger.error(e);
            return null;
        }
    }
    /**
     * 获取机器IP
     * @return
     */
    public String getHostIp() {

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
        	logger.error(e);
            return null;
        }
    }

}
