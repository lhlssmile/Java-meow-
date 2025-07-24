package lhl.hana.xk.review_multi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lhl.hana.xk.review_multi.mapper.CategoryMapper;
import lhl.hana.xk.review_multi.pojo.po.Category;
import lhl.hana.xk.review_multi.service.ICategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements ICategoryService {
}
