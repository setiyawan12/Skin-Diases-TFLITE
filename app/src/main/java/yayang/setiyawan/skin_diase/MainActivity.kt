package yayang.setiyawan.skin_diase

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.github.florent37.runtimepermission.kotlin.askPermission
import kotlinx.android.synthetic.main.activity_main.*
import yayang.setiyawan.skin_diase.clasification.Clasification
import yayang.setiyawan.skin_diase.data.DetectionResult
import yayang.setiyawan.skin_diase.databinding.ActivityMainBinding
import yayang.setiyawan.skin_diase.fragment.AkunFragment
import yayang.setiyawan.skin_diase.fragment.HistoryFragment
import yayang.setiyawan.skin_diase.fragment.HomeFragment
import yayang.setiyawan.skin_diase.fragment.NewsFragment
import yayang.setiyawan.skin_diase.helper.SharedPref
import yayang.setiyawan.skin_diase.ui.LoginActivity
import yayang.setiyawan.skin_diase.ui.ResultActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val CAMERA_REQUEST_CODE = 2
        private const val IMAGE_REQUEST_CODE=100
    }
    private lateinit var binding:ActivityMainBinding
    //buat variable Minput, Modelpath, mlabel path
    private val mInputSize = 224
    private val mModelPath = "model_ches.tflite"
    private val mLabelPath = "labels.txt"
    //
    private lateinit var clasification: Clasification

    private val rotateOpen: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.rotate_anim_open)}
    private val rotateClose: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.rotate_anim_close)}
    private val fromBottom: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy{AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim)}
    lateinit var sharedPref: SharedPref

    private var clicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)

        //dipanggil
        clasification = Clasification(assets,mModelPath,mLabelPath,mInputSize)
        askPermissions()
        val homeFragment = HomeFragment()
        val historyFragment = HistoryFragment()
        val newsFragment = NewsFragment()
        val akunFragment = AkunFragment()

        setCurrentFragment(homeFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nvHome ->setCurrentFragment(homeFragment)
                R.id.nvNews->setCurrentFragment(newsFragment)
                R.id.nvHistory->setCurrentFragment(historyFragment)
                R.id.nvProfile->{
                 if (sharedPref.getStatusLogin()){
                     setCurrentFragment(akunFragment)
                 }else{
                     startActivity(Intent(this,LoginActivity::class.java))
                 }
                }
            }
            true
        }

        fab.setOnClickListener {
            onAddButtonClicked()
        }
        fab1.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
        fab2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                CAMERA_REQUEST_CODE->{
                    val bitmap = data?.extras?.get("data") as Bitmap
                    val result = clasification.recognizeImage(bitmap)
                    val confidence = result[0].confidence * 100
                    val detectionResult = DetectionResult(
                        1,
                        bitmap,
                        result[0].title,
                        confidence.toInt()
                    )
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(ResultActivity.EXTRA_DATA, detectionResult)
                    startActivity(intent)
                }
//                IMAGE_REQUEST_CODE->{
//                    val bitmap = data?.extras?.get("data") as Bitmap
//                    val result = clasification.recognizeImage(bitmap)
//                    val confidence = result[0].confidence * 100
//                    val detectionResult = DetectionResult(
//                        1,
//                        bitmap,
//                        result[0].title,
//                        confidence.toInt()
//                    )
//                    val intent = Intent(this, ResultActivity::class.java)
//                    intent.putExtra(ResultActivity.EXTRA_DATA, detectionResult)
//                    startActivity(intent)
//                }
            }
        }


    }

    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
        setClickable(clicked)
    }

    private fun setVisibility(clicked:Boolean){
        if (clicked){
            fab1.visibility = View.VISIBLE
            fab2.visibility = View.VISIBLE
        }else{
            fab1.visibility = View.INVISIBLE
            fab2.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked:Boolean){
        if (!clicked){
            fab1.startAnimation(fromBottom)
            fab2.startAnimation(fromBottom)
            fab.startAnimation(rotateOpen)
        }else{
            fab1.startAnimation(toBottom)
            fab2.startAnimation(toBottom)
            fab.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean){
        if (!clicked){
            fab1.isClickable=false
            fab2.isClickable=false
        }else{
            fab1.isClickable=true
            fab2.isClickable=true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    private fun askPermissions(){
        askPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        ){

        }.onDeclined{e ->
            if (e.hasDenied()){
                e.denied.forEach{

                }

                AlertDialog.Builder(this)
                    .setMessage("Please Accept Our Permission")
                    .setPositiveButton("Yes"){_,_ ->
                        e.askAgain()
                    }
                    .setNegativeButton("No"){dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            if (e.hasForeverDenied()){
                e.foreverDenied.forEach {

                }
                e.goToSettings()
            }
        }
    }
}