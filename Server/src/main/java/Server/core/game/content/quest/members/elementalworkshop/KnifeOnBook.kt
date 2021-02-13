package core.game.content.quest.members.elementalworkshop

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.Items

@Initializable
class KnifeOnBook : UseWithHandler(Items.KNIFE_946) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.BATTERED_BOOK_2886, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        val player = event?.player?: return false
        player.dialogueInterpreter.sendItemMessage(Items.SLASHED_BOOK_9715, "You make a small cut in the spine of the book.")
        player.sendMessage("Inside you find a small, old, battered key.")
        if (player.inventory.remove(Item(Items.BATTERED_BOOK_2886))) {
            player.inventory.add(Item(Items.SLASHED_BOOK_9715))
            player.inventory.add(Item(Items.BATTERED_KEY_2887))
        }
        return true
    }
}