package io.github.ztgoto.commons.redis;

public interface RedisConnection extends RedisCommands,BinaryRedisCommands,AutoCloseable {
	
	boolean isClosed();
	
}
