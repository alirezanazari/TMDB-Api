package ir.alirezanazari.tmdbapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.internal.Navigator
import ir.alirezanazari.tmdbapi.ui.movieList.MoviesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMovieFragment()
    }

    private fun loadMovieFragment(){
        Navigator.openMoviesList(supportFragmentManager)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1){
            val fragment = supportFragmentManager.findFragmentById(R.id.fragments_container)
            if(fragment is MoviesListFragment){
                if(fragment.onBackPressed()) finish()
                return
            }else {
                finish()
                return
            }
        }
        super.onBackPressed()
    }
}
