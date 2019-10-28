package com.insigma.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.facade.OpenapiAuditFacade;
import com.insigma.facade.openapi.po.OpenapiAudit;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditDetailVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditListVO;
import com.insigma.facade.openapi.vo.OpenapiAudit.OpenapiAuditSaveVO;
import com.insigma.mapper.OpenapiAuditMapper;
import com.insigma.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


public class OpenapiAuditServiceImpl implements OpenapiAuditFacade {

    @Autowired
    OpenapiAuditMapper openapiAuditMapper;

    /**
     * 根据条件查询审核信息列表
     * @param openapiAuditListVO
     * @return
     */
    @Override
    public PageInfo<OpenapiAudit> getOpenapiAuditList(OpenapiAuditListVO openapiAuditListVO) {
        if (openapiAuditListVO==null||openapiAuditListVO.getPageNum()==null||openapiAuditListVO.getPageSize()==null) {
            return null;
        }
        PageHelper.startPage(openapiAuditListVO.getPageNum().intValue(),openapiAuditListVO.getPageSize().intValue());
        Example example = new Example(OpenapiAudit.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(openapiAuditListVO.getOrganization())){
            criteria.andEqualTo("organize",openapiAuditListVO.getOrganization());
        }
        if (!StringUtils.isEmpty(openapiAuditListVO.getApplication())){
            criteria.andEqualTo("application",openapiAuditListVO.getApplication());
        }
        if (openapiAuditListVO.getAuditStatus() != null){
            criteria.andEqualTo("auditStatus",openapiAuditListVO.getAuditStatus());
        }
        if(openapiAuditListVO.getOrganization() != null){
           if(openapiAuditListVO.getPending() == 0){    //待处理
               criteria.andEqualTo("auditStatus",2);
           }
            if(openapiAuditListVO.getPending() == 1){  //已处理
                criteria.andCondition(" audit_status != 2 ");
            }
        }
        if (!StringUtils.isEmpty(openapiAuditListVO.getKeyWord())) {
            criteria.andCondition("(interface_name like '%" + openapiAuditListVO.getKeyWord() + "%'" + "or contact like '%"
                    + openapiAuditListVO.getKeyWord() + "%')");
        }
        if((openapiAuditListVO.getStartDate() != null) || (openapiAuditListVO.getEndDate() != null)){
            criteria.andBetween("aTime",openapiAuditListVO.getStartDate(),openapiAuditListVO.getEndDate());
        }
        List<OpenapiAudit> openapiAuditList=openapiAuditMapper.selectByExample(example);
        PageInfo<OpenapiAudit> openapiAuditPageInfo=new PageInfo<>(openapiAuditList);
        return openapiAuditPageInfo;
    }

    /**
     * 根据id查询审核信息
     * @param openapiAuditDetailVO
     * @return
     */
    @Override
    public OpenapiAudit getOpenapiAuditDetail(OpenapiAuditDetailVO openapiAuditDetailVO) {
        if (openapiAuditDetailVO==null||openapiAuditDetailVO.getId()==null) {
            return null;
        };
        OpenapiAudit openapiAudit=openapiAuditMapper.selectByPrimaryKey(openapiAuditDetailVO.getId());
        return openapiAudit;
    }

    /**
     * 新增或修改审核信息
     * @param openapiAuditSaveVO
     * @return
     */
    @Override
    public Integer saveOpenapiAudit(OpenapiAuditSaveVO openapiAuditSaveVO) {
        if (openapiAuditSaveVO==null){
            return 0;
        }
        OpenapiAudit openapiAudit= JSONUtil.convert(openapiAuditSaveVO,OpenapiAudit.class);
        if (openapiAudit.getId()==null){
            openapiAudit.setATime(new Date());  //申请时间
            openapiAudit.setAuditStatus(2);    //新增时状态为待审核2
            return openapiAuditMapper.insertSelective(openapiAudit);
        }else {
            openapiAudit.setAuditTime(new Date());  //审核时间
            openapiAudit.setAuditId(openapiAuditSaveVO.getUserId());   //审核人id
            openapiAudit.setAuditor(openapiAuditSaveVO.getUserName());  //审核人
            Example example=new Example(OpenapiAudit.class);
            example.createCriteria().andEqualTo("id",openapiAudit.getId());
            return openapiAuditMapper.updateByExampleSelective(openapiAudit,example);
        }
    }

    @Override
    public Integer deleteOpenapiAudit(OpenapiAuditDeleteVO openapiAuditDeleteVO) {
        if (openapiAuditDeleteVO==null||openapiAuditDeleteVO.getId()==null){
            return 0;
        }
        OpenapiAudit openapiAudit=new OpenapiAudit();
        //openapiAudit.setModifyTime(new Date());
        //openapiAudit.setIsDelete(openapiAuditDeleteVO.getIsDelete());
        Example example=new Example(OpenapiAudit.class);
        example.createCriteria().andEqualTo("id",openapiAuditDeleteVO.getId());
        return openapiAuditMapper.updateByExampleSelective(openapiAudit,example);
    }
}
