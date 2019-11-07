 
package com.insigma.facade.openapi.facade;

import com.github.pagehelper.PageInfo;
import com.insigma.facade.openapi.po.OpenapiDictionary;
import com.insigma.facade.openapi.vo.OpenapiDictionary.*;


public interface OpenapiDictionaryFacade{

	PageInfo<OpenapiDictionary> getOpenapiDictionaryList(OpenapiDictionaryListVO listVO);

    OpenapiDictionary getOpenapiDictionaryDetail(OpenapiDictionaryDetailVO detailVO);

    Integer saveOpenapiDictionary(OpenapiDictionarySaveVO saveVO);

    Integer deleteOpenapiDictionary(OpenapiDictionaryDeleteVO deleteVO);

    Integer updateOpenapiDictionary(OpenapiDictionaryUpdateVO updateVO);

    String getValueByCode(String code);
	 

}

 
