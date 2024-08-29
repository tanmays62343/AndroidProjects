package com.trx.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.trx.R
import com.trx.databinding.ActivityEncryptionBinding

class Encryption : AppCompatActivity() {

    private lateinit var binding : ActivityEncryptionBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncryptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val encryptedString = "gAAAAABml44YgveYuDofAWiwNBLwcEoh_PNBCMN8lKT-FI9Z-__zlty-KRUq8FeulmUPvAD28ZL328VY3ygDXSpWzbN1k2iFkA=="
        val decryptedString = EncryptDecrypt.decrypt(encryptedString)

        Log.d("RBRB", decryptedString)*/

        val plainText = "Dr. Brian"

        /*val encryptedText = EncryptDecryptM.encrypt(plainText)
        val decryptedText = EncryptDecryptM.decrypt(encryptedText)*/

        val encryptedText = EncryptDecryptC.encrypt(plainText)
        val decryptedText = EncryptDecryptC.decrypt(encryptedText)

        Log.d("BRB", encryptedText)
        Log.d("BRB", decryptedText)

        binding.showEncryptedTxt.text = encryptedText
        binding.showDecryptedTxt.text = decryptedText

    }
}