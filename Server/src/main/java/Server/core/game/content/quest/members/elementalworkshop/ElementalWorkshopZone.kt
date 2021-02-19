package core.game.content.quest.members.elementalworkshop

import core.game.content.dialogue.FacialExpression
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.build.DynamicRegion
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class ElementalWorkshopZone : MapZone("elewrkshop-zone", false), Plugin<Unit> {

    private val NPCS = emptyArray<NPC>(
        /*NPC.create(7938, Location.create(2524, 5005, 0), Direction.NORTH),
        NPC.create(7965, Location.create(2524, 4997, 0), Direction.NORTH)*/
    )

    /**
     * The region of the zone.
     */
    var region: DynamicRegion = DynamicRegion.create(10906)

    /**
     * The base location.
     */
    private var base: Location? = null

    override fun configure() {
        register(ZoneBorders(2688, 9856, 2751, 9934))
        region = DynamicRegion.create(10906)
        setRegionBase()
        registerRegion(region.id)
    }

    private fun setNpcs() {
        for (n in NPCS) {
            val ent = NPC.create(n.id, n.location, n.direction)
            ent.location = base!!.transform(n.location.localX, n.location.localY, 0)
            ent.isRespawn = false
            ent.init()
            ent.isWalks = false
            ent.animate(Animation(-1))
        }
    }

    override fun enter(e: Entity?): Boolean {
        e ?: return false
        if (e is Player) {
            var player = e.asPlayer()
            if (!player.getAttribute("elewrkshp:first-time", false)) {
                player.setAttribute("/save:elewrkshp:first-time", true)
                player.dialogueInterpreter.sendDialogues(
                    player,
                    FacialExpression.NEUTRAL,
                    "Now to explore this area thoroughly, to find what",
                    "forgotten secrets it contains."
                )
                player.teleport(getBase()!!.transform(25, 31, 0))
            } else {
                player.teleport(base!!.transform(player.location.localX, player.location.localY, 0))
            }
            return true
        }
        return false
    }

    /**
     * Sets the region base.
     */
    private fun setRegionBase() {
        setBase(Location.create(region.borders.southWestX, region.borders.southWestY, 0))
    }

    /**
     * Gets the base.
     *
     * @return the base
     */
    fun getBase(): Location? {
        return base
    }

    /**
     * Sets the base.
     *
     * @param base the base to set.
     */
    fun setBase(base: Location?) {
        this.base = base
    }

    override fun newInstance(arg: Unit?): Plugin<Unit> {
        ZoneBuilder.configure(this)
        return this
    }

    override fun fireEvent(identifier: String?, vararg args: Any?): Any {
        return Unit
    }
}