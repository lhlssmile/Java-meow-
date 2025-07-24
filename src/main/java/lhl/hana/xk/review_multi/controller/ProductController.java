package lhl.hana.xk.review_multi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lhl.hana.xk.review_multi.pojo.dto.ProductListDTO;
import lhl.hana.xk.review_multi.service.IProductService;
import lhl.hana.xk.review_multi.util.ResponseMineUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Tag(name = "商品管理", description = "商品相关的API接口")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/list")
    @Operation(summary = "获取商品列表", description = "根据条件分页查询商品列表")
    public ResponseMineUtil<?> list(
            @Parameter(description = "查询条件", required = true)
            @RequestBody ProductListDTO dto) {
        // mybatis-plus 查询 分页
        return ResponseMineUtil.success(productService.list(dto));
    }
}
