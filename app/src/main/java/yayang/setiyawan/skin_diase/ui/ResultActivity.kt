package yayang.setiyawan.skin_diase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import yayang.setiyawan.skin_diase.R
import yayang.setiyawan.skin_diase.clasification.ModelClass
import yayang.setiyawan.skin_diase.data.DetectionResult
import yayang.setiyawan.skin_diase.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding:ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        extras?.let {
            val result = extras.getParcelable<DetectionResult>(EXTRA_DATA)
            if (result != null) {
                populateItem(result)
            }
        }

    }
    private fun populateItem(result: DetectionResult) {
        with(binding) {
            tvMessage.text = getMessage(result.title) as CharSequence?
            tvConfidence.text = result.confidence.toString()
            tvResult.text = result.title
            Glide.with(this@ResultActivity)
                .load(result.image)
                .into(imgInput)
        }
    }
    private fun getMessage(title: String): Any =
        if (title == ModelClass.label[0]) {
            "Unfortunately, You are"
        }else if (title == ModelClass.label[1]){
            "lorem"
        }else{
            "ipsumm"
        }
}