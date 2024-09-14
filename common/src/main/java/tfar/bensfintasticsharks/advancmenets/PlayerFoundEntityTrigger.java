package tfar.bensfintasticsharks.advancmenets;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import tfar.bensfintasticsharks.BensFintasticSharks;

public class PlayerFoundEntityTrigger extends SimpleCriterionTrigger<PlayerFoundEntityTrigger.TriggerInstance> {
    static final ResourceLocation ID = BensFintasticSharks.id("player_found_entity");


    public PlayerFoundEntityTrigger() {
    }

    public ResourceLocation getId() {
        return ID;
    }

    public TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        ContextAwarePredicate contextawarepredicate = EntityPredicate.fromJson(pJson, "entity", pDeserializationContext);
        return new TriggerInstance(ID, pPredicate,contextawarepredicate);
    }

    public void trigger(ServerPlayer pPlayer, LivingEntity entity) {
        LootContext lootcontext = EntityPredicate.createContext(pPlayer, entity);
        this.trigger(pPlayer, instance -> instance.matches(pPlayer,lootcontext));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final ContextAwarePredicate entity;


        public TriggerInstance(ResourceLocation pCriterion, ContextAwarePredicate pPlayer, ContextAwarePredicate entity) {
            super(pCriterion, pPlayer);
            this.entity = entity;
        }

        public boolean matches(ServerPlayer pPlayer, LootContext pContext) {
            return this.entity.matches(pContext);
        }

        public static TriggerInstance located(EntityPredicate entityPredicate) {
            return new TriggerInstance(BensFintasticSharks.PLAYER_FOUND_ENTITY.getId(),ContextAwarePredicate.ANY, EntityPredicate.wrap(entityPredicate));
        }
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("entity", this.entity.toJson(pConditions));
            return jsonobject;
        }
    }
}
