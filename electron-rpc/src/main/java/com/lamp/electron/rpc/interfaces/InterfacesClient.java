package com.lamp.electron.rpc.interfaces;

import java.util.concurrent.atomic.AtomicInteger;

import com.lamp.electron.base.common.ability.config.RpcRequestConfig;
import com.lamp.electron.base.common.invoker.AgreementResponse;
import com.lamp.electron.base.common.invoker.ElectronRequest;
import com.lamp.electron.base.common.invoker.ElectronResponse;
import com.lamp.electron.base.common.invoker.Invoker;
import com.lamp.electron.base.common.perception.Perception;
import com.lamp.electron.base.common.register.data.NetworkAddress;
import com.lamp.electron.rpc.api.RpcHandle;

public class InterfacesClient implements Invoker{

	private RpcHandle rpcHandle;
	
	public InterfacesClient( NetworkAddress networkAddress, RpcHandle rpcHandle,
			Perception<RpcRequestConfig> rpcRequestConfig) {
		this.rpcHandle = rpcHandle;
	}
	
	@Override
	public ElectronResponse run(ElectronRequest electronRequest, ElectronResponse electronResponse, Invoker invoker) {

		return null;
	}
	
	
	class InterfacesAgreementResponse implements AgreementResponse{

		private AtomicInteger atomicInteger = new AtomicInteger();
		
		private ElectronRequest mainElectronRequest;
		
		@Override
		public void reply(ElectronResponse electronResponse, ElectronRequest electronRequest) {
			
		}
		
	}

}
