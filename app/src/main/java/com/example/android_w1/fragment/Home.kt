package com.example.android_w1.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_w1.*
import com.example.android_w1.databinding.FragmentHomeBinding
import com.example.android_w1.model.Restaurant

class Home : Fragment(), RestaurantAdapter.OnItemClickListener {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : MenuViewModel
    private lateinit var adapter: RestaurantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        setupMenu()
        registerData()
//        openProfile()
//        logout()
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
//    private fun openProfile(){
//        binding.ivProfile.setOnClickListener {
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
//        }
//    }
//    private fun logout(){
//        val builder = AlertDialog.Builder(requireContext())
//        builder.apply {
//            setTitle("Log out")
//            setMessage("Do you want to log out?")
//            setPositiveButton("YES"){dialog, _ ->
//                val intentLogin = Intent(this, LoginActivity::class.java)
//                startActivity(intentLogin)
//                dialog.dismiss()
//            }
//            setNegativeButton("NO"){dialog, _ ->
//                dialog.dismiss()
//            }
//        }
//        binding.btnBack.setOnClickListener {
//            builder.show()
//        }
//    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
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
}