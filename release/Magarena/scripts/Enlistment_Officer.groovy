[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return new MagicEvent(
                permanent,
                this,
                "Reveal the top four cards of your library. Put all Soldier cards revealed this way into your hand and the rest on the bottom of your library in any order."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCardList top4 = event.getPlayer().getLibrary().getCardsFromTop(4) ;
            for (final MagicCard top : top4) {
                game.doAction(new RevealAction(top4));
                game.doAction(new RemoveCardAction(
                    top,
                    MagicLocationType.OwnersLibrary
                ));
                game.doAction(new MoveCardAction(
                    top,
                    MagicLocationType.OwnersLibrary,
                    top.hasSubType(MagicSubType.Soldier) ?
                      MagicLocationType.OwnersHand :
                      MagicLocationType.BottomOfOwnersLibrary
                ));
            }
        }
    }
]
