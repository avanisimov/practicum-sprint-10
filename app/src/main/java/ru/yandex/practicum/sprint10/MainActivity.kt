package ru.yandex.practicum.sprint10

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = (1..20).map {
            ListElement.createRandomElement(id = it.toString())
        }
        val problem = mutableListOf(Problem())
        val header = mutableListOf(Header("I'm Header"))

        val items = header + data

        val adapter = MainActivityAdapter().apply {
            this.items = items


            onListElementClickListener = OnListElementClickListener { item ->
                Toast.makeText(this@MainActivity, "Click on ${item.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val itemsRv: RecyclerView = findViewById(R.id.items_rv)
        itemsRv.layoutManager = LinearLayoutManager(this)
        itemsRv.adapter = adapter
    }

}

class MainActivityAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LIST_ELEMENT = 1
        const val VIEW_TYPE_PROBLEM = 2
        const val VIEW_TYPE_HEADER = 3
    }

    var items: List<Any> = emptyList()
    var onListElementClickListener: OnListElementClickListener? = null

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return when (item) {
            is ListElement -> VIEW_TYPE_LIST_ELEMENT
            is Problem -> VIEW_TYPE_PROBLEM
            is Header -> VIEW_TYPE_HEADER
            else -> throw java.lang.IllegalStateException("Cannot find viewType for item $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LIST_ELEMENT -> ListElementViewHolder(parent)
            VIEW_TYPE_PROBLEM -> ProblemViewHolder(parent)
            VIEW_TYPE_HEADER -> HeaderViewHolder(parent)
            else -> throw java.lang.IllegalStateException("Cannot create ViewHolder for viewType $viewType")
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item) {
            is ListElement -> {
                val listElementViewHolder: ListElementViewHolder = holder as ListElementViewHolder
                listElementViewHolder.bind(item)
                listElementViewHolder.itemView.setOnClickListener {
                    onListElementClickListener?.onListElementClick(item)
                }
            }
            is Problem -> {
                val problemViewHolder: ProblemViewHolder = holder as ProblemViewHolder
            }
            is Header -> {
                val headerViewHolder: HeaderViewHolder = holder as HeaderViewHolder
                headerViewHolder.bind(item)
            }
            else -> throw java.lang.IllegalStateException("Cannot find viewType for item $item")
        }
    }

}

fun interface OnListElementClickListener {
    fun onListElementClick(item: ListElement)

}

val list1 = listOf(
    ListElement(id = "1", name = "ListElement 1", Color.MAGENTA),
    ListElement(id = "2", name = "ListElement 2", Color.CYAN),
    ListElement(id = "3", name = "ListElement 3", Color.BLACK),
)

val list2 = listOf(
    ListElement(id = "1", name = "ListElement 1", Color.MAGENTA),
    ListElement(id = "3", name = "ListElement BLACK", Color.BLACK),
    ListElement(id = "2", name = "ListElement 2", Color.YELLOW),
)
