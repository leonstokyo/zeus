package com.st.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.st.admin.mapper.WorkIssueMapper;
import com.st.admin.domain.WorkIssue;
import com.st.admin.service.WorkIssueService;

/**
 * @author leon
 * @date 2024/2/24 01:06   
 */
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

}
