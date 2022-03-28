package com.example.android_w1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_w1.databinding.MainMenuBinding

class MainMenu : AppCompatActivity() {
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
                val lm = GridLayoutManager(this,2)
                binding.rvRestaurant.layoutManager = lm
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
        adapter = RestaurantAdapter()
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
                val intentLogin = Intent(this@MainMenu, LoginActivity::class.java)
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
}