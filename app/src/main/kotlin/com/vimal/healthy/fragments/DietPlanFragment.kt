package com.vimal.healthy.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vimal.healthy.R
import com.vimal.healthy.adapters.CategoryButtonAdapter
import com.vimal.healthy.adapters.DPBannerItemAdapter
import com.vimal.healthy.adapters.DietPlanVerticalAdapter
import com.vimal.healthy.models.BannerModel
import com.vimal.healthy.models.CategoryButtonModel
import com.vimal.healthy.models.DietPlanModel
import kotlinx.android.synthetic.main.fragment_diet_plan.rvBannerList
import kotlinx.android.synthetic.main.fragment_diet_plan.rvCategoriesList
import kotlinx.android.synthetic.main.fragment_diet_plan.rvDietList


class DietPlanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dietList: List<DietPlanModel>
    private var dietPlanImage = arrayOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet_plan, container, false)

    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setVerticalRvData(4)
        dietPlanImage = resources.getStringArray(R.array.dietPlanImages)

        val bannerList = listOf(
            BannerModel(
                "Individual Recipes",
                "Free menu planning to suit your needs!",
                R.drawable.banner_pot
            ),
            BannerModel(
                "Wide Varieties",
                "A wide variety of food diets is available!",
                R.drawable.banner_image
            )
        )
        val adapter = DPBannerItemAdapter(bannerList)
        rvBannerList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvBannerList.adapter = adapter

        val categoryButtonMode = listOf(
            CategoryButtonModel("Baking", R.drawable.ic_baking, 1),
            CategoryButtonModel("Dessert", R.drawable.ic_dessert, 2),
            CategoryButtonModel("Salads", R.drawable.ic_salad_second, 3)
        )

        val categoryAdapter = CategoryButtonAdapter(categoryButtonMode)
        rvCategoriesList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategoriesList.adapter = categoryAdapter
        categoryAdapter.setOnItemClickListener(object :
            CategoryButtonAdapter.onRecyclerViewItemClickListener {
            override fun onItemClickListener(
                view: View?,
                position: Int,
                oldPosition: Int,
                categoryModel: CategoryButtonModel
            ) {
                if (position == oldPosition) {
                    setVerticalRvData(4)
                } else {
                    categoryModel.getNum()?.let { setVerticalRvData(it) }
                }

            }
        })

    }

    private fun setVerticalRvData(num: Int) {
        rvDietList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        dietList = listOf()

        if (num == 1) {
            dietList = listOf(
                DietPlanModel(
                    "Sticky Date Pudding",
                    "12 Kcal",
                    "3:20",
                    "Easy",
                    "4 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/2b88y783"
                ),
                DietPlanModel(
                    "Quinoa Biscuits Recipe",
                    "120 Kcal",
                    "5:20",
                    "Easy",
                    "4 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/4sdbu7wa"
                ),
                DietPlanModel(
                    "Strawberry Cupcakes",
                    "120 Kcal",
                    "6:40",
                    "Medium",
                    "7 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/ywwednb4"
                ),
                DietPlanModel(
                    "Bajra Tartlets With Fruit Custard",
                    "12 Kcal",
                    "7:35",
                    "Medium",
                    "7 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/3rrhfnwz"
                ),
            )

        } else if (num == 2) {
            dietList = listOf(
                DietPlanModel(
                    "Mango Rabri Recipe",
                    "15 Kcal",
                    "3:40",
                    "Easy",
                    "5 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/yw98p8x2"
                ),
                DietPlanModel(
                    "Fantakuchen: German cake",
                    "12 Kcal",
                    "4:25",
                    "Medium",
                    "7 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/bdzht23u"
                ),
                DietPlanModel(
                    "Phool Makhana Kheer",
                    "11 Kcal",
                    "3:50",
                    "Easy",
                    "3 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/mryf3mw3"
                ),
                DietPlanModel(
                    "Carrot Cake Muffins",
                    "15 Kcal",
                    "5:00",
                    "Medium",
                    "8 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/2p97aszx"
                )
            )
        } else if (num == 3) {
            dietList = listOf(
                DietPlanModel(
                    "Quinoa & Jamun Salad",
                    "9 Kcal",
                    "6:22",
                    "Medium",
                    "5 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/2vp77nhu"
                ),
                DietPlanModel(
                    "Cheese Souffles with Apple Walnut",
                    "14 Kcal",
                    "3:20",
                    "Easy",
                    "4 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/yc53atna"
                ),
                DietPlanModel(
                    "Classic Superfood Salad ",
                    "10 Kcal",
                    "3:00",
                    "Easy",
                    "3 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/yjuea3fd"
                ),
                DietPlanModel(
                    "Cucumber and peanut salad Recipe",
                    "13 Kcal",
                    "6:00",
                    "Medium",
                    "7 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/pzs7trj7"
                ),
            )
        } else if (num == 4) {
            dietList = listOf(
                DietPlanModel(
                    "Jowar Upma Recipe",
                    "14 Kcal",
                    "3:20",
                    "Easy",
                    "4 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/mmprc869"
                ),
                DietPlanModel(
                    "Peanut Protein Balls",
                    "10 Kcal",
                    "6:50",
                    "Medium",
                    "7 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/2jh9r8b2"
                ),
                DietPlanModel(
                    "Makhana Dry Fruit",
                    "13 Kcal",
                    "6:50",
                    "Easy",
                    "3 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/3edc2ucz"
                ),
                DietPlanModel(
                    "Chana Dal Kebab",
                    "15 Kcal",
                    "7:50",
                    "Medium",
                    "3 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/323nnmmp"
                ),
                DietPlanModel(
                    "Avocado Toast Recipe",
                    "16 Kcal",
                    "5:22",
                    "Medium",
                    "3 steps",
                    requireContext().resources.getColor(R.color.mediumColorPrimary),
                    requireContext().resources.getColor(R.color.mediumColorSecondary),
                    "#f2a041",
                    "https://tinyurl.com/yc6e2ce5"
                ),
                DietPlanModel(
                    "Aloo Corn Cutlets",
                    "10 Kcal",
                    "3:30",
                    "Easy",
                    "2 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/rwtzarhv"
                ),
                DietPlanModel(
                    "Beetroot Pulao Recipe",
                    "11 Kcal",
                    "4:00",
                    "Easy",
                    "2 steps",
                    requireContext().resources.getColor(R.color.easyColorPrimary),
                    requireContext().resources.getColor(R.color.easyColorSecondary),
                    "#38B000",
                    "https://tinyurl.com/2s3eavp5"
                )
            )
        }

        val adapter = DietPlanVerticalAdapter(dietList)
        rvDietList.adapter = adapter

    }

}