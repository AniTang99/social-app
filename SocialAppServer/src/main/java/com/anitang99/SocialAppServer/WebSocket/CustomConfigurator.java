package com.anitang99.SocialAppServer.WebSocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

/**
 *<h1>Custom Configurator</h1>
 *<p>
 *This class is used to register every websocket endpoint created with the rest of the Spring Application. 
 *
 * @author Anirudh Tangellapalli
 *
 */
public class CustomConfigurator extends ServerEndpointRegistration.Configurator implements ApplicationContextAware {

	/**
     * Beanfactory from Spring used to register the endpoint in the application context
     */
    private static volatile BeanFactory context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CustomConfigurator.context = applicationContext;
	}
	
	@Override
    public <T> T getEndpointInstance(Class<T> endpoint) throws InstantiationException {
        return context.getBean(endpoint);
    }

}
