package tw.edu.pu.csim.tcyang.firestore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.tcyang.myapplication.UserScoreModel
import tw.edu.pu.csim.tcyang.myapplication.UserScoreViewModel

@Composable
fun UserScoreScreen(userScoreViewModel: UserScoreViewModel = viewModel()) {
    var user by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // 添加滾動功能
        verticalArrangement = Arrangement.Top, // 改為從上方開始排列
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp)) // 頂部留空間
        // 輸入姓名
        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("姓名") },
            placeholder = { Text("請輸入您的姓名") },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 輸入分數
        TextField(
            value = score,
            onValueChange = { score = it },
            label = { Text("分數") },
            placeholder = { Text("請輸入分數") },
            modifier = Modifier.width(300.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 新增資料按鈕
       /* Button(
            onClick = {
                val userScore = UserScoreModel(
                    user = user.ifEmpty { "子青" },
                    score = score.toIntOrNull() ?: 0
                )
                userScoreViewModel.addUser(userScore)
            },
            modifier = Modifier.width(250.dp)
        ) {
            Text("新增資料")
        }*/

        Spacer(modifier = Modifier.height(8.dp))

        // 更新資料按鈕
        Button(
            onClick = {
                val userScore = UserScoreModel(
                    user = user.ifEmpty { "子青" },
                    score = score.toIntOrNull() ?: 0
                )
                userScoreViewModel.updateUser(userScore)
            },
            modifier = Modifier.width(250.dp)
        ) {
            Text("新增/異動資料")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 刪除資料按鈕
        /*Button(
            onClick = {
                val userScore = UserScoreModel(
                    user = user.ifEmpty { "子青" },
                    score = 0
                )
                userScoreViewModel.deleteUser(userScore)
            },
            modifier = Modifier.width(250.dp)
        ) {
            Text("刪除資料")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 查詢資料按鈕
        Button(
            onClick = {
                val userScore = UserScoreModel(
                    user = user.ifEmpty { "子青" },
                    score = 0
                )
                userScoreViewModel.getUserScoreByName(userScore)
            },
            modifier = Modifier.width(250.dp)
        ) {
            Text("查詢資料")
        }*/

        Spacer(modifier = Modifier.height(8.dp))

        // 排序查詢按鈕
        Button(
            onClick = {
                userScoreViewModel.orderByScore()
            },
            modifier = Modifier.width(250.dp)
        ) {
            Text("依分數排序（前3名）")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 顯示訊息 - 移除寬度限制，使用 padding
        Text(
            text = userScoreViewModel.message,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp)) // 底部留空間
    }
}