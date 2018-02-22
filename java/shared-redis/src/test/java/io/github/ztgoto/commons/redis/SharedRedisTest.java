package io.github.ztgoto.commons.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import io.github.ztgoto.commons.redis.jedis.ShardedRedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

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

		ShardedRedisConnectionFactory factory = new ShardedRedisConnectionFactory(config);

		factory.addMaster("master1");
		factory.addMaster("master2");

		factory.addSentinel("192.168.4.83:26379");
		factory.addSentinel("192.168.4.83:26380");
		factory.addSentinel("192.168.4.83:26381");

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

	public static void main(String[] args) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();

		ShardedRedisConnectionFactory factory = new ShardedRedisConnectionFactory(config);

		factory.addMaster("master1");
		factory.addMaster("master2");

		factory.addSentinel("192.168.4.83:26379");
		factory.addSentinel("192.168.4.83:26380");
		factory.addSentinel("192.168.4.83:26381");

		factory.init();

		int count = 0;

		while (true) {
			try {
				System.out.println("--------------------------");
				RedisConnection conn = factory.getConnection();

				for (int i = 0; i < 10; i++) {
					String key = "key" + i;
					String value = "value" + i;
					conn.setex(key, 30, value);

					System.out.println(conn.get(key));
				}

				System.out.println(">>>>>" + (++count));
				conn.close();
				Thread.sleep(2000);
				System.out.println("******************************");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
