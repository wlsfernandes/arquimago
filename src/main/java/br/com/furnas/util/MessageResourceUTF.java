package br.com.furnas.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Addson Bastos
 * 
 */
public class MessageResourceUTF extends ResourceBundle implements Filter {

	protected static final String BUNDLE_NAME = "mensagens";

	protected static final String BUNDLE_EXTENSION = "properties";
	protected static final Locale LOCALE = new Locale("pt", "BR");
	
	
	protected static final Control UTF8_CONTROL = new UTF8Control();

	public MessageResourceUTF() {
		setParent(ResourceBundle.getBundle(BUNDLE_NAME, LOCALE, UTF8_CONTROL));
	}

	@Override
	protected Object handleGetObject(String key) {
		return parent.getObject(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Enumeration getKeys() {
		return parent.getKeys();
	}

	protected static class UTF8Control extends Control {
		public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
				} finally {
					stream.close();
				}
			}
			return bundle;
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
