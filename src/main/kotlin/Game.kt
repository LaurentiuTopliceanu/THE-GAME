class Game {
    fun run() {
        println("================================")
        println("   Welcome to  T H E  G A M E  ")
        println("================================")
        print("Enter your hero name: ")
        val name = readLine()?.trim()?.ifBlank { "Hero" } ?: "Hero"
        val player = Player(name)
        println("\nGreetings, $name! Your quest begins...")
        mainLoop(player)
    }
    private fun mainLoop(player: Player) {
        var running = true
        while (running && player.isAlive()) {
            player.showStats()
            println("\nWhere do you go?")
            println("1) Dark Forest  2) Mountain Pass  3) Ancient Ruins  4) Shop  5) Rest  6) Quit")
            when (readLine()?.trim()) {
                "1" -> encounter(player, "Dark Forest", EnemyFactory.goblin(), EnemyFactory.orc())
                "2" -> encounter(player, "Mountain Pass", EnemyFactory.orc(), EnemyFactory.troll())
                "3" -> encounter(player, "Ancient Ruins", EnemyFactory.troll(), EnemyFactory.dragon())
                "4" -> shop(player)
                "5" -> rest(player)
                "6" -> { running = false; println("Farewell, ${player.name}!") }
                else -> println("Invalid choice.")
            }
        }
        if (!player.isAlive()) println("\n You have fallen. Game over.")
    }
    private fun encounter(player: Player, location: String, vararg enemies: Enemy) {
        println("\nYou venture into $location...")
        for (enemy in enemies) {
            if (!player.isAlive()) break
            val battle = Battle(player, enemy)
            val won = battle.start()
            if (!won) return
            println("\nContinue deeper? (y/n)")
            if (readLine()?.trim()?.lowercase() != "y") return
        }
        println("You return safely from $location.")
    }
    private fun shop(player: Player) {
        println("\n=== SHOP ===")
        println("Gold: ${player.gold}")
        println("1) Small Health Potion  - 10g")
        println("2) Medium Health Potion - 20g")
        println("3) Power Shard          - 30g")
        println("4) Iron Skin Scroll     - 30g")
        println("5) Leave")
        when (readLine()?.trim()) {
            "1" -> buy(player, HealthPotion("Small"), 10)
            "2" -> buy(player, HealthPotion("Medium"), 20)
            "3" -> buy(player, AtkBooster(), 30)
            "4" -> buy(player, DefBooster(), 30)
            else -> println("Safe travels.")
        }
    }
    private fun buy(player: Player, item: Item, cost: Int) {
        if (player.gold >= cost) {
            player.gold -= cost
            player.inventory.add(item)
            println("  Purchased ${item.name}! Gold: ${player.gold}")
        } else println("  Not enough gold!")
    }
    private fun rest(player: Player) {
        if (player.gold >= 15) {
            player.gold -= 15
            player.heal(40)
            println("  You rest at the inn. (-15g)")
        } else println("  You cannot afford the inn (need 15g).")
    }
}
