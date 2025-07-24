package lhl.hana.xk.review_multi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lhl.hana.xk.review_multi.pojo.po.Product;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * QueryConditionBuilder使用示例
 * 展示各种查询条件的构建方式
 * 
 * @author hana
 */
@Slf4j
public class QueryConditionBuilderExample {
    
    /**
     * 基础查询示例
     */
    public void basicQueryExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotEmpty("main_category", "Electronics")
                .eqIfNotNull("status", 1)
                .likeIfNotEmpty("title", "iPhone")
                .build();
        
        log.info("基础查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 价格区间查询示例
     */
    public void priceRangeExample() {
        BigDecimal minPrice = new BigDecimal("100.00");
        BigDecimal maxPrice = new BigDecimal("500.00");
        
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .priceBetween("price", minPrice, maxPrice)
                .orderByDesc("price")
                .build();
        
        log.info("价格区间查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 时间区间查询示例
     */
    public void timeRangeExample() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(30);
        LocalDateTime endTime = LocalDateTime.now();
        
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .timeBetween("create_time", startTime, endTime)
                .orderByDesc("create_time")
                .build();
        
        log.info("时间区间查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 复杂条件查询示例
     */
    public void complexQueryExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotEmpty("main_category", "Electronics")
                .inIfNotEmpty("brand", Arrays.asList("Apple", "Samsung", "Huawei"))
                .gtIfNotNull("rating", 4.0)
                .likeIfNotEmpty("title", "phone")
                .neIfNotNull("status", 0)
                .orderByDesc("rating", "create_time")
                .build();
        
        log.info("复杂查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 批量条件查询示例
     */
    public void batchConditionExample() {
        Map<String, Object> eqConditions = new HashMap<>();
        eqConditions.put("status", 1);
        eqConditions.put("is_active", true);
        eqConditions.put("category_id", 100);
        
        Map<String, String> likeConditions = new HashMap<>();
        likeConditions.put("title", "phone");
        likeConditions.put("description", "smartphone");
        
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .batchEq(eqConditions)
                .batchLike(likeConditions)
                .orderByAsc("price")
                .build();
        
        log.info("批量条件查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * OR条件查询示例
     */
    public void orConditionExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotEmpty("main_category", "Electronics")
                .or(w -> w.like("title", "phone").or().like("title", "tablet"))
                .gtIfNotNull("price", new BigDecimal("100"))
                .orderByDesc("create_time")
                .build();
        
        log.info("OR条件查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 嵌套条件查询示例
     */
    public void nestedConditionExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotEmpty("status", "1")
                .nested(w -> w.like("title", "phone").and(w2 -> w2.gt("price", 100).lt("price", 1000)))
                .orderByDesc("rating")
                .build();
        
        log.info("嵌套条件查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 分组聚合查询示例
     */
    public void groupByExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .select("main_category", "COUNT(*) as count", "AVG(price) as avg_price")
                .eqIfNotNull("status", 1)
                .groupBy("main_category")
                .having("COUNT(*) > {0}", 10)
                .orderByDesc("count")
                .build();
        
        log.info("分组聚合查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 条件排序示例
     */
    public void conditionalSortExample() {
        boolean sortByPrice = true;
        boolean sortByRating = false;
        
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotEmpty("main_category", "Electronics")
                .orderByDescIf(sortByPrice, "price")
                .orderByAscIf(sortByRating, "rating")
                .orderByDesc("create_time") // 默认排序
                .build();
        
        log.info("条件排序查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 限制查询结果示例
     */
    public void limitExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotEmpty("main_category", "Electronics")
                .orderByDesc("rating")
                .last("LIMIT 10")
                .build();
        
        log.info("限制查询结果SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 字段选择示例
     */
    public void selectFieldsExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .select("id", "title", "price", "rating")
                .eqIfNotEmpty("main_category", "Electronics")
                .orderByDesc("rating")
                .build();
        
        log.info("字段选择查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 自定义条件示例
     */
    public void customConditionExample() {
        String searchKeyword = "phone";
        
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                .eqIfNotNull("status", 1)
                .customCondition(
                    () -> searchKeyword != null && !searchKeyword.trim().isEmpty(),
                    w -> w.and(w2 -> w2.like("title", searchKeyword).or().like("description", searchKeyword))
                )
                .orderByDesc("rating")
                .build();
        
        log.info("自定义条件查询SQL: {}", wrapper.getSqlSegment());
    }
    
    /**
     * 链式调用完整示例
     */
    public void fullChainExample() {
        QueryWrapper<Product> wrapper = QueryConditionBuilder.<Product>create()
                // 基础条件
                .eqIfNotEmpty("main_category", "Electronics")
                .eqIfNotNull("status", 1)
                .neIfNotNull("is_deleted", 1)
                
                // 价格条件
                .priceBetween("price", new BigDecimal("50"), new BigDecimal("2000"))
                
                // 文本搜索
                .likeIfNotEmpty("title", "smartphone")
                .likeRightIfNotEmpty("brand", "Apple")
                
                // 集合条件
                .inIfNotEmpty("category_id", Arrays.asList(1, 2, 3, 4, 5))
                .notInIfNotEmpty("brand_id", Arrays.asList(99, 100))
                
                // 数值比较
                .geIfNotNull("rating", 4.0)
                .ltIfNotNull("review_count", 10000)
                
                // 时间条件
                .timeBetween("create_time", 
                    LocalDateTime.now().minusMonths(6), 
                    LocalDateTime.now())
                
                // 复杂条件组合
                .or(w -> w.like("title", "iPhone").or().like("title", "Samsung"))
                
                // 排序
                .orderByDesc("rating")
                .orderByAsc("price")
                .orderByDesc("create_time")
                
                .build();
        
        log.info("完整链式调用查询SQL: {}", wrapper.getSqlSegment());
    }
}