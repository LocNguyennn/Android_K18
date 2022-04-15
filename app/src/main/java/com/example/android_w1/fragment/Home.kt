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
import com.example.android_w1.model.Movie
import com.example.android_w1.viewModel_Adapter.HomeVM
import com.example.android_w1.viewModel_Adapter.MovieAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeVM
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.options_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onStart() {
        super.onStart()
        viewModel.getNowPlaying()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_listview -> {
                val lm = LinearLayoutManager(context)
                binding.rvMovie.layoutManager = lm
            }
            R.id.action_gridview -> {
                val gm = GridLayoutManager(context,2)
                binding.rvMovie.layoutManager = gm
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMovieList()
        registerMovieList()
        registerErrorList()
        bottomNavigation()
        openProfile()
    }
    private fun openProfile(){
        binding.ivProfile.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setUpMovieList() {
        adapter = MovieAdapter(this)
        val lm = LinearLayoutManager(context)
        binding.rvMovie.layoutManager = lm
        binding.rvMovie.adapter = adapter
    }

    private fun registerMovieList() {
        viewModel.movieData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun registerErrorList() {
        viewModel.errEvent.observe(viewLifecycleOwner){
            //show dialog
        }
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout =layoutInflater.inflate(R.layout.item_view_movie_clicked,null)
        val tvName = dialogLayout.findViewById<TextView>(R.id.txtMovieName)
        val tvDesc = dialogLayout.findViewById<TextView>(R.id.txtMovieDescription)
        val ivImage = dialogLayout.findViewById<ImageView>(R.id.imgMovie)
        val movie : Movie = adapter.getMovie(position)
        tvName.text = movie.title
        tvDesc.text = movie.overview
        val urlImage = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(dialogLayout).load(urlImage).into(ivImage)
        with(builder){
            setTitle("${tvName.text}")
            setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            setView(dialogLayout)
            show()
        }
    }
    private fun bottomNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.now_playing -> {
                    // Respond to navigation item 1 click
                    Toast.makeText(requireContext(),"Now playing clicked", Toast.LENGTH_SHORT).show()
                    viewModel.getNowPlaying()
                    true
                }
                R.id.top_rating -> {
                    // Respond to navigation item 2 click
                    Toast.makeText(requireContext(),"Top rating clicked", Toast.LENGTH_SHORT).show()
                    viewModel.getTopRatedMovie()
                    true
                }
                else -> false
            }

        }
    }

}