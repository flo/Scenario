/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.scenario.internal.events;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.Event;
import org.terasology.network.ServerEvent;

import java.util.List;

/**
 * Event that is called that tells the server to recreate the entity on the server side based on a list of serialized
 * strings generated by the client by the logic editing screen
 */
@ServerEvent
public class ReplaceEntityFromConstructionStringsEvent implements Event {
    private EntityRef replaceEntity;
    private List<String> conversions;

    public ReplaceEntityFromConstructionStringsEvent() {
    }

    public ReplaceEntityFromConstructionStringsEvent(EntityRef replaceEntity, List<String> conversions) {
        this.replaceEntity = replaceEntity;
        this.conversions = conversions;
    }

    public EntityRef getReplaced() {
        return replaceEntity;
    }

    public List<String> getConversions() {
        return conversions;
    }
}
