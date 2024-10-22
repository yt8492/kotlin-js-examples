import app.cash.sqldelight.async.coroutines.awaitAsList
import com.yt8492.express.db.Database
import cz.sazel.sqldelight.node.sqlite3.executeSuspendingAsList
import cz.sazel.sqldelight.node.sqlite3.initSqlite3SqlDriver
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.js.json
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
suspend fun main() {
  val mutex = Mutex()
  val driver = initSqlite3SqlDriver(
    filename = "todo.db",
    schema = Database.Schema,
  )
  val database = Database(driver)
  val queries = database.todoQueries
  val app = express()
  app.use(express.json())
  app.use(cors())
  app.get("/todos") { _, res, next ->
    MainScope().promise {
      mutex.withLock {
        val todos = queries.selectAll()
          .executeSuspendingAsList()
          .map {
            json("id" to it.id, "text" to it.text)
          }
          .toTypedArray()
        res.json(todos)
      }
    }.catch(next)
  }
  app.post("/todos") { req, res, next ->
    val text = req.body.text as? String ?: run {
      res.status(400).json(json("message" to "text not found"))
      return@post
    }
    val id = Uuid.random().toHexString()
    MainScope().promise {
      mutex.withLock {
        database.transaction {
          queries.insert(id, text)
        }
        res.sendStatus(201)
      }
    }.catch(next)
  }
  app.delete("/todos/:id") { req, res, next ->
    val id = req.params["id"] as? String ?: run {
      res.status(400).json(json("message" to "invalid id"))
      return@delete
    }
    MainScope().promise {
      mutex.withLock {
        database.transaction {
          queries.delete(id)
        }
        res.sendStatus(204)
      }
    }
  }
  app.listen(8080)
}
