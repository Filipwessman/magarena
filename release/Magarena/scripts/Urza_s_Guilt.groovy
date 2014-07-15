[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "Each player draws two cards, then discards three cards, then loses 4 life."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            for (final MagicPlayer player : game.getPlayersAPNAP()) {
                game.doAction(new MagicDrawAction(player,2));
            }
            for (final MagicPlayer player : game.getPlayersAPNAP()) {
                game.addEvent(new MagicDiscardEvent(event.getSource(),player,3));
            }
            for (final MagicPlayer player : game.getPlayersAPNAP()) {
                game.doAction(new MagicChangeLifeAction(player,-4));
            }
        }
    }
]
