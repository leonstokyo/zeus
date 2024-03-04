package com.st.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.st.admin.mapper.ConfigMapper;
import com.st.admin.domain.Config;
import com.st.admin.service.ConfigService;

/**
 * @author leon
 * @date 2024/2/24 01:06   
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{

}
