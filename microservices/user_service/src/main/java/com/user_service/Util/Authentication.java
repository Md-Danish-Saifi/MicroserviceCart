package com.user_service.Util;
public class Authentication 
{
	private String token = "dANdplbQ9e2ncUX7D4A1";
	private String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	

	public String getToken() {
		return token;
	}


	public Authentication getAuthentication(String userAgent)
	{
		Authentication authentication	=	new Authentication();
		if(userAgent.equals("dANdplbQ9e2ncUX7D4A1"))
		{
			authentication.setResult(MsgResponses.AUTH);
			return authentication;
		}
		else
		{
			authentication.setResult(MsgResponses.NOTAUTH);
			return authentication;
		}
	}
}
