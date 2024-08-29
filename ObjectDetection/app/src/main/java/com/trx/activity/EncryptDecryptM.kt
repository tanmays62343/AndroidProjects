package com.trx.activity

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.GCMParameterSpec

object EncryptDecryptM {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_LENGTH = 16
    private const val GCM_IV_LENGTH = 12

    // Static key (ensure it's 16, 24, or 32 bytes long for AES)
    private const val KEY = "0123456789abcdef0123456789abcdef"

    // Function to get the SecretKey from the static key
    private fun getKey(): SecretKey {
        val decodedKey = Base64.decode(KEY, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, ALGORITHM)
    }

    // Decrypt function
    fun decrypt(encryptedData: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)

        // Extract IV and cipher text
        val iv = decodedData.copyOfRange(0, GCM_IV_LENGTH)
        val cipherText = decodedData.copyOfRange(GCM_IV_LENGTH, decodedData.size)

        // Initialize cipher with the GCM spec
        val spec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)

        // Decrypt the data
        val decryptedBytes = cipher.doFinal(cipherText)
        return String(decryptedBytes)
    }

    // Encrypt function
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = ByteArray(GCM_IV_LENGTH)
        java.security.SecureRandom().nextBytes(iv)

        // Initialize cipher with the GCM spec
        val spec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), spec)

        // Encrypt the data
        val cipherText = cipher.doFinal(data.toByteArray())
        // Combine IV and cipher text
        val encryptedBytes = iv + cipherText

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

}