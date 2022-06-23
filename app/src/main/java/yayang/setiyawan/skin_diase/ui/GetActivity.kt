package yayang.setiyawan.skin_diase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_get.*
import yayang.setiyawan.skin_diase.R
import yayang.setiyawan.skin_diase.adapter.AdapterProduk
import yayang.setiyawan.skin_diase.contract.ProductContract
import yayang.setiyawan.skin_diase.model.Produk
import yayang.setiyawan.skin_diase.presenter.ProductPresenter

class GetActivity : AppCompatActivity(),ProductContract.View {
    private lateinit var presenter:ProductContract.Presenter
    lateinit var adapterProduk: AdapterProduk
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)
        presenter = ProductPresenter(this)
        presenter?.getAllProduct()
    }

    override fun attachToRecycler(listProduk: List<Produk>) {
        rv_product?.apply {
            adapterProduk = AdapterProduk(this@GetActivity,listProduk)
            adapter = adapterProduk
            val linearLayoutManager = LinearLayoutManager(this@GetActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun emptydata(status: Boolean) {
    }

    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter?.getAllProduct()
    }
}