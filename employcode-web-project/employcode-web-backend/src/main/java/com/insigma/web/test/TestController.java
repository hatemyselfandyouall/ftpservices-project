package com.insigma.web.test;

import com.insigma.contract.Asset;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

	@RequestMapping("/ajax" )
	public String ajax(ModelMap modelMap) {
		return "test/ajax";
	}

	@RequestMapping(value = "testBlockChain",method = RequestMethod.GET)
	public String testBlockChain(){
//        for (int i=0;i<10;i++) {
//            redisTemplate.opsForValue().set(i+"","缓存测试"+i);
//        }
		try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            Service service = context.getBean(Service.class);
            service.run();
            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setChannelService(service);
            channelEthereumService.setTimeout(10000);
            Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
            Credentials credentials = Credentials.create(Keys.createEcKeyPair());
            //准备部署和调用合约的参数
            Asset asset = Asset.deploy(web3j, credentials, new StaticGasProvider(new BigInteger("1"), new BigInteger("10000"))).send();
            Tuple2<BigInteger, BigInteger> result = asset.select("1").send();
// register接口调用
            TransactionReceipt receipt = asset.register("1", new BigInteger("1")).send();
		}catch (Exception e){
			log.error("测试区块链异常",e);
			return "测试区块链异常"+e;
		}
		return "测试区块链成功";
	}

	public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            Service service = context.getBean(Service.class);
            service.run();

            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setChannelService(service);
// 初始化Web3j对象
            Web3j web3j = Web3j.build(channelEthereumService, 1);
// 初始化Credentials对象
            Credentials credentials = Credentials.create(Keys.createEcKeyPair());
        }catch (Exception e){
            log.error("测试区块链异常",e);
        }
	}
}
