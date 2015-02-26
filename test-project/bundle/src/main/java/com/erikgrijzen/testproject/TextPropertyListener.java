package com.erikgrijzen.testproject;

import java.util.Arrays;

import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TextPropertyListener implements EventListener {
	
	private final Logger log = LoggerFactory.getLogger(TextPropertyListener.class);
	
	@Reference
	private SlingRepository repository;
	
	private Session session;
	
	private ObservationManager observationManager;
	
	protected void activate(ComponentContext context) throws Exception {
		session = repository.loginAdministrative(null);
		
		if (repository.getDescriptor(Repository.OPTION_OBSERVATION_SUPPORTED).equals("true")) {
            observationManager = session.getWorkspace().getObservationManager();
            
            final String[] types = { "nt:unstructured" };
            final String path = "/content/testproject/";
            
            observationManager.addEventListener(this, Event.PROPERTY_ADDED | Event.PROPERTY_CHANGED, path, true, null, types, true);
            
            log.info("Observing property changes to {} nodes under {}", Arrays.asList(types), path);
		}
	}
	
	protected void deactivate(ComponentContext componentContext) throws RepositoryException {
        if (observationManager != null) {
            observationManager.removeEventListener(this);
        }
        
        if (session != null) {
            session.logout();
            session = null;
        }
    }
	
	public void onEvent(EventIterator itr) {
		while (itr.hasNext()) {
			Event event = itr.nextEvent();
			
			try {
				log.info("********** New property event: {}", event.getPath());
				
				Property property = session.getProperty(event.getPath());
				
				if (property.getName().equalsIgnoreCase("jcr:text")) {
					property.setValue(new StringBuffer(property.getString()).reverse().toString());
					session.save();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}	
	}
}
	