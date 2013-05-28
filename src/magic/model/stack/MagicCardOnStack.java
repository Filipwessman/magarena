package magic.model.stack;

import magic.model.MagicAbility;
import magic.model.MagicColor;
import magic.model.MagicCard;
import magic.model.MagicCopyMap;
import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPayedCost;
import magic.model.MagicPlayer;
import magic.model.MagicSource;
import magic.model.MagicType;
import magic.model.event.MagicActivation;
import magic.model.event.MagicEvent;
import magic.model.event.MagicCardEvent;

import javax.swing.ImageIcon;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MagicCardOnStack extends MagicItemOnStack implements MagicSource {

    private MagicLocationType moveLocation=MagicLocationType.Graveyard;
    private final int x;
    private final MagicCardEvent cardEvent;
    
    public MagicCardOnStack(final MagicCard card,final MagicPlayer controller,final MagicCardEvent aCardEvent, final MagicPayedCost payedCost) {
        super(card, controller, aCardEvent, payedCost);
        x = payedCost.getX();
        cardEvent = aCardEvent;
    }
    
    public MagicCardOnStack(final MagicCard card,final MagicCardEvent aCardEvent, final MagicPayedCost payedCost) {
        this(card, card.getController(), aCardEvent, payedCost);
    }
    
    public MagicCardOnStack(final MagicCard card,final MagicPlayer controller,final MagicPayedCost payedCost) {
        this(card, controller, card.getCardDefinition().getCardEvent(), payedCost);
    }

    public MagicCardOnStack(final MagicCard card,final MagicPayedCost payedCost) {
        this(card, card.getController(), card.getCardDefinition().getCardEvent(), payedCost);
    }
    
    private MagicCardOnStack(final MagicCopyMap copyMap, final MagicCardOnStack cardOnStack) {
        super(copyMap, cardOnStack);
        moveLocation = cardOnStack.moveLocation;
        x = cardOnStack.x;
        cardEvent = cardOnStack.cardEvent;
    }
    
    public MagicCardOnStack copyCardOnStack(final MagicPlayer player) {
        final MagicPayedCost cost=new MagicPayedCost();
        cost.setX(x);
        final MagicCard card=MagicCard.createTokenCard(getCardDefinition(),player);
        final MagicCardOnStack copyCardOnStack=new MagicCardOnStack(card,cardEvent,cost);
        final Object[] choiceResults=getChoiceResults();
        if (choiceResults!=null) {
            copyCardOnStack.setChoiceResults(Arrays.copyOf(choiceResults,choiceResults.length));
        }
        return copyCardOnStack;
    }
    
    @Override
    public MagicCardOnStack copy(final MagicCopyMap copyMap) {
        return new MagicCardOnStack(copyMap, this);
    }
    
    @Override
    public MagicCardOnStack map(final MagicGame game) {
        return (MagicCardOnStack)super.map(game);
    }
    
    public MagicCard getCard() {
        return (MagicCard)getSource();
    }
    
    public void setMoveLocation(final MagicLocationType moveLocation) {
        this.moveLocation=moveLocation;
    }
    
    public MagicLocationType getMoveLocation() {
        return moveLocation;
    }
    
    public int getConvertedCost() {
        return getCardDefinition().getConvertedCost()+x;
    }

    public int getX() {
        return x;
    }
    
    @Override
    public boolean isSpell() {
        return true;
    }

    @Override
    public boolean canBeCountered() {
        return !getCardDefinition().hasAbility(MagicAbility.CannotBeCountered);
    }
    
    @Override
    public ImageIcon getIcon() {
        return getCard().getCardDefinition().getIcon();
    }

    @Override
    public MagicGame getGame() {
        return getSource().getGame();
    }
    
    @Override
    public Collection<MagicActivation> getActivations() {
        return Collections.emptyList();
    }
}
