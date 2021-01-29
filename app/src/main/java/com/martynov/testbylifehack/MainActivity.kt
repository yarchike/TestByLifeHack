package com.martynov.testbylifehack

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.martynov.testbylifehack.adapter.CompanyAdapter
import com.martynov.testbylifehack.databinding.ActivityMainBinding
import com.martynov.testbylifehack.dto.CompanyRequest
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.io.IOException

class MainActivity : AppCompatActivity(), CompanyAdapter.OnBtnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var dialog: ProgressDialog? = null
    var iteams = ArrayList<CompanyRequest>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            dialog = ProgressDialog(this@MainActivity).apply {
                setMessage(getString(R.string.please_wait))
                setTitle(getString(R.string.loading_data))
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            try {
                val result = App.repository.getCompanyList()
                dialog?.dismiss()
                if (result.isSuccessful) {
                    with(binding.recyclerCompany) {
                        iteams = result.body() as ArrayList<CompanyRequest>
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = CompanyAdapter(iteams).apply {
                            btnClickListener = this@MainActivity
                        }
                    }

                } else {
                    toast(R.string.error_occured)
                }
            } catch (e: IOException) {
                dialog?.dismiss()
                toast(R.string.falien_connect)
            }
        }
    }

    override fun onBtnClicked(item: CompanyRequest) {
        val intent = Intent(this, CompanyActivity::class.java)
        intent.putExtra("id", item.id.toString())
        startActivity(intent)

    }

}