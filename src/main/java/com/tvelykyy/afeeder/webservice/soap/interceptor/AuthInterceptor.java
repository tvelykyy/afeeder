package com.tvelykyy.afeeder.webservice.soap.interceptor;

import java.util.List;
import java.util.TreeMap;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;

import com.tvelykyy.afeeder.domain.User;
import com.tvelykyy.afeeder.service.UserService;
import com.tvelykyy.afeeder.webservice.Const;

public class AuthInterceptor extends AbstractPhaseInterceptor<Message> {
	@Autowired
	UserService userService;
	
	public AuthInterceptor() {
        super(Phase.RECEIVE);
    }
	@Override
	public void handleMessage(Message message) throws Fault {
		String httpRequestMethod = (String) message.get(Message.HTTP_REQUEST_METHOD);
		
		//requesting wsdl goes through GET, so there is no need to check authority here
		if (httpRequestMethod.equals("POST")) {
			List<Object> tokenList = ((TreeMap<String, List<Object>>)message.get(Message.PROTOCOL_HEADERS))
				.get(Const.AUTH_TOKEN);
			
			if(tokenList == null) {
				throwFault();
			}
			
			String token = (String) tokenList.get(0);
			User user = userService.getUserByToken(token);
			if (user == null) {
				throwFault();
			}
			
			boolean isTokenValid = userService.isTokenValid(user.getId());
			if (!isTokenValid) {
				throwFault();
			}
			
			//Resetting AuthToken to contain userId
			tokenList.set(0, user.getId());
		}
	}
	private void throwFault() {
		throw new Fault(new Exception(Const.INVALID_TOKEN));
	}

}
