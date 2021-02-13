package core.game.content.quest.members.elementalworkshop

import core.cache.def.impl.ObjectDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.`object`.GameObject
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.Items

@Initializable
class ElementalWorkshopHandler : OptionHandler() {

    val bookcase = 26113

    override fun newInstance(arg: Any?): Plugin<Any> {
        ObjectDefinition.forId(bookcase).handlers["option:search"] = this //Bookcase
        return this
    }

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        when(node?.asObject()?.id) {
            bookcase -> search(player)
        }
        return true
    }

    fun search(player: Player?) {
        player ?: return
        if (player.inventory.containsItem(Item(Items.BATTERED_BOOK_2886))) {
            player.dialogueInterpreter.sendItemMessage(Item(Items.BATTERED_BOOK_2886), "There is a book here titled 'The Elemental Shield'.<br>It can stay here, as you have a copy in your backpack.")
        } else {
            player.inventory.add(Item(Items.BATTERED_BOOK_2886))
            player.dialogueInterpreter.sendItemMessage(Item(Items.BATTERED_BOOK_2886), "You find a book titled 'The Elemental Shield'.")
        }
    }
}