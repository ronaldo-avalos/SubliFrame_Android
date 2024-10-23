package com.coopertec.subliframe

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    private lateinit var addImage: Button
    private lateinit var imageView: ImageView
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
        setListeners()
        pickImageLauncherGallery()
    }

    private fun setListeners() {
        addImage.setOnClickListener {
            pickImageFromGallery()
        }

    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun pickImageLauncherGallery() {
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Aquí manejamos la imagen seleccionada
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                // Usa la URI de la imagen seleccionada (por ejemplo, mostrarla en un ImageView)
                imageUri?.let {
                   imageView.setImageURI(it)
                }
            }
        }
    }

//    //HANDLE PERMISSION
//    private fun checkStoragePermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION),
//                REQUEST_CODE_STORAGE_PERMISSION
//            )
//        } else {
//            // Permiso ya concedido, podemos abrir la galería
//            pickImageFromGallery()
//        }
//    }
//
//    // Manejar la respuesta de la solicitud de permisos
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permiso concedido, abre la galería
//                pickImageFromGallery()
//            } else {
//                // Permiso denegado, muestra un mensaje
//                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    private fun initUI() {
        addImage = findViewById(R.id.image_add_button)
        imageView = findViewById(R.id.imageView)
    }

}