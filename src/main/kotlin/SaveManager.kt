import java.io.File
import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileReader

object SaveManager {
    private val saveFile = File("savegame.txt")

    fun save(player: Player) {
        val itemNames = player.inventory.joinToString(",") { it.name }
        val data = listOf(
            player.name,
            player.level.toString(),
            player.hp.toString(),
            player.maxHp.toString(),
            player.attackPower.toString(),
            player.defence.toString(),
            player.xp.toString(),
            player.xpToNextLevel.toString(),
            player.gold.toString(),
            itemNames
        ).joinToString("|")
        FileWriter(saveFile).use { it.write(data) }
        println("  ✅ Game saved!")
    }

    fun load(): Player? {
        if (!saveFile.exists()) return null
        return try {
            val line = BufferedReader(FileReader(saveFile)).readLine() ?: return null
            val parts = line.split("|")
            if (parts.size < 10) return null
            val player = Player(parts[0])
            player.level = parts[1].toInt()
            player.hp = parts[2].toInt()
            player.maxHp = parts[3].toInt()
            player.attackPower = parts[4].toInt()
            player.defence = parts[5].toInt()
            player.xp = parts[6].toInt()
            player.xpToNextLevel = parts[7].toInt()
            player.gold = parts[8].toInt()
            val itemNames = parts[9]
            if (itemNames.isNotBlank()) {
                itemNames.split(",").forEach { name ->
                    val item = nameToItem(name.trim())
                    if (item != null) player.inventory.add(item)
                }
            }
            println("  ✅ Save file loaded!")
            player
        } catch (e: Exception) {
            println("  Save file corrupted, starting fresh.")
            null
        }
    }

    fun hasSave() = saveFile.exists()

    fun deleteSave() {
        if (saveFile.exists()) saveFile.delete()
        println("  Save file deleted.")
    }

    private fun nameToItem(name: String): Item? = when {
        name.contains("Large Health") -> HealthPotion("Large")
        name.contains("Medium Health") -> HealthPotion("Medium")
        name.contains("Small Health") -> HealthPotion("Small")
        name.contains("Power Shard") -> AtkBooster()
        name.contains("Iron Skin") -> DefBooster()
        else -> null
    }
}
