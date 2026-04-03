import kotlin.random.Random

class Battle(private val player: Player, private val enemy: Enemy) {

    fun start(): Boolean {
        println("\n⚔️  A ${enemy.name} appears!")
        println("  ${enemy.name} | HP: ${enemy.hp} | ATK: ${enemy.attackPower} | DEF: ${enemy.defence}")
        while (player.isAlive() && enemy.isAlive()) {
            takeTurn()
        }
        return player.isAlive()
    }

    private fun takeTurn() {
        println("\n--- Your turn ---")
        println("1) Attack  2) Use Item  3) Flee")
        val choice = readLine()?.trim() ?: "1"
        when (choice) {
            "1" -> playerAttack()
            "2" -> useItemMenu()
            "3" -> flee()
            else -> playerAttack()
        }
        if (enemy.isAlive()) enemyAttack()
    }

    private fun playerAttack() {
        val dmg = player.attackPower + Random.nextInt(0, 6)
        println("  You strike the ${enemy.name} for $dmg damage!")
        enemy.takeDamage(dmg)
        if (!enemy.isAlive()) onEnemyDefeated()
    }

    private fun enemyAttack() {
        println("\n--- Enemy turn ---")
        enemy.attack(player)
    }

    private fun useItemMenu() {
        if (player.inventory.isEmpty()) {
            println("  No items in inventory!")
            playerAttack()
            return
        }
        println("  Choose an item:")
        player.inventory.forEachIndexed { i, item ->
            println("  ${i + 1}) ${item.name} - ${item.description}")
        }
        val idx = (readLine()?.trim()?.toIntOrNull() ?: 1) - 1
        if (idx in player.inventory.indices) {
            player.useItem(player.inventory[idx])
        } else {
            println("  Invalid choice, attacking instead.")
            playerAttack()
        }
    }

    private fun flee() {
        if (Random.nextInt(100) < 40) {
            println("  You fled from battle!")
        } else {
            println("  You failed to flee!")
            enemyAttack()
        }
    }

    private fun onEnemyDefeated() {
        println("\n  ✅ ${enemy.name} defeated!")
        player.gainXp(enemy.xpReward)
        player.gold += enemy.goldReward
        println("  You found ${enemy.goldReward} gold!")
        if (enemy.lootTable.isNotEmpty() && Random.nextInt(100) < 60) {
            val drop = enemy.lootTable.random()
            player.inventory.add(drop)
            println("  Item dropped: ${drop.name}!")
        }
    }
}
