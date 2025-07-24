package lhl.hana.xk.review_multi.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("products")
@Getter
@Setter
public class Product {
    @TableId
    private Long id;
    private String parentAsin;
    private String title;
    private String mainCategory;
    private BigDecimal price;
    private BigDecimal averageRating;
    private Integer ratingNumber;
    private String store;
    private Integer inventory;
    private Byte shardId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
