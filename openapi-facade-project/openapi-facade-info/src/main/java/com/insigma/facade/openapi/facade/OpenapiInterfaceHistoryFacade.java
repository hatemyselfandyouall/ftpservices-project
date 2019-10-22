 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiInterfaceHistory;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryDetailVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistoryListVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistorySaveVO;


public interface OpenapiInterfaceHistoryFacade{

	PageInfo<OpenapiInterfaceHistory> getOpenapiInterfaceHistoryList(OpenapiInterfaceHistoryListVO listVO);

    OpenapiInterfaceHistory getOpenapiInterfaceHistoryDetail(OpenapiInterfaceHistoryDetailVO detailVO);

    Integer saveOpenapiInterfaceHistory(OpenapiInterfaceHistorySaveVO saveVO);

    Integer deleteOpenapiInterfaceHistory(OpenapiInterfaceHistoryDeleteVO deleteVO);

	 

}

 
