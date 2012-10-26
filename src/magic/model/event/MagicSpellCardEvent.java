package magic.model.event;

import magic.model.MagicCardDefinition;
import magic.model.MagicChangeCardDefinition;
import magic.model.MagicGame;
import magic.model.MagicPayedCost;
import magic.model.choice.MagicTargetChoice;
import magic.model.stack.MagicCardOnStack;
import magic.model.target.MagicDefaultTargetPicker;
import magic.model.target.MagicTargetPicker;

public abstract class MagicSpellCardEvent implements MagicCardEvent,MagicEventAction,MagicChangeCardDefinition {

    @Override
    public void change(final MagicCardDefinition cdef) {
        cdef.setEvent(this);
    }

    public static MagicSpellCardEvent create(final String rule) {
        final String effect = rule.toLowerCase();
        final MagicRuleEventAction ruleAction = MagicRuleEventAction.build(effect);
        final MagicEventAction action  = ruleAction.action;
        final MagicTargetPicker picker = ruleAction.picker; 
        final MagicTargetChoice choice = ruleAction.getChoice(effect);

        return new MagicSpellCardEvent() {
            @Override
            public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
                return new MagicEvent(
                    cardOnStack,
                    choice,
                    (picker != null ? picker : MagicDefaultTargetPicker.create()),
                    this,
                    rule + "$"
                );
            }
            @Override
            public void executeEvent(
                    final MagicGame game, 
                    final MagicEvent event, 
                    final Object[] choiceResults) {
                action.executeEvent(game, event, choiceResults);
            }
        };
    }
}
