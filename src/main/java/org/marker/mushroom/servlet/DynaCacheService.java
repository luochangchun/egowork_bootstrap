/*
################################################################################
#                                                                               
# Name : DynaCacheService.java       
# Author: Zheng Zhang                                             
# Desc : DynaCacheService is used to store user data.
#                                                                               
#                                                                               
# (C) COPYRIGHT IBM Corporation 2012                                      
# All Rights Reserved.                                                          
#                                                                               
# Licensed Materials-Property of IBM                                            
#                                                                               
################################################################################ 
*/
package org.marker.mushroom.servlet;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class DynaCacheService {

	private static ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

	private static Logger logger = Logger.getLogger(DynaCacheService.class);

	/**
	 * This class is singleton so private constructor is used.
	 */
	private DynaCacheService() {
		super();
	}

	/**
	 * returns cache item from hashmap
	 *
	 * @param key
	 * @return Cache
	 */
	private synchronized static Cache getCache(String key) {
		return (Cache) cacheMap.get(key);
	}

	/**
	 * Looks at the hashmap if a cache item exists or not
	 *
	 * @param key
	 * @return Cache
	 */
	private synchronized static boolean hasCache(String key) {
		return cacheMap.containsKey(key);
	}

	/**
	 * Invalidates all cache
	 */
	public synchronized static void invalidateAll() {
		cacheMap.clear();
	}

	/**
	 * Invalidates a single cache item
	 *
	 * @param key
	 */
	public synchronized static void invalidate(String key) {
		cacheMap.remove(key);
	}

	/**
	 * Adds new item to cache hashmap
	 *
	 * @param key
	 * @return Cache
	 */
	private synchronized static void putCache(String key, Cache object) {
		cacheMap.put(key, object);
		if (logger.isDebugEnabled())
			logger.debug("Put object to cache, key:" + key + ". cache size:" + cacheMap.size());
	}

	/**
	 * Reads a cache item's content
	 *
	 * @param key
	 * @return
	 */
	public static Cache getContent(String key) {
		if (hasCache(key)) {
			if (logger.isDebugEnabled())
				logger.debug("Get object from cache, key:" + key);
			Cache cache = getCache(key);
			if (cacheExpired(cache)) {
				// cache.setExpired(true);
				invalidate(key);
				cache = null;
				if (logger.isDebugEnabled())
					logger.debug("Cache expired, key:" + key);
			}
			return cache;
		} else {
			return null;
		}
	}

	/**
	 * @param key
	 * @param content
	 * @param ttl
	 */
	public static Cache putContent(String key, Object content, long ttl) {
		Cache cache = new Cache();
		cache.setKey(key);
		cache.setValue(content);
		if (ttl <= 0)
			cache.setTimeOut(-1);
		else
			cache.setTimeOut(ttl * 1000 + new Date().getTime());
		cache.setExpired(false);
		putCache(key, cache);
		return cache;
	}

	/**
	 * @param cache
	 * @return
	 */
	private static boolean cacheExpired(Cache cache) {
		if (cache == null) {
			return false;
		}
		long milisNow = new Date().getTime();
		long milisExpire = cache.getTimeOut();
		// Cache never expires
		return milisExpire >= 0 && milisNow >= milisExpire;
	}

}