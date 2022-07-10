package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.entity.Danmu;

import java.util.List;

/**
 * 创建在 2020/7/30 18:46
 */
public interface DanmuService extends IService<Danmu> {

    /**
     * @param [vid, second]
     * @return java.util.List<pers.czj.entity.Danmu>
     * @author czj
     * 返回对应弹幕
     * @date 2020/7/30 21:00
     */
    public List<Danmu> listDanmu(long vid, long showSecond);
}
