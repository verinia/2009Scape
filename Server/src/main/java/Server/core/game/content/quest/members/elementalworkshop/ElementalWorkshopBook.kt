package core.game.content.quest.members.elementalworkshop

import core.game.content.dialogue.book.BookLine
import core.game.content.dialogue.DialoguePlugin
import core.game.content.dialogue.book.Book
import core.game.content.dialogue.book.Page
import core.game.content.dialogue.book.PageSet
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.Items

/**
 * Created for 2009Scape
 * User: Ethan Kyle Millard
 * Date: March 30, 2020
 * Time: 2:49 PM
 */
@Initializable
class ElementalWorkshopBook : Book {

    constructor(player: Player?) : super(player, "Book of the elemental shield", Items.BATTERED_BOOK_2886, *PAGES) {

    }

    constructor() {

    }

    override fun finish() {}
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

        var ID = 4501988

        /**
         * Represents the array of pages for this book.
         */
        private val PAGES = arrayOf(
            PageSet(
                Page(
                    BookLine("<col=ff0000>2nd of Pentember", 55),
                    BookLine("Experiment is growing", 56),
                    BookLine("larger daily. Making", 57),
                    BookLine("excellent progress now. I", 58),
                    BookLine("am currently feeding it", 59),
                    BookLine("on a mixture of fungus,", 60),
                    BookLine("tar and clay.", 61),
                    BookLine("It seems to like this", 62),
                    BookLine("combination a lot!", 63)
                ),
                Page(
                    BookLine("<col=ff0000>3rd of Pentember", 66),
                    BookLine("Experiment still going", 67),
                    BookLine("extremely well. Moved it", 68),
                    BookLine("to the wooden garden", 69),
                    BookLine("shed; it does too much", 70),
                    BookLine("damage in the house! it", 71),
                    BookLine("is getting very strong", 72),
                    BookLine("now, but unfortunately is", 73),
                    BookLine("not too intelligent yet. It", 74),
                    BookLine("has a really mean stare", 75),
                    BookLine("too!", 76)
                )
            ),
            PageSet(
                Page(
                    BookLine("<col=ff0000>4th of Pentember", 55),
                    BookLine("Sausages for dinner", 56),
                    BookLine("tonight! Lovely!", 57),
                    BookLine("<col=ff0000>5th of Pentember", 59),
                    BookLine("A guy called Professor", 60),
                    BookLine("Oddenstein installed a", 61),
                    BookLine("new security system for", 62),
                    BookLine("me in the basement. He", 63),
                    BookLine("seems to have a lot of", 64),
                    BookLine("good security ideas.", 65)
                ),
                Page(
                    BookLine("<col=ff0000>6th of Pentember", 66),
                    BookLine("Don't want people getting", 67),
                    BookLine("into back garden to see", 68),
                    BookLine("the experiment. Professor", 69),
                    BookLine("Oddenstein is fitting me a", 70),
                    BookLine("new security system,", 71),
                    BookLine("after his successful", 72),
                    BookLine("installation in the cellar.", 73)
                )
            ),
            PageSet(
                Page(
                    BookLine("<col=ff0000>7th of Pentember", 55),
                    BookLine("That pesky kid keeps", 56),
                    BookLine("kicking his ball into my", 57),
                    BookLine("garden. I swear, if he", 58),
                    BookLine("does it AGAIN, I'm going", 59),
                    BookLine("to lock his ball away in", 60),
                    BookLine("the shed.", 61),
                    BookLine("<col=ff0000>8th of Pentember.", 63),
                    BookLine("The security system is", 64),
                    BookLine("done. By Zamorak! Wow,", 65)
                ),
                Page(
                    BookLine("is it contrived! Now, to", 66),
                    BookLine("open my own back door,", 67),
                    BookLine("I lure a mouse out of a", 68),
                    BookLine("hole in the back porch, I", 69),
                    BookLine("fit a magic curved piece", 70),
                    BookLine("of metal to the harness", 71),
                    BookLine("on its back, the mouse", 72),
                    BookLine("goes back in the hole, and", 73),
                    BookLine("the door unlocks! The", 74),
                    BookLine("prof tells me that this is", 75),
                    BookLine("cutting edge technology!", 76)
                )
            ),
            PageSet(
                Page(
                    BookLine("As an added precaution I", 55),
                    BookLine("have hidden the key to", 56),
                    BookLine("the shed in a secret", 57),
                    BookLine("compartment of the", 58),
                    BookLine("fountain in the garden.", 59),
                    BookLine("No one will ever look", 60),
                    BookLine("there.", 61),
                    BookLine("<col=ff0000>9th of Pentember", 63),
                    BookLine("Still can't think of a good", 64),
                    BookLine("name of 'The", 65)
                ),
                Page(
                    BookLine("Experiment'. Leaning", 66),
                    BookLine("Towards 'fritz'... Although", 67),
                    BookLine("am considering Lucy as", 68),
                    BookLine("it reminds me of my", 69),
                    BookLine("Mother!", 70)
                )
            )
        )
    }
}