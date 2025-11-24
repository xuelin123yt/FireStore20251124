package tw.edu.pu.csim.tcyang.myapplication

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class UserScoreRepository {
    val db = Firebase.firestore

    /*suspend fun addUser(userScore: UserScoreModel): String {
        return try {
            val userScoreWithTimestamp = hashMapOf(
                "user" to userScore.user,
                "score" to userScore.score,
                "timestamp" to Timestamp.now()
            )

            val documentReference = db.collection("UserScore")
                .add(userScoreWithTimestamp)
                .await()
            "新增資料成功！Document ID:\n${documentReference.id}"
        } catch (e: Exception) {
            "新增資料失敗：${e.message}"
        }
    }*/

    suspend fun updateUser(userScore: UserScoreModel): String {
        return try {
            val userScoreWithTimestamp = hashMapOf(
                "user" to userScore.user,
                "score" to userScore.score,
                "timestamp" to Timestamp.now()
            )

            db.collection("UserScore")
                .document(userScore.user)
                .set(userScoreWithTimestamp)
                .await()
            "新增/異動資料成功！Document ID:\n${userScore.user}"
        } catch (e: Exception) {
            "新增/異動資料失敗：${e.message}"
        }
    }

    /*suspend fun deleteUser(userScore: UserScoreModel): String {
        return try {
            val documentRef = db.collection("UserScore").document(userScore.user)
            val documentSnapshot = documentRef.get().await()

            if (documentSnapshot.exists()) {
                documentRef.delete().await()
                "刪除資料成功！Document ID: ${userScore.user}"
            } else {
                "刪除失敗：Document ID ${userScore.user} 不存在。"
            }
        } catch (e: Exception) {
            "刪除資料失敗：${e.message}"
        }
    }

    suspend fun getUserScoreByName(userScore: UserScoreModel): String {
        return try {
            val querySnapshot = db.collection("UserScore")
                .whereEqualTo("user", userScore.user)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first()
                val userScoreResult = document.toObject(UserScoreModel::class.java)
                "查詢成功！${userScoreResult?.user} 的分數是 ${userScoreResult?.score}"
            } else {
                "查詢失敗：找不到使用者 ${userScore.user} 的資料。"
            }
        } catch (e: Exception) {
            "查詢資料失敗：${e.message}"
        }
    }*/

    suspend fun orderByScore(): String {
        return try {
            val querySnapshot = db.collection("UserScore")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                "抱歉，資料庫目前無相關資料"
            } else {
                buildString {
                    append("查詢成功！分數由大到小排序為：\n\n")

                    querySnapshot.documents.forEachIndexed { index, document ->
                        val user = document.getString("user") ?: ""
                        val score = document.getLong("score")?.toInt() ?: 0
                        val timestamp = document.getTimestamp("timestamp")

                        append("第 ${index + 1} 名：$user 的分數為 $score\n")

                        if (timestamp != null) {
                            val date = timestamp.toDate()
                            // 修改時間格式為 Sun Nov 23 14:46:23 GMT+08:00 2025
                            val formatter = SimpleDateFormat(
                                "EEE MMM dd HH:mm:ss 'GMT+08:00' yyyy",
                                Locale.ENGLISH
                            )
                            formatter.timeZone = TimeZone.getTimeZone("GMT+8")

                            val formattedDate = formatter.format(date)
                            append("($formattedDate)\n")
                        }

                        append("\n")
                    }
                }
            }
        } catch (e: Exception) {
            "查詢資料失敗：${e.message}"
        }
    }
}