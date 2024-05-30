package com.kv.linkme.presentation.screens.usersScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kv.linkme.presentation.components.UserDataHolder
import com.kv.linkme.presentation.components.UsersScreenAppbar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun UsersScreen() {
    val scope = rememberCoroutineScope()
    val viewModel: UserViewModel = koinViewModel()
    val formState by viewModel.userScreenState.observeAsState(UserScreenState())
    val users by viewModel.allUsers.observeAsState(emptyList())
    val onEvent = viewModel::onEvent

    val (nameFocusRequester, emailFocusRequester) = remember { FocusRequester.createRefs() }
    val snackBarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lazyListState = rememberLazyListState()

    if (formState.message.isNotBlank()) {
        LaunchedEffect(formState.message) {
            scope.launch {
                snackBarHostState.showSnackbar(formState.message)
            }
        }
    }


    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        if (lazyListState.firstVisibleItemIndex > 0 ) {
            keyboardController?.hide()
            focusManager.clearFocus()

        }
    }



//    key(users.hashCode()) {

        Scaffold(
            topBar = { UsersScreenAppbar() },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(8.dp)
                    .fillMaxSize()
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = lazyListState
            ) {
                item {
                    OutlinedTextField(
                        value = formState.name,
                        onValueChange = { onEvent(UserScreenEvents.SetName(it)) },
                        supportingText = { Text(text = formState.nameErr) },
                        isError = formState.nameErr.isNotEmpty(),
                        label = { Text(text = "Name") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(nameFocusRequester)
                    )

                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = { onEvent(UserScreenEvents.SetEmail(it)) },
                        supportingText = { Text(text = formState.emailErr) },
                        isError = formState.emailErr.isNotEmpty(),
                        label = { Text(text = "Email") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedButton(
                            modifier = Modifier.width(200.dp),
                            onClick = {
                                if (formState.isToggled) {
                                    onEvent(UserScreenEvents.OnUpdateUser)
                                    keyboardController?.hide()
                                } else {
                                    onEvent(UserScreenEvents.OnClearForm)
                                }
                            }
                        ) {
                            Text(
                                text = if (formState.isToggled) "Update" else "Clear",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                            )
                        }

                        Button(
                            modifier = Modifier.width(200.dp),
                            onClick = {
                                if (formState.isToggled) {
                                    onEvent(UserScreenEvents.OnDeleteUser)
                                } else {
                                    onEvent(UserScreenEvents.OnSaveForm)
                                    keyboardController?.hide()
                                }
                            }
                        ) {
                            Text(
                                text = if (formState.isToggled) "Delete" else "Save",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                stickyHeader(
                    key = "STICKY_HEADER"
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(12.dp),
                            text = "SAVE DETAILS",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                items(
                    count = users.size,
                    key = { users[it].id }
                ) { index ->
                    val data = users[index]
                    UserDataHolder(
                        user = data,
                        onDeleteUser = {
                            //onEvent(UserScreenEvents.OnDeleteUser(it))
                            //onEvent(UserScreenEvents.OnToggleButtons(true))
                        },
                        onEditUser = {
                            onEvent(UserScreenEvents.OnSetUser(it))
                            onEvent(UserScreenEvents.OnToggleButtons(true))
                        }
                    )
                }
            }
//        }
    }
}

