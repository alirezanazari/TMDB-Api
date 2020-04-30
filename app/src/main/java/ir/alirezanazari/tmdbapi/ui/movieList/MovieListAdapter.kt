package ir.alirezanazari.tmdbapi.ui.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alirezanazari.data.utils.Consts.IMAGE_BASE_URL
import ir.alirezanazari.domain.entities.MovieEntity
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.internal.ImageLoader
import kotlinx.android.synthetic.main.row_movie_list.view.*


class MovieListAdapter(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<MovieEntity?>()
    var onClick: ((id: Long?) -> Unit)? = null

    fun setItems(venues: List<MovieEntity>) {
        items.addAll(venues)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addLoader() {
        items.add(null)
        notifyItemInserted(items.size - 1)
    }

    fun removeLoader() {
        if (items.size == 0) return
        val item = items[items.size - 1]
        if (item == null) {
            items.remove(item)
            notifyItemRemoved(items.size - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null)
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_movie_list,
                    parent,
                    false
                )
            )
        else
            LoaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_loader,
                    parent,
                    false
                )
            )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoaderViewHolder) {
            holder.bind()
        } else if (holder is MovieViewHolder) {
            if (items[position] != null) holder.bind(items[position]!!)
        }    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MovieEntity){
            itemView.apply {
                tvName.text = item.title
                tvDescription.text = if(item.overview.isEmpty()) "No Description..." else item.overview
                tvRate.text = "Rate : ${item.voteAverage} of ${item.voteCount} people"
                imageLoader.load(ivPoster , "$IMAGE_BASE_URL${item.posterPath}" , R.drawable.place_holder)
                setOnClickListener { onClick?.invoke(items[adapterPosition]?.id) }
            }
        }
    }

    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.apply {

            }
        }
    }

}