package com.martynov.testbylifehack

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.martynov.testbylifehack.databinding.ActivityCompanyBinding
import com.martynov.testbylifehack.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.IOException

class CompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyBinding
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                    val company = result.body()?.get(0)
                    Log.d("My", company.toString())
                    binding.textNameCompany.text = company?.name
                    binding.textDescriptionCompany.text = company?.description
                    binding.imageView
                    company?.url?.let { loadImage(binding.imageView, it) }
                    val coordinator = "${company?.lat} : ${company?.lan}"
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
}