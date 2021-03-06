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
package org.terasology.scenario.components.regions;

import org.terasology.entitySystem.Component;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.math.Region3i;
import org.terasology.network.FieldReplicateType;
import org.terasology.network.Replicate;
import org.terasology.scenario.components.ScenarioComponent;
import org.terasology.scenario.internal.systems.RegionSystem;
import org.terasology.structureTemplates.components.ProtectedRegionsComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Creation component for a Scenario argument entity, includes a list of player character entities that are contained within the region
 *
 * Scenario region entities are detailed in {@link ScenarioComponent}
 */
public class RegionContainingEntitiesComponent implements Component {
    @Replicate(FieldReplicateType.SERVER_TO_CLIENT)
    public List<EntityRef> entities = new ArrayList<>();
}
