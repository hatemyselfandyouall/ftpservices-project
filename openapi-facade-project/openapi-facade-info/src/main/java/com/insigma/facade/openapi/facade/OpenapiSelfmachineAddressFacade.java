 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiSelfmachineAddress;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressDetailVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressListVO;
import com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress.OpenapiSelfmachineAddressSaveVO;


public interface OpenapiSelfmachineAddressFacade{

	PageInfo<OpenapiSelfmachineAddress> getOpenapiSelfmachineAddressList(OpenapiSelfmachineAddressListVO listVO, Long userId);

    OpenapiSelfmachineAddress getOpenapiSelfmachineAddressDetail(OpenapiSelfmachineAddressDetailVO detailVO);

    OpenapiSelfmachineAddress saveOpenapiSelfmachineAddress(OpenapiSelfmachineAddressSaveVO saveVO, Long userId, String userName);

    Integer deleteOpenapiSelfmachineAddress(OpenapiSelfmachineAddressDeleteVO deleteVO, Long userId);

}

 
