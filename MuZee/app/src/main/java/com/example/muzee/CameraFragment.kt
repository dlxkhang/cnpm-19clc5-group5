//package com.example.muzee
//import android.Manifest
//import android.app.Activity.RESULT_OK
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Bundle
//import android.os.Environment
//import android.provider.MediaStore
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.muzee.databinding.FragmentCameraBinding
//import io.fotoapparat.Fotoapparat
//import io.fotoapparat.log.loggers
//import io.fotoapparat.parameter.ScaleType
//import io.fotoapparat.selector.back
//import java.io.File
//
//
//
//import io.fotoapparat.log.logcat
//
//import io.fotoapparat.view.CameraRenderer
//
//class CameraFragment : Fragment() {
//    lateinit var imageView: ImageView
//    private val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//    private var binding: FragmentCameraBinding? = null
//    var fotoapparat: Fotoapparat? = null
//    val filename = "test.png"
//    val sd = Environment.getExternalStorageDirectory()
//    val dest = File(sd, filename)
//    private val pickImage = 100
//    private var imageUri: Uri? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val activity = activity as AppCompatActivity?
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        val fragmentbinding = FragmentCameraBinding.inflate(inflater,container,false)
//        binding = fragmentbinding
//        createFotoapparat()
//
//
//        binding?.fabCamera?.setOnClickListener {
//            takePhoto()
//        }
//        binding?.fabGallery?.setOnClickListener{
//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, pickImage)
//        }
//        return fragmentbinding.root
//    }
//    private fun hasNoPermissions(): Boolean{
//        return ContextCompat.checkSelfPermission(requireActivity(),
//            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(),
//            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(),
//            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//    }
//
//    fun requestPermission(){
//        ActivityCompat.requestPermissions(requireActivity(), permissions,0)
//    }
//    private fun createFotoapparat(){
//        val cameraView = binding?.cameraView as CameraRenderer
//        fotoapparat = Fotoapparat(
//            context = requireContext(),
//            view = cameraView,
//            scaleType = ScaleType.CenterCrop,
//            lensPosition = back(),
//            logger = loggers(
//                logcat()
//            ),
//            cameraErrorCallback = { error ->
//                println("Recorder errors: $error")
//            }
//        )
//    }
//
//    private fun takePhoto() {
//        if (hasNoPermissions()) {
//            requestPermission()
//        }else{
//            fotoapparat
//                ?.takePicture()
//                ?.saveToFile(dest)
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (hasNoPermissions()) {
//            requestPermission()
//        }else{
//            fotoapparat?.start()
//        }
//    }
//
//
//    override fun onStop() {
//        super.onStop()
//        fotoapparat?.stop()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == pickImage) {
//            imageUri = data?.data
//
//            imageView.setImageURI(imageUri)
//        }
//    }
//
//}