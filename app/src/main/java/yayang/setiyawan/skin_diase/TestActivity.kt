package yayang.setiyawan.skin_diase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.cameraonly.CameraOnlyConfig
import com.esafirm.imagepicker.features.registerImagePicker
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    private  var chooseImage:com.esafirm.imagepicker.model.Image?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        chooseImage()
    }

    private val imagePickerLauncher = registerImagePicker{
        chooseImage = if (it.size == 0) null else it[0]
        imageView.setImageURI(chooseImage?.uri)
    }

    private fun chooseImage(){
        btn_1.setOnClickListener {
            imagePickerLauncher.launch(CameraOnlyConfig())
        }
    }

}