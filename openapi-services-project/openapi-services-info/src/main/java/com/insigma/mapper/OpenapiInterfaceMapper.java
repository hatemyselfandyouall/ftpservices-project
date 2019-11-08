 
package com.insigma.mapper;

import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.vo.interfaceStatistics.InterfaceStatisticsVO;
import com.insigma.facade.openapi.vo.openapiInterface.OpenapiInterfaceStaaticsFieldsVO;
import com.insigma.facade.openapi.vo.openapiInterface.OpenapiInterfaceStaaticsVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface OpenapiInterfaceMapper extends Mapper<OpenapiInterface>{


    List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByYear(@Param("param") InterfaceStatisticsVO interfaceStatisticsVO);

    List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByWeek(@Param("param") InterfaceStatisticsVO interfaceStatisticsVO);

    List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByMonth(@Param("param") InterfaceStatisticsVO interfaceStatisticsVO);

    List<OpenapiInterfaceStaaticsFieldsVO> interfacePublishingTrendByDay(@Param("param") InterfaceStatisticsVO interfaceStatisticsVO);


}
