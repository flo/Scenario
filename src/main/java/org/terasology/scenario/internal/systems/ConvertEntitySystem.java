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

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.players.event.OnPlayerRespawnedEvent;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.scenario.components.actions.ArgumentContainerComponent;
import org.terasology.scenario.components.actions.GiveBlockActionComponent;
import org.terasology.scenario.components.actions.GiveItemActionComponent;
import org.terasology.scenario.components.actions.LogInfoComponent;
import org.terasology.scenario.components.actions.SendChatActionComponent;
import org.terasology.scenario.components.conditionals.BlockConditionalComponent;
import org.terasology.scenario.components.conditionals.IntComparisonConditionalComponent;
import org.terasology.scenario.components.conditionals.PlayerRegionComponent;
import org.terasology.scenario.components.events.OnBlockDestroyComponent;
import org.terasology.scenario.components.events.OnEnterRegionComponent;
import org.terasology.scenario.components.events.OnLeaveRegionComponent;
import org.terasology.scenario.components.events.OnRespawnComponent;
import org.terasology.scenario.components.events.OnSpawnComponent;
import org.terasology.scenario.components.information.ConcatStringComponent;
import org.terasology.scenario.components.information.ConstBlockComponent;
import org.terasology.scenario.components.information.ConstComparatorComponent;
import org.terasology.scenario.components.information.ConstIntegerComponent;
import org.terasology.scenario.components.information.ConstItemPrefabComponent;
import org.terasology.scenario.components.information.ConstRegionComponent;
import org.terasology.scenario.components.information.ConstStringComponent;
import org.terasology.scenario.components.information.ItemCountComponent;
import org.terasology.scenario.components.information.PlayerComponent;
import org.terasology.scenario.components.information.PlayerNameComponent;
import org.terasology.scenario.components.information.RandomIntComponent;
import org.terasology.scenario.components.information.RegionNameStringComponent;
import org.terasology.scenario.components.information.TriggeringBlockComponent;
import org.terasology.scenario.components.information.TriggeringRegionComponent;
import org.terasology.scenario.internal.events.ConvertScenarioEntityEvent;

import java.util.Map;

@RegisterSystem(RegisterMode.CLIENT)
public class ConvertEntitySystem extends BaseComponentSystem {

    /**
     * Most entities will follow a similar method, but to leave the system open for future inclusions with new event/action/conditions
     * it is designed with each specific component(the unique component of a logic prefab)
     */



    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, TriggeringRegionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, SendChatActionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }



    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, TriggeringBlockComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, RandomIntComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, PlayerNameComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConstStringComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:String]" + component.string);
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConstRegionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:Region]" + component.regionEntity.getId());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConstItemPrefabComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:Item]" + component.prefabURI);
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConstIntegerComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:Integer]" + Integer.toString(component.value));
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConstComparatorComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:Comparator]" + component.compare.name());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConstBlockComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:Block]" + component.block_uri);
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, PlayerComponent component) {
        event.getOutputList().add(event.getPrefix() + "[VALUE:Player]" + component.type.name());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, RegionNameStringComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, PlayerRegionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, OnSpawnComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, OnRespawnComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, OnLeaveRegionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, OnEnterRegionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, OnBlockDestroyComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, LogInfoComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ItemCountComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, IntComparisonConditionalComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, GiveItemActionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, GiveBlockActionComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, ConcatStringComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }

    @ReceiveEvent
    public void onConvertEntityEvent(ConvertScenarioEntityEvent event, EntityRef entity, BlockConditionalComponent component) {
        event.getOutputList().add(event.getPrefix() + "[PREFAB]" + entity.getParentPrefab().getName());

        for (Map.Entry<String, EntityRef> e : entity.getComponent(ArgumentContainerComponent.class).arguments.entrySet()) {
            ConvertScenarioEntityEvent newEvent = new ConvertScenarioEntityEvent(event.getPrefix() + "{" + e.getKey() + "}");
            e.getValue().send(newEvent);
            for (String s : newEvent.getOutputList()) {
                event.getOutputList().add(s);
            }
        }
    }
}