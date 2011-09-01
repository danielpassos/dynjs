/**
 *  Copyright 2011 dynjs contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dynjs.runtime;

import java.util.Map;

public class DynObject implements DynAtom {

    private Map<String, Attribute<? extends DynAtom>> attributes = new Attributes();

    public Attribute<? extends DynAtom> get(Object attribute) {
        return attributes.get(attribute);
    }

    public AttributeBuilder set(String person) {
        return new AttributeBuilder<DynObject>(this, person);
    }

    public void setAttribute(String attributeName, Attribute attribute) {
        attributes.put(attributeName, attribute);
    }

    @Override
    public boolean isUndefined() {
        return false;
    }

}