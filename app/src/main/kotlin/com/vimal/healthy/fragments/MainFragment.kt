package com.vimal.healthy.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.vimal.healthy.R
import com.vimal.healthy.activities.ProfileActivity
import com.vimal.healthy.adapters.HorizontalItemAdapter
import com.vimal.healthy.adapters.VerticalItemAdapter
import com.vimal.healthy.models.Model
import com.vimal.healthy.models.VerticalRvModel
import com.vimal.healthy.util.InternalStoragePhoto
import com.vimal.healthy.viewmodel.DataStoreViewModel
import com.vimal.healthy.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.drawerLayout
import kotlinx.android.synthetic.main.fragment_main.main_menu
import kotlinx.android.synthetic.main.fragment_main.main_profile
import kotlinx.android.synthetic.main.fragment_main.navigation_view
import kotlinx.android.synthetic.main.fragment_main.rv_dishesCard
import kotlinx.android.synthetic.main.fragment_main.rv_vertical_dishes
import kotlinx.android.synthetic.main.fragment_main.search_view
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


@AndroidEntryPoint
class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private val userViewModel: UserViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var filepath: Uri? = null
    private var detailsDescription = arrayOf<String>()
    private var detailsDescriptionComplete = arrayOf<String>()
    private lateinit var tempArrayList: MutableList<VerticalRvModel>
    private lateinit var verticalDishes: MutableList<VerticalRvModel>
    private lateinit var verticalAdapter: VerticalItemAdapter


    override fun onPause() {
        super.onPause()

        search_view.setQuery("", false)
        search_view.clearFocus()
        search_view.isIconified = true
        search_view.isIconified = true


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        navigation_view.setNavigationItemSelectedListener(this)

        main_menu.setOnClickListener {
            toggleDrawer()

        }

        getUserDetails()
        checkIfUserHasSavedDetails()


        main_profile.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)

        }

        detailsDescription = resources.getStringArray(R.array.dishDetails)
        detailsDescriptionComplete = resources.getStringArray(R.array.dishDetailsComplete)

        //initialize late init variables
        tempArrayList = ArrayList<VerticalRvModel>()
        verticalDishes = ArrayList<VerticalRvModel>()


        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()

                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    verticalDishes.forEach {
                        if (it.dishTitle.lowercase(Locale.getDefault()).contains(searchText)) {
                            tempArrayList.add(it)
                        }
                    }
                    rv_vertical_dishes.adapter!!.notifyDataSetChanged()
                } else {
                    tempArrayList.clear()
                    tempArrayList.addAll(verticalDishes)
                    rv_vertical_dishes.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })


        val dishes = listOf(
            Model("Vegetables", "10 Dishes", R.drawable.ic_vegetable),
            Model("Mushroom", "12 Dishes", R.drawable.ic_mushroom),
            Model("Fruits", "5 Dishes", R.drawable.ic_fruits),
            Model("Non-Veg", "8 Dishes", R.drawable.ic_meat),
            Model("Dairy Foods", "15 Dishes", R.drawable.ic_dairy),
            Model("Cereals", "7 Dishes", R.drawable.cereals)
        )


        verticalDishes = listOf(
            VerticalRvModel(
                "Kale And Quinoa Salad Recipe",
                "Vegetable",
                R.drawable.ic_diet_dish_main,
                "10g",
                "4g",
                "600g",
                "3g",
                intArrayOf(
                    R.drawable.chopped_kale,
                    R.drawable.cucumber,
                    R.drawable.olive_oil,
                    R.drawable.dried_red_onions,
                    R.drawable.feta_cheese,
                    R.drawable.ic_fruits
                )
            ),
            VerticalRvModel(
                "Stuffed Mushrooms Recipe",
                "Veg",
                R.drawable.ic_stuffed_mushrooms,
                "15g",
                "2.2g",
                "0.2g",
                "2.3g",
                intArrayOf(R.drawable.ic_mushroom, R.drawable.butter)
            ),
            VerticalRvModel(
                "Beetroot Idli Recipe",
                "Veg",
                R.drawable.ic_beetroot_idli,
                "7.3g",
                "4.24g",
                "3.2g",
                "2.13g",
                intArrayOf(R.drawable.ic_fruits)
            ),
            VerticalRvModel(
                "Jackfruit And Spinach Sambar Recipe",
                "Veg",
                R.drawable.ic_sambar,
                "0.3g",
                "4g",
                "6g",
                "3g",
                intArrayOf(R.drawable.ic_mushroom, R.drawable.butter)
            ),
            VerticalRvModel(
                "Beetroot-Amla Juice Recipe",
                "Vegetable Dish",
                R.drawable.ic_beetroot_juice,
                "13.3g",
                "4g",
                "6g",
                "3g",
                intArrayOf(
                    R.drawable.ic_beetroot,
                    R.drawable.ic_lemon,
                    R.drawable.ic_pudina,
                    R.drawable.ic_honey,
                    R.drawable.ic_giner
                )
            ),
        ) as MutableList<VerticalRvModel>


        val adapter = HorizontalItemAdapter(dishes)
        rv_dishesCard.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_dishesCard.adapter = adapter


        tempArrayList.addAll(verticalDishes)
        verticalAdapter =
            VerticalItemAdapter(tempArrayList, detailsDescription, detailsDescriptionComplete)
        rv_vertical_dishes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_vertical_dishes.adapter = verticalAdapter

    }

    private fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.favourite -> {
                Toast.makeText(requireContext().applicationContext, "yes", Toast.LENGTH_SHORT)
                    .show()
            }

            R.id.rate -> {
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun checkIfUserHasSavedDetails() {
        dataStoreViewModel.savedKey.observe(requireActivity()) {
            if (it != true) {
                // Glide.with(requireContext()).load(R.drawable.ic_baseline_person_24).centerInside().into(main_profile)
            } else if (it == true) {

            }
        }
    }


    private fun getUserDetails() {
        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.doGetUserDetails()
                userViewModel.userDetails.collect { users ->
                    val listOfImage = loadImageFromInternalStorage()
                    for (i in listOfImage) {
                        dataStoreViewModel.savedKey.observe(requireActivity()) {
                            if (it == true) {
                                Glide.with(requireContext()).load(i.bitmap).circleCrop()
                                    .into(main_profile)
                            } else {
                                Glide.with(requireContext()).load(R.drawable.ic_baseline_person_24)
                                    .centerInside().into(main_profile)
                            }
                        }

                    }
                }
            }
        }
    }

    private suspend fun loadImageFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = requireContext().filesDir.listFiles()
            files.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            }
        }
    }


}