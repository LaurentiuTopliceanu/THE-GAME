class Enemy(
    val name: String,
    var hp: Int,
    val attackPower: Int,
    val defence: Int,
    val xpReward: Int,
    val goldReward: Int,
    val lootTable: List<Item> = emptyList()
) {
    fun isAlive() = hp > 0

    fun takeDamage(damage: Int) {
        val actual = maxOf(0, damage - defence)
        hp = maxOf(0, hp - actual)
        println("  $name takes $actual damage! (HP: $hp)")
    }

    fun attack(player: Player) {
        println("  $name attacks ${player.name}!")
        player.takeDamage(attackPower)
    }
}

object EnemyFactory {
    fun goblin() = Enemy(
        name = "Goblin",
        hp = 30, attackPower = 8, defence = 1,
        xpReward = 20, goldReward = 10,
        lootTable = listOf(HealthPotion("Small"))
    )

    fun orc() = Enemy(
        name = "Orc Warrior",
        hp = 60, attackPower = 14, defence = 4,
        xpReward = 45, goldReward = 25,
        lootTable = listOf(HealthPotion("Medium"), AtkBooster())
    )

    fun troll() = Enemy(
        name = "Cave Troll",
        hp = 90, attackPower = 18, defence = 6,
        xpReward = 70, goldReward = 40,
        lootTable = listOf(HealthPotion("Large"), DefBooster())
    )

    fun dragon() = Enemy(
        name = "Ancient Dragon",
        hp = 150, attackPower = 28, defence = 10,
        xpReward = 150, goldReward = 100,
        lootTable = listOf(HealthPotion("Large"), AtkBooster(), DefBooster())
    )
}
