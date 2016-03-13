package org.jboss.samples.rs.webservices;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class WsApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public WsApplication(){
	     singletons.add(new Prueba());
	}
	@Override
	public Set<Class<?>> getClasses() {
	     return empty;
	}
	@Override
	public Set<Object> getSingletons() {
	     return singletons;
	}
}
