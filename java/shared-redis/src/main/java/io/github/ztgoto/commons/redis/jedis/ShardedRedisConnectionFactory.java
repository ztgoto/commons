package io.github.ztgoto.commons.redis.jedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import io.github.ztgoto.commons.redis.RedisConnection;
import io.github.ztgoto.commons.redis.RedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class ShardedRedisConnectionFactory implements RedisConnectionFactory {
	
	static final Hashing DEFAULT_ALGO = Hashing.MURMUR_HASH;
	
	static final Pattern DEFAULT_PATTERN = Sharded.DEFAULT_KEY_TAG_PATTERN;
	
	static final int DEFAULT_TIMEOUT = 60000;
	
	
	private List<JedisShardInfo> shardInfos;
	
	private ShardedJedisPool pool;
	
	private Set<String> sentinels;
	
	private List<String> masters;
	
	private ShardedJedisSentinelPool sentinelPool;
	
	private GenericObjectPoolConfig sentinelPoolConfig;
	
	private JedisPoolConfig poolConfig;
	
	private Hashing algo = DEFAULT_ALGO;
	
	private Pattern pattern = DEFAULT_PATTERN;
	
	private int timeout = DEFAULT_TIMEOUT;
	
	public ShardedRedisConnectionFactory(GenericObjectPoolConfig sentinelPoolConfig) {
		this.sentinelPoolConfig = sentinelPoolConfig;
	}
	
	public ShardedRedisConnectionFactory(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	public ShardedRedisConnectionFactory(JedisPoolConfig poolConfig,Hashing algo, Pattern pattern) {
		this.poolConfig = poolConfig;
		this.algo = algo;
		this.pattern = pattern;
	}
	
	public void init(){
		createPool();
	}
	
	public void destroy() {
		if (sentinelPool != null) {
			sentinelPool.destroy();
			sentinelPool = null;
		}
		if (pool != null) {
			pool.destroy();
			pool = null;
		}
	}

	@Override
	public RedisConnection getConnection() {
		if (pool == null && sentinelPool == null)
			throw new IllegalAccessError("not init");
		ShardedJedis jedis = null;
		if (sentinelPool != null) {
			jedis = sentinelPool.getResource();
		} else if (pool != null) {
			jedis = pool.getResource();
		}
		if (jedis == null) {
			throw new IllegalAccessError("not get a redis connection");
		}
		ShardedRedisConnection conn = new ShardedRedisConnection(jedis);
		return conn;
	}
	
	private void createPool() {
		if (sentinels != null && sentinels.size() > 0 
				&& masters != null && masters.size() > 0) {
			if (sentinelPool != null) {
				sentinelPool.destroy();
				sentinelPool = null;
			}
			
			sentinelPool =  new ShardedJedisSentinelPool(masters, sentinels, sentinelPoolConfig
					, timeout<=0?DEFAULT_TIMEOUT:timeout);
		} else if (shardInfos != null && shardInfos.size() > 0) {
			if (pool != null) {
				pool.destroy();
				pool = null;
			}
			pool = new ShardedJedisPool(poolConfig, shardInfos
					, algo == null? DEFAULT_ALGO: algo
							, pattern == null? DEFAULT_PATTERN: pattern);
		}
		
		if (pool == null && sentinelPool == null)
			throw new IllegalAccessError("pool not init!");
			
	}
	
	public void addShardInfo(JedisShardInfo info){
		if (shardInfos == null) {
			shardInfos = new ArrayList<JedisShardInfo>();
		}
		shardInfos.add(info);
	}

	public List<JedisShardInfo> getShardInfos() {
		return shardInfos;
	}

	public void setShardInfos(List<JedisShardInfo> shardInfos) {
		this.shardInfos = shardInfos;
	}
	
	public void addSentinel(String host){
		if (this.sentinels == null) {
			this.sentinels = new HashSet<String>();
		}
		this.sentinels.add(host);
	}

	public Set<String> getSentinels() {
		return sentinels;
	}

	public void setSentinels(Set<String> sentinels) {
		this.sentinels = sentinels;
	}
	
	public void addMaster(String masterName){
		if (this.masters == null) {
			this.masters = new ArrayList<String>();
		}
		this.masters.add(masterName);
	}

	public List<String> getMasters() {
		return masters;
	}

	public void setMasters(List<String> masters) {
		this.masters = masters;
	}

	public Hashing getAlgo() {
		return algo;
	}


	public Pattern getPattern() {
		return pattern;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	

}
