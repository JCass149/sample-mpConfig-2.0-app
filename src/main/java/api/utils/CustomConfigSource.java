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

package api.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class CustomConfigSource implements ConfigSource {

    public Map<String, String> props = new HashMap<>();

    public CustomConfigSource() {
        props.put("a.property.from.custom.config.source", "custom.config.source value");
    }

    /**
     * {@inheritDoc}
     * 
     * New to MP Config 2.0: In previous version this method had a default implementation.
     * 
     * Now it doesn't, meaning it has to be implemented or the following compile time exception will be thrown:
     * 
     * "CustomConfigSource is not abstract and does not override abstract method getPropertyNames() in ConfigSource"
     */
    @Override
    public Set<String> getPropertyNames() {
        return props.keySet();
    }

    /** {@inheritDoc} */
    @Override
    public String getValue(String propertyName) {
        return props.get(propertyName);
    }

    @Override
    public String getName() {
        return "CustomConfigSource";
    }

    public String toString() {
        return getName() + "(" + getOrdinal() + ")";
    }
}
