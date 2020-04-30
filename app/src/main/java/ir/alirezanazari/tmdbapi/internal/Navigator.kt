package ir.alirezanazari.tmdbapi.internal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ir.alirezanazari.tmdbapi.R
import ir.alirezanazari.tmdbapi.ui.movieList.MoviesListFragment


class Navigator {

    companion object{

        fun openMoviesList(fm: FragmentManager){
            load(true , fm , MoviesListFragment())
        }

        fun openMovieDetail(id: Long, fm: FragmentManager){

        }

        private fun load(isReplace: Boolean , fm:FragmentManager , fragment: Fragment){
            if (isReplace){
                fm.beginTransaction()
                    .addToBackStack(fragment.javaClass.name)
                    .replace(R.id.fragments_container , fragment, fragment.javaClass.name)
                    .commit()
            }else{
                fm.beginTransaction()
                    .addToBackStack(fragment.javaClass.name)
                    .add(R.id.fragments_container , fragment, fragment.javaClass.name)
                    .commit()
            }
        }
    }
}