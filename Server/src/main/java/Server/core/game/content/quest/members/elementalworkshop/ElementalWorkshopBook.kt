package core.game.content.quest.members.elementalworkshop

import core.game.content.dialogue.book.BookLine
import core.game.content.dialogue.DialoguePlugin
import core.game.content.dialogue.book.Book
import core.game.content.dialogue.book.Page
import core.game.content.dialogue.book.PageSet
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.Items
import java.lang.StringBuilder
import java.util.regex.Pattern

/**
 * Created for 2009Scape
 * User: Ethan Kyle Millard
 * Date: March 30, 2020
 * Time: 2:49 PM
 */
@Initializable
class ElementalWorkshopBook : Book {


    var ID = 4501988
    constructor(player: Player?) : super(player, "Book of the elemental shield", Items.BATTERED_BOOK_2886, *PAGES) {

    }

    constructor() {

    }

    override fun finish() {
        player.setAttribute("/save:elewrkshp-read-book",true)
    }
    override fun display(set: Array<Page>) {
        player.lock()
        player.interfaceManager.open(getInterface())
        for (i in 55..76) {
            player.packetDispatch.sendString("", getInterface().id, i)
        }

        player.packetDispatch.sendString(getName(), getInterface().id, 6)
        player.packetDispatch.sendString("", getInterface().id, 77)
        player.packetDispatch.sendString("", getInterface().id, 78)
        for (page in set) {
            for (line in page.lines) {
                player.packetDispatch.sendString(line.message, getInterface().id, line.child)
            }
        }
        player.packetDispatch.sendInterfaceConfig(getInterface().id, 51, index < 1)
        val lastPage = index == sets.size - 1
        player.packetDispatch.sendInterfaceConfig(getInterface().id, 53, lastPage)
        if (lastPage) {
            finish()
        }
        player.unlock()
    }



    override fun newInstance(player: Player): DialoguePlugin {
        return ElementalWorkshopBook(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(ID)
    }

    companion object {

        private val PAGES = arrayOf(
            PageSet(
                Page(
                    BookLine("Within the pages of this ", 55),
                    BookLine("book you will find the", 56),
                    BookLine("secret to working the very", 57),
                    BookLine("elements themselves.", 58),
                    BookLine("Early in the fifth age, a", 59),
                    BookLine("new ore was discovered.", 60),
                    BookLine("This ore has a uniqu", 61),
                    BookLine("property of absorbing,", 62),
                    BookLine("transforming or focusing", 63),
                    BookLine("elemental energy. A", 64),
                    BookLine("workshop was erected", 65)
                ),
                Page(
                    BookLine("close by to work this new", 66),
                    BookLine("material. The workshop", 67),
                    BookLine("was set up for artisans", 68),
                    BookLine("and inventors to be able", 69),
                    BookLine("to come and create", 70),
                    BookLine("devices made from the", 71),
                    BookLine("unique ore, found only in", 72),
                    BookLine("the village of the Seers.", 73),
                )
            ),
            PageSet(
                Page(
                    BookLine("After some time of", 55),
                    BookLine("successful industry the", 56),
                    BookLine("true power of this ore", 57),
                    BookLine("became apparent, as", 59),
                    BookLine("greater and more", 60),
                    BookLine("powerful weapons were", 61),
                    BookLine("created. Realising the", 62),
                    BookLine("threat this posed, the magi", 63),
                    BookLine("of the time closed down", 64),
                    BookLine(" the workshop and bound", 65),
                    BookLine(" the workshop and bound", 66)
                ),
                Page(
                    BookLine("also trying to destroy all", 67),
                    BookLine("knowledge of", 68),
                    BookLine("manufacturing processes.", 69),
                    BookLine("Yet this book remains and", 70),
                    BookLine("you may still find a way", 71),
                    BookLine("to enter the workshop", 72),
                    BookLine("within this leather bound", 73),
                    BookLine("volume.", 74)
                )
            )
        )
    }
}