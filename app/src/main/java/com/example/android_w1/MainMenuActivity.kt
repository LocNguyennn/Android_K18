package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_w1.databinding.MainMenuBinding
import com.example.android_w1.model.Restaurant

class MainMenuActivity : AppCompatActivity(), RestaurantAdapter.OnItemClickListener {
    private lateinit var binding : MainMenuBinding
    private lateinit var viewModel : MenuViewModel
    private lateinit var adapter: RestaurantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.main_menu)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        setupMenu()
        registerData()
        openProfile()
        logout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_listview -> {
               val lm = LinearLayoutManager(this)
                binding.rvRestaurant.layoutManager = lm
            }
            R.id.action_gridview -> {
                val gm = GridLayoutManager(this,2)
                binding.rvRestaurant.layoutManager = gm
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    private fun registerData(){
        viewModel.listOfData.observe(this){
            adapter.submitList(it)
        }
    }
    private fun setupMenu(){
        adapter = RestaurantAdapter(this@MainMenuActivity)
        val lm = LinearLayoutManager(this)
        binding.rvRestaurant.layoutManager = lm
        binding.rvRestaurant.adapter = adapter
    }
    private fun openProfile(){
        binding.ivProfile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private fun logout(){
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Log out")
            setMessage("Do you want to log out?")
            setPositiveButton("YES"){dialog, _ ->
                val intentLogin = Intent(this@MainMenuActivity, LoginActivity::class.java)
                startActivity(intentLogin)
                dialog.dismiss()
            }
            setNegativeButton("NO"){dialog, _ ->
                dialog.dismiss()
            }
        }
        binding.btnBack.setOnClickListener {
            builder.show()
        }
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(this)
        val dialogLayout =layoutInflater.inflate(R.layout.item_view_restaurant,null)
        val tvName = dialogLayout.findViewById<TextView>(R.id.txtRestaurantName)
        val tvAddress = dialogLayout.findViewById<TextView>(R.id.txtRestaurantAddr)
        val ivImage = dialogLayout.findViewById<ImageView>(R.id.imgRestaurant)
        tvName.text = RestaurantStore.getDataset()[position].name
        tvAddress.text = RestaurantStore.getDataset()[position].address
        Glide.with(dialogLayout).load(RestaurantStore.getDataset()[position].image).into(ivImage)
        with(builder){
            setTitle("Remove item")
            setMessage("Do you want to remove this item?")
            setPositiveButton("Delete"){dialog, which ->
                val restaurant : ArrayList<Restaurant>  = RestaurantStore.getDataset()
                restaurant.removeAt(position)
                adapter.submitList(restaurant)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this@MainMenuActivity,"Xoa item thanh cong",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            setView(dialogLayout)
            show()
        }
    }
}