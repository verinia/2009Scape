package core.game.interaction.item;

import core.cache.def.impl.ItemDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.Items;

/**
 * Represents the plugin used to handle the "read" option of a book.
 *
 * @author 'Vexia
 * @version 1.0
 */
@Initializable
public class BookreadOption extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(Items.BOOK_ON_BAXTORIAN_292).getHandlers().put("option:read", this);
        ItemDefinition.forId(Items.BOOK_757).getHandlers().put("option:read", this);
        ItemDefinition.forId(Items.GUIDE_BOOK_1856).getHandlers().put("option:read", this);
        ItemDefinition.forId(Items.BATTERED_BOOK_2886).getHandlers().put("option:read", this);
        ItemDefinition.forId(Items.SECURITY_BOOK_9003).getHandlers().put("option:read", this);
        ItemDefinition.forId(Items.SLASHED_BOOK_9715).getHandlers().put("option:read", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        final int id = getDialId(((Item) node).getId());
        player.getInterfaceManager().close();
        return player.getDialogueInterpreter().open(id, node);
    }

    @Override
    public boolean isWalk() {
        return false;
    }

    /**
     * Gets the dialogue id from the item id.
     *
     * @param item the item.
     * @return the dial id.
     */
    public int getDialId(int item) {
        switch (item) {
            case Items.BOOK_757:
                return 49610758;
            case Items.SECURITY_BOOK_9003:
                return 49610759;
            case Items.BATTERED_BOOK_2886:
            case Items.SLASHED_BOOK_9715:
                return 4501988;
            case Items.BOOK_ON_BAXTORIAN_292:
                return 183764;
            case Items.GUIDE_BOOK_1856:
                return 387454;
        }
        return -1;
    }
}
