package com.samy.rick.data.datasorce

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {

    suspend fun register(name: String, email: String, password: String, age: Int): FirebaseUser? {
        Log.d("mos samy", "on AuthDataSource.register()")
        Log.d("mos samy", "name: ${name}, email: $email ,pass: $password, age: $age")
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnFailureListener {
                Log.d("mos samy", "on register falure : ${it.message}")
            }.addOnSuccessListener {
                Log.d("mos samy", "on register success : ${it.user}")
            }.await()
        result?.user?.let { user ->
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "age" to age
            )
            firestore.collection("users").document(user.uid).set(userData).await()
        }
        return result.user
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        Log.d("mos samy", "on AuthDataSource.register()")
        Log.d("mos samy", "email: $email ,pass: $password")
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            Log.d("mos samy", "on login falure : ${it.message}")
        }.addOnSuccessListener {
            Log.d("mos samy", "on login success : ${it.user}")
        }.await()
        return result.user

    }

}

