package com.kv.linkme.presentation.screens.usersScreen

import androidx.lifecycle.*
import com.kv.linkme.data.model.User
import com.kv.linkme.domain.repository.UserRepository
import com.kv.linkme.core.utils.isValidEmail
import kotlinx.coroutines.launch
import timber.log.Timber

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val allUsers: LiveData<List<User>> = repository.allUsers

    private val _userScreenState = MutableLiveData(UserScreenState())
    val userScreenState: LiveData<UserScreenState> = _userScreenState

    private fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
        updateState { it.copy(message = "User Inserted Successfully") }
    }

    private fun update(user: User) = viewModelScope.launch {
        Timber.tag("Update in Repo--->").d(user.toString())
        repository.update(user)
        updateState { it.copy(message = "User : ${user.name}, Updated Successfully") }
    }

    private fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
        updateState { it.copy(message = "User Deleted Successfully") }
    }

    fun onEvent(event: UserScreenEvents) {
        when (event) {
            UserScreenEvents.OnClearForm -> updateState { UserScreenState() }
            UserScreenEvents.OnSaveForm -> onValidateForm(type="Create")
            is UserScreenEvents.SetEmail -> updateState { it.copy(email = event.email) }
            is UserScreenEvents.SetEmailErr -> updateState { it.copy(emailErr = event.emailErr) }
            is UserScreenEvents.SetName -> updateState { it.copy(name = event.name) }
            is UserScreenEvents.SetNameErr -> updateState { it.copy(nameErr = event.nameErr) }
            is UserScreenEvents.OnDeleteUser -> {
                val currentState = _userScreenState.value ?: return
                Timber.tag("On Delete--->").d(currentState.onSetUser.toString())
                delete(user = currentState.onSetUser ?: return)

                onEvent(UserScreenEvents.OnClearForm)
                onEvent(UserScreenEvents.OnToggleButtons(isToggled = false))
            }

            is UserScreenEvents.OnUpdateUser -> {

                val currentState = _userScreenState.value ?: return
                val updatedState = currentState.copy(
                    name = currentState.name,
                    email = currentState.email,
                )
                updateState { updatedState }

                onValidateForm("Update")
            }

            is UserScreenEvents.OnToggleButtons -> updateState { it.copy(isToggled = event.isToggled) }

            is UserScreenEvents.OnSetUser -> {
                updateState { it.copy(onSetUser = event.onSetUser) }
                val currentState = _userScreenState.value ?: return
                val updatedState = currentState.copy(
                    name = event.onSetUser.name,
                    email = event.onSetUser.email,
                )
                updateState { updatedState }
            }

            is UserScreenEvents.OnSetMessage -> updateState { it.copy(message = event.message) }
        }
    }



    private fun onValidateForm(type:String) {

        val currentState = _userScreenState.value ?: return
        val updatedState = currentState.copy(
            nameErr = if (currentState.name.isEmpty()) "Enter name" else "",
            emailErr = when {
                currentState.email.isEmpty() -> "Enter email"
                !currentState.email.isValidEmail() -> "Invalid email format"
                else -> ""
            }
        )
        updateState { updatedState }

        if (updatedState.name.isNotEmpty() && updatedState.emailErr.isEmpty()) {
            val user = User(
                id = if (type == "Update") currentState.onSetUser?.id ?: 0 else 0,
                name = updatedState.name,
                email = updatedState.email
            )
            when(type){
                "Create" -> insert(user = user)
                "Update" -> update(user = user)
            }

            onEvent(UserScreenEvents.OnClearForm)
            onEvent(UserScreenEvents.OnToggleButtons(isToggled = false))
        }
    }


    private inline fun updateState(update: (UserScreenState) -> UserScreenState) {
        _userScreenState.value = update(_userScreenState.value ?: UserScreenState())
    }
}
