package info.jiuyou.codediy_sdk.api.base

import android.content.Context
import android.text.TextUtils
import info.jiuyou.codediy_sdk.api.base.config.OAuth
import info.jiuyou.codediy_sdk.api.base.config.URLConstant
import info.jiuyou.codediy_sdk.utils.TokenUtil
import info.jiuyou.codediy_sdk.utils.UUIDGenerator
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

/**
 * ==========================================
 * author ：jianshijiuyou@gmail.com
 * create date ：2017/7/18 0018  10:31
 * des ：
 * ==========================================
 */
open class BaseImpl<out Service>(context: Context) : AnkoLogger {
    protected val tokenUtil = TokenUtil(context)
    protected val mService: Service

    companion object {
        var mRetrofit: Retrofit? = null
    }

    init {
        initRetrofit()
        mService = mRetrofit!!.create(getServiceClass())
    }

    private fun getServiceClass(): Class<Service> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<Service>
    }

    private fun initRetrofit() {
        if (mRetrofit != null) {
            return
        }
        info("initRetrofit")
        // 设置 Log 拦截器，可以用于以后处理一些异常情况
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        // 为所有请求自动添加 token
        val mTokenInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()

            //如果token没有缓存直接返回
            if (tokenUtil.getToken() == null) {
                info("token没有缓存直接返回")
                return@Interceptor chain.proceed(originalRequest)
            }
            //如果已经携带token直接返回
            if (!TextUtils.isEmpty(originalRequest.header(OAuth.KEY_TOKEN))) {
                info("已经携带token直接返回")
                return@Interceptor chain.proceed(originalRequest)
            }
            //如果是请求token的链接，直接返回
            if (originalRequest.url().toString().contains(URLConstant.OAUTH_URL)) {
                info("是请求token的链接，直接返回")
                return@Interceptor chain.proceed(originalRequest)
            }

            //附加token
            info("附加token")
            val token = OAuth.TOKEN_PREFIX + tokenUtil.getToken()?.access_token
            val authorised = originalRequest.newBuilder().header(OAuth.KEY_TOKEN, token).build()
            chain.proceed(authorised)
        }

        //自动刷新token
        val mAuthenticator = Authenticator { _, response ->
            info("自动刷新 token 开始")
            val tokenService = mRetrofit!!.create(TokenService::class.java)
            var accessToken = ""
            try {
                val call = tokenService.refreshToken(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_REFRESH, tokenUtil.getToken()!!.access_token)
                val tokenResponse = call.execute()
                val token = tokenResponse.body()
                if (token != null) {
                    tokenUtil.saveToken(token)
                    accessToken = token.access_token

                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
            info("自动刷新 token 结束：$accessToken")
            response.request().newBuilder().header(OAuth.KEY_TOKEN, OAuth.TOKEN_PREFIX + accessToken).build()
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)                // 设置拦截器
                .retryOnConnectionFailure(true)             // 是否重试
                .connectTimeout(5, TimeUnit.SECONDS)        // 连接超时时间
                .readTimeout(5, TimeUnit.SECONDS)           // 读取超时时间
                .addNetworkInterceptor(mTokenInterceptor)   // 自动附加 token
                .authenticator(mAuthenticator)              // 认证失败自动刷新token
                .build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)                         // 设置 base url
                .client(client)                                     // 设置 client
                .addConverterFactory(GsonConverterFactory.create()) // 设置 Json 转换工具
                .build()
    }

    protected fun baseFun(block: (uuid:String) -> Unit): String {
        val uuid = UUIDGenerator.getUUID()
        block(uuid)
        return uuid
    }
}