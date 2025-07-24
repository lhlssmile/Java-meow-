package lhl.hana.xk.review_multi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.modelmapper.ModelMapper;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PageUtil {

    /**
     * 通用的分页查询方法
     *
     * @param dto        查询条件DTO
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param mapper     MyBatis-Plus Mapper
     * @param querySetup 查询条件设置
     * @param modelMapper ModelMapper实例
     * @param voClass    目标VO类
     * @param <T>        实体类型
     * @param <V>        VO类型
     * @param <D>        DTO类型
     * @return 分页VO结果
     */
    public static <T, V, D> Page<V> queryPage(D dto, int pageNum, int pageSize, BaseMapper<T> mapper,
                                              Consumer<QueryWrapper<T>> querySetup, ModelMapper modelMapper, Class<V> voClass) {
        Page<T> page = new Page<>(pageNum, pageSize);
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        querySetup.accept(wrapper);
        Page<T> result = mapper.selectPage(page, wrapper);
        Page<V> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(result.getRecords().stream()
                .map(entity -> modelMapper.map(entity, voClass))
                .collect(Collectors.toList()));
        return voPage;
    }
}