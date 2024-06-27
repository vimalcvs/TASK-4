package com.vimal.healthy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vimal.healthy.R
import com.vimal.healthy.models.BannerModel
import kotlinx.android.synthetic.main.banner_one_rv_item.view.bannerCaption
import kotlinx.android.synthetic.main.banner_one_rv_item.view.bannerImage
import kotlinx.android.synthetic.main.banner_one_rv_item.view.bannerTitle
import kotlinx.android.synthetic.main.banner_one_rv_item.view.rlBannerOne

class DPBannerItemAdapter(private val banner: List<BannerModel>) :
    RecyclerView.Adapter<DPBannerItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlBannerOne = itemView.rlBannerOne
        val bannerTitle = itemView.bannerTitle
        val bannerCaption = itemView.bannerCaption
        val bannerImage = itemView.bannerImage
        val view = itemView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.banner_one_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val banner = banner[position]
        holder.bannerTitle.text = banner.title
        holder.bannerCaption.text = banner.caption
        holder.bannerImage.setImageDrawable(holder.view.context.getDrawable(banner.image))
        if (position % 2 == 0) {
            holder.rlBannerOne.background = ContextCompat.getDrawable(
                holder.view.context,
                R.drawable.banner_item_background_second
            )
        }
    }

    override fun getItemCount(): Int {
        return banner.size
    }

}