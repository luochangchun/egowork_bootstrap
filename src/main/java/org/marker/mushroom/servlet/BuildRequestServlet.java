package org.marker.mushroom.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.marker.mushroom.utils.EncryptUtil;
import org.marker.mushroom.utils.PropertyUtil;
import org.marker.mushroom.utils.SpringContextUtil;


public class BuildRequestServlet extends HttpServlet
{

	private static final PropertyUtil propertyUtil = (PropertyUtil) SpringContextUtil.getBean("propertyUtil");

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{

		response.setCharacterEncoding("GBK");

		final String userName = new String(request.getParameter("userName").getBytes("ISO-8859-1"), "UTF-8");
		final String aesPassword = request.getParameter("aesPassword");

		final String expressPassword = EncryptUtil.getInstance().AESdecode(aesPassword, EncryptUtil.KEY);

		final Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", userName);
		parameters.put("password", expressPassword);
		final String url = propertyUtil.getValue("tsb.login.url");


		final PrintWriter out = response.getWriter();
		final String sHtmlText = buildRequest(parameters, "post", "queren", url);
		out.println(sHtmlText);
	}

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	public String buildRequest(final Map<String, String> sParaTemp, final String strMethod, final String strButtonName,
			final String action)
	{
		//待请求参数数组
		final Map<String, String> sPara = sParaTemp;
		final List<String> keys = new ArrayList<>(sPara.keySet());

		final StringBuffer sbHtml = new StringBuffer();

		sbHtml.append(
				"<html lang=\"en\"><body> <form accept-charset=\"UTF-8\" target=\"\" id=\"requestsubmit\" name=\"requestsubmit\" action=\""
						+ action + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++)
		{
			final String name = keys.get(i);
			final String value = sPara.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\">");
		}

		//submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.getElementById('requestsubmit').submit();</script></body></html>");


		return sbHtml.toString();
	}

}
