package com.qiuchenly.comicx.UI.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qiuchenly.comicx.ProductModules.Bika.BikaApi
import com.qiuchenly.comicx.ProductModules.Bika.requests.SignInBody
import com.qiuchenly.comicx.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicx.ProductModules.Bika.responses.SignInResponse
import com.qiuchenly.comicx.UI.BaseImp.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : BaseModel() {
    data class LoginBean(val username: String, val password: String, val token: String)

    private var loginSuccessData = MutableLiveData<LoginBean>()
    var loginData: LiveData<LoginBean> = loginSuccessData

    fun loginBika(userName: String, pass: String) {
        BikaApi.getAPI()
            ?.signIn(SignInBody(userName, pass))
            ?.enqueue(object : Callback<GeneralResponse<SignInResponse>> {
                override fun onFailure(call: Call<GeneralResponse<SignInResponse>>, t: Throwable) {
                    setError("访问哔咔服务器失败。")
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<GeneralResponse<SignInResponse>>,
                    response: Response<GeneralResponse<SignInResponse>>
                ) {
                    if (response.code() == 200 && !response.body()?.data?.token.isNullOrEmpty()) {
                        loginSuccessData.value = LoginBean(userName, pass, response.body()?.data?.token!!)
                    } else {
                        setError(response.errorBody()?.string() ?: "登录Bika失败!请检查账号密码!")
                    }
                }
            })
    }
}