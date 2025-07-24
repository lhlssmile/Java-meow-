package lhl.hana.xk.review_multi.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 动态表名插件 - 必须在分页插件之前添加
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();

        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            Long shardKey = ThreadLocalHolder.getShardKey();
            int shardIndex = shardKey != null ? shardKey.intValue() : 0;
            return switch (tableName) {
                case "categories" -> "categories_" + shardIndex;
                case "products" -> "products_" + shardIndex;
                case "reviews" -> "reviews_" + shardIndex;
                default -> tableName;
            };
        });
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        
        // 分页插件 - 在动态表名插件之后添加
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(1000L); // 设置最大查询数量限制
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        
        return interceptor;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}