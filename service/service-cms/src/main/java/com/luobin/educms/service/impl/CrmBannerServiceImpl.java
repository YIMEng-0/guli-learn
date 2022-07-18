package com.luobin.educms.service.impl;

import com.luobin.educms.entity.CrmBanner;
import com.luobin.educms.mapper.CrmBannerMapper;
import com.luobin.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-06-28
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> crmBanners = baseMapper.selectList(null);

        return crmBanners;
    }
}
