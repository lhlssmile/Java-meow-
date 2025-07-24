package lhl.hana.xk.review_multi.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductListVO {
    private Long id;
    private String parentAsin;
    private String title;
    private String mainCategory;
    private BigDecimal price;
    private BigDecimal averageRating;
    private Integer ratingNumber;
    private String store;
    private Integer inventory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}