package ayds.songinfo.moredetails.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDAO(): CardDAO
}

@Entity
data class CardEntity(
    @PrimaryKey
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    val source: Int,
    val logoURL : String
)

@Dao
interface CardDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(article: CardEntity)

    @Query("SELECT * FROM CardEntity WHERE artistName LIKE :artistName LIMIT 1")
    fun getCardByArtistName(artistName: String): CardEntity?

    @Query("SELECT * FROM CardEntity WHERE artistName LIKE :artistName")
    fun getAllCardsByArtistName(artistName: String): List<CardEntity>

}