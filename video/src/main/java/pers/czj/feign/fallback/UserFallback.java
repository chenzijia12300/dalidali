package pers.czj.feign.fallback;

import org.springframework.stereotype.Component;
import pers.czj.common.CommonResult;
import pers.czj.dto.UserCollectionLogInputDto;
import pers.czj.feign.UserFeignClient;
import pers.czj.utils.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建在 2020/11/18 18:46
 */
@Component
public class UserFallback implements UserFeignClient {

    private Map<String,Object> tempUserInfoMap;

    private CommonResult failResult;

    public UserFallback() {
        tempUserInfoMap = new HashMap<>();
        tempUserInfoMap.put("username","UP_NAME");
        tempUserInfoMap.put("img","http://127.0.0.1:9000/image/noface.png");
        tempUserInfoMap.put("follow",Boolean.FALSE);
        tempUserInfoMap = Collections.unmodifiableMap(tempUserInfoMap);

        failResult = CommonResult.failed("调用用户模块失败");
    }

    @Override
    public int findCoinNumById(long id) {
        return -1;
    }

    @Override
    public CommonResult incrCoinNumById(long id, int num) {
        return CommonResult.failed("调用用户模块失败");
    }

    @Override
    public CommonResult dynamicCollection(UserCollectionLogInputDto dto) {
        return failResult;
    }

    @Override
    public CommonResult addDynamic(Map<String, Object> map) {
        return failResult;
    }

    @Override
    public Map<String, Object> findBasicInfoById(long uid, long followerUserId) {
        return tempUserInfoMap;
    }

    @Override
    public Long createUserIfNeeded(Map<String, String> map) {
        return -1l;
    }
}
