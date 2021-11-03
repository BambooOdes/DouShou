package com.bamboo.doushou.fragmrnt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bamboo.doushou.R
import com.bamboo.doushou.adapter.VideoCardAdapter
import com.bamboo.doushou.datarepository.Video
import kotlinx.android.synthetic.main.fragment_account.*


const val REQUEST_WRITE_EXTERNAL_STORAGE = 1
const val CHOOSE_PHOTO = 0
class AccountFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel
    private lateinit var videoList:LiveData<List<Video>>
    private lateinit var myAdapter:VideoCardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        myAdapter = VideoCardAdapter(viewModel)
        recyclerView.adapter = myAdapter
        videoList = viewModel.allVideo
        videoList.observe(viewLifecycleOwner){
            val temp = myAdapter.itemCount
            myAdapter.allVideos = it
            if(it.size != temp){
                myAdapter.notifyDataSetChanged()
            }
        }


        floatingActionButton.setOnClickListener {
            if(Build.VERSION.SDK_INT < 29 && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_WRITE_EXTERNAL_STORAGE)
            }
            else{
                //recordVideo()
            }
        }
    }

}
/*
 override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_WRITE_EXTERNAL_STORAGE->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    recordVideo()
                }
                else{
                    Toast.makeText(requireContext(),"权限未获取",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun recordVideo(){
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.type = "video/*";
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CHOOSE_PHOTO&&data!=null&&resultCode == RESULT_OK)
        {
            val uri = data.data
            val  path = getRealPathFromUri(uri!!)
            val file = File(path)
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(file.absolutePath)
            val bitmap = mmr.frameAtTime
        }
    }
    fun getRealPathFromUri(uri: Uri):String?{
        var res:String? = null
        val prj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri,prj,null,null,null)
        cursor?.let {
            if(it.moveToFirst()){
                res = it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            it.close()
        }
        return  res
    }
 */