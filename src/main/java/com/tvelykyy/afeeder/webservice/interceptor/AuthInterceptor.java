package com.tvelykyy.afeeder.webservice.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

public class AuthInterceptor extends AbstractSoapInterceptor {
	
	public AuthInterceptor() {
        super(Phase.RECEIVE);
    }
	@Override
	public void handleMessage(SoapMessage arg0) throws Fault {
		throw new Fault(new Exception("Yeah"));
	}

}
