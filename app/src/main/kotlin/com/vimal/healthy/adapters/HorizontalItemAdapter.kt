package com.vimal.healthy.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vimal.healthy.R
import com.vimal.healthy.models.Model
import kotlinx.android.synthetic.main.horizontal_recyclerview_item.view.cardTitle
import kotlinx.android.synthetic.main.horizontal_recyclerview_item.view.dishImageView
import kotlinx.android.synthetic.main.horizontal_recyclerview_item.view.ll_horizontal_recyclerView
import kotlinx.android.synthetic.main.horizontal_recyclerview_item.view.totalDishes


class HorizontalItemAdapter(
    private val dishes: List<Model>
) : RecyclerView.Adapter<HorizontalItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val llHorizontalRecyclerViewItem = itemView.ll_horizontal_recyclerView
        val cardTitle: TextView = itemView.cardTitle
        val totalDishes: TextView = itemView.totalDishes
        val dishImage: ImageView = itemView.dishImageView
        val view = itemView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dishes = dishes[position]
        holder.cardTitle.text = dishes.cardTitle
        holder.totalDishes.text = dishes.totalDishes
        holder.dishImage.setImageDrawable(holder.view.context.getDrawable(dishes.dishImages))

        if (position % 2 == 0) {
            holder.llHorizontalRecyclerViewItem.background = ContextCompat.getDrawable(
                holder.view.context,
                R.drawable.items_background_card
            )
        }


//        holder.view.setOnClickListener{
//             val intent = Intent(it.context, DishesDescriptionActivity::class.java)
//             intent.putExtra("cardTitle", title[position])
//            intent.putExtra("totalDishes", dishes[position])
//            //intent.putExtra("dishImage", images[position])
//
//            holder.view.context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return dishes.size
    }
}