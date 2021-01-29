package com.martynov.testbylifehack.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.martynov.testbylifehack.R
import com.martynov.testbylifehack.dto.CompanyRequest

@Suppress("UNREACHABLE_CODE")
class CompanyAdapter(val listCompany: ArrayList<CompanyRequest>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var btnClickListener: OnBtnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val companyView =
            LayoutInflater.from(parent.context).inflate(R.layout.company_iteam, parent, false)
        return CompanyViewHolder(this, companyView)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val CompanyIndex = position
        when (holder) {
            is CompanyViewHolder -> holder.bind(listCompany[CompanyIndex])
        }
        holder.itemView.setOnClickListener {
            this.btnClickListener?.onBtnClicked(listCompany.get(position))
        }

    }

    override fun getItemCount(): Int {
        return listCompany.size
    }
    interface OnBtnClickListener {
        fun onBtnClicked(item: CompanyRequest)
    }


    inner class CompanyViewHolder(val adapter: CompanyAdapter, view: View) :
        RecyclerView.ViewHolder(view) {


        fun bind(company: CompanyRequest) {
            with(itemView) {
                val imageCompany = findViewById<ImageView>(R.id.imageCompany)
                val textCompany = findViewById<TextView>(R.id.textCompany)
                textCompany.text = company.name
                loadImage(imageCompany, company.url)

            }

        }

        private fun loadImage(photoImg: ImageView, imageUrl: String) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.error_image)
            Glide.with(photoImg.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .into(photoImg)
        }
    }
}