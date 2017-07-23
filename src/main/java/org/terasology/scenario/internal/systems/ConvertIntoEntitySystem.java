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
package org.terasology.scenario.internal.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.assets.management.AssetManager;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.prefab.Prefab;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.In;
import org.terasology.scenario.components.ScenarioHubToolUpdateComponent;
import org.terasology.scenario.components.actions.ArgumentContainerComponent;
import org.terasology.scenario.components.information.ConstBlockComponent;
import org.terasology.scenario.components.information.ConstComparatorComponent;
import org.terasology.scenario.components.information.ConstIntegerComponent;
import org.terasology.scenario.components.information.ConstItemPrefabComponent;
import org.terasology.scenario.components.information.ConstRegionComponent;
import org.terasology.scenario.components.information.ConstStringComponent;
import org.terasology.scenario.components.information.InformationEnums;
import org.terasology.scenario.components.information.PlayerComponent;
import org.terasology.scenario.internal.events.ConvertIntoEntityConstantEvent;
import org.terasology.scenario.internal.events.ConvertIntoEntityEvent;
import org.terasology.scenario.internal.utilities.ArgumentParser;
import org.terasology.world.block.BlockManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RegisterSystem(RegisterMode.AUTHORITY)
public class ConvertIntoEntitySystem extends BaseComponentSystem {

    @In
    EntityManager entityManager;

    @In
    AssetManager assetManager;

    @In
    BlockManager blockManager;

    private Logger logger = LoggerFactory.getLogger(ConvertIntoEntitySystem.class);

    @ReceiveEvent
    public void onConvertIntoEntityEvent(ConvertIntoEntityEvent event, EntityRef entity, ScenarioHubToolUpdateComponent component) {
        List<String> constructions = event.getConstructionStrings();
        Pattern patternMain = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = patternMain.matcher(constructions.get(0));
        matcher.find();
        String sub = constructions.get(0).substring(matcher.end());
        EntityRef starterEntity = entityManager.create(assetManager.getAsset(sub, Prefab.class).get());
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.setEntityManager(entityManager);
        argumentParser.setAssetManager(assetManager);
        argumentParser.setBlockManager(blockManager);
        argumentParser.parseDefaults(starterEntity);
        constructions.remove(0);
        Pattern keyPattern = Pattern.compile("\\{(.*?)\\}");
        for (String s : constructions) {
            Matcher keyMatcher = keyPattern.matcher(s);
            EntityRef previousEntity = starterEntity;
            EntityRef currentEntity = starterEntity;
            String lastKey = "";
            int lastIndex = 0;
            while(keyMatcher.find()) {
                String key = keyMatcher.group(1);
                lastKey = key;
                lastIndex = keyMatcher.end();
                previousEntity = currentEntity;
                currentEntity = currentEntity.getComponent(ArgumentContainerComponent.class).arguments.get(key);
            }
            // At this point previousEntity is the second to last entity, current entity is the furthest depth entity
            // lastKey is the key from the previous entity to the current entity and lastIndex is the index in the string
            // that begins the last "segment" that isn't keyed(either a prefab or a value)

            Matcher matcherLast = patternMain.matcher(s);
            matcherLast.find(lastIndex);
            if (matcherLast.group(1).equals("PREFAB")) {
                String subPrefab = s.substring(matcherLast.end());
                EntityRef newEntity = entityManager.create(assetManager.getAsset(subPrefab, Prefab.class).get());
                argumentParser.parseDefaults(newEntity);
                previousEntity.getComponent(ArgumentContainerComponent.class).arguments.put(lastKey, newEntity);
                previousEntity.saveComponent(previousEntity.getComponent(ArgumentContainerComponent.class));
            }
            else {
                //Type doesn't actually matter because the entity will be set up to a constant value at the end and therefore
                //Will be able to be detected by component
                String value = s.substring(matcherLast.end());
                ConvertIntoEntityConstantEvent constEvent = new ConvertIntoEntityConstantEvent(value);
                currentEntity.send(constEvent);
                previousEntity.saveComponent(previousEntity.getComponent(ArgumentContainerComponent.class));
            }
        }

        event.setReturnEntity(starterEntity);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, ConstIntegerComponent component) {
        component.value = Integer.parseInt(event.getValue());
        entity.saveComponent(component);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, ConstBlockComponent component) {
        component.block_uri = event.getValue();
        entity.saveComponent(component);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, PlayerComponent component) {
        component.type = InformationEnums.PlayerType.valueOf(event.getValue());
        entity.saveComponent(component);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, ConstStringComponent component) {
        component.string = event.getValue();
        entity.saveComponent(component);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, ConstItemPrefabComponent component) {
        component.prefabURI = event.getValue();
        entity.saveComponent(component);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, ConstComparatorComponent component) {
        component.compare = ConstComparatorComponent.comparison.valueOf(event.getValue());
        entity.saveComponent(component);
    }

    @ReceiveEvent
    public void onConvertIntoEntityConstantEvent(ConvertIntoEntityConstantEvent event, EntityRef entity, ConstRegionComponent component) {
        component.regionEntity = entityManager.getEntity(Integer.parseInt(event.getValue()));
        entity.saveComponent(component);
    }
}
