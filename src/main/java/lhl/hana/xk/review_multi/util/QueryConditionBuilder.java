package lhl.hana.xk.review_multi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.BooleanSupplier;

/**
 * 通用查询条件构建器
 * 提供链式调用和类型安全的查询条件构建
 * 
 * @param <T> 实体类型
 * @author hana
 */
@Slf4j
public class QueryConditionBuilder<T> {
    
    private final QueryWrapper<T> wrapper;
    
    private QueryConditionBuilder() {
        this.wrapper = new QueryWrapper<>();
    }
    
    /**
     * 创建查询条件构建器实例
     */
    public static <T> QueryConditionBuilder<T> create() {
        return new QueryConditionBuilder<>();
    }
    
    /**
     * 字符串相等条件（非空时添加）
     */
    public QueryConditionBuilder<T> eqIfNotEmpty(String column, String value) {
        return addCondition(() -> value != null && !value.trim().isEmpty(), 
                           w -> w.eq(column, value));
    }
    
    /**
     * 数值相等条件（非空时添加）
     */
    public QueryConditionBuilder<T> eqIfNotNull(String column, Object value) {
        return addCondition(() -> value != null, 
                           w -> w.eq(column, value));
    }
    
    /**
     * 大于等于条件（非空时添加）
     */
    public QueryConditionBuilder<T> geIfNotNull(String column, Comparable<?> value) {
        return addCondition(() -> value != null, 
                           w -> w.ge(column, value));
    }
    
    /**
     * 小于等于条件（非空时添加）
     */
    public QueryConditionBuilder<T> leIfNotNull(String column, Comparable<?> value) {
        return addCondition(() -> value != null, 
                           w -> w.le(column, value));
    }
    
    /**
     * 模糊查询条件（非空时添加）
     */
    public QueryConditionBuilder<T> likeIfNotEmpty(String column, String value) {
        return addCondition(() -> value != null && !value.trim().isEmpty(), 
                           w -> w.like(column, value));
    }
    
    /**
     * 价格区间查询（BigDecimal类型）
     */
    public QueryConditionBuilder<T> priceBetween(String column, BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice != null) {
            geIfNotNull(column, minPrice);
        }
        if (maxPrice != null) {
            leIfNotNull(column, maxPrice);
        }
        return this;
    }
    
    /**
     * 时间区间查询
     */
    public QueryConditionBuilder<T> timeBetween(String column, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null) {
            geIfNotNull(column, startTime);
        }
        if (endTime != null) {
            leIfNotNull(column, endTime);
        }
        return this;
    }
    
    /**
     * IN查询条件（集合非空时添加）
     */
    public QueryConditionBuilder<T> inIfNotEmpty(String column, Collection<?> values) {
        return addCondition(() -> values != null && !values.isEmpty(), 
                           w -> w.in(column, values));
    }
    
    /**
     * NOT IN查询条件（集合非空时添加）
     */
    public QueryConditionBuilder<T> notInIfNotEmpty(String column, Collection<?> values) {
        return addCondition(() -> values != null && !values.isEmpty(), 
                           w -> w.notIn(column, values));
    }
    
    /**
     * 左模糊查询（非空时添加）
     */
    public QueryConditionBuilder<T> likeLeftIfNotEmpty(String column, String value) {
        return addCondition(() -> value != null && !value.trim().isEmpty(), 
                           w -> w.likeLeft(column, value));
    }
    
    /**
     * 右模糊查询（非空时添加）
     */
    public QueryConditionBuilder<T> likeRightIfNotEmpty(String column, String value) {
        return addCondition(() -> value != null && !value.trim().isEmpty(), 
                           w -> w.likeRight(column, value));
    }
    
    /**
     * 不等于条件（非空时添加）
     */
    public QueryConditionBuilder<T> neIfNotNull(String column, Object value) {
        return addCondition(() -> value != null, 
                           w -> w.ne(column, value));
    }
    
    /**
     * 大于条件（非空时添加）
     */
    public QueryConditionBuilder<T> gtIfNotNull(String column, Comparable<?> value) {
        return addCondition(() -> value != null, 
                           w -> w.gt(column, value));
    }
    
    /**
     * 小于条件（非空时添加）
     */
    public QueryConditionBuilder<T> ltIfNotNull(String column, Comparable<?> value) {
        return addCondition(() -> value != null, 
                           w -> w.lt(column, value));
    }
    
    /**
     * 升序排序
     */
    public QueryConditionBuilder<T> orderByAsc(String... columns) {
        wrapper.orderByAsc(Arrays.asList(columns));
        log.debug("Added ascending order: {}", String.join(", ", columns));
        return this;
    }
    
    /**
     * 降序排序
     */
    public QueryConditionBuilder<T> orderByDesc(String... columns) {
        wrapper.orderByDesc(Arrays.asList(columns));
        log.debug("Added descending order: {}", String.join(", ", columns));
        return this;
    }
    
    /**
     * 条件排序（当条件为真时添加升序排序）
     */
    public QueryConditionBuilder<T> orderByAscIf(boolean condition, String... columns) {
        if (condition) {
            orderByAsc(columns);
        }
        return this;
    }
    
    /**
     * 条件排序（当条件为真时添加降序排序）
     */
    public QueryConditionBuilder<T> orderByDescIf(boolean condition, String... columns) {
        if (condition) {
            orderByDesc(columns);
        }
        return this;
    }
    
    /**
     * 自定义条件
     */
    public QueryConditionBuilder<T> customCondition(BooleanSupplier condition, Consumer<QueryWrapper<T>> action) {
        return addCondition(condition, action);
    }
    
    /**
     * 添加条件的通用方法
     */
    private QueryConditionBuilder<T> addCondition(BooleanSupplier condition, Consumer<QueryWrapper<T>> action) {
        if (condition.getAsBoolean()) {
            action.accept(wrapper);
            log.debug("Added query condition: {}", wrapper.getTargetSql());
        }
        return this;
    }
    
    /**
     * 构建最终的QueryWrapper
     */
    public QueryWrapper<T> build() {
        return wrapper;
    }
    
    /**
     * 分组查询
     */
    public QueryConditionBuilder<T> groupBy(String... columns) {
        wrapper.groupBy(Arrays.asList(columns));
        log.debug("Added group by: {}", String.join(", ", columns));
        return this;
    }
    
    /**
     * HAVING条件
     */
    public QueryConditionBuilder<T> having(String sqlHaving, Object... params) {
        wrapper.having(sqlHaving, params);
        log.debug("Added having condition: {}", sqlHaving);
        return this;
    }
    
    /**
     * 选择字段
     */
    public QueryConditionBuilder<T> select(String... columns) {
        wrapper.select(columns);
        log.debug("Added select columns: {}", String.join(", ", columns));
        return this;
    }
    
    /**
     * 排除字段（需要传入实体类）
     */
    public QueryConditionBuilder<T> selectExclude(Class<T> entityClass, String... columns) {
        wrapper.select(entityClass, info -> !java.util.Arrays.asList(columns).contains(info.getColumn()));
        log.debug("Excluded columns: {}", String.join(", ", columns));
        return this;
    }
    
    /**
     * 限制查询数量
     */
    public QueryConditionBuilder<T> last(String lastSql) {
        wrapper.last(lastSql);
        log.debug("Added last SQL: {}", lastSql);
        return this;
    }
    
    /**
     * 批量添加等值条件
     */
    public QueryConditionBuilder<T> batchEq(java.util.Map<String, Object> conditions) {
        if (conditions != null && !conditions.isEmpty()) {
            conditions.forEach((column, value) -> {
                if (value != null) {
                    if (value instanceof String && !((String) value).trim().isEmpty()) {
                        wrapper.eq(column, value);
                    } else if (!(value instanceof String)) {
                        wrapper.eq(column, value);
                    }
                }
            });
            log.debug("Added batch equal conditions: {}", conditions.keySet());
        }
        return this;
    }
    
    /**
     * 批量添加模糊查询条件
     */
    public QueryConditionBuilder<T> batchLike(java.util.Map<String, String> conditions) {
        if (conditions != null && !conditions.isEmpty()) {
            conditions.forEach((column, value) -> {
                if (value != null && !value.trim().isEmpty()) {
                    wrapper.like(column, value);
                }
            });
            log.debug("Added batch like conditions: {}", conditions.keySet());
        }
        return this;
    }
    
    /**
     * OR条件组合
     */
    public QueryConditionBuilder<T> or(Consumer<QueryWrapper<T>> consumer) {
        wrapper.or(consumer);
        log.debug("Added OR condition group");
        return this;
    }
    
    /**
     * AND条件组合
     */
    public QueryConditionBuilder<T> and(Consumer<QueryWrapper<T>> consumer) {
        wrapper.and(consumer);
        log.debug("Added AND condition group");
        return this;
    }
    
    /**
     * 嵌套查询
     */
    public QueryConditionBuilder<T> nested(Consumer<QueryWrapper<T>> consumer) {
        wrapper.nested(consumer);
        log.debug("Added nested condition");
        return this;
    }
    
    /**
     * 直接应用到Consumer（用于PageUtil）
     */
    public Consumer<QueryWrapper<T>> toConsumer() {
        return existingWrapper -> {
            // 将当前构建的条件合并到传入的wrapper中
            existingWrapper.setEntity(wrapper.getEntity());
            if (wrapper.getSqlSegment() != null && !wrapper.getSqlSegment().isEmpty()) {
                existingWrapper.apply(wrapper.getSqlSegment());
            }
        };
    }
    
    /**
     * 获取当前构建的SQL片段（用于调试）
     */
    public String getSqlSegment() {
        return wrapper.getSqlSegment();
    }
    
    /**
     * 清空所有条件
     */
    public QueryConditionBuilder<T> clear() {
        wrapper.clear();
        log.debug("Cleared all conditions");
        return this;
    }
}