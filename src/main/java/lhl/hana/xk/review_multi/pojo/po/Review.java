package lhl.hana.xk.review_multi.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("reviews")
@Getter
@Setter
public class Review {
    @TableId
    private Long id;
    private String parentAsin;
    private String userId;
    private BigDecimal rating;
    private String title;
    private String reviewText;
    private Long timestamp;
    private Integer helpfulVote;
    private Byte verifiedPurchase;
    private Byte shardId;
    private LocalDateTime createdAt;
}
