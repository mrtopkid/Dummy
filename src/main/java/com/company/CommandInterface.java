package com.company;

import me.diax.comportment.jdacommand.CommandAttribute;
import me.diax.comportment.jdacommand.CommandDescription;
import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;


    public interface CommandInterface {
        void execute(Message var1, String var2);

        default CommandDescription getDescription() {
            return (CommandDescription)this.getClass().getAnnotation(CommandDescription.class);
        }

        default String getAttributeValueFromKey(String key) {
            return !this.hasAttribute(key) ? null : ((CommandAttribute) Arrays.stream(this.getDescription().attributes()).filter((ca) -> {
                return ca.key().equals(key);
            }).findFirst().get()).value();
        }

        default boolean hasAttribute(String key) {
            return Arrays.stream(this.getDescription().attributes()).anyMatch((ca) -> {
                return ca.key().equals(key);
            });
        }
    }

