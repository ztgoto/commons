package io.github.ztgoto.commons.redis.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
	
	
	private List<JedisShardInfo> shardInfos;
	
	private ShardedJedisPool pool;
	
	private JedisPoolConfig poolConfig;
	
	private Hashing algo = DEFAULT_ALGO;
	
	private Pattern pattern = DEFAULT_PATTERN;
	
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
		if (pool != null) {
			pool.close();
			pool = null;
		}
	}

	@Override
	public RedisConnection getConnection() {
		if (pool == null)
			throw new IllegalAccessError("not init");
		ShardedJedis jedis = pool.getResource();
		ShardedRedisConnection conn = new ShardedRedisConnection(jedis);
		return conn;
	}
	
	private void createPool() {
		if (shardInfos == null || shardInfos.size() == 0)
			throw new IllegalArgumentException("shardInfos is empty");
		
		if (pool != null) {
			pool.close();
			pool = null;
		}
		pool = new ShardedJedisPool(poolConfig, shardInfos
				, algo == null? DEFAULT_ALGO: algo
						, pattern == null? DEFAULT_PATTERN: pattern);
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

	public Hashing getAlgo() {
		return algo;
	}


	public Pattern getPattern() {
		return pattern;
	}

	

}
