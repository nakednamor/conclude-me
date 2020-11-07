package com.nakednamor.conclude.me.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nakednamor.conclude.me.R
import com.nakednamor.conclude.me.data.weight.WeightRecord

class WeightRecordListAdapter(
    context: Context
) : RecyclerView.Adapter<WeightRecordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var weightRecords = emptyList<WeightRecord>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightRecordViewHolder {
        val itemView = inflater.inflate(R.layout.weight_recyclerview_item, parent, false)
        return WeightRecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeightRecordViewHolder, position: Int) {
        val current = weightRecords[position]
        holder.weightRecordView.text = "on ${current.recordedAt}: ${current.weight} kg"
    }

    override fun getItemCount(): Int {
        return this.weightRecords.size
    }

    internal fun setWeightRecords(records: List<WeightRecord>) {
        this.weightRecords = records
        notifyDataSetChanged()
    }
}