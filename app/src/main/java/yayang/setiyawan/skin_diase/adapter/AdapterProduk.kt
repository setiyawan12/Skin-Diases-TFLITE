package yayang.setiyawan.skin_diase.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import yayang.setiyawan.skin_diase.R
import yayang.setiyawan.skin_diase.model.Produk

class AdapterProduk(var activity: Activity, var data:List<Produk>):RecyclerView.Adapter<AdapterProduk.Holder>(){
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk1,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image =data[position].image
        holder.tvNama.text = data[position].name
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imgProduk)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}