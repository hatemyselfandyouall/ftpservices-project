package com.wangxinenpu.springbootdemo.controller.solidity;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class TransInfoSave extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b5061002861002d640100000000026401000000009004565b610185565b600061100190508073ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001806020018481038452600a8152602001807f7472616e735f696e666f00000000000000000000000000000000000000000000815250602001848103835260028152602001807f6964000000000000000000000000000000000000000000000000000000000000815250602001848103825260098152602001807f696e666f5f6a736f6e00000000000000000000000000000000000000000000008152506020019350505050602060405180830381600087803b15801561014657600080fd5b505af115801561015a573d6000803e3d6000fd5b505050506040513d602081101561017057600080fd5b81019080805190602001909291905050505050565b610d44806101946000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306e63ff814610051578063fcd7e3c114610180575b600080fd5b34801561005d57600080fd5b506100fe600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610269565b6040518083815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610144578082015181840152602081019050610129565b50505050905090810190601f1680156101715780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561018c57600080fd5b506101e7600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610739565b6040518083815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561022d578082015181840152602081019050610212565b50505050905090810190601f16801561025a5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b60006060600080600061027a610c29565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156102e057600080fd5b505af11580156102f4573d6000803e3d6000fd5b505050506040513d602081101561030a57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260098152602001807f696e666f5f6a736f6e0000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156103dd5780820151818401526020810190506103c2565b50505050905090810190601f16801561040a5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561042a57600080fd5b505af115801561043e573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3688846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156104fd5780820151818401526020810190506104e2565b50505050905090810190601f16801561052a5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561054a57600080fd5b505af115801561055e573d6000803e3d6000fd5b505050506040513d602081101561057457600080fd5b810190808051906020019092919050505090506001811415156105f4577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8090506040805190810160405280600f81526020017fe4bf9de5ad98e5a4b1e8b4a5efbc8100000000000000000000000000000000008152509450945061072f565b60008273ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260028152602001807f6964000000000000000000000000000000000000000000000000000000000000815250602001915050600060405180830381600087803b15801561069657600080fd5b505af11580156106aa573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525060208110156106d457600080fd5b8101908080516401000000008111156106ec57600080fd5b8281019050602081018481111561070257600080fd5b815185600182028301116401000000008211171561071f57600080fd5b5050929190505050819150945094505b5050509250929050565b60006060600080600061074a610c29565b92508273ffffffffffffffffffffffffffffffffffffffff1663e8434e39878573ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156107cd57600080fd5b505af11580156107e1573d6000803e3d6000fd5b505050506040513d60208110156107f757600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156108a557808201518184015260208101905061088a565b50505050905090810190601f1680156108d25780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b1580156108f257600080fd5b505af1158015610906573d6000803e3d6000fd5b505050506040513d602081101561091c57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561099357600080fd5b505af11580156109a7573d6000803e3d6000fd5b505050506040513d60208110156109bd57600080fd5b810190808051906020019092919050505060001415610a39577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8090506040805190810160405280601d81526020017fe69caae69fa5e588b0e8afa56964e5afb9e5ba94e79a84e695b0e68dae00000081525094509450610c21565b8173ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b158015610aa957600080fd5b505af1158015610abd573d6000803e3d6000fd5b505050506040513d6020811015610ad357600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff16639c981fcb6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f696e666f5f6a736f6e0000000000000000000000000000000000000000000000815250602001915050600060405180830381600087803b158015610b8857600080fd5b505af1158015610b9c573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f820116820180604052506020811015610bc657600080fd5b810190808051640100000000811115610bde57600080fd5b82810190506020810184811115610bf457600080fd5b8151856001820283011164010000000082111715610c1157600080fd5b5050929190505050819150945094505b505050915091565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600a8152602001807f7472616e735f696e666f00000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610cd357600080fd5b505af1158015610ce7573d6000803e3d6000fd5b505050506040513d6020811015610cfd57600080fd5b810190808051906020019092919050505090508092505050905600a165627a7a72305820be5f7a0de7799658636bc32d45a59543095ecbeddd8fb57ec86309a1060506ac0029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"info_json\",\"type\":\"string\"}],\"name\":\"insert\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"id\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_INSERT = "insert";

    public static final String FUNC_SELECT = "select";

    @Deprecated
    protected TransInfoSave(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TransInfoSave(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TransInfoSave(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TransInfoSave(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> insert(String id, String info_json) {
        final Function function = new Function(
                FUNC_INSERT, 
                Arrays.<Type>asList(new Utf8String(id),
                new Utf8String(info_json)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void insert(String id, String info_json, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_INSERT, 
                Arrays.<Type>asList(new Utf8String(id),
                new Utf8String(info_json)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String insertSeq(String id, String info_json) {
        final Function function = new Function(
                FUNC_INSERT, 
                Arrays.<Type>asList(new Utf8String(id),
                new Utf8String(info_json)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, String> getInsertInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_INSERT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
                );
    }

    public Tuple2<BigInteger, String> getInsertOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_INSERT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<BigInteger, String>(

                (BigInteger) results.get(0).getValue(),
                (String) results.get(1).getValue()
                );
    }

    public RemoteCall<Tuple2<BigInteger, String>> select(String id) {
        final Function function = new Function(FUNC_SELECT,
                Arrays.<Type>asList(new Utf8String(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<BigInteger, String>>(
                new Callable<Tuple2<BigInteger, String>>() {
                    @Override
                    public Tuple2<BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, String>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue());
                    }
                });
    }

    @Deprecated
    public static TransInfoSave load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TransInfoSave(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TransInfoSave load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TransInfoSave(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TransInfoSave load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TransInfoSave(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TransInfoSave load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TransInfoSave(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TransInfoSave> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TransInfoSave.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<TransInfoSave> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TransInfoSave.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TransInfoSave> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TransInfoSave.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TransInfoSave> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TransInfoSave.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
