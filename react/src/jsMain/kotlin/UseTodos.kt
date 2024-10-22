import data.Todo
import react.useCallback
import react.useState

fun useTodos() {
  val (state, setState) = useState {
    UseTodosResult(
      todos = listOf(),
      loading = false,
      refetch = {},
    )
  }
  val fetchTodos = useCallback(Unit) {

  }
}

data class UseTodosResult(
  val todos: List<Todo>,
  val loading: Boolean,
  val refetch: () -> Unit,
)
