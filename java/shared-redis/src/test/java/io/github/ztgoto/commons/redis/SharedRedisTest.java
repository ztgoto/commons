package io.github.ztgoto.commons.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import io.github.ztgoto.commons.redis.jedis.ShardedJedisSentinelPool;
import io.github.ztgoto.commons.redis.jedis.ShardedRedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@RunWith(BlockJUnit4ClassRunner.class)
public class SharedRedisTest {

	@Test
	public void testShared() {

		JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置

		config.setTestOnBorrow(true);

		ShardedRedisConnectionFactory factory = new ShardedRedisConnectionFactory(config);
		JedisShardInfo jsi1 = new JedisShardInfo("redis://192.168.4.83:6379/0");
		JedisShardInfo jsi2 = new JedisShardInfo("redis://192.168.4.83:6381/0");
		factory.addShardInfo(jsi1);
		factory.addShardInfo(jsi2);
		factory.init();

		try (RedisConnection conn = factory.getConnection();) {

			for (int i = 0; i < 10; i++) {
				String key = "key" + i;
				String value = "value" + i;
				conn.setex(key, 30, value);

				System.out.println(conn.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		factory.destroy();
	}

	@Test
	public void testSharedSentinel() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();

		List<String> masters = new ArrayList<String>();
		masters.add("master1");
		masters.add("master2");

		Set<String> sentinels = new HashSet<String>();
		sentinels.add("192.168.4.83:26379");
		sentinels.add("192.168.4.83:26380");
		sentinels.add("192.168.4.83:26381");

		ShardedJedisSentinelPool pool = new ShardedJedisSentinelPool(masters, sentinels, config, 60000);

		ShardedJedis conn = pool.getResource();
		
		for (int i = 0; i < 10; i++) {
			String key = "key" + i;
			String value = "value" + i;
			conn.setex(key, 30, value);

			System.out.println(conn.get(key));
		}

		pool.destroy();
	}

}
