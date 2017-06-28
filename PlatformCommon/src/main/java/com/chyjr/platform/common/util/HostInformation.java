package com.chyjr.platform.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.HostInformation.java] 
 * @ClassName:    [HostInformation]  
 * @Description:  [获得Host 信息]  
 * @Author:       [wangxiaohua]  
 * @CreateDate:   [Oct 14, 2014 4:58:13 PM]  
 * @UpdateUser:   [wangxiaohua]  
 * @UpdateDate:   [Oct 14, 2014 4:58:13 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
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
