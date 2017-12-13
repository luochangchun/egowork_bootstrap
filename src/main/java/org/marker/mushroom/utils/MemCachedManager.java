package org.marker.mushroom.utils;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.Date;
import java.util.List;

public class MemCachedManager {

	// 创建全局的唯一实例
	private static MemCachedClient mcc = new MemCachedClient();

	private static MemCachedManager memCached = new MemCachedManager();

	// 设置与缓存服务器的连接池
	static {

		// 服务器列表和其权重
		String[] servers = {"192.168.11.117:11211", "192.168.11.118:11211"};
		//String[] servers = { "10.11.102.117:11211", "10.11.102.118:11211" };
		Integer[] weights = {3};

		// 获取socke连接池的实例对象
		SockIOPool pool = SockIOPool.getInstance();
		// 设置服务器信息
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setWeights(weights);
		// 设置初始连接数、最小和最大连接数以及最大处理时间
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000 * 60 * 60 * 6);

		// 设置主线程的睡眠时间
		pool.setMaintSleep(30);

		// 设置TCP的参数，连接超时等
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);

		// 初始化连接池
		pool.initialize();

		// 压缩设置，超过指定大小（单位为K）的数据都会被压缩
		// mcc.setCompressEnable( true );
		// mcc.setCompressThreshold( 64 * 1024 );
	}

	protected MemCachedManager() {
	}

	public static MemCachedManager getInstance() {
		return memCached;
	}

	public boolean add(String key, Object value) {
		return mcc.add(key, value);
	}

	public boolean add(String key, Object value, Date expiry) {
		return mcc.add(key, value, expiry);
	}

	public void set(String key, Object obj) {
		mcc.set(key, obj);
	}

	public void set(String key, Object obj, int expire) {
		mcc.set(key, obj, new Date(expire * 1000));
	}

	public boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}

	public boolean replace(String key, Object value, Date expiry) {
		return mcc.replace(key, value, expiry);
	}

	public boolean remove(String key) {
		return mcc.delete(key);
	}

	public Object get(String key) {
		return mcc.get(key);
	}

	public boolean delete(String key) {
		return mcc.delete(key);
	}

	/** @noinspection unused */
	@SuppressWarnings("unchecked")
	public void removeByPreKey(String preKey) {
		((List<String>) mcc.get(preKey)).stream().filter(item -> mcc.get(item) != null).forEach(mcc::delete);
		mcc.delete(preKey);
	}
}
