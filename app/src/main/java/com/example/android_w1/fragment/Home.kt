package com.example.android_w1.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_w1.*
import com.example.android_w1.databinding.FragmentHomeBinding
import com.example.android_w1.model.MenuViewModel
import com.example.android_w1.model.Restaurant
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : Fragment(), RestaurantAdapter.OnItemClickListener {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : MenuViewModel
    private lateinit var adapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        setupMenu()
        registerData()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.options_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_listview -> {
                val lm = LinearLayoutManager(context)
                binding.rvRestaurant.layoutManager = lm
            }
            R.id.action_gridview -> {
                val gm = GridLayoutManager(context,2)
                binding.rvRestaurant.layoutManager = gm
            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun registerData(){
        viewModel.listOfData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
    private fun setupMenu(){
        adapter = RestaurantAdapter(this)
        val lm = LinearLayoutManager(requireContext())
        binding.rvRestaurant.layoutManager = lm
        binding.rvRestaurant.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openProfile()
    }
    private fun openProfile(){
        binding.ivProfile.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout =layoutInflater.inflate(R.layout.item_view_restaurant_clicked,null)
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
                Toast.makeText(requireContext(),"Xoa item thanh cong", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            setView(dialogLayout)
            show()
        }
    }
    fun bottomNavigation(){
        BottomNavigationView.OnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.now_playing -> {
                    // Respond to navigation item 1 click
                    Toast.makeText(requireContext(),"Now playing clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.top_rating -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(requireContext(),"Top rating clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

}