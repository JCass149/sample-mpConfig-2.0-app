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

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import api.utils.ServerDetailsBean;

@RequestScoped
@Produces(MediaType.TEXT_PLAIN)
@Path("test")
public class MPConfig20ExamplesResource {

    /*
     * ------------------- Example 0 -------------------
     */

    @Inject
    @ConfigProperty(name = "os.name")
    String osName;

    @GET
    @Path("/getOS")
    @Tag(name = "0. Example")
    public String getOS() {
        return osName;
    }

    /*
     * ------------------- Example 1 -------------------
     */

    @Inject
    @ConfigProperty(name = "server.host")
    String serverHost;

    @Inject
    @ConfigProperty(name = "mp.config.profile")
    Optional<String> configProfile;

    @GET
    @Path("/getHost")
    @Tag(name = "1. Config Profiles")
    public String getHost() {
        return serverHost + ". Config Profile: " + configProfile.orElse("Not set");
    }

    @Inject
    @ConfigProperty(name = "server.port")
    String serverPort;

    /**
     * Get port. Could be 9080 (default "phase") or 9081 (testing "phase") or 9082 (production "phase")
     */
    @GET
    @Path("/getPort")
    @Tag(name = "1. Config Profiles")
    public String getPort() {
        return serverPort + ". Config Profile: " + configProfile.orElse("Not set");
    }

    /*
     * ------------------- Example 2 -------------------
     */

    @Inject
    @ConfigProperty(name = "a.property.defined.in.many.places")
    ConfigValue aPropertyConfigValue;

    @GET
    @Path("/getConfigValue")
    @Tag(name = "2. ConfigValue")
    public String getConfigValue() {

        String sourceName = aPropertyConfigValue.getSourceName();
        int sourceOrdinal = aPropertyConfigValue.getSourceOrdinal();

        return sourceName + "(" + sourceOrdinal + ")";
    }

    /*
     * ------------------- Example 3 -------------------
     */

    @Inject
    @ConfigProperties
    ServerDetailsBean serverDetails;

    @GET
    @Path("/getServerConfigProperties")
    @Tag(name = "3. @ConfigProperties")
    public String getServerConfigProperties() {
        return serverDetails.port + ", " + serverDetails.host + ", " + serverDetails.url;
    }

    @Inject
    @ConfigProperties(prefix = "client")
    ServerDetailsBean clientDetails;

    @GET
    @Path("/getClientConfigProperties")
    @Tag(name = "3. @ConfigProperties")
    public String getClientConfigProperties() {
        return clientDetails.port + ", " + clientDetails.host + ", " + clientDetails.url;
    }

    /*
     * ------------------- Example 4 -------------------
     */

    @Inject
    @ConfigProperty(name = "server.endpoint")
    String serverEndpoint;

    @GET
    @Path("/getPropertyExpression")
    @Tag(name = "4. PropertyExpression")
    public String getPropertyExpression() {
        return serverEndpoint;
    }

    /*
     * ------------------- Example 5 -------------------
     */

    @Inject
    Config config;

    @GET
    @Path("/getNewConfigAPIMethods")
    @Tag(name = "5. Config API methods")
    public String getNewConfigAPIMethods() {
        List<Integer> httpsPorts = config.getValues("server.https.ports", Integer.class);
        Optional<List<Integer>> httpsOptionalPorts = config.getOptionalValues("server.https.ports", Integer.class);
        return httpsPorts + ", " + httpsOptionalPorts;
    }

    /*
     * ------------------- Example 6 -------------------
     */

    @Inject
    @ConfigProperty(name = "server.port")
    OptionalInt optionalIntServerPort;

    @Inject
    @ConfigProperty(name = "server.port")
    OptionalLong optionalLongServerPort;

    @Inject
    @ConfigProperty(name = "server.port")
    OptionalDouble optionalDoubleServerPort;

    @GET
    @Path("/getOptionalConvertersValues")
    @Tag(name = "6. Optional* Converters")
    public String getOptionalInt() {
        return optionalIntServerPort + ", " + optionalLongServerPort + ", " + optionalDoubleServerPort;
    }

}
