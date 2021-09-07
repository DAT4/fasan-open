package sh.mama.fasanapp.mvvm

import sh.mama.fasanapp.api.Resource
import sh.mama.fasanapp.api.RetrofitInstance
import sh.mama.fasanapp.models.Token
import sh.mama.fasanapp.models.User
import retrofit2.Response

class UserRepository {
    suspend fun auth(user: User): Resource<Token> =
        handleResponse(RetrofitInstance.api.auth(user.username, user.password))

    private fun handleResponse(r: Response<Token>): Resource<Token> {
        if (r.isSuccessful) {
            r.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error("Not logged in")
    }

    suspend fun open(token: Token): Boolean =
        RetrofitInstance.api.open(token = "Bearer ${token.access_token}").isSuccessful
}