package com.martynov.testbylifehack

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.martynov.testbylifehack.databinding.ActivityCompanyBinding
import com.martynov.testbylifehack.model.CompanyModel
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.io.IOException


class CompanyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCompanyBinding
    private var dialog: ProgressDialog? = null
    var company: CompanyModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textCoordinatesCompany.setOnClickListener(this@CompanyActivity)
        binding.textTelephoneCompany.setOnClickListener(this@CompanyActivity)
        binding.textSiteCompany.setOnClickListener(this@CompanyActivity)


    }

    override fun onStart() {
        super.onStart()
        val id = intent.extras?.getString("id")?.toInt()
        lifecycleScope.launch {
            dialog = ProgressDialog(this@CompanyActivity).apply {
                setMessage(getString(R.string.please_wait))
                setTitle(getString(R.string.loading_data))
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            try {
                val result = id?.let { App.repository.getCompayById(it) }
                dialog?.dismiss()
                if (result?.isSuccessful == true) {
                    company = result.body()?.get(0)
                    Log.d("My", company.toString())
                    binding.textNameCompany.text = company?.name
                    binding.textDescriptionCompany.text = company?.description
                    binding.imageView
                    company?.url?.let { loadImage(binding.imageView, it) }
                    val coordinator = "${company?.lat} : ${company?.lon}"
                    binding.textCoordinatesCompany.text = coordinator
                    binding.textSiteCompany.text = company?.www
                    binding.textTelephoneCompany.text = company?.phone
                }
            } catch (e: IOException) {

            }
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.textCoordinatesCompany -> {
                toast("${company?.lat} : ${company?.lon}")
            }
            R.id.textTelephoneCompany -> {
                toast("${company?.phone}")
            }
            R.id.textSiteCompany -> {
                if(company?.www?.let { isValidUrl(it) } == true){
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(company?.www))
                    startActivity(intent)
                }else{
                   toast(getString(R.string.not_a_valid_link))
                }
            }

        }
    }
}