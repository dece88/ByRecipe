package com.example.byrecipe.Adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.byrecipe.Activity.ClickReceiptActivity
import com.example.byrecipe.Activity.FormRecipeActivity
import com.example.byrecipe.CustomOnItemClickListener
import com.example.byrecipe.DBHelper.DBHelperRecipe
import com.example.byrecipe.Model.Recipe
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.recipe_row.view.*

class RecipeAdapter (private val activity: Activity) : RecyclerView.Adapter<RecipeAdapter.NoteViewHolder>() {
    private lateinit var dbRecipe: DBHelperRecipe
    public var user: User ?= null
    var listRecipes = ArrayList<Recipe>()
        set(listRecipes) {
            if (listRecipes.size > 0) {
                this.listRecipes.clear()
            }
            this.listRecipes.addAll(listRecipes)
            notifyDataSetChanged()
        }

    fun addItem(recipe: Recipe) {
        this.listRecipes.add(recipe)
        notifyItemInserted(this.listRecipes.size - 1)
    }

    fun updateItem(position: Int, recipe: Recipe) {
        this.listRecipes[position] = recipe
        notifyItemChanged(position, recipe)
    }

    fun removeItem(position: Int) {
        this.listRecipes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listRecipes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_row, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listRecipes[position])
    }

    override fun getItemCount(): Int = this.listRecipes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {
            with(itemView){
                dbRecipe = DBHelperRecipe(context)
                var dataImageProfile: Bitmap? = dbRecipe.getImageProfile(recipe) //ambil dari database
                if(dataImageProfile!=null){
                    val imageView: ImageView = findViewById(R.id.foto)
                    imageView.setImageBitmap(dataImageProfile) //diset
                }
                tv_item_name.text = recipe.nama
                tv_item_description.text = recipe.waktu + "minutes"

                cv_item_recipe.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, ClickReceiptActivity::class.java)
                        intent.putExtra(ClickReceiptActivity.EXTRA_POSITION, position)
                        intent.putExtra(ClickReceiptActivity.USER, user)
                        intent.putExtra(ClickReceiptActivity.EXTRA_NOTE, recipe)
                        activity.startActivityForResult(intent, ClickReceiptActivity.REQUEST_UPDATE)
                    }
                }))
            }
        }
    }
}