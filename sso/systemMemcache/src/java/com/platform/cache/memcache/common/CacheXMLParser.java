package com.platform.cache.memcache.common;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * <pre>
 * MEMCACHE的解析类
 * </pre>
 * 
 * @author pengchenq
 * @version $ MemcacheUtil.java, v 0.1 2011-8-24 下午02:58:10 pengchenq Exp $
 * @since JDK1.6
 */
public class CacheXMLParser {

	private final static Logger logger = Logger.getLogger(CacheXMLParser.class);

	private SocketPoolBean commonSocketPoolBean = null;

	public CacheXMLParser(String fileName) {

		readXML(fileName);
	}

	/**
	 * 解析配置文件
	 * 
	 * @param fileName
	 */
	private void readXML(String fileName) {

		SAXReader saxReader = new SAXReader();

		Document document = null;

		File file = new File(fileName);

		try {
			document = saxReader.read(file);

			commonSocketPoolBean = readCommonSocketPool(document);// 解析commonsocketpool标签

		} catch (Exception e) {

			logger.error("file can not find", e);
		}
	}

	/**
	 * 解析commonsocketpool标签
	 * 
	 * @param document
	 */
	@SuppressWarnings("unchecked")
	private SocketPoolBean readCommonSocketPool(Document document) {
		List<Element> socketpools = document
				.selectNodes("/cache/commonsocketpool");
		return readSocketPool(socketpools);
	}

	private SocketPoolBean readSocketPool(List<Element> socketpools) {

		if (socketpools == null || socketpools.size() == 0) {

			logger.warn("Configuration file is not valid!");

			return null;
		}

		for (Element element : socketpools) {

			SocketPoolBean socketPoolBean = new SocketPoolBean();

			socketPoolBean.setName(element.attributeValue("name").trim());

			socketPoolBean.setFailover(Boolean.parseBoolean(element
					.attributeValue("failover").trim()));

			socketPoolBean.setInitConn(Integer.parseInt(element.attributeValue(
					"initConn").trim()));

			socketPoolBean.setMinConn(Integer.parseInt(element.attributeValue(
					"minConn").trim()));

			socketPoolBean.setMaxConn(Integer.parseInt(element.attributeValue(
					"maxConn").trim()));

			socketPoolBean.setMaintSleep(Integer.parseInt(element
					.attributeValue("maintSleep").trim()));

			socketPoolBean.setNagle(Boolean.parseBoolean(element
					.attributeValue("nagle").trim()));

			socketPoolBean.setSocketTo(Integer.parseInt(element.attributeValue(
					"socketTo").trim()));

			socketPoolBean.setAliveCheck(Boolean.parseBoolean(element
					.attributeValue("aliveCheck").trim()));
			List<Element> servers = element.selectNodes("servers/value");

			for (Element server : servers) {

				socketPoolBean.add(server.getTextTrim());
			}

			return socketPoolBean;
		}
		logger.warn("Configuration file is not valid!");
		return null;
	}

	/**
	 *实例化解析的commonsocketpool标签成SocketIOPool和MemcachedClient
	 */
	public MemCachedClient getCommonMemcachedClient() {

		return getMemcachedClient(commonSocketPoolBean);

	}

	private MemCachedClient getMemcachedClient(SocketPoolBean socketPoolBean) {

		if (socketPoolBean == null) {

			logger.warn("please check your xml file!");

			return null;
		}

		SockIOPool pool = SockIOPool.getInstance(socketPoolBean.getName());

		pool.setServers(socketPoolBean.getServers().toArray(
				new String[socketPoolBean.getServers().size()]));

		pool.setInitConn(socketPoolBean.getInitConn());// 设置cache服务器的初始化可用连接

		pool.setMinConn(socketPoolBean.getMinConn()); // 设置每个服务器最少可用连接数

		pool.setMaxConn(socketPoolBean.getMaxConn()); // 设置每个服务器最大可用连接数

		pool.setMaxIdle(socketPoolBean.getMaxIdle()); // 设置可用连接池的等待时间

		pool.setMaintSleep(socketPoolBean.getMaintSleep());// 设置连接池维护线程的睡眠时间，设置为0，维护线程不启动

		pool.setNagle(socketPoolBean.isNagle()); // 设置是否使用Nagle算法，因为我们的通讯数据量�?常比较大

		pool.setSocketTO(socketPoolBean.getSocketTo()); // 设置读取等待超时

		pool.setAliveCheck(socketPoolBean.isAliveCheck());// 设置连接心跳监测

		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);

		pool.initialize();

		MemCachedClient mc = new MemCachedClient(socketPoolBean.getName());

		mc.setCompressEnable(true); // 压缩开关

		mc.setCompressThreshold(64 * 1024); // 压缩设置，超过指定大小（单位为K）的数据都会被压缩

		return mc;
	}

}
