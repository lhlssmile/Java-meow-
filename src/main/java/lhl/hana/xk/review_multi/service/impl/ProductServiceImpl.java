package lhl.hana.xk.review_multi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lhl.hana.xk.review_multi.config.ThreadLocalHolder;
import lhl.hana.xk.review_multi.mapper.ProductMapper;
import lhl.hana.xk.review_multi.pojo.dto.ProductListDTO;
import lhl.hana.xk.review_multi.pojo.po.Product;
import lhl.hana.xk.review_multi.pojo.po.ShardRouting;
import lhl.hana.xk.review_multi.pojo.vo.ProductListVO;
import lhl.hana.xk.review_multi.service.IProductService;
import lhl.hana.xk.review_multi.service.IShardRoutingService;
import lhl.hana.xk.review_multi.util.PageUtil;
import lhl.hana.xk.review_multi.util.QueryConditionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    private final ModelMapper modelMapper;
    private final IShardRoutingService shardRoutingService;

    public ProductServiceImpl(ModelMapper modelMapper, IShardRoutingService shardRoutingService) {
        this.modelMapper = modelMapper;
        this.shardRoutingService = shardRoutingService;
    }

    @Override
    public Page<ProductListVO> list(ProductListDTO dto) {
        // 使用路由表查询分片ID
        if (dto.getParentAsin() != null && !dto.getParentAsin().isEmpty()) {
            ShardRouting shardRouting = shardRoutingService.getById(dto.getParentAsin());
            if (shardRouting != null) {
                ThreadLocalHolder.setShardKey((long) shardRouting.getShardId());
                log.info("Parent ASIN: {}, Shard ID from routing table: {}", dto.getParentAsin(), shardRouting.getShardId());
            } else {
                // 如果路由表中没有找到，使用默认分片0
                ThreadLocalHolder.setShardKey(0L);
                log.warn("Parent ASIN: {} not found in routing table, using default shard 0", dto.getParentAsin());
            }
        } else {
            // 如果没有parentAsin，使用默认分片0
            ThreadLocalHolder.setShardKey(0L);
            log.info("No parent ASIN provided, using default shard 0");
        }
        try {
            // 使用QueryConditionBuilder构建查询条件
            return PageUtil.queryPage(dto, dto.getPageNum(), dto.getPageSize(), getBaseMapper(),
                    QueryConditionBuilder.<Product>create()
                            .eqIfNotEmpty("main_category", dto.getMainCategory())
                            .priceBetween("price", dto.getMinPrice(), dto.getMaxPrice())
                            .toConsumer(),
                    modelMapper, ProductListVO.class);
        } finally {
            ThreadLocalHolder.removeShardKey();
        }
    }
}