package com.trx.activity

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.GCMParameterSpec

object EncryptDecrypt {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_LENGTH = 16
    private const val GCM_IV_LENGTH = 12

    // Original key string
    private const val KEY = "b'8lP6JyR1MZrSk_evM5gjn1q0tjN3lER0tqBGwu5aeJE='"

    private fun getKey(): SecretKey {
        // Remove the "b'" and "'" to get the raw Base64 key
        val cleanKey = KEY.removePrefix("b'").removeSuffix("'")
        val decodedKey = Base64.decode(cleanKey, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, ALGORITHM)
    }

    fun decrypt(encryptedData: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)

        val iv = decodedData.copyOfRange(0, GCM_IV_LENGTH)
        val cipherText = decodedData.copyOfRange(GCM_IV_LENGTH, decodedData.size)

        val spec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)

        val decryptedBytes = cipher.doFinal(cipherText)
        return String(decryptedBytes)
    }

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = ByteArray(GCM_IV_LENGTH)
        java.security.SecureRandom().nextBytes(iv)

        val spec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), spec)

        val cipherText = cipher.doFinal(data.toByteArray())
        val encryptedBytes = iv + cipherText

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

}