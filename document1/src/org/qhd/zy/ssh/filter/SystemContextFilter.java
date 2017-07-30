package org.qhd.zy.ssh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;

public class SystemContextFilter implements Filter {
	private int pagerSize;
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		int pagerOffest=0;
		try {
			try {
				pagerOffest=Integer.parseInt(req.getParameter("pager.offset"));
			} catch (NumberFormatException e) {}
			SystemContext.setPageSize(pagerSize);
			SystemContext.setOffset(pagerOffest);
			HttpServletRequest reqs=(HttpServletRequest) req;
			String path=reqs.getSession().getServletContext().getRealPath("");
			//本来下面这个地址就用上面表示的,但STS用了虚拟路径,就只有用下面的觉得路径得出
			path="D:\\JAVA\\document\\document1\\WebContent";
			SystemContext.setRealPath(path);
			//这里要session中的
			User u=(User)reqs.getSession().getAttribute("loginUser");
			if(u!=null)SystemContext.setLoginUser(u);
			chain.doFilter(req, resp);
		} finally{
			SystemContext.removePagerSize();
			SystemContext.removeOffest();
			SystemContext.removeLoginUser();
			SystemContext.removeRealPath();
		}
	}

	@Override
	public void init(FilterConfig cig) throws ServletException {
		try {
			pagerSize=Integer.parseInt(cig.getInitParameter("pagerSize"));
		} catch (NumberFormatException e) {
			pagerSize=10;
		}
		
	}

}
