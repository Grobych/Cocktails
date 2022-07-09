package com.globa.cocktails.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.globa.cocktails.R
import com.globa.cocktails.databinding.CocktailListFragmentItemBinding
import com.globa.cocktails.models.Cocktail

class CocktailsAdapter() : RecyclerView.Adapter<CocktailsAdapter.ViewHolder>() {

    var list : List<Cocktail> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value){
        field = value
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CocktailListFragmentItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(cocktail: Cocktail){
            binding.cocktail = cocktail
            binding.itemTagContainer.tags = cocktail.ingredients
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cocktail_list_fragment_item,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}