package com.garth.studentgoalsice


import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalAdapter : ListAdapter<Goal, GoalAdapter.CategoryAdapter>(CategoryViewHolder())
{
    private var onClickListener: OnClickListener? = null
    class CategoryAdapter(view : View): RecyclerView.ViewHolder(view)
    {

    }
    override fun onCreateViewHolder(parent : ViewGroup,viewType:Int):CategoryAdapter
    {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryAdapter(inflater.inflate(
            R.layout.goal_item,parent,false
        ))
    }

    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: CategoryAdapter, position: Int)
    {
        val goal = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.txtDisplayTitle).text = goal.title
        holder.itemView.findViewById<TextView>(R.id.txtDisplayDescription).text = goal.description
        holder.itemView.findViewById<CheckBox>(R.id.cbCompleteGoal).isChecked = goal.completed
        holder.itemView.setOnClickListener{
            if (onClickListener != null){
                onClickListener!!.onClick(position, goal)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Goal)
    }
}

class CategoryViewHolder : DiffUtil.ItemCallback<Goal>()
{
    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }
}