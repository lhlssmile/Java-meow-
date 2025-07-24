package lhl.hana.xk.review_multi.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@TableName("categories")
@Setter
public class Category {
    @TableId
    private Integer id;

    private String parentAsin;

    private String category;

    private Byte categoryLevel;

    private Byte shardId;
}
