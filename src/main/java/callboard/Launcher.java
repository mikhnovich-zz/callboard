package callboard;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;

import callboard.config.JerseyConfiguration;


public class Launcher
{

	private static final String JERSEY_SERVLET_NAME = "jersey-container-servlet";

	void start() throws Exception
	{

		String port = System.getenv("PORT");
		if (port == null || port.isEmpty())
		{
			port = "8082";
		}

		String contextPath = "";
		String appBase = ".";

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(Integer.parseInt(port));
		tomcat.getHost().setAppBase(appBase);

		Context context = tomcat.addContext(contextPath, appBase);
		Tomcat.addServlet(context, JERSEY_SERVLET_NAME, new ServletContainer(new JerseyConfiguration()));
		context.addServletMappingDecoded("/api/*", JERSEY_SERVLET_NAME);

		tomcat.start();
		tomcat.getServer().await();
	}

	public static void main(String[] args) throws Exception
	{
		new Launcher().start();
	}
}
