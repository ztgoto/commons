package io.github.ztgoto.commons.redis.jedis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.ztgoto.commons.redis.RedisConnection;
import redis.clients.jedis.ShardedJedis;

public class ShardedRedisConnection implements RedisConnection {

	private final ShardedJedis jedis;

	private boolean closed;

	public ShardedRedisConnection(ShardedJedis jedis) {
		this.jedis = jedis;
		this.closed = false;
	}

	@Override
	public void close() {
		jedis.close();
		this.closed = true;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public String set(String key, String value) {

		return jedis.set(key, value);
	}

	@Override
	public String set(String key, String value, String nxxx, String expx, long time) {

		return jedis.set(key, value, nxxx, expx, time);
	}

	@Override
	public String set(String key, String value, String nxxx) {

		return jedis.set(key, value, nxxx);
	}

	@Override
	public String get(String key) {

		return jedis.get(key);
	}

	@Override
	public Boolean exists(String key) {

		return jedis.exists(key);
	}

	@Override
	public Long persist(String key) {

		return jedis.persist(key);
	}

	@Override
	public String type(String key) {

		return jedis.type(key);
	}

	@Override
	public Long expire(String key, int seconds) {

		return jedis.expire(key, seconds);
	}

	@Override
	public Long pexpire(String key, long milliseconds) {

		return jedis.pexpire(key, milliseconds);
	}

	@Override
	public Long expireAt(String key, long unixTime) {

		return jedis.expireAt(key, unixTime);
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {

		return jedis.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Long ttl(String key) {

		return jedis.ttl(key);
	}

	@Override
	public Long pttl(String key) {

		return jedis.pttl(key);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {

		return jedis.setbit(key, offset, value);
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {

		return jedis.setbit(key, offset, value);
	}

	@Override
	public Boolean getbit(String key, long offset) {

		return jedis.getbit(key, offset);
	}

	@Override
	public Long setrange(String key, long offset, String value) {

		return jedis.setrange(key, offset, value);
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {

		return jedis.getrange(key, startOffset, endOffset);
	}

	@Override
	public String getSet(String key, String value) {

		return jedis.getSet(key, value);
	}

	@Override
	public Long setnx(String key, String value) {

		return jedis.setnx(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {

		return jedis.setex(key, seconds, value);
	}

	@Override
	public String psetex(String key, long milliseconds, String value) {

		return jedis.psetex(key, milliseconds, value);
	}

	@Override
	public Long decrBy(String key, long integer) {

		return jedis.decrBy(key, integer);
	}

	@Override
	public Long decr(String key) {

		return jedis.decr(key);
	}

	@Override
	public Long incrBy(String key, long integer) {

		return jedis.incrBy(key, integer);
	}

	@Override
	public Double incrByFloat(String key, double value) {

		return jedis.incrByFloat(key, value);
	}

	@Override
	public Long incr(String key) {

		return jedis.incr(key);
	}

	@Override
	public Long append(String key, String value) {

		return jedis.append(key, value);
	}

	@Override
	public String substr(String key, int start, int end) {

		return jedis.substr(key, start, end);
	}

	@Override
	public Long hset(String key, String field, String value) {

		return jedis.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {

		return jedis.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {

		return jedis.hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {

		return jedis.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {

		return jedis.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {

		return jedis.hincrBy(key, field, value);
	}

	@Override
	public Double hincrByFloat(String key, String field, double value) {

		return jedis.hincrByFloat(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) {

		return jedis.hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {

		return jedis.hdel(key, field);
	}

	@Override
	public Long hlen(String key) {

		return jedis.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {

		return jedis.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {

		return jedis.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {

		return jedis.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... string) {

		return jedis.rpush(key, string);
	}

	@Override
	public Long lpush(String key, String... string) {

		return jedis.lpush(key, string);
	}

	@Override
	public Long llen(String key) {

		return jedis.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {

		return jedis.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {

		return jedis.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {

		return jedis.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {

		return jedis.lset(key, index, value);
	}

	@Override
	public Long lrem(String key, long count, String value) {

		return jedis.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {

		return jedis.lpop(key);
	}

	@Override
	public String rpop(String key) {

		return jedis.rpop(key);
	}

	@Override
	public Long sadd(String key, String... member) {

		return jedis.sadd(key, member);
	}

	@Override
	public Set<String> smembers(String key) {

		return jedis.smembers(key);
	}

	@Override
	public Long srem(String key, String... member) {

		return jedis.srem(key, member);
	}

	@Override
	public String spop(String key) {

		return jedis.spop(key);
	}

	@Override
	public Set<String> spop(String key, long count) {

		return jedis.spop(key, count);
	}

	@Override
	public Long scard(String key) {

		return jedis.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {

		return jedis.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {

		return jedis.srandmember(key);
	}

	@Override
	public List<String> srandmember(String key, int count) {

		return jedis.srandmember(key, count);
	}

	@Override
	public Long strlen(String key) {

		return jedis.strlen(key);
	}

	@Override
	public Long zadd(String key, double score, String member) {

		return jedis.zadd(key, score, member);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {

		return jedis.zadd(key, scoreMembers);
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {

		return jedis.zrange(key, start, end);
	}

	@Override
	public Long zrem(String key, String... member) {

		return jedis.zrem(key, member);
	}

	@Override
	public Double zincrby(String key, double score, String member) {

		return jedis.zincrby(key, score, member);
	}

	@Override
	public Long zrank(String key, String member) {

		return jedis.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {

		return jedis.zrevrank(key, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {

		return jedis.zrevrange(key, start, end);
	}

	@Override
	public Long zcard(String key) {

		return jedis.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) {

		return jedis.zscore(key, member);
	}

	@Override
	public List<String> sort(String key) {

		return jedis.sort(key);
	}

	@Override
	public Long zcount(String key, double min, double max) {

		return jedis.zcount(key, min, max);
	}

	@Override
	public Long zcount(String key, String min, String max) {

		return jedis.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {

		return jedis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {

		return jedis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {

		return jedis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {

		return jedis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {

		return jedis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {

		return jedis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {

		return jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {

		return jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {

		return jedis.zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {

		return jedis.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {

		return jedis.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zlexcount(String key, String min, String max) {

		return jedis.zlexcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {

		return jedis.zrangeByLex(key, min, max);
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {

		return jedis.zrangeByLex(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {

		return jedis.zrevrangeByLex(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {

		return jedis.zrevrangeByLex(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) {

		return jedis.zremrangeByLex(key, min, max);
	}

	@Override
	public Long lpushx(String key, String... string) {

		return jedis.lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String... string) {

		return jedis.rpushx(key, string);
	}

	@Override
	public List<String> blpop(int timeout, String key) {

		return jedis.blpop(timeout, key);
	}

	@Override
	public List<String> brpop(int timeout, String key) {

		return jedis.brpop(timeout, key);
	}

	@Override
	public Long del(String key) {

		return jedis.del(key);
	}

	@Override
	public String echo(String string) {

		return jedis.echo(string);
	}

	@Override
	public Long move(String key, int dbIndex) {

		return jedis.move(key, dbIndex);
	}

	@Override
	public Long bitcount(String key) {

		return jedis.bitcount(key);
	}

	@Override
	public Long bitcount(String key, long start, long end) {

		return jedis.bitcount(key, start, end);
	}

	@Override
	public Long bitpos(String key, boolean value) {

		return jedis.bitpos(key, value);
	}

	@Override
	public Long pfadd(String key, String... elements) {

		return jedis.pfadd(key, elements);
	}

	@Override
	public long pfcount(String key) {

		return jedis.pfcount(key);
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude, String member) {

		return jedis.geoadd(key, longitude, latitude, member);
	}

	@Override
	public Double geodist(String key, String member1, String member2) {

		return jedis.geodist(key, member1, member2);
	}

	@Override
	public List<String> geohash(String key, String... members) {

		return jedis.geohash(key, members);
	}

	@Override
	public List<Long> bitfield(String key, String... arguments) {

		return jedis.bitfield(key, arguments);
	}

	@Override
	public String set(byte[] key, byte[] value) {

		return jedis.set(key, value);
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx) {

		return jedis.set(key, value, nxxx);
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {

		return jedis.set(key, value, nxxx, expx, time);
	}

	@Override
	public byte[] get(byte[] key) {

		return jedis.get(key);
	}

	@Override
	public Boolean exists(byte[] key) {

		return jedis.exists(key);
	}

	@Override
	public Long persist(byte[] key) {

		return jedis.persist(key);
	}

	@Override
	public String type(byte[] key) {

		return jedis.type(key);
	}

	@Override
	public Long expire(byte[] key, int seconds) {

		return jedis.expire(key, seconds);
	}

	@Override
	public Long pexpire(byte[] key, long milliseconds) {

		return jedis.pexpire(key, milliseconds);
	}

	@Override
	public Long expireAt(byte[] key, long unixTime) {

		return jedis.expireAt(key, unixTime);
	}

	@Override
	public Long pexpireAt(byte[] key, long millisecondsTimestamp) {

		return jedis.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Long ttl(byte[] key) {

		return jedis.ttl(key);
	}

	@Override
	public Boolean setbit(byte[] key, long offset, boolean value) {

		return jedis.setbit(key, offset, value);
	}

	@Override
	public Boolean setbit(byte[] key, long offset, byte[] value) {

		return jedis.setbit(key, offset, value);
	}

	@Override
	public Boolean getbit(byte[] key, long offset) {

		return jedis.getbit(key, offset);
	}

	@Override
	public Long setrange(byte[] key, long offset, byte[] value) {

		return jedis.setrange(key, offset, value);
	}

	@Override
	public byte[] getrange(byte[] key, long startOffset, long endOffset) {

		return jedis.getrange(key, startOffset, endOffset);
	}

	@Override
	public byte[] getSet(byte[] key, byte[] value) {

		return jedis.getSet(key, value);
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {

		return jedis.setnx(key, value);
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {

		return jedis.setex(key, seconds, value);
	}

	@Override
	public Long decrBy(byte[] key, long integer) {

		return jedis.decrBy(key, integer);
	}

	@Override
	public Long decr(byte[] key) {

		return jedis.decr(key);
	}

	@Override
	public Long incrBy(byte[] key, long integer) {

		return jedis.incrBy(key, integer);
	}

	@Override
	public Double incrByFloat(byte[] key, double value) {

		return jedis.incrByFloat(key, value);
	}

	@Override
	public Long incr(byte[] key) {

		return jedis.incr(key);
	}

	@Override
	public Long append(byte[] key, byte[] value) {

		return jedis.append(key, value);
	}

	@Override
	public byte[] substr(byte[] key, int start, int end) {

		return jedis.substr(key, start, end);
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {

		return jedis.hset(key, field, value);
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {

		return jedis.hget(key, field);
	}

	@Override
	public Long hsetnx(byte[] key, byte[] field, byte[] value) {

		return jedis.hsetnx(key, field, value);
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {

		return jedis.hmset(key, hash);
	}

	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fields) {

		return jedis.hmget(key, fields);
	}

	@Override
	public Long hincrBy(byte[] key, byte[] field, long value) {

		return jedis.hincrBy(key, field, value);
	}

	@Override
	public Double hincrByFloat(byte[] key, byte[] field, double value) {

		return jedis.hincrByFloat(key, field, value);
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {

		return jedis.hexists(key, field);
	}

	@Override
	public Long hdel(byte[] key, byte[]... field) {

		return jedis.hdel(key, field);
	}

	@Override
	public Long hlen(byte[] key) {

		return jedis.hlen(key);
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {

		return jedis.hkeys(key);
	}

	@Override
	public Collection<byte[]> hvals(byte[] key) {

		return jedis.hvals(key);
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {

		return jedis.hgetAll(key);
	}

	@Override
	public Long rpush(byte[] key, byte[]... args) {

		return jedis.rpush(key, args);
	}

	@Override
	public Long lpush(byte[] key, byte[]... args) {

		return jedis.lpush(key, args);
	}

	@Override
	public Long llen(byte[] key) {

		return jedis.llen(key);
	}

	@Override
	public List<byte[]> lrange(byte[] key, long start, long end) {

		return jedis.lrange(key, start, end);
	}

	@Override
	public String ltrim(byte[] key, long start, long end) {

		return jedis.ltrim(key, start, end);
	}

	@Override
	public byte[] lindex(byte[] key, long index) {

		return jedis.lindex(key, index);
	}

	@Override
	public String lset(byte[] key, long index, byte[] value) {

		return jedis.lset(key, index, value);
	}

	@Override
	public Long lrem(byte[] key, long count, byte[] value) {

		return jedis.lrem(key, count, value);
	}

	@Override
	public byte[] lpop(byte[] key) {

		return jedis.lpop(key);
	}

	@Override
	public byte[] rpop(byte[] key) {

		return jedis.rpop(key);
	}

	@Override
	public Long sadd(byte[] key, byte[]... member) {

		return jedis.sadd(key, member);
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {

		return jedis.smembers(key);
	}

	@Override
	public Long srem(byte[] key, byte[]... member) {

		return jedis.srem(key, member);
	}

	@Override
	public byte[] spop(byte[] key) {

		return jedis.spop(key);
	}

	@Override
	public Set<byte[]> spop(byte[] key, long count) {

		return jedis.spop(key, count);
	}

	@Override
	public Long scard(byte[] key) {

		return jedis.scard(key);
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {

		return jedis.sismember(key, member);
	}

	@Override
	public byte[] srandmember(byte[] key) {

		return jedis.srandmember(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<byte[]> srandmember(byte[] key, int count) {

		return jedis.srandmember(key, count);
	}

	@Override
	public Long strlen(byte[] key) {

		return jedis.strlen(key);
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member) {

		return jedis.zadd(key, score, member);
	}

	@Override
	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {

		return jedis.zadd(key, scoreMembers);
	}

	@Override
	public Set<byte[]> zrange(byte[] key, long start, long end) {

		return jedis.zrange(key, start, end);
	}

	@Override
	public Long zrem(byte[] key, byte[]... member) {

		return jedis.zrem(key, member);
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member) {

		return jedis.zincrby(key, score, member);
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {

		return jedis.zrank(key, member);
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {

		return jedis.zrevrank(key, member);
	}

	@Override
	public Set<byte[]> zrevrange(byte[] key, long start, long end) {

		return jedis.zrevrange(key, start, end);
	}

	@Override
	public Long zcard(byte[] key) {

		return jedis.zcard(key);
	}

	@Override
	public Double zscore(byte[] key, byte[] member) {

		return jedis.zscore(key, member);
	}

	@Override
	public List<byte[]> sort(byte[] key) {

		return jedis.sort(key);
	}

	@Override
	public Long zcount(byte[] key, double min, double max) {

		return jedis.zcount(key, min, max);
	}

	@Override
	public Long zcount(byte[] key, byte[] min, byte[] max) {

		return jedis.zcount(key, min, max);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {

		return jedis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {

		return jedis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {

		return jedis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {

		return jedis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {

		return jedis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {

		return jedis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {

		return jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {

		return jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(byte[] key, long start, long end) {

		return jedis.zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {

		return jedis.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {

		return jedis.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zlexcount(byte[] key, byte[] min, byte[] max) {

		return jedis.zlexcount(key, min, max);
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {

		return jedis.zrangeByLex(key, min, max);
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {

		return jedis.zrangeByLex(key, min, max, offset, count);
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {

		return jedis.zrevrangeByLex(key, max, min);
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {

		return jedis.zrevrangeByLex(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {

		return jedis.zremrangeByLex(key, min, max);
	}

	@Override
	public Long lpushx(byte[] key, byte[]... arg) {

		return jedis.lpushx(key, arg);
	}

	@Override
	public Long rpushx(byte[] key, byte[]... arg) {

		return jedis.rpushx(key, arg);
	}

	@Override
	public Long del(byte[] key) {

		return jedis.del(key);
	}

	@Override
	public byte[] echo(byte[] arg) {

		return jedis.echo(arg);
	}

	@Override
	public Long move(byte[] key, int dbIndex) {

		return jedis.move(key, dbIndex);
	}

	@Override
	public Long bitcount(byte[] key) {

		return jedis.bitcount(key);
	}

	@Override
	public Long bitcount(byte[] key, long start, long end) {

		return jedis.bitcount(key, start, end);
	}

	@Override
	public Long pfadd(byte[] key, byte[]... elements) {

		return jedis.pfadd(key, elements);
	}

	@Override
	public long pfcount(byte[] key) {

		return jedis.pfcount(key);
	}

	@Override
	public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {

		return jedis.geoadd(key, longitude, latitude, member);
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2) {

		return jedis.geodist(key, member1, member2);
	}

	@Override
	public List<byte[]> geohash(byte[] key, byte[]... members) {

		return jedis.geohash(key, members);
	}

	@Override
	public List<byte[]> bitfield(byte[] key, byte[]... arguments) {

		return jedis.bitfield(key, arguments);
	}

}
