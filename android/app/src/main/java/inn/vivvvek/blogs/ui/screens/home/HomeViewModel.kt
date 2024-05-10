package inn.vivvvek.blogs.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import inn.vivvvek.blogs.auth.BlogAuth
import inn.vivvvek.blogs.models.AuthenticatedUser
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: BlogAuth
) : ViewModel() {
    val currentUser: AuthenticatedUser?
        get() = auth.loggedInUser

    val isLoggedIn: Boolean
        get() = auth.isLoggedIn
}