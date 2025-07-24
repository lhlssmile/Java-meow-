package lhl.hana.xk.review_multi.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lhl.hana.xk.review_multi.mapper.ShardRoutingMapper;
import lhl.hana.xk.review_multi.pojo.po.ShardRouting;
import lhl.hana.xk.review_multi.service.IShardRoutingService;
import org.springframework.stereotype.Service;

@Service
public class ShardRoutingServiceImpl extends ServiceImpl<ShardRoutingMapper, ShardRouting>
        implements IShardRoutingService {
}
