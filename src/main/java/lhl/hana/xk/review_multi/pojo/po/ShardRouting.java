package lhl.hana.xk.review_multi.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@TableName("shard_routing")
@Getter
@Setter
public class ShardRouting {
    @TableId(type = IdType.NONE)
    private String parentAsin;
    private Byte shardId;
    private LocalDateTime createdAt;
}