package lhl.hana.xk.review_multi.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "商品列表查询条件")
public class ProductListDTO {
    
    @Schema(description = "主分类", example = "Electronics")
    private String mainCategory;
    
    @Schema(description = "最低价格", example = "10.00")
    private BigDecimal minPrice;
    
    @Schema(description = "最高价格", example = "100.00")
    private BigDecimal maxPrice;
    
    @Schema(description = "父ASIN（分片键，用于分表查询）", example = "B0000086D1")
    private String parentAsin;
    
    @Schema(description = "页码", example = "1", defaultValue = "1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小", example = "10", defaultValue = "10")
    private Integer pageSize = 10;
}