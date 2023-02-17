package com.binar.kos.view.ui.myroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.kos.databinding.ActivityMyRoomBinding
import com.binar.kos.utils.Status
import com.binar.kos.view.adapter.HistoryPenyewaAdapter
import com.binar.kos.view.adapter.MyRoomAdapter
import com.binar.kos.view.ui.add.AddRoomActivity
import com.binar.kos.view.ui.historyPenyewa.HistoryPenyewaActivity
import com.binar.kos.view.ui.logout.LogoutActivity
import com.binar.kos.view.ui.profile.ProfileActivity
import com.binar.kos.viewmodel.DatastoreViewModel
import com.binar.kos.viewmodel.LogoutViewModel
import com.binar.kos.viewmodel.RoomViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyRoomBinding
    private val dataStore: DatastoreViewModel by viewModel()
    private val logoutViewModel: LogoutViewModel by viewModel()
    private val roomViewModel: RoomViewModel by viewModel()

    private lateinit var myRoomAdapter: MyRoomAdapter


    private var userData = true
    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                getMyRoom()
            }
        }

        getUserdata()
        toAddRoom()
        toHome()
        toProfile()
        toHistory()
    }




    private fun getUserdata() {
        dataStore.getAccessToken().observe(this) { token ->
            if (!token.equals("default value")) {
                accessToken = token
                Log.d("token1",token)

                logoutViewModel.getUsedata(token).observe(this) { result ->
                    when (result.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            if (result.data!!.data!!.noTelepon == null) {
                                userData = false
                            }
                            if (result.data.data!!.bank == null) {
                                userData = false
                            }
                            if (result.data.data.gender == null) {
                                userData = false
                            }
                            if (result.data.data.kotaAsal == null) {
                                userData = false
                            }
                            if (result.data.data.noRekening == null) {
                                userData = false
                            }
                            if (result.data.data.profesi == null) {
                                userData = false
                            }
//                            if (result.data.data.tanggalKelahiran == null) {
//                                userData = false
//                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }



    private fun getMyRoom(){
        Log.d("token",accessToken)
        roomViewModel.getMyRoom(accessToken).observe(this) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.pbRv.visibility = View.VISIBLE
                    binding.cvAddRoom.visibility = View.GONE
                    binding.rvRoom.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.pbRv.visibility = View.GONE
                    binding.cvAddRoom.visibility = View.VISIBLE
                    binding.rvRoom.visibility = View.VISIBLE
                    myRoomAdapter =
                        MyRoomAdapter(result.data!!)
                    val linearLayoutManager = LinearLayoutManager(this)
                    binding.rvRoom.layoutManager = linearLayoutManager
                    binding.rvRoom.adapter = myRoomAdapter

                }
                Status.ERROR -> {
                    binding.pbRv.visibility = View.GONE
                    binding.cvAddRoom.visibility = View.VISIBLE

                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    private fun toHome() {
        binding.btnHome.setOnClickListener {
            finish()
        }
    }

    private fun toProfile() {
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, LogoutActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun toHistory(){
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryPenyewaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toAddRoom(){
        if(userData){
            binding.btnAddRoom.setOnClickListener {
                val intent = Intent(this, AddRoomActivity::class.java)
                startActivity(intent)
            }
        }else{
            Snackbar.make(binding.root,
                "Data profil belum terisi",
                Snackbar.LENGTH_LONG)
                .setAction("Isi data profile") {
                    val intent = Intent(this, ProfileActivity::class.java)
                    finish()
                    startActivity(intent)
                }.show()
        }
    }
}