package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.czj.entity.PlayNumTab;
import pers.czj.mapper.PlayNumTabMapper;
import pers.czj.service.PlayNumTabService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 创建在 2020/7/17 22:53
 */
@Service
public class PlayNumTabServiceImpl extends ServiceImpl<PlayNumTabMapper, PlayNumTab> implements PlayNumTabService {


    @Override
    public boolean addAll(Set<ZSetOperations.TypedTuple> set) {

        List<PlayNumTab> playNumTabs = new ArrayList<>();
        Iterator<ZSetOperations.TypedTuple> iterator = set.iterator();
        ZSetOperations.TypedTuple typedTuple = null;
        PlayNumTab playNumTab = null;
        int len = 0;
        while (iterator.hasNext()){
            typedTuple = iterator.next();
            playNumTab = new PlayNumTab(Long.parseLong(typedTuple.getValue().toString()),typedTuple.getScore().longValue());
            playNumTabs.add(playNumTab);
            if (playNumTabs.size()==100){
                saveBatch(playNumTabs);
                playNumTabs.clear();
            }
        }
        if (!CollectionUtils.isEmpty(playNumTabs)){
            saveBatch(playNumTabs);
        }
        return true;
    }
}
