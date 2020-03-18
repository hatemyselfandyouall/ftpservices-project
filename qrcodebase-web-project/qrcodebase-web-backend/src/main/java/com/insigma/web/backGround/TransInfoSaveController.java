//package com.insigma.web.backGround;
//
//import com.alibaba.fastjson.JSONObject;
//import com.insigma.constract.TransInfoSave;
//import com.insigma.facade.qrcodebase.dto.SaveJSONDTO;
//import com.insigma.facade.qrcodebase.dto.SaveResultVO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.fisco.bcos.web3j.crypto.Credentials;
//import org.fisco.bcos.web3j.protocol.Web3j;
//import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.fisco.bcos.web3j.tuples.generated.Tuple2;
//import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import star.vo.result.ResultVo;
//
//import java.math.BigInteger;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("selfMachineRequest")
//@Api(tags = "区块链信息保存模块")
//@Slf4j
//public class TransInfoSaveController {
//
//    @Autowired
//    private Web3j web3j;
//
//    @Autowired
//    private Credentials credentials;
//
//    private static final String constractAddress="0xd2033708b8fc9042119b2c809136a001b9bb9988";
//
//    @ApiOperation(value = "区块链保存Json信息")
//    @RequestMapping(value = "/saveJSON",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<SaveResultVO> saveJSON(@RequestBody SaveJSONDTO saveJSONDTO){
//        ResultVo resultVo=new ResultVo();
//        try {
//            TransInfoSave transInfoSave=TransInfoSave.load(constractAddress,web3j,credentials,new StaticGasProvider(new BigInteger("1"), new BigInteger("10000")));
//            String id= UUID.randomUUID().toString().replaceAll("-","");
//            TransactionReceipt receiptRemoteCall= transInfoSave.insert(id,saveJSONDTO.getJsonObject().toJSONString()).send();
//            log.info("result",receiptRemoteCall.getOutput());
//            Tuple2<BigInteger, String> result= transInfoSave.getInsertOutput(receiptRemoteCall);
//            BigInteger resultCode=result.getValue1();
//            if (0==resultCode.intValue()){
//                String transHash=receiptRemoteCall.getTransactionHash();
//                SaveResultVO saveResultVO=new SaveResultVO(transHash,id);
//                resultVo.setSuccess(true);
//                resultVo.setResult(saveResultVO);
//            }else {
//                log.error("区块链存储失败,result为{}，saveDTO为{}",result,saveJSONDTO);
//                resultVo.setResultDes("区块链存储失败");
//            }
//        }catch (Exception e){
//            log.error("区块链存储异常",e);
//            resultVo.setResultDes("区块链存储异常"+e.getMessage());
//        }
//        return resultVo;
//    }
//
//
//    @ApiOperation(value = "根据id获取JSON信息")
//    @RequestMapping(value = "/getJSONbyId",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
//    public ResultVo<JSONObject> getJSONbyId(@RequestParam(value = "id") String id){
//        ResultVo resultVo=new ResultVo();
//        try {
//            TransInfoSave transInfoSave=TransInfoSave.load(constractAddress,web3j,credentials,new StaticGasProvider(new BigInteger("1"), new BigInteger("10000")));
//            Tuple2<BigInteger, String> result= transInfoSave.select(id).send();
//            BigInteger resultCode=result.getValue1();
//            if (0==resultCode.intValue()){
//                resultVo.setSuccess(true);
//                resultVo.setResult(result.getValue2());
//            }else {
//                log.error("区块链获取失败,result为{}，id为{}",result,id);
//                resultVo.setResultDes("区块链获取失败");
//            }
//        }catch (Exception e){
//            log.error("区块链获取异常",e);
//            resultVo.setResultDes("区块链获取异常"+e.getMessage());
//        }
//        return resultVo;
//    }
//
//}
