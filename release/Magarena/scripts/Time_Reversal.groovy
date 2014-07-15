[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "Each player shuffles his or her hand and graveyard into his or her library, then draws seven cards. "+
                "Exile SN."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            for (final MagicPlayer player : game.getPlayersAPNAP()) {
                final MagicCardList graveyard = new MagicCardList(player.getGraveyard());
                final MagicCardList hand = new MagicCardList(player.getHand());
                for (final MagicCard card : graveyard) {
                    game.doAction(new MagicRemoveCardAction(card,MagicLocationType.Graveyard));
                    game.doAction(new MagicMoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.OwnersLibrary));
                }
                for (final MagicCard card : hand) {
                    game.doAction(new MagicRemoveCardAction(card,MagicLocationType.OwnersHand));
                    game.doAction(new MagicMoveCardAction(card,MagicLocationType.OwnersHand,MagicLocationType.OwnersLibrary));
                }
            }
            for (final MagicPlayer player : game.getPlayersAPNAP()) {
                game.doAction(new MagicDrawAction(player,7));
            }
            game.doAction(new MagicChangeCardDestinationAction(event.getCardOnStack(),MagicLocationType.Exile));
        }
    }
]
