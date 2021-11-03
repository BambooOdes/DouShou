package com.bamboo.doushou.adapter


import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bamboo.doushou.R
import com.bamboo.doushou.datarepository.Video
import com.bamboo.doushou.fragmrnt.AccountViewModel

class VideoCardAdapter(private val accountViewModel: AccountViewModel):RecyclerView.Adapter<VideoCardAdapter.MyViewHolder>(){
    var allVideos:List<Video> = listOf()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.titleText)
        val time:TextView = itemView.findViewById(R.id.timeText)
        val label:TextView = itemView.findViewById(R.id.labelText)
        val switch:Switch  = itemView.findViewById(R.id.selectSwitch)
        val image:ImageView = itemView.findViewById(R.id.videoImageView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.card_view,parent,false)
        val myViewHolder = MyViewHolder(viewItem)
        myViewHolder.switch.setOnCheckedChangeListener { _, isChecked ->
            val video = myViewHolder.itemView.getTag(R.id.key_for_videoAdapter) as Video
            video.selection = isChecked
            accountViewModel.updateVideos(video)
        }
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val video = allVideos.get(position)
        holder.label.text = video.label
        holder.time.text = video.time
        holder.title.text = video.title
        holder.itemView.setTag(R.id.key_for_videoAdapter,video)
        holder.switch.isChecked = video.selection!!
        holder.image.setImageBitmap(BitmapFactory.decodeFile(video.imagePath))
    }

    override fun getItemCount(): Int {
        Log.d("adapter",allVideos.size.toString())
        return allVideos.size
    }
}