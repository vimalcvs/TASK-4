package com.vimal.healthy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vimal.healthy.R
import com.vimal.healthy.models.CategoryButtonModel
import kotlinx.android.synthetic.main.dietplan_category_button_item.view.buttonCategory
import kotlinx.android.synthetic.main.dietplan_category_button_item.view.categoryImage
import kotlinx.android.synthetic.main.dietplan_category_button_item.view.lineView
import kotlinx.android.synthetic.main.dietplan_category_button_item.view.tvCategoryName


class CategoryButtonAdapter(private val categoriesList: List<CategoryButtonModel>) :
    RecyclerView.Adapter<CategoryButtonAdapter.ViewHolder>() {

    private var mItemClickListener: onRecyclerViewItemClickListener? = null
    private var oldposition = -1


    private val listener: OnItemsClickListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tvCategoryName
        val image: ImageView = itemView.categoryImage
        val lineView: View = itemView.lineView
        val view = itemView
        val buttonCategory = itemView.buttonCategory!!

        fun setName(name: String) {
            this.name.text = name
        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dietplan_category_button_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categories = categoriesList[position]
        holder.setName(categoriesList[position].getName()!!)
        holder.image.setImageDrawable(holder.view.context.getDrawable(categories.getImage()!!))

        holder.buttonCategory.setOnClickListener(View.OnClickListener { v ->
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClickListener(
                    v,
                    holder.adapterPosition,
                    oldposition,
                    categories
                )
            }

            listener?.onItemClick(categories)

            if (oldposition == position) {
                oldposition = -1
                notifyDataSetChanged()
                return@OnClickListener
            }
            oldposition = position
            notifyDataSetChanged()
        })

        if (position == oldposition) {
            holder.buttonCategory.background =
                holder.view.context.getDrawable(R.drawable.diet_plan_menu_button_bg_selected)
            holder.lineView.background = holder.view.context.getDrawable(R.color.white)
            holder.name.setTextColor(holder.view.context.resources.getColor(R.color.white))

        } else {
            holder.buttonCategory.background =
                holder.view.context.getDrawable(R.drawable.diet_plan_menu_button_bg)
            holder.lineView.background = holder.view.context.getDrawable(R.color.lineColor)
            holder.name.setTextColor(holder.view.context.resources.getColor(R.color.secondary_text_color))
        }

        if (position == 0) {
            holder.image.rotation = (-30).toFloat()
        }

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    //Interface to perform events on Main-List item click
    interface OnItemsClickListener {
        fun onItemClick(buttonCategoryModel: CategoryButtonModel)
    }

    fun setOnItemClickListener(mItemClickListener: onRecyclerViewItemClickListener?) {
        this.mItemClickListener = mItemClickListener
    }


    interface onRecyclerViewItemClickListener {
        fun onItemClickListener(
            view: View?,
            position: Int,
            oldPosition: Int,
            categoryModel: CategoryButtonModel
        )
    }
}