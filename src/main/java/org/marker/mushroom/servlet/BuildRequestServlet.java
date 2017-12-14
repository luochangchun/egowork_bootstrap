package org.marker.mushroom.servlet;

import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.utils.EncryptUtil;
import org.marker.mushroom.utils.PropertyUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class BuildRequestServlet extends HttpServlet {

	private static final PropertyUtil propertyUtil = SpringContextHolder.getBean(PropertyUtil.class);

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {

		response.setCharacterEncoding("GBK");

		final String userName = new String(request.getParameter("userName").getBytes("ISO-8859-1"), "UTF-8");
		final String aesPassword = request.getParameter("aesPassword");

		final String expressPassword = EncryptUtil.getInstance().AESdecode(aesPassword, EncryptUtil.KEY);

		final Map<String, String> parameters = new HashMap<>();
		parameters.put("username", userName);
		parameters.put("password", expressPassword);
		final String url = propertyUtil.getValue("tsb.login.url");

		final PrintWriter out = response.getWriter();
		final String sHtmlText = buildRequest(parameters, "post", "queren", url);
		out.println(sHtmlText);
	}

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
		doGet(request, response);
	}

	public String buildRequest(final Map<String, String> sParaTemp,
							   final String strMethod,
							   final String strButtonName,
							   final String action) {

		final StringBuilder sbHtml = new StringBuilder();

		sbHtml.append("<html lang=\"en\"><body> ")
			  .append("<form accept-charset=\"UTF-8\" target=\"\" id=\"requestsubmit\" name=\"requestsubmit\" action=\"")
			  .append(action)
			  .append("\" method=\"")
			  .append(strMethod)
			  .append("\">");

		//待请求参数数组
		sParaTemp.keySet().forEach(name -> sbHtml.append("<input type=\"hidden\" name=\"")
												 .append(name)
												 .append("\" value=\"")
												 .append(sParaTemp.get(name))
												 .append("\">"));

		//submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"").append(strButtonName).append("\" style=\"display:none;\"></form>")
			  .append("<script>document.getElementById('requestsubmit').submit();</script></body></html>");

		return sbHtml.toString();
	}

}
