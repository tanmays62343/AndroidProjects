package com.trx.activity

import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptDecryptC {

    private const val AES_MODE = "AES/CBC/PKCS7Padding"
    private const val KEY = "mY9Ns7wT5b9X1cE8g3R4qW7lA0fV2tK6"

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        val secretKeySpec = SecretKeySpec(KEY.toByteArray(), "AES")
        val iv = ByteArray(cipher.blockSize)
        SecureRandom().nextBytes(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(iv))
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(iv + encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        val secretKeySpec = SecretKeySpec(KEY.toByteArray(), "AES")
        val dataBytes = Base64.decode(data, Base64.DEFAULT)
        val iv = dataBytes.copyOfRange(0, cipher.blockSize)
        val encryptedBytes = dataBytes.copyOfRange(cipher.blockSize, dataBytes.size)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(iv))
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

}