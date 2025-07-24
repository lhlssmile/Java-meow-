package lhl.hana.xk.review_multi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lhl.hana.xk.review_multi.pojo.dto.ProductListDTO;
import lhl.hana.xk.review_multi.pojo.po.Product;

public interface IProductService extends IService<Product> {
    Page<?> list(ProductListDTO dto);
}
