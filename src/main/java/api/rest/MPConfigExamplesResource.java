/*******************************************************************************
 * Copyright (c) 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package api.rest;

import java.util.Iterator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;

@RequestScoped
@Path("test")
public class MPConfigExamplesResource {

    @Inject
    Config config;

    // Liberty variables
    @GET
    @Path("/getLibertyPredefinedVariables")
    public Response getLibertyPredefinedVariables() {

        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("wlp.install.dir", config.getValue("wlp.install.dir", String.class));
        builder.add("wlp.server.name", config.getValue("wlp.server.name", String.class));
        builder.add("wlp.user.dir", config.getValue("wlp.user.dir", String.class));
        builder.add("server.config.dir", config.getValue("server.config.dir", String.class));

        return Response.ok(builder.build()).build();
    }

    // Liberty Config Sources
    @GET
    @Path("/getLibertyConfigSources")
    public Response getLibertyConfigSources() {

        JsonObjectBuilder builder = Json.createObjectBuilder();

        Iterator<ConfigSource> configSources = config.getConfigSources().iterator();
        while (configSources.hasNext()) {
            ConfigSource cs = configSources.next();
            builder.add(cs.getName(), "Contains " + cs.getProperties().size() + " properties");
        }

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path("/getValues")
    public Response getDefaultConfigSourceValues() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        addProperty("a.property.from.server.xml.appProperties", builder);
        addProperty("a.property.from.server.xml.variable", builder);
        addProperty("a.property.from.bootstrap.properties", builder);
        addProperty("a.property.from.jvm.options", builder);
        addProperty("a.property.from.server.env", builder);
        addProperty("a.property.from.microprofile-config.properties", builder);
        addProperty("a.property.from.custom.config.source", builder);

        return Response.ok(builder.build()).build();

    }

    private void addProperty(String propertyName, JsonObjectBuilder builder) {
        ConfigValue cv = config.getConfigValue(propertyName);
        String formattedString = cv.getValue() + ", with ordinal(" + cv.getSourceOrdinal() + ")";
        builder.add(propertyName, formattedString);
    }

}
