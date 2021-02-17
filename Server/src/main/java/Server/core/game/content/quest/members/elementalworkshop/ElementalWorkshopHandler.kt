package core.game.content.quest.members.elementalworkshop

import core.cache.def.impl.ObjectDefinition
import core.cache.def.impl.VarbitDefinition
import core.game.content.global.action.DoorActionHandler
import core.game.content.global.travel.EssenceTeleport
import core.game.interaction.OptionHandler
import core.game.interaction.`object`.LadderManagingPlugin
import core.game.node.Node
import core.game.node.`object`.GameObject
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphics
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.Items

@Initializable
class ElementalWorkshopHandler : OptionHandler() {

    val west_water_controls_varbit = 2058
    val east_water_controls_varbit = 2059
    val water_wheel_varbit = 2060
    val bookcase = 26113
    val odd_looking_wall_left = 26115
    val odd_looking_wall_right = 26114
    val lever = 3406

    override fun newInstance(arg: Any?): Plugin<Any> {
        ObjectDefinition.forId(bookcase).handlers["option:search"] = this
        ObjectDefinition.forId(odd_looking_wall_left).handlers["option:open"] = this
        ObjectDefinition.forId(odd_looking_wall_right).handlers["option:open"] = this
        ObjectDefinition.forId(lever).handlers["option:pull"] = this
        return this
    }

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        when(node?.asObject()?.id) {
            bookcase -> search(player)
            odd_looking_wall_right, odd_looking_wall_left -> open(player, node.asObject())
            lever -> pull(player, node.asObject())
        }
        return true
    }
    fun pull(player: Player?, door: GameObject?) {
        player ?: return
        println(VarbitDefinition.forObjectID(ObjectDefinition.forId(water_wheel_varbit).varbitID))
        player.varpManager.sendAllVarps()
//        player.varpManager.get(VarbitDefinition.forObjectID(ObjectDefinition.forId(west_water_controls_varbit).varbitID).getValue(player)
        player.varpManager.setVarbit(VarbitDefinition.forObjectID(ObjectDefinition.forId(water_wheel_varbit).varbitID), 1)
    }

    fun open(player: Player?, door: GameObject?) {
        player ?: return
        if (player.location.y > 3495) {
            DoorActionHandler.handleDoor(player, door)
            return
        }
        if (!player.inventory.containsItem(Item(Items.BATTERED_KEY_2887))) {
            player.sendMessage("You see a small hole but no way to open it.")
            return
        } else {
            player.sendMessage("You use the battered key to open the doors.")
            DoorActionHandler.handleDoor(player, door)
            return
        }
    }

    fun search(player: Player?) {
        player ?: return
        if (player.inventory.containsItem(Item(Items.BATTERED_BOOK_2886))) {
            player.dialogueInterpreter.sendItemMessage(Item(Items.BATTERED_BOOK_2886), "There is a book here titled 'The Elemental Shield'.<br>It can stay here, as you have a copy in your backpack.")
        } else {
            if (player.getAttribute("elewrkshp-slashed-book", false)) {
                Pulser.submit(object : Pulse(1) {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0-> {
                                player.lock()
                                if (!player.inventory.containsItem(Item(Items.SLASHED_BOOK_9715))) {
                                    if (player.inventory.add(Item(Items.SLASHED_BOOK_9715))) {
                                        player.dialogueInterpreter.sendItemMessage(Item(Items.SLASHED_BOOK_9715), false,"You find a book titled 'The Elemental Shield'.")
                                    }
                                } else {
                                    player.dialogueInterpreter.sendItemMessage(Item(Items.SLASHED_BOOK_9715), "There is a book here titled 'The Elemental Shield'.<br>It can stay here, as you have a copy in your backpack.")
                                }
                            }
                            3 -> {
                                if (!player.inventory.containsItem(Item(Items.BATTERED_KEY_2887))) {
                                    if (player.inventory.add(Item(Items.BATTERED_KEY_2887))) {
                                        player.dialogueInterpreter.sendItemMessage(Item(Items.BATTERED_KEY_2887), false,"You find a battered key.")
                                    }
                                } else {
                                    player.dialogueInterpreter.sendItemMessage(Item(Items.BATTERED_KEY_2887), "There is a battered key here.<br>It can stay here, as you have a copy in your backpack.")

                                }
                                player.unlock()
                                return true
                            }
                        }
                        return false
                    }
                })
                return
            }
            player.inventory.add(Item(Items.BATTERED_BOOK_2886))
            player.dialogueInterpreter.sendItemMessage(Item(Items.BATTERED_BOOK_2886), "You find a book titled 'The Elemental Shield'.")
        }
    }
}