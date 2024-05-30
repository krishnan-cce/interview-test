package com.kv.linkme.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kv.linkme.data.model.User

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewUserDataHolder() {
    val user = User(
        name = "Krishnan KV",
        email = "krishnanvenugopal707@gmail.com"
    )
    UserDataHolder(
        user = user,
        onDeleteUser = {},
        onEditUser = {}
    )
}

@Composable
fun UserDataHolder(
    user: User,
    onEditUser:(User)->Unit,
    onDeleteUser:(User)->Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 4.dp
    ) {
        Row (
            modifier= Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(9f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier ,
                    text = "Name : ${user.name}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    textAlign = TextAlign.Start,
                    modifier = Modifier ,
                    text = "Email : ${user.email}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.secondary
                )

            }
            Row(
                modifier = Modifier
            ) {
                IconButton(onClick = { onEditUser(user) }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription ="Edit",tint = MaterialTheme.colorScheme.primary )
                }

//                IconButton(onClick = { onDeleteUser(user) }) {
//                    Icon(imageVector = Icons.Filled.Delete, contentDescription ="Edit",tint = MaterialTheme.colorScheme.error )
//                }

            }
        }
    }
}