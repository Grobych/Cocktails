package com.globa.cocktails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.globa.cocktails.R
import com.globa.cocktails.databinding.CocktailIngredientsItemBinding

class IngredientsAdapter(
    private val map: Map<String,String> = emptyMap()
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CocktailIngredientsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient : String, measure : String){
            binding.ingredient = ingredient
            binding.measure = measure
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.cocktail_ingredients_item,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(map.keys.toList()[position],map.values.toList()[position])
    }

    override fun getItemCount() = map.size
}