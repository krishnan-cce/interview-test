package com.kv.linkme.presentation.screens.usersScreen

import com.kv.linkme.data.model.User

sealed interface UserScreenEvents {
    data class SetName(val name: String): UserScreenEvents
    data class SetNameErr(val nameErr: String): UserScreenEvents
    data class SetEmail(val email: String): UserScreenEvents
    data class SetEmailErr(val emailErr: String): UserScreenEvents
    data object OnDeleteUser: UserScreenEvents
    data object OnUpdateUser: UserScreenEvents
    data object OnClearForm: UserScreenEvents
    data object OnSaveForm: UserScreenEvents
    data class OnToggleButtons(val isToggled: Boolean): UserScreenEvents
    data class OnSetUser(val onSetUser: User): UserScreenEvents
    data class OnSetMessage(val message: String): UserScreenEvents
}

data class UserScreenState(
    val name: String = "",
    val nameErr: String = "",
    val email: String = "",
    val emailErr: String = "",
    val isToggled: Boolean=false,
    val onSetUser: User?=null,
    val message: String=""

)