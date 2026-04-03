abstract class Item(val name: String, val description: String) {
    abstract fun apply(player: Player)
}

class HealthPotion(size: String = "Small") : Item(
    name = "$size Health Potion",
    description = "Restores HP"
) {
    private val healAmount = when (size) {
        "Large" -> 60
        "Medium" -> 35
        else -> 20
    }
    override fun apply(player: Player) {
        player.heal(healAmount)
    }
}

class AtkBooster : Item(
    name = "Power Shard",
    description = "Permanently increases attack by 5"
) {
    override fun apply(player: Player) {
        player.attackPower += 5
        println("  Attack power increased to ${player.attackPower}!")
    }
}

class DefBooster : Item(
    name = "Iron Skin Scroll",
    description = "Permanently increases defence by 3"
) {
    override fun apply(player: Player) {
        player.defence += 3
        println("  Defence increased to ${player.defence}!")
    }
}
