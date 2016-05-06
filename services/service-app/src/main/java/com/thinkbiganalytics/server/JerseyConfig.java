package com.thinkbiganalytics.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {


  public JerseyConfig() {

    //Register Swagger
    Set<Class<?>> resources = new HashSet();
    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    registerClasses(resources);

    //TODO uncomment once we move away from Spring boot single app.
    //Spring Boot Jar plugin requires you to unpack all Jersey Controller jars
    //and also forces you to do package scanning at a more detailed level
    //TODO: either make the packages driven by a property, or change and uncomment the top level com.thinkbiganalytics package

    //packages("com.thinkbiganalytics");
    packages("com.thinkbiganalytics.ui.rest.controller","com.thinkbiganalytics.servicemonitor.rest.controller", "com.thinkbiganalytics.scheduler.rest.controller",
             "com.thinkbiganalytics.jobrepo.rest.controller", "com.thinkbiganalytics.hive.rest.controller",
             "com.thinkbiganalytics.feedmgr.rest.controller","com.thinkbiganalytics.policy.rest.controller","com.thinkbiganalytics.metadata.rest.api");

    register(JacksonFeature.class);
    register(MultiPartFeature.class);
    register(WadlResource.class);

    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JodaModule());
    om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    provider.setMapper(om);
    register(provider);


  }

}
