class Player(val name: String) {
    var hp: Int = 100
    val maxHp: Int = 100
    var attackPower: Int = 15
    var defence: Int = 5
    var level: Int = 1
    var xp: Int = 0
    val xpToNextLevel: Int = 100
    val inventory: MutableList<Item> = mutableListOf()
    var gold: Int = 50

    fun isAlive() = hp > 0

    fun takeDamage(damage: Int) {
        val actual = maxOf(0, damage - defence)
        hp = maxOf(0, hp - actual)
        println("  $name takes $actual damage! (HP: $hp/$maxHp)")
    }

    fun heal(amount: Int) {
        hp = minOf(maxHp, hp + amount)
        println("  $name heals $amount HP! (HP: $hp/$maxHp)")
    }

    fun gainXp(amount: Int) {
        xp += amount
        println("  $name gains $amount XP!")
        if (xp >= xpToNextLevel) levelUp()
    }

    private fun levelUp() {
        level++
        xp = 0
        attackPower += 5
        defence += 2
        hp = maxHp
        println("  *** $name levelled up to Level $level! ***")
        println("  Attack: $attackPower | Defence: $defence")
    }

    fun useItem(item: Item) {
        item.apply(this)
        inventory.remove(item)
    }

    fun showStats() {
        println("\n=== $name (Level $level) ===")
        println("HP: $hp/$maxHp | ATK: $attackPower | DEF: $defence")
        println("XP: $xp/$xpToNextLevel | Gold: $gold")
        val inv = if (inventory.isEmpty()) "empty" else inventory.joinToString { it.name }
        println("Inventory: $inv")
    }
}
