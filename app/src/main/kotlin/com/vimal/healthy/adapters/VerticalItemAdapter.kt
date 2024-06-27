package com.vimal.healthy.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vimal.healthy.R
import com.vimal.healthy.activities.DishesDescriptionActivity
import com.vimal.healthy.models.VerticalRvModel
import kotlinx.android.synthetic.main.vertical_recyclerview_item.view.iv_dishesImage
import kotlinx.android.synthetic.main.vertical_recyclerview_item.view.ll_vertical_recyclerView
import kotlinx.android.synthetic.main.vertical_recyclerview_item.view.ll_vertical_recyclerView_image_background
import kotlinx.android.synthetic.main.vertical_recyclerview_item.view.tv_dish_caption
import kotlinx.android.synthetic.main.vertical_recyclerview_item.view.tv_dish_title


class VerticalItemAdapter(
    private val dishes: List<VerticalRvModel>,
    private val details: Array<String>, private val detailsComplete: Array<String>
) : RecyclerView.Adapter<VerticalItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llVerticalRecyclerView = itemView.ll_vertical_recyclerView
        val dishesTitle: TextView = itemView.tv_dish_title
        val dishesCaption: TextView = itemView.tv_dish_caption
        val dishesImages: ImageView = itemView.iv_dishesImage
        val verticalRecyclerView_imageBg = itemView.ll_vertical_recyclerView_image_background
        val view = itemView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vertical_recyclerview_item, parent, false)
        return ViewHolder(view)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dishes = dishes[position]



        holder.dishesTitle.text = dishes.dishTitle
        holder.dishesCaption.text = dishes.dishCalories
        holder.dishesImages.setImageDrawable(holder.view.context.getDrawable(dishes.dishImage))

        if (position % 2 == 0) {
            holder.verticalRecyclerView_imageBg.background = ContextCompat.getDrawable(
                holder.view.context,
                R.drawable.vertical_items_background_card_blue
            )
        }

        holder.view.setOnClickListener {
            val intent = Intent(it.context, DishesDescriptionActivity::class.java)
            intent.putExtra("dishesTitle", dishes.dishTitle)
            intent.putExtra("dishCategory", dishes.dishCategory)
            intent.putExtra("calories", dishes.dishCalories)
            intent.putExtra("dishesImages", dishes.dishImage)
            intent.putExtra("protein", dishes.protein)
            intent.putExtra("fat", dishes.fat)
            intent.putExtra("carbo", dishes.carbo)
            intent.putExtra("details", details[position])
            intent.putExtra("detailsComplete", detailsComplete[position])


            intent.putExtra("arrayOfInt", dishes.images)


            holder.view.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dishes.size
    }


}