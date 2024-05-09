package inn.vivvvek.blogs.models

data class AuthenticatedUser(
    val uid: String,
    val name: String = "",
    val profilePic: String = "",
    val phoneNumber: String = "",
    val email: String = "",
)

data class SignInResult(
    val user: AuthenticatedUser?,
    val error: String?
)