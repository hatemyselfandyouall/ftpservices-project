 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiDictionary;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryDeleteVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryDetailVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionaryListVO;
import com.insigma.facade.openapi.vo.OpenapiDictionary.OpenapiDictionarySaveVO;


public interface OpenapiDictionaryFacade{

	PageInfo<OpenapiDictionary> getOpenapiDictionaryList(OpenapiDictionaryListVO listVO);

    OpenapiDictionary getOpenapiDictionaryDetail(OpenapiDictionaryDetailVO detailVO);

    Integer saveOpenapiDictionary(OpenapiDictionarySaveVO saveVO);

    Integer deleteOpenapiDictionary(OpenapiDictionaryDeleteVO deleteVO);

    String getValueByCode(String code);
	 

}

 
