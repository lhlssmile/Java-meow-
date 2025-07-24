package lhl.hana.xk.review_multi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lhl.hana.xk.review_multi.mapper.ReviewMapper;
import lhl.hana.xk.review_multi.pojo.po.Review;
import lhl.hana.xk.review_multi.service.IReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
        implements IReviewService {
}
