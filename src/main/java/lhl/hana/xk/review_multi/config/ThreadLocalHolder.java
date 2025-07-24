package lhl.hana.xk.review_multi.config;

public class ThreadLocalHolder {
    private static final ThreadLocal<Long> SHARD_KEY = new ThreadLocal<>();

    public static void setShardKey(Long shardKey) {
        SHARD_KEY.set(shardKey);
    }

    public static Long getShardKey() {
        return SHARD_KEY.get();
    }

    public static void removeShardKey() {
        SHARD_KEY.remove();
    }
}