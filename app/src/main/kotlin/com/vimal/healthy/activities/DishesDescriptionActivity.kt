package com.vimal.healthy.activities


import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vimal.healthy.R
import com.vimal.healthy.adapters.IngredientsItemAdapter
import kotlinx.android.synthetic.main.activity_dishes_description.back_button_dishesDescription
import kotlinx.android.synthetic.main.activity_dishes_description.description_dish_image
import kotlinx.android.synthetic.main.activity_dishes_description.rv_ingredients
import kotlinx.android.synthetic.main.activity_dishes_description.tv_calories
import kotlinx.android.synthetic.main.activity_dishes_description.tv_carbo
import kotlinx.android.synthetic.main.activity_dishes_description.tv_details_description
import kotlinx.android.synthetic.main.activity_dishes_description.tv_dish_category
import kotlinx.android.synthetic.main.activity_dishes_description.tv_dish_description_title
import kotlinx.android.synthetic.main.activity_dishes_description.tv_fat
import kotlinx.android.synthetic.main.activity_dishes_description.tv_protein
import kotlinx.android.synthetic.main.custom_dialog_layout.cancelDialogBox
import kotlinx.android.synthetic.main.custom_dialog_layout.tv_ingredientsDialogBox


class DishesDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dishes_description)

        val bundle: Bundle = intent.extras!!

        val dishTitle = intent.getStringExtra("dishesTitle")
        val dishCategory = intent.getStringExtra("dishCategory")
        val dishCalories = intent.getStringExtra("calories")
        val dishImage = bundle.getInt("dishesImages")
        val protein = intent.getStringExtra("protein")
        val fat = intent.getStringExtra("fat")
        val carbo = intent.getStringExtra("carbo")
        val details = intent.getStringExtra("details")
        val ingredientsImages = intent.getIntArrayExtra("arrayOfInt")
        //end

        /*
        Assigning the extra values from intent to respective fields
         */
        tv_dish_description_title.text = dishTitle
        tv_dish_category.text = dishCategory
        tv_protein.text = protein
        tv_calories.text = dishCalories
        tv_fat.text = fat
        tv_carbo.text = carbo
        tv_details_description.text = details
        description_dish_image.setImageResource(dishImage)


        //end


        /*
         Setting up the ingredient adapter
         */
        //start
        val adapter = ingredientsImages?.let { IngredientsItemAdapter(it) }
        rv_ingredients.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_ingredients.adapter = adapter
        //end

        /*
        Creating spannable text for Read More... dialog
         */
        //Start
        val text = tv_details_description.text
        val spannableString = SpannableString(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {

                showIngredientsDetailsDialog()

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#75c842")
            }
        }

        val indexStart = tv_details_description.length() - 12
        val endIndex = tv_details_description.length() - 0

        spannableString.setSpan(
            clickableSpan,
            indexStart,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_details_description.text = (spannableString)
        tv_details_description.movementMethod = LinkMovementMethod.getInstance()
        //end

        back_button_dishesDescription.setOnClickListener {
            onBackPressed()
        }


    }


    fun showIngredientsDetailsDialog() {
        val dialog = Dialog(this@DishesDescriptionActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_layout)
        dialog.setCancelable(true)
        val details = intent.getStringExtra("detailsComplete")
        dialog.tv_ingredientsDialogBox.text = details


        val cancelButton = dialog.cancelDialogBox
        cancelButton.setOnClickListener {
            dialog.dismiss()

        }
        dialog.show()

    }


}